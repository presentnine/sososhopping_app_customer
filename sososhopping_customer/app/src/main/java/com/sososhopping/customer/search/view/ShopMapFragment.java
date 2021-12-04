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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.sososhopping.customer.HomeActivity;
import com.sososhopping.customer.R;
import com.sososhopping.customer.common.StringFormatMethod;
import com.sososhopping.customer.common.types.Location;
import com.sososhopping.customer.databinding.SearchShopMapBinding;
import com.sososhopping.customer.search.HomeViewModel;
import com.sososhopping.customer.search.model.ShopInfoShortModel;
import com.sososhopping.customer.shop.view.ShopMainFragment;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

public class ShopMapFragment extends Fragment {
    private NavController navController;
    private SearchShopMapBinding binding;
    private HomeViewModel homeViewModel;
    private int navigateFrom;

    private MapView mapView;
    private ViewGroup mapViewContainer;

    MutableLiveData<ShopInfoShortModel> focusedShop;

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
                //마커 추가
                addMarkers(mapView);
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        //지도 연결
        setMap();

        //마커 추가
        addMarkers(mapView);

        binding.buttonGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location location = homeViewModel.getLocation(getContext());
                mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(location.getLat(), location.getLng()), 2, true);
            }
        });
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
    public void onPause() {
        mapViewContainer.removeView(mapView);
        super.onPause();
    }


    public void setMap(){
        mapView = new MapView(getActivity());
        mapViewContainer = (ViewGroup) binding.mapView;
        mapViewContainer.addView(mapView);

        navigateFrom = ShopMapFragmentArgs.fromBundle(getArguments()).getNavigateFrom();

        switch (navigateFrom){

            //홈
            //리스트에서 옴 -> 검색내용 토대로 구성
            //검색결과로 구성
            case R.id.home2 :
            case R.id.shopListFragment:
            case R.id.searchDialogFragment:
                Location location = homeViewModel.getLocation(getContext());

                //내 좌표 표시, 패널 띄우기
                mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(location.getLat(), location.getLng()), 2, true);
                break;


            //상점에서 옴
            case R.id.mysosoPointDetailFragment:
            case R.id.shopReportFragment:
            case R.id.shopMainFragment:{
                double shopLat = ShopMapFragmentArgs.fromBundle(getArguments()).getLat();
                double shopLng = ShopMapFragmentArgs.fromBundle(getArguments()).getLng();

                //좌표 표시, 패널 띄우기
                mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(shopLat, shopLng), 2, true);
                break;
           }
        }

        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithMarkerHeadingWithoutMapMoving);
        mapView.setShowCurrentLocationMarker(true);
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

    public void addMarkers(MapView mapView){
        mapView.removeAllPOIItems();
        ArrayList<MapPOIItem> markers = new ArrayList<>();

        for(int i= 0; i<homeViewModel.getShopList().getValue().size(); i++){
            ShopInfoShortModel s = homeViewModel.getShopList().getValue().get(i);
            MapPOIItem marker = new MapPOIItem();

            /*
            marker.setTag(i);
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(s.getLocation().getLat(), s.getLocation().getLng()));
            marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            marker.setCustomImageResourceId(R.drawable.icon_location_pin);
            marker.setCustomImageAnchor(0.5f, 1.0f);
            marker.setCustomImageAutoscale(false);
            marker.setCustomSelectedImageResourceId(R.drawable.icon_app_gear);
            markers.add(marker);*/

            marker.setTag(i);
            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(s.getLocation().getLat(), s.getLocation().getLng()));
            marker.setItemName(s.getName());
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
            markers.add(marker);

            Log.e("tags", s.getName() + " " + s.getLocation().getLat() + " " + s.getLocation().getLng());
        }
        mapView.addPOIItems(markers.toArray(new MapPOIItem[markers.size()]));
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {

        public CustomCalloutBalloonAdapter() {
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            int index = poiItem.getTag();
            focusedShop.postValue(homeViewModel.getShopList().getValue().get(index));
            Log.e("선택", index+" ");
            return null;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }

}