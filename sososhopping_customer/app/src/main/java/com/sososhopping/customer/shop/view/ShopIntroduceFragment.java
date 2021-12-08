package com.sososhopping.customer.shop.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.sososhopping.customer.HomeActivity;
import com.sososhopping.customer.NavGraphDirections;
import com.sososhopping.customer.R;
import com.sososhopping.customer.ShopGraphDirections;
import com.sososhopping.customer.common.carousel.CarouselMethod;
import com.sososhopping.customer.databinding.ShopIntroduceBinding;
import com.sososhopping.customer.shop.model.ShopIntroduceModel;
import com.sososhopping.customer.shop.viewmodel.ShopInfoViewModel;
import com.sososhopping.customer.shop.viewmodel.ShopIntroduceViewModel;

public class ShopIntroduceFragment extends Fragment {
    private NavController navController;
    private ShopIntroduceBinding binding;
    private ShopIntroduceModel shopIntroduceModel;
    private ShopIntroduceViewModel shopIntroduceViewModel = new ShopIntroduceViewModel();
    private ShopInfoViewModel shopInfoViewModel;

    public static ShopIntroduceFragment newInstance(){return new ShopIntroduceFragment();}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ShopIntroduceBinding.inflate(inflater, container, false);
        shopInfoViewModel = new ViewModelProvider(getParentFragment().getParentFragment()).get(ShopInfoViewModel.class);
        shopIntroduceViewModel.setStoreId(shopInfoViewModel.getShopId().getValue());
        shopIntroduceViewModel.requestShopIntroduce(
                ((HomeActivity)getActivity()).getLoginToken(),
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
                NavHostFragment.findNavController(getParentFragment().getParentFragment().getParentFragment())
                        .navigate(ShopGraphDirections.actionGlobalShopMapFragment(R.id.shopMainFragment)
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

        //TODO : 채팅방 생성 (상점소개) -> 고객 닉네임 필요
        binding.buttonShopChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long storeId = shopIntroduceModel.getStoreId();
                long ownerId = shopIntroduceModel.getOwnerId();
                String storeName = shopIntroduceModel.getName();
                String customerName = "";

<<<<<<< HEAD
                NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(NavGraphDirections.actionGlobalConversationFragment(storeName)
                        .setStoreId(storeId)
                        .setOwnerId(ownerId));
=======
                String chatroomId = ((HomeActivity) getActivity()).makeChatroom(Long.toString(storeId), Long.toString(ownerId), storeName, customerName);

                NavHostFragment.findNavController(getParentFragment().getParentFragment())
                        .navigate(NavGraphDirections.actionGlobalConversationFragment(storeName)
                                .setChatroomId(chatroomId));
>>>>>>> 8c6b8bdad3e4698a8ece34b70809841ee3d15a40
            }
        });

        binding.buttonShopFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //관심가게 여부 변경 api
                shopIntroduceViewModel.requestShopFavoriteChange(
                        ((HomeActivity)getActivity()).getLoginToken(),
                        ShopIntroduceFragment.this::onSuccessFavoriteChange,
                        ShopIntroduceFragment.this::onFailedLogIn,
                        ShopIntroduceFragment.this::onFailed,
                        ShopIntroduceFragment.this::onNetworkError);
            }
        });
    }

    private void onSuccess(ShopIntroduceModel shopIntroduceModel){
        if(shopIntroduceModel != null){

            if(shopInfoViewModel.getShopIntroduceModel().getValue() == null){
                ((ShopMainFragment)getParentFragment().getParentFragment()).initialSetting(shopIntroduceModel);
            }

            this.shopIntroduceModel = shopIntroduceModel;

            binding.textViewShopLocation.setText(shopIntroduceViewModel.getAddress(shopIntroduceModel));

            //영업일 계산
            try {
                binding.textViewShopOpen.setText(shopIntroduceViewModel.getBusinessDay(shopIntroduceModel));


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
            changeFavoriteState(shopIntroduceModel.isInterestStore());

            //이미지
            CarouselMethod carouselMethod = new CarouselMethod(binding.layoutIndicators, binding.viewpagerIntroduce, getContext());
            carouselMethod.setCarousel(shopIntroduceModel.getStoreImages());

            }catch (Exception e){
                //View에서는 혹시 에러 터져도 진행되게
                e.printStackTrace();
            }
        }
    }

    public void changeFavoriteState(boolean status){
        if(!status){
            binding.buttonShopFavorite.setText(getResources().getString(R.string.introduce_button_favorite));
        }else{
            binding.buttonShopFavorite.setText(getResources().getString(R.string.introduce_button_favorite_not));
        }
    }

    private void onSuccessFavoriteChange(){
        //관심여부 변경 (View단)
        boolean status = !shopIntroduceModel.isInterestStore();
        shopIntroduceModel.setInterestStore(status);
        changeFavoriteState(status);
        ((ShopMainFragment) getParentFragment().getParentFragment()).changeFavoriteState(status);
    }

    private void onFailedLogIn(){
        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(NavGraphDirections.actionGlobalLogInRequiredDialog().setErrorMsgId(R.string.login_error_token));
    }

    private void onFailed() {
        Toast.makeText(getContext(),getResources().getString(R.string.shop_error), Toast.LENGTH_LONG).show();
    }

    private void onNetworkError() {
        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_global_networkErrorDialog);
    }

}
