package com.sososhopping.customer.search.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sososhopping.customer.MainActivity;
import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.SearchShopMapBinding;
import com.sososhopping.customer.home.HomeViewModel;
import com.sososhopping.customer.search.ShopViewModel;
import com.sososhopping.customer.shop.view.ShopMainFragment;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class ShopMapFragment extends Fragment {
    private NavController navController;
    private SearchShopMapBinding binding;
    private HomeViewModel homeViewModel;
    private ShopViewModel shopViewModel;
    private int navigateFrom;

    private MapView mapView;
    private ViewGroup mapViewContainer;

    public static ShopMainFragment newInstance() {return new ShopMainFragment();}

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
        binding = SearchShopMapBinding.inflate(inflater,container,false);

        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        shopViewModel = new ViewModelProvider(getActivity()).get(ShopViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

    }

    @Override
    public void onResume() {
        //앱바 설정
        ((MainActivity) getActivity()).hideBottomNavigation();
        ((MainActivity) getActivity()).showTopAppBar();
        setAppBar(homeViewModel);

        //지도 연결
        setMap(shopViewModel);
        super.onResume();
    }

    @Override
    public void onPause() {
        mapViewContainer.removeView(mapView);
        super.onPause();
    }


    public void setMap(ShopViewModel shopViewModel){
        mapView = new MapView(getActivity());
        navigateFrom = ShopMapFragmentArgs.fromBundle(getArguments()).getNavigateFrom();

        switch (navigateFrom){
            //홈에서 바로 옴 -> 별다른거 x
            case R.id.home2 : {
                break;
            }
            //리스트에서 옴 -> 검색내용 토대로 구성
            case R.id.shopListFragment:{
                //shopViewModel을 토대로 좌표 재구성
                break;
            }
            case R.id.shopMainFragment:{

                //좌표 표시, 패널 띄우기
                mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.583912, 127.058981), 1, true);
                break;
            }
            //검색결과로 구성
            case R.id.searchDialogFragment:{
                break;
            }
        }
        mapViewContainer = (ViewGroup) binding.mapView;
        mapViewContainer.addView(mapView);
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
                        navController.navigate(ShopMapFragmentDirections.actionShopMapFragmentToSearchDialogFragment(R.id.shopMapFragment));
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
