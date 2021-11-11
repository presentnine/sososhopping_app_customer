package com.sososhopping.customer.shop.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.sososhopping.customer.R;
import com.sososhopping.customer.common.CarouselMethod;
import com.sososhopping.customer.databinding.ShopIntroduceBinding;
import com.sososhopping.customer.shop.model.ShopIntroduceModel;
import com.sososhopping.customer.shop.model.typeclass.BusinessDays;
import com.sososhopping.customer.shop.model.typeclass.Location;
import com.sososhopping.customer.shop.viewmodel.ShopIntroduceViewModel;

import java.util.ArrayList;

public class ShopIntroduceFragment extends Fragment {
    private NavController navController;
    private ShopIntroduceBinding binding;
    private ShopIntroduceModel shopIntroduceModel;
    private ShopIntroduceViewModel shopIntroduceViewModel;

    public static ShopIntroduceFragment newInstance(){return new ShopIntroduceFragment();}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ShopIntroduceBinding.inflate(inflater, container, false);

        //더미
        shopIntroduceViewModel = new ShopIntroduceViewModel();
        dummyItem();
        binding.textViewShopLocation.setText(shopIntroduceViewModel.getAddress(shopIntroduceModel));
        binding.textViewShopOpen.setText(shopIntroduceViewModel.getBusinessDay(shopIntroduceModel));
        binding.textViewShopOpenDetail.setText(shopIntroduceModel.getExtraBusinessDay());

        //제한
        if(shopIntroduceModel.getMinimumOrderPrice() != null){
            binding.textViewShopRestrict.setText(shopIntroduceViewModel.getMinimum(shopIntroduceModel));
        }else{
            binding.textViewShopRestrict.setVisibility(View.GONE);
        }

        //설명
        binding.textViewShopIntroduce.setText(shopIntroduceModel.getDescription());

        //포인트
        if(shopIntroduceModel.getSaveRate() != null){
            binding.textViewShopPoint.setText(shopIntroduceViewModel.getSaveRate(shopIntroduceModel));
        }

        //번호
        if(shopIntroduceModel.getPhone() != null){
            binding.textViewShopPhone.setText(shopIntroduceModel.getPhone());
        }

        //이미지
        CarouselMethod carouselMethod = new CarouselMethod(binding.layoutIndicators, binding.viewpagerIntroduce, getContext());
        carouselMethod.setCarousel(shopIntroduceModel.getStoreImages());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.buttonShopMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(getParentFragment().getParentFragment())
                        .navigate(ShopMainFragmentDirections.actionShopMainFragmentToShopMapFragment(R.id.shopMainFragment));
            }
        });

        binding.buttonShopCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shopIntroduceModel.getPhone() != null){
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+shopIntroduceModel.getPhone())));
                }
            }
        });

        binding.buttonShopChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.buttonShopFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //관심가게 여부 변경 api
                shopIntroduceModel.setFavoriteStatus(!shopIntroduceModel.isFavoriteStatus());
                ((ShopMainFragment) getParentFragment().getParentFragment()).changeFavoriteState(shopIntroduceModel.isFavoriteStatus());
            }
        });
    }

    public void dummyItem(){
        shopIntroduceModel = new ShopIntroduceModel();
        shopIntroduceModel.setStoreId(1);
        shopIntroduceModel.setName("가상의 가게");
        shopIntroduceModel.setImgUrl(null);
        shopIntroduceModel.setDescription("1956년 대전역 앞 작은 찐빵집으로 시작된 성심당은 대전 시민의 자부심과 사랑으로 성장하여 대전을 대표하는 향토빵집이 되었습니다. " +
                "'모든 이가 다 좋게 여기는 일을 하도록 하십시오'라는 가톨릭 정신을 바탕으로 봉사하는 가치있는 기업이 되어 함께 '사랑의 문화'를 이루어 가고자 합니다. \n " +
                "백년가게는 30년 이상 명맥을 유지하면서도 오래도록 고객의 꾸준한 사랑을 받아온 점포 가운데, 중소벤처기업부에서 그 우수성과 성장 가능성을 높게 평가 받아 공식 인증받은 점포입니다");
        shopIntroduceModel.setPhone("01012345678");
        shopIntroduceModel.setBusinessStatus(true);
        shopIntroduceModel.setLocalCurrencyStatus(true);
        shopIntroduceModel.setPickupStatus(true);
        shopIntroduceModel.setDeliveryStatus(true);
        shopIntroduceModel.setFavoriteStatus(true);
        shopIntroduceModel.setMinimumOrderPrice(20000);
        shopIntroduceModel.setSaveRate(0.1);
        shopIntroduceModel.setStreetAddress("서울시 용산구 녹사평대로 40다길 19");
        shopIntroduceModel.setDetailedAddress("A동 101호");

        ArrayList<BusinessDays> businessDays = new ArrayList<>();
        businessDays.add(new BusinessDays("월",true,"0800","2300"));
        businessDays.add(new BusinessDays("화",true,"0800","2300"));
        businessDays.add(new BusinessDays("수",true,"0800","1200"));
        businessDays.add(new BusinessDays("목",true,"0800","2300"));
        businessDays.add(new BusinessDays("금",true,"0800","2300"));
        businessDays.add(new BusinessDays("토",true,"1000","2000"));
        businessDays.add(new BusinessDays("일",false,"0800","2300"));
        shopIntroduceModel.setBusinessDays(businessDays);
        shopIntroduceModel.setExtraBusinessDay("매월 2주, 4주 수요일은 휴무일입니다");


        ArrayList<String> storeImages = new ArrayList<>();
        shopIntroduceModel.setStoreImages(storeImages);
        shopIntroduceModel.setLocation(new Location(34.12345,45.12345));

        shopIntroduceModel.setScore(4.5f);
    }
}
