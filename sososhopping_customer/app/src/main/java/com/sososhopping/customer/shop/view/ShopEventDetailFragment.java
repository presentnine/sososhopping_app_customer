package com.sososhopping.customer.shop.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.sososhopping.customer.MainActivity;
import com.sososhopping.customer.R;
import com.sososhopping.customer.common.carousel.CarouselMethod;
import com.sososhopping.customer.common.DateFormatMethod;
import com.sososhopping.customer.databinding.ShopEventDetailBinding;
import com.sososhopping.customer.shop.model.EventDetailModel;
import com.sososhopping.customer.shop.viewmodel.ShopEventDetailViewModel;

public class ShopEventDetailFragment extends Fragment {
    ShopEventDetailBinding binding;
    EventDetailModel eventDetailModel;
    ShopEventDetailViewModel shopEventDetailViewModel = new ShopEventDetailViewModel();

    int writingId, storeId;
    String storeName;

    public static ShopEventDetailFragment newInstance(){return new ShopEventDetailFragment();}

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_top_none, menu);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){

        binding = ShopEventDetailBinding.inflate(inflater,container,false);

        //내용 받아오기
        writingId = ShopEventDetailFragmentArgs.fromBundle(getArguments()).getWriteId();
        storeId = ShopEventDetailFragmentArgs.fromBundle(getArguments()).getShopId();
        storeName = ShopEventDetailFragmentArgs.fromBundle(getArguments()).getStoreName();

        shopEventDetailViewModel.requestShopEventDetail(storeId, writingId,
                this::onSuccess, this::onFailed, this::onNetworkError);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
    }

    @Override
    public void onResume() {
        ((MainActivity) getActivity()).getBinding().topAppBar.setTitle(storeName);
        super.onResume();
    }

    public void setShopInfo(EventDetailModel eventDetailModel){
        binding.textViewTitle.setText(eventDetailModel.getTitle());
        binding.textViewContent.setText(eventDetailModel.getContent());
        binding.textViewWriteDate.setText(DateFormatMethod.dateFormatMin(eventDetailModel.getCreatedAt()));
        binding.textViewType.setText(eventDetailModel.getWritingType().getValue());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onSuccess(EventDetailModel eventDetailModel){
        //setting Info
        setShopInfo(eventDetailModel);

        //이미지 세팅
        if(eventDetailModel.getImgUrl() == null){
            binding.viewpagerEventDetail.setVisibility(View.GONE);
            binding.layoutIndicators.setVisibility(View.GONE);
        }
        else if(eventDetailModel.getImgUrl().size() <= 0){
            binding.viewpagerEventDetail.setVisibility(View.GONE);
            binding.layoutIndicators.setVisibility(View.GONE);
        }else{
            CarouselMethod carouselMethod = new CarouselMethod(binding.layoutIndicators, binding.viewpagerEventDetail, getContext());
            carouselMethod.setCarousel(eventDetailModel.getImgUrl());
        }
    }

    private void onFailed() {
        Toast.makeText(getContext(),getResources().getString(R.string.shop_error), Toast.LENGTH_LONG).show();
    }

    private void onNetworkError() {
        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_global_networkErrorDialog);
    }

}
