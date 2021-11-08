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

import com.sososhopping.customer.databinding.ShopIntroduceBinding;
import com.sososhopping.customer.shop.model.ShopIntroduceModel;
import com.sososhopping.customer.shop.model.common.BusinessDays;
import com.sososhopping.customer.shop.model.common.Location;
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
        shopIntroduceViewModel = new ShopIntroduceViewModel();

        //더미
        dummyItem();
        binding.textViewShopLocation.setText(shopIntroduceViewModel.getAddress(shopIntroduceModel));
        binding.textViewShopOpen.setText(shopIntroduceViewModel.getBusinessDay(shopIntroduceModel));
        binding.textViewShopOpenDetail.setText(shopIntroduceModel.getExtraBusinessDay());

        if(shopIntroduceModel.getMinimumOrderPrice() != null){
            binding.textViewShopRestrict.setText(shopIntroduceViewModel.getMinimum(shopIntroduceModel));
        }else{
            binding.textViewShopRestrict.setVisibility(View.GONE);
        }
        binding.textViewShopIntroduce.setText(shopIntroduceModel.getDescription());

        if(shopIntroduceModel.getSaveRate() != null){
            binding.textViewShopPoint.setText(shopIntroduceViewModel.getSaveRate(shopIntroduceModel));
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        super.onViewCreated(view, savedInstanceState);
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
