package com.sososhopping.customer.shop.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.google.android.material.tabs.TabLayout;
import com.sososhopping.customer.MainActivity;
import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.ShopMainBinding;
import com.sososhopping.customer.search.model.ShopInfoShortModel;
import com.sososhopping.customer.search.view.ShopListFragment;
import com.sososhopping.customer.shop.viewmodel.ShopInfoViewModel;

public class ShopMainFragment extends Fragment {

    private NavController navController;
    private ShopMainBinding binding;
    private ShopInfoShortModel shopInfoShortModel;
    private ShopInfoViewModel shopInfoViewModel;


    public static ShopListFragment newInstance(){return new ShopListFragment();}

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_top_shop, menu);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        binding = DataBindingUtil.inflate(inflater, R.layout.shop_main,container,false);

        //상단 정보 설정
        shopInfoShortModel = getArguments().getParcelable("shopInfo");
        setShopInfo(shopInfoShortModel);

        //ViewModel Setting -> Main에서 유지되게
        shopInfoViewModel = new ViewModelProvider(getActivity()).get(ShopInfoViewModel.class);
        shopInfoViewModel.setShopId(shopInfoShortModel.getStoreId());
        shopInfoViewModel.setShopName(shopInfoShortModel.getName());
        shopInfoViewModel.setPhone(shopInfoShortModel.getPhone());
        shopInfoViewModel.setLocation(shopInfoShortModel.getLocation());



        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //navigation setting
        navController = Navigation.findNavController(binding.getRoot());
        binding.taplayoutShopMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:{
                        Navigation.findNavController(binding.navShopFragment).navigate(R.id.shopIntroduceFragment);
                        break;
                    }
                    case 1:{
                        Navigation.findNavController(binding.navShopFragment).navigate(R.id.shopItemListFragment);
                        break;
                    }
                    case 2:{
                        Navigation.findNavController(binding.navShopFragment).navigate(R.id.shopEventFragment);
                        break;
                    }
                    case 3:{
                        Navigation.findNavController(binding.navShopFragment).navigate(R.id.shopReviewFragment);
                        break;
                    }
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        MainActivity activity = (MainActivity) getActivity();

        //상단바 설정
        setAppBar(activity, shopInfoShortModel.getName());
        activity.showTopAppBar();

        //하단바 숨기기
        activity.hideBottomNavigation();
        super.onResume();
    }


    public void setShopInfo(ShopInfoShortModel shopInfoShortModel){
        binding.textViewShopName.setText(shopInfoShortModel.getName());
        binding.textViewRating.setText(Double.toString(shopInfoShortModel.getScore()));
        binding.textViewDistance.setText(Integer.toString(shopInfoShortModel.getDistance())+"m");

        //지역화폐, 배달여부
        if(!shopInfoShortModel.isLocalCurrencyStatus()){
            binding.layoutLocalPay.setVisibility(View.GONE);
        }
        if(!shopInfoShortModel.isDeliveryStatus()){
            binding.layoutDelivery.setVisibility(View.GONE);
        }
        if(!shopInfoShortModel.isInterestStore()){
            binding.layoutFavorite.setVisibility(View.GONE);
        }

        //이미지
        Glide.with(binding.getRoot())
                .load(shopInfoShortModel.getImgUrl())
                .transform(new CenterCrop(),new RoundedCorners(10))
                .thumbnail(0.2f)
                .placeholder(R.drawable.icon_app_groceries)
                .error(R.drawable.icon_app_groceries)
                .fallback(R.drawable.icon_app_groceries)
                .into(binding.imageViewStore);
    }

    public void setAppBar(MainActivity activity, String shopName){
        activity.getBinding().topAppBar.setTitle("매장 정보");
        activity.getBinding().topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){

                    case R.id.menu_call:{
                        if(shopInfoShortModel.getPhone() == null){
                            Toast.makeText(getContext(),"등록된 전화번호가 없습니다",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ shopInfoShortModel.getPhone())));
                        }
                        break;
                    }
                    case R.id.menu_chat:{
                        break;

                    }
                    case R.id.menu_report:{
                        navController.navigate(R.id.action_shopMainFragment_to_shopReportFragment);
                        break;
                    }
                }

                return false;
            }
        });
        activity.invalidateOptionsMenu();
    }

    public void changeFavoriteState(boolean isFavorite){
        if(isFavorite){
            binding.layoutFavorite.setVisibility(View.VISIBLE);
        }else{
            binding.layoutFavorite.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
