package com.sososhopping.customer.shop.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.ShopItemListBinding;
import com.sososhopping.customer.shop.dto.ItemListDto;
import com.sososhopping.customer.shop.view.adapter.ShopItemAdapter;
import com.sososhopping.customer.shop.model.ShopItemModel;
import com.sososhopping.customer.shop.viewmodel.ShopInfoViewModel;
import com.sososhopping.customer.shop.viewmodel.ShopItemViewModel;

import java.util.ArrayList;

public class ShopItemListFragment extends Fragment {
    private NavController navController;
    private ShopItemAdapter shopItemAdapter = new ShopItemAdapter();
    private ShopItemViewModel shopItemViewModel;
    private ShopItemListBinding binding;

    public static ShopItemListFragment newInstance(){return new ShopItemListFragment();}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ShopItemListBinding.inflate(inflater, container, false);

        //부모레벨
        shopItemViewModel = new ViewModelProvider(getParentFragment().getParentFragment()).get(ShopItemViewModel.class);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);


        //상품은 Create에서 로딩
        int storeId = new ViewModelProvider(getActivity()).get(ShopInfoViewModel.class).getShopId().getValue();
        shopItemViewModel.requestShopItem(storeId,
                this::onSuccess,
                this::onFailed,
                this::onNetworkError);
        binding.recyclerView.setAdapter(shopItemAdapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        shopItemAdapter.setOnItemClickListener(new ShopItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //해당 물품의 상세정보 확인
            }
            @Override
            public void onItemAdd(View v, int pos, int num) {
                //장바구니 담기 API
            }
        });
    }

    @Override
    public void onResume() {
        shopItemAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onSuccess(ItemListDto itemListDto){
        shopItemViewModel.getShopItem().setValue(itemListDto.getShopItemModels());
        shopItemAdapter.setShopItemModels(shopItemViewModel.getShopItem().getValue());
        shopItemAdapter.notifyDataSetChanged();
    }

    private void onFailed() {
        Toast.makeText(getContext(),getResources().getString(R.string.shop_error), Toast.LENGTH_LONG).show();
    }

    private void onNetworkError() {
        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_global_networkErrorDialog);
    }
}
