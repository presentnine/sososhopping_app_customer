package com.sososhopping.customer.shop.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.customer.databinding.ShopItemListBinding;
import com.sososhopping.customer.shop.view.adapter.ShopItemAdapter;
import com.sososhopping.customer.shop.model.ShopItemModel;

import java.util.ArrayList;

public class ShopItemListFragment extends Fragment {
    private NavController navController;
    private ShopItemAdapter shopItemAdapter = new ShopItemAdapter();
    private ShopItemListBinding binding;

    public static ShopItemListFragment newInstance(){return new ShopItemListFragment();}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ShopItemListBinding.inflate(inflater, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recyclerView.setLayoutManager(layoutManager);

        //Adapter 연결
        ArrayList<ShopItemModel> dummyItems = new ArrayList<>();
        dummyItems.add(new ShopItemModel(1, "가상의 상품", "가상의 상품입니다 \n두 줄도 확인해보겠습니다.", "1개", 4500, null, true));
        dummyItems.add(new ShopItemModel(2, "가상의 사과", "가상의 상품입니다 \n두 줄도 확인해보겠습니다.", "1통", 10000, null, false));
        dummyItems.add(new ShopItemModel(3, "가상의 복숭아", "가상의 상품입니다 \n두 줄도 확인해보겠습니다. \n세 줄도 확인해보겠습니다.", "1박스", 6000, null, true));
        dummyItems.add(new ShopItemModel(4, "가상의 호두", "가상의 상품입니다 \n두 줄도 확인해보겠습니다. \n세 줄도 확인해보겠습니다. \n네 줄도 확인해보겠습니다.", "1개", 4500, null, true));
        shopItemAdapter.setShopItemModels(dummyItems);
        binding.recyclerView.setAdapter(shopItemAdapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
