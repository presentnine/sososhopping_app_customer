package com.sososhopping.customer.shop.view;

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
import com.sososhopping.customer.search.ShopInfoShort;
import com.sososhopping.customer.search.view.ShopListFragment;
import com.sososhopping.customer.shop.viewmodel.ShopInfoViewModel;

public class ShopMainFragment extends Fragment {

    private NavController navController;
    private ShopMainBinding binding;
    private ShopInfoShort shopInfoShort;
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
        shopInfoShort = getArguments().getParcelable("shopInfo");
        setShopInfo(shopInfoShort);

        //ViewModel Setting -> Main에서 유지되게
        shopInfoViewModel = new ViewModelProvider(this).get(ShopInfoViewModel.class);
        shopInfoViewModel.setShopId(shopInfoShort.getShopId());
        shopInfoViewModel.setShopName(shopInfoShort.getShopName());


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
        setAppBar(activity, shopInfoShort.getShopName());

        //하단바 숨기기
        activity.hideBottomNavigation();
        super.onResume();
    }


    public void setShopInfo(ShopInfoShort shopInfoShort){
        binding.textViewShopName.setText(shopInfoShort.getShopName());
        binding.textViewRating.setText(Double.toString(shopInfoShort.getRating()));
        binding.textViewDistance.setText(Integer.toString(shopInfoShort.getDistance())+"m");

        //지역화폐, 배달여부
        if(!shopInfoShort.isLocalPay()){
            binding.layoutLocalPay.setVisibility(View.GONE);
        }
        if(!shopInfoShort.isDelivery()){
            binding.layoutDelivery.setVisibility(View.GONE);
        }
        if(!shopInfoShort.isFavorite()){
            binding.layoutFavorite.setVisibility(View.GONE);
        }

        //이미지
        Glide.with(binding.getRoot())
                .load(shopInfoShort.getShopImageURL())
                .transform(new CenterCrop(),new RoundedCorners(10))
                .thumbnail(0.2f)
                .placeholder(R.drawable.icon_category_cafe)
                .error(R.drawable.icon_category_cafe)
                .fallback(R.drawable.icon_category_cafe)
                .into(binding.imageViewStore);
    }

    public void setAppBar(MainActivity activity, String shopName){
        activity.getBinding().topAppBar.setTitle("매장 정보");
        activity.getBinding().topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){

                    case R.id.menu_call:{
                        if(shopInfoShort.getPhone() == null){
                            Toast.makeText(getContext(),"등록된 전화번호가 없습니다",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+shopInfoShort.getPhone())));
                        }
                        break;
                    }
                    case R.id.menu_chat:{
                        break;

                    }
                    case R.id.menu_report:{
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
