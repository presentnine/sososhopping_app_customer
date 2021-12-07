package com.sososhopping.customer.purchase.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.customer.HomeActivity;
import com.sososhopping.customer.NavGraphDirections;
import com.sososhopping.customer.R;
import com.sososhopping.customer.purchase.dto.CartDto;
import com.sososhopping.customer.purchase.dto.CartItemDto;
import com.sososhopping.customer.purchase.dto.CartStoreDto;
import com.sososhopping.customer.purchase.dto.CartUpdateDto;
import com.sososhopping.customer.purchase.view.adapter.CartStoreAdapter;
import com.sososhopping.customer.purchase.view.adapter.OnItemClickListenerCartStore;
import com.sososhopping.customer.purchase.viewmodel.CartViewModel;
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

        //로그인 안되면 못하게
        if(!((HomeActivity)getActivity()).getIsLogIn()){
            ((HomeActivity)getActivity()).bottomItemClicked(R.id.home2);
            NavHostFragment.findNavController(this)
                    .navigate(NavGraphDirections.actionGlobalLogInRequiredDialog().setErrorMsgId(R.string.login_error_description));
        }

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
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        binding.recyclerViewCart.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        cartStoreAdapter = new CartStoreAdapter();
        binding.recyclerViewCart.setAdapter(cartStoreAdapter);

        cartStoreAdapter.setOnItemClickListener(new OnItemClickListenerCartStore() {
            @Override
            public void onButtonPurchaseClick(CartStoreDto cartStoreDto, ArrayList<CartUpdateDto> purchaseList) {
                navController.navigate(CartMainFragmentDirections.actionCartMainFragmentToPurchaseFragment(
                        cartViewModel.getPurchaseList(cartStoreDto),
                        cartStoreDto.getStoreId()
                ));
            }

            @Override
            public void onButtonStoreClick(int storeId) {
                navController.navigate(CartMainFragmentDirections.actionCartMainFragmentToShopGraph(storeId));
            }

            @Override
            public void itemDelete(int storePos, int itemPos, CartItemDto cartItemDto) {
                int itemId = cartStoreAdapter.getCartstores().get(storePos).getCartItems().get(itemPos).getItemId();
                cartViewModel.requestDelete(((HomeActivity)getActivity()).getLoginToken(), itemId, storePos, itemPos,
                        CartMainFragment.this::onDeleteSuccess,
                        CartMainFragment.this::onFailed,
                        CartMainFragment.this::onNetworkError);
            }

            @Override
            public void itemCountChanged(int val) {
                cartViewModel.getTotalPrice().setValue(cartViewModel.getTotalPrice().getValue() + val);
                binding.textViewTotalStorePrice.setText(cartViewModel.getTotalPrice().getValue() + "원");
            }
        });

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
        ((HomeActivity)getActivity()).showTopAppBar();
        ((HomeActivity)getActivity()).getBinding().topAppBar.setTitle(getResources().getString(R.string.mysoso_shoppingBag));
        ((HomeActivity)getActivity()).getBinding().topAppBar.setTitleCentered(true);
        ((HomeActivity)getActivity()).getBinding().topAppBar.setOnClickListener(null);
        ((HomeActivity)getActivity()).setTopAppBarNotHome(false);

        cartViewModel.requestMyCart(((HomeActivity)getActivity()).getLoginToken(),
                this::onSuccess,
                this::onFailedLogIn,
                this::onNetworkError);

        //하단바
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();

        //장바구니 목록 전부 보내주기 -> 업데이트용
        cartViewModel.updateItem(
                ((HomeActivity)getActivity()).getLoginToken(),
                cartViewModel.getCartList());
        Log.i("보내기", cartViewModel.getCartList().toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onSuccess(CartDto cartDto){
        cartViewModel.getStores().setValue(cartDto.getCartList());
        cartStoreAdapter.setCartstores(cartDto.getCartList());
        cartStoreAdapter.notifyDataSetChanged();

        binding.textViewTotalStore.setText(cartViewModel.calTotalStore()+"개 매장");
        binding.textViewTotalStorePrice.setText(cartViewModel.calTotalPrice()+"원");
    }


    private void onFailed(){
        Toast.makeText(getContext(),getResources().getString(R.string.item_deleteCart_error), Toast.LENGTH_LONG).show();
    }

    private void onFailedLogIn(){
        NavHostFragment.findNavController(this)
                .navigate(NavGraphDirections.actionGlobalLogInRequiredDialog().setErrorMsgId(R.string.login_error_token));
    }

    private void onNetworkError() {
        getActivity().onBackPressed();
        NavHostFragment.findNavController(this).navigate(R.id.action_global_networkErrorDialog);
    }



    public void onDeleteSuccess(int storepos, int itempos){
        CartStoreDto dto = cartViewModel.getStores().getValue().get(storepos);
        dto.getCartItems().remove(itempos);

        if(cartViewModel.getStores().getValue().get(storepos).getCartItems().size() <= 0){
            cartViewModel.getStores().getValue().remove(storepos);
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
}
