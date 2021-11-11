package com.sososhopping.customer.shop.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sososhopping.customer.MainActivity;
import com.sososhopping.customer.R;
import com.sososhopping.customer.common.CarouselMethod;
import com.sososhopping.customer.common.DateFormatMethod;
import com.sososhopping.customer.databinding.ItemShopCarouselImgBinding;
import com.sososhopping.customer.databinding.ShopEventDetailBinding;
import com.sososhopping.customer.shop.model.EventDetailModel;
import com.sososhopping.customer.shop.model.enumType.WritingType;

public class ShopEventDetailFragment extends Fragment {
    ShopEventDetailBinding binding;
    EventDetailModel eventDetailModel;

    int writeId, shopId;
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
    public void onResume() {
        ((MainActivity) getActivity()).getBinding().topAppBar.setTitle(storeName);
        super.onResume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){

        binding = ShopEventDetailBinding.inflate(inflater,container,false);

        //내용 받아오기
        writeId = ShopEventDetailFragmentArgs.fromBundle(getArguments()).getWriteId();
        shopId = ShopEventDetailFragmentArgs.fromBundle(getArguments()).getShopId();
        storeName = ShopEventDetailFragmentArgs.fromBundle(getArguments()).getStoreName();

        //받아오는 API 동작
        eventDetailModel = new EventDetailModel(writeId,"가상의 이벤트 글입니다",
                "가상의 이벤트 글입니다. \n여러 줄을 자유롭게 작성할 수 있습니다. \n 저장 역시 여러 줄에 대해 이루어질 수 있습니다",
                WritingType.PROMOTION, "2021-11-04T16:33:17.341119",null);

        //setting Info
        setShopInfo(eventDetailModel);

        //이미지 세팅
        CarouselMethod carouselMethod = new CarouselMethod(binding.layoutIndicators, binding.viewpagerEventDetail, getContext());
        carouselMethod.setCarousel(eventDetailModel.getImgUrl());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
    }


    public void setShopInfo(EventDetailModel eventDetailModel){
        binding.textViewTitle.setText(eventDetailModel.getTitle());
        binding.textViewContent.setText(eventDetailModel.getContent());
        binding.textViewWriteDate.setText(DateFormatMethod.dateFormatMin(eventDetailModel.getDate()));
        binding.textViewType.setText(eventDetailModel.getWritingType().getValue());
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
