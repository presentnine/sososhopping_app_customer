package com.sososhopping.customer.cart.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.customer.MainActivity;
import com.sososhopping.customer.R;
import com.sososhopping.customer.cart.dto.CartDto;
import com.sososhopping.customer.cart.dto.CartItemDto;
import com.sososhopping.customer.cart.dto.CartStoreDto;
import com.sososhopping.customer.cart.dto.PurchaseDto;
import com.sososhopping.customer.cart.view.adapter.CartStoreAdapter;
import com.sososhopping.customer.cart.view.adapter.OnItemClickListenerCartStore;
import com.sososhopping.customer.cart.viewmodel.CartViewModel;
import com.sososhopping.customer.databinding.CartMainBinding;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class CartMainFragment extends Fragment {

    CartMainBinding binding;
    NavController navController;
    CartStoreAdapter cartStoreAdapter;
    CartViewModel cartViewModel;
    public static CartMainFragment newInstance() {return new CartMainFragment();}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //메뉴 변경 확인
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_top_none, menu);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        //binding 설정
        binding = CartMainBinding.inflate(inflater, container, false);

        //Viewmodel
        cartViewModel = new CartViewModel();

        binding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        cartStoreAdapter = new CartStoreAdapter();
        binding.recyclerViewCart.setAdapter(cartStoreAdapter);

        cartStoreAdapter.setOnItemClickListener(new OnItemClickListenerCartStore() {
            @Override
            public void onButtonPurchaseClick(CartStoreDto cartStoreDto, ArrayList<PurchaseDto> purchaseList) {

            }

            @Override
            public void onButtonStoreClick(int storeId) {
                navController.navigate(CartMainFragmentDirections.actionCartMainFragmentToShopGraph(storeId));
            }

            @Override
            public void itemDelete(int storePos, int itemPos, CartItemDto cartItemDto) {
                //삭제 API 호출
                onDeleteSuccess(storePos, itemPos);
            }

            @Override
            public void itemCountChanged(int val) {
                cartViewModel.setTotalPrice(cartViewModel.getTotalPrice() + val);
                binding.textViewTotalStorePrice.setText(cartViewModel.getTotalPrice() + "원");
            }
        });

        onSuccess(dummyData());


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Controller 설정
        navController = Navigation.findNavController(view);

    }

    @Override
    public void onResume() {
        //상단바
        ((MainActivity)getActivity()).showTopAppBar();
        ((MainActivity)getActivity()).getBinding().topAppBar.setTitle(getResources().getString(R.string.mysoso_shoppingBag));

        //하단바
        super.onResume();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onSuccess(CartDto cartDto){
        cartViewModel.setStores(cartDto.getCartList());
        cartStoreAdapter.setCartstores(cartDto.getCartList());
        cartStoreAdapter.notifyDataSetChanged();

        binding.textViewTotalStore.setText(cartViewModel.calTotalStore()+"개 매장");
        binding.textViewTotalStorePrice.setText(cartViewModel.calTotalPrice()+"원");
    }

    public void onDeleteSuccess(int storepos, int itempos){
        CartStoreDto dto = cartViewModel.getStores().get(storepos);
        dto.getCartItems().remove(itempos);

        if(cartViewModel.getStores().get(storepos).getCartItems().size() <= 0){
            cartViewModel.getStores().remove(storepos);
            cartStoreAdapter.notifyItemRemoved(storepos);
            binding.textViewTotalStore.setText(cartViewModel.calTotalStore()+"개 매장");
        }
        else{
            //금액계산도 다 다시
            cartViewModel.calTotalStorePrice(storepos);
            cartStoreAdapter.notifyItemChanged(storepos);
        }
        binding.textViewTotalStorePrice.setText(cartViewModel.calTotalPrice()+"원");
    }

    public CartDto dummyData(){
        CartDto c = new CartDto();

        for (int i=1; i<5; i++){
            c.getCartList().add(new CartStoreDto(i,"매장"+i,new ArrayList<>(), 10000));
            for(int j=1; j<5; j++){
                c.getCartList().get(i-1).getCartItems().add(new CartItemDto(j,"상품"+j,"설명",null,1000,true, j,true));
            }
        }

        return c;
    }

}
