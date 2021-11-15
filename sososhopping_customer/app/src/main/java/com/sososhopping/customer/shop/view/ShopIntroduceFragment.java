package com.sososhopping.customer.shop.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.sososhopping.customer.MainActivity;
import com.sososhopping.customer.NavGraphDirections;
import com.sososhopping.customer.R;
import com.sososhopping.customer.common.CarouselMethod;
import com.sososhopping.customer.common.types.BusinessDays;
import com.sososhopping.customer.common.types.Location;
import com.sososhopping.customer.databinding.ShopIntroduceBinding;
import com.sososhopping.customer.shop.model.ShopIntroduceModel;
import com.sososhopping.customer.shop.viewmodel.ShopInfoViewModel;
import com.sososhopping.customer.shop.viewmodel.ShopIntroduceViewModel;

import java.util.ArrayList;

public class ShopIntroduceFragment extends Fragment {
    private NavController navController;
    private ShopIntroduceBinding binding;
    private ShopIntroduceModel shopIntroduceModel;
    private ShopIntroduceViewModel shopIntroduceViewModel = new ShopIntroduceViewModel();;

    public static ShopIntroduceFragment newInstance(){return new ShopIntroduceFragment();}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ShopIntroduceBinding.inflate(inflater, container, false);

        shopIntroduceViewModel.setStoreId(new ViewModelProvider(getActivity()).get(ShopInfoViewModel.class).getShopId().getValue());
        shopIntroduceViewModel.requestShopIntroduce(
                ShopIntroduceFragment.this::onSuccess,
                ShopIntroduceFragment.this::onFailed,
                ShopIntroduceFragment.this::onNetworkError);

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
                        .navigate(ShopMainFragmentDirections.actionShopMainFragmentToShopMapFragment(R.id.shopMainFragment)
                                .setLat((float)shopIntroduceModel.getLocation().getLat())
                                .setLng((float)shopIntroduceModel.getLocation().getLng()));
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
                shopIntroduceViewModel.requestShopFavoriteChange(
                        ((MainActivity)getActivity()).getLoginToken(),
                        ShopIntroduceFragment.this::onSuccessFavoriteChange,
                        ShopIntroduceFragment.this::onFailedLogIn,
                        ShopIntroduceFragment.this::onFailed,
                        ShopIntroduceFragment.this::onNetworkError);
            }
        });
    }

    private void onSuccess(ShopIntroduceModel shopIntroduceModel){
        if(shopIntroduceModel != null){
            this.shopIntroduceModel = shopIntroduceModel;

            binding.textViewShopLocation.setText(shopIntroduceViewModel.getAddress(shopIntroduceModel));

            //영업일 계산
            try {
                binding.textViewShopOpen.setText(shopIntroduceViewModel.getBusinessDay(shopIntroduceModel));
            }catch (Exception e){
                //혹시 에러 터져도 진행되게
                e.printStackTrace();
            }

            binding.textViewShopOpenDetail.setText(shopIntroduceModel.getExtraBusinessDay());

            //제한
            if(shopIntroduceModel.getMinimumOrderPrice() != null){
                binding.textViewShopRestrict.setText(shopIntroduceViewModel.getMinimum(shopIntroduceModel));
                binding.textViewShopRestrict.setVisibility(View.VISIBLE);
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
        }
    }

    private void onSuccessFavoriteChange(){
        //관심여부 변경 (View단)
        boolean status = !shopIntroduceModel.isFavoriteStatus();
        shopIntroduceModel.setFavoriteStatus(status);
        ((ShopMainFragment) getParentFragment().getParentFragment()).changeFavoriteState(status);
    }

    private void onFailedLogIn(){
        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(NavGraphDirections.actionGlobalLogInRequiredDialog().setErrorMsgId(R.string.login_error_token));
    }

    private void onFailed() {
        Toast.makeText(getContext(),getResources().getString(R.string.shop_error), Toast.LENGTH_LONG).show();
    }

    private void onNetworkError() {
        //((MainActivity) getActivity()).onBackPressed();
        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_global_networkErrorDialog);
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
