package com.sososhopping.customer.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sososhopping.customer.MainActivity;
import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.SearchShopListBinding;
import com.sososhopping.customer.home.HomeViewModel;
import com.sososhopping.customer.search.ShopInfoShort;
import com.sososhopping.customer.search.ShopViewModel;
import com.sososhopping.customer.search.view.adapter.ShopListAdapter;

import java.util.ArrayList;

public class ShopListFragment extends Fragment {

    private NavController navController;
    private ShopListAdapter shopListAdapter = new ShopListAdapter();
    private ShopViewModel shopViewModel;
    private HomeViewModel homeViewModel;
    public SearchShopListBinding binding;

    public static ShopListFragment newInstance() {return new ShopListFragment();}

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_top_search, menu);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        binding = SearchShopListBinding.inflate(inflater, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recyclerViewShopList.setLayoutManager(layoutManager);

        //Adapter 연결
        ArrayList<ShopInfoShort> dummyShops = new ArrayList<>();
        dummyShops.add(new ShopInfoShort(1, "가상의 상점",
                "가상의 상점 입니다 \n 이 상점은 가상의 상점입니다. 줄바꿈이 잘 되나 확인해보겠습니다.",
                4.5, true, true, 500, null, true));
        dummyShops.add(new ShopInfoShort(2, "가상의 상점2",
                "가상의 상점 입니다 \n 이 상점은 가상의 상점2입니다. 줄바꿈이 잘 되나 확인해보겠습니다.",
                1.0, false, true, 1000, null, false));
        shopListAdapter.setShopLists(dummyShops);
        binding.recyclerViewShopList.setAdapter(shopListAdapter);

        return binding.getRoot();
    }

    public void setAppBar(HomeViewModel homeViewModel){
        MainActivity activity = (MainActivity) getActivity();
        activity.getBinding().topAppBar.setTitle(homeViewModel.getSearchContent().getValue());
        activity.getBinding().topAppBar.setTitleCentered(false);
        activity.getBinding().topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.menu_search :{
                        //검색 dialog 띄우기
                        navController.navigate(R.id.action_shopListFragment_to_searchDialogFragment);
                        break;
                    }
                    case R.id.menu_map:{
                        //지도로 navigate
                        break;
                    }
                }

                return false;
            }
        });
        activity.invalidateOptionsMenu();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //데이터 받아오기
        shopViewModel = new ViewModelProvider(this).get(ShopViewModel.class);
        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        navController = Navigation.findNavController(view);

        //앱바 설정
        setAppBar(homeViewModel);

        shopListAdapter.setOnItemClickListener(new ShopListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                //해당 매장을 검색조건으로 이동
                Bundle bundle =  new Bundle();
                bundle.putParcelable("shopInfo", shopListAdapter.getShopLists().get(pos));
                navController.navigate(R.id.action_shopListFragment_to_shopMainFragment, bundle);
            }

            @Override
            public void onFavoriteClick(View v, int pos, boolean isFavorite){
                //관심가게 변경 api
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}