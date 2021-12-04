package com.sososhopping.customer.search.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.MarkerIcons;
import com.sososhopping.customer.HomeActivity;
import com.sososhopping.customer.R;
import com.sososhopping.customer.common.StringFormatMethod;
import com.sososhopping.customer.common.types.Location;
import com.sososhopping.customer.databinding.SearchShopMapBinding;
import com.sososhopping.customer.search.HomeViewModel;
import com.sososhopping.customer.search.model.ShopInfoShortModel;
import com.sososhopping.customer.shop.view.ShopMainFragment;

import java.util.ArrayList;

public class ShopMapFragment extends Fragment implements OnMapReadyCallback {
    private NavController navController;
    private SearchShopMapBinding binding;
    private HomeViewModel homeViewModel;
    private int navigateFrom;

    private MapFragment mapFragment;
    private NaverMap naverMap;

    MutableLiveData<ShopInfoShortModel> focusedShop;
    ArrayList<Marker> markers;

    public static ShopMainFragment newInstance() {return new ShopMainFragment();}

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        FragmentManager fm = getChildFragmentManager();
        mapFragment = (MapFragment)fm.findFragmentById(R.id.map);

        NaverMapOptions options;
        switch (navigateFrom){
            //홈
            //리스트에서 옴 -> 검색내용 토대로 구성
            //검색결과로 구성
            case R.id.home2 :
            case R.id.shopListFragment:
            case R.id.searchDialogFragment:
            default:
                Location location = homeViewModel.getLocation(getContext());

                //내 좌표 표시, 패널 띄우기
                options = new NaverMapOptions()
                        .camera(new CameraPosition(new LatLng(location.getLat(), location.getLng()), 16));
                break;


            //상점에서 옴
            case R.id.mysosoPointDetailFragment:
            case R.id.shopReportFragment:
            case R.id.shopMainFragment:{
                double shopLat = ShopMapFragmentArgs.fromBundle(getArguments()).getLat();
                double shopLng = ShopMapFragmentArgs.fromBundle(getArguments()).getLng();

                //좌표 표시, 패널 띄우기
                options = new NaverMapOptions()
                        .camera(new CameraPosition(new LatLng(shopLat, shopLng), 2));
                break;
            }
        }
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance(options);
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this::onMapReady);
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

        focusedShop = new MutableLiveData<>();
        focusedShop.observe(this, new Observer<ShopInfoShortModel>() {
            @Override
            public void onChanged(ShopInfoShortModel shopInfoShortModel) {
                bindShopItem(shopInfoShortModel);
                Log.e("선택", shopInfoShortModel.getScore()+" ");
            }
        });

        homeViewModel.getShopList().observe(this, new Observer<ArrayList<ShopInfoShortModel>>() {
            @Override
            public void onChanged(ArrayList<ShopInfoShortModel> shopInfoShortModels) {
                if(naverMap != null){
                    Log.e("마커 세팅", shopInfoShortModels.size()+"");
                    //마커 추가
                    addMarkers(naverMap);
                }
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        //지도 연결

        //마커 추가
    }


    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        navigateFrom = ShopMapFragmentArgs.fromBundle(getArguments()).getNavigateFrom();
        this.naverMap = naverMap;

        binding.buttonGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location location = homeViewModel.getLocation(getContext());
                //좌표설정
                naverMap.moveCamera(
                        CameraUpdate.scrollTo(new LatLng(location.getLat(), location.getLng()))
                );
            }
        });
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(false);
        addMarkers(naverMap);
    }


    @Override
    public void onResume() {
        //앱바 설정
        ((HomeActivity) getActivity()).hideBottomNavigation();
        ((HomeActivity) getActivity()).showTopAppBar();
        setAppBar(homeViewModel);

        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void addMarkers(NaverMap naverMap){
        if(markers != null){
            for(Marker m : markers){
                Log.e("makrers", m.getTag().toString());
                m.setMap(null);
            }
        }
        else{
            markers = new ArrayList<>();
        }

        for(int i= 0; i<homeViewModel.getShopList().getValue().size(); i++){
            ShopInfoShortModel s = homeViewModel.getShopList().getValue().get(i);

            Marker m = new Marker();

            m.setPosition(new LatLng(s.getLocation().getLat(), s.getLocation().getLng()));
            m.setIcon(MarkerIcons.GREEN);
            //m.setIcon(OverlayImage.fromResource(R.drawable.icon_location_pin));
            m.setTag(i);
            m.setOnClickListener(new Overlay.OnClickListener() {
                @Override
                public boolean onClick(@NonNull Overlay overlay) {
                    Marker marker = (Marker) overlay;
                    focusedShop.postValue(homeViewModel.getShopList().getValue().get((Integer)marker.getTag()));
                    return false;
                }
            });

            m.setMap(naverMap);
            markers.add(m);
            Log.e("tags", s.getName() + " " + s.getLocation().getLat() + " " + s.getLocation().getLng());
        }
    }
    public void bindShopItem(ShopInfoShortModel s){
        binding.itemSearchMap.textViewShopName.setText(s.getName());

        //특징설정
        if(!s.isLocalCurrencyStatus()){
            binding.itemSearchMap.layoutLocalPay.setVisibility(View.GONE);
        }
        if(!s.isDeliveryStatus()){
            binding.itemSearchMap.layoutDelivery.setVisibility(View.GONE);
        }

        //별점설정
        binding.itemSearchMap.textViewRating.setText(StringFormatMethod.getRating(s.getScore()));
        binding.itemSearchMap.buttonMove.setText("매장 둘러보기 (" + StringFormatMethod.getDistance(s.getDistance())+")");


        binding.itemSearchMap.buttonMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이동
            }
        });

        binding.itemSearchMap.buttonShopChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //채팅
            }
        });

        binding.itemSearchMap.buttonShopCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(s.getPhone() == null){
                    Snackbar.make(binding.getRoot(), "등록된 전화번호가 없습니다", Snackbar.LENGTH_SHORT).show();
                }
                else{
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ s.getPhone())));
                }
            }
        });
        binding.itemSearchMap.getRoot().setVisibility(View.VISIBLE);
    }
    public void setAppBar(HomeViewModel homeViewModel){
        HomeActivity activity = (HomeActivity) getActivity();
        activity.getBinding().topAppBar.setTitle(homeViewModel.getSearchContent().getValue());
        activity.getBinding().topAppBar.setTitleCentered(false);
        activity.getBinding().topAppBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //검색 dialog 띄우기
                navController.navigate(ShopMapFragmentDirections.actionShopMapFragmentToSearchDialogFragment(R.id.shopMapFragment));
                return;
            }
        });
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


}