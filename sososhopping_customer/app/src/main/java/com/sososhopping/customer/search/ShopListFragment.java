package com.sososhopping.customer.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.SearchShopListBinding;

public class ShopListFragment extends Fragment {

    private NavController navController;
    private ShopListAdapter shopListAdapter = new ShopListAdapter();
    public SearchShopListBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        binding = DataBindingUtil.inflate(inflater, R.layout.search_shop_list, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recyclerViewShopList.setLayoutManager(layoutManager);

        //Adapter 연결
        return binding.getRoot();
    }

}