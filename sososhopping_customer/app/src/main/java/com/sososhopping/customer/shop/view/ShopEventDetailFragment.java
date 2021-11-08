package com.sososhopping.customer.shop.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.sososhopping.customer.MainActivity;
import com.sososhopping.customer.R;
import com.sososhopping.customer.StaticMethod;
import com.sososhopping.customer.databinding.ShopEventDetailBinding;
import com.sososhopping.customer.shop.model.EventDetailModel;
import com.sososhopping.customer.shop.model.enumType.WritingType;
import com.sososhopping.customer.shop.view.adapter.ShopEventDetailImgAdapter;

public class ShopEventDetailFragment extends Fragment {
    ShopEventDetailBinding binding;
    EventDetailModel eventDetailModel;

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
        int writeId = getArguments().getInt("writeId");
        int shopId = getArguments().getInt("shopId");
        String storeName = getArguments().getString("storeName");

        //받아오는 API 동작
        eventDetailModel = new EventDetailModel(writeId,"가상의 이벤트 글입니다",
                "가상의 이벤트 글입니다. \n여러 줄을 자유롭게 작성할 수 있습니다. \n 저장 역시 여러 줄에 대해 이루어질 수 있습니다",
                WritingType.PROMOTION, "2021-11-04T16:33:17.341119",null);

        //setting Info
        setShopInfo(eventDetailModel);
        ((MainActivity) getActivity()).getBinding().topAppBar.setTitle(storeName);
        setCarousel(eventDetailModel);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
    }


    public void setShopInfo(EventDetailModel eventDetailModel){
        binding.textViewTitle.setText(eventDetailModel.getTitle());
        binding.textViewContent.setText(eventDetailModel.getContent());
        binding.textViewWriteDate.setText(StaticMethod.dateFormatMin(eventDetailModel.getDate()));
        binding.textViewType.setText(eventDetailModel.getWritingType().getValue());
    }

    public void setCarousel(EventDetailModel eventDetailModel){
        //sample Images
        String[] sampleImage = new String[] {
                "https://postfiles.pstatic.net/MjAyMDA4MjZfNDgg/MDAxNTk4NDEyODEzOTE5.PXbPOn5JDUAI1voaqLg24k6NZ0sHfmf8cmhY2HC_I5sg.oyeDpP0pF4iPgSC5nwLsjNvYXhq5KN984lqmFD0FXgwg.JPEG.gmldms784/IMG_8194.jpg?type=w966",
                "https://cdn.pixabay.com/photo/2020/11/04/15/29/coffee-beans-5712780_1280.jpg",
                "https://cdn.pixabay.com/photo/2020/03/08/21/41/landscape-4913841_1280.jpg",
                "https://cdn.pixabay.com/photo/2020/09/02/18/03/girl-5539094_1280.jpg",
                "https://cdn.pixabay.com/photo/2014/03/03/16/15/mosque-279015_1280.jpg"
        };

        binding.viewpagerEventDetail.setOffscreenPageLimit(1);
        binding.viewpagerEventDetail.setAdapter(new ShopEventDetailImgAdapter(sampleImage));

        binding.viewpagerEventDetail.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });
        setupIndicators(sampleImage.length);
    }

    private void setupIndicators(int count) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getContext(),
                    R.drawable.drawable_bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            binding.layoutIndicators.addView(indicators[i]);
        }
        setCurrentIndicator(0);
    }

    private void setCurrentIndicator(int position) {
        int childCount = binding.layoutIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) binding.layoutIndicators.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getContext(),
                        R.drawable.drawable_bg_indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getContext(),
                        R.drawable.drawable_bg_indicator_inactive
                ));
            }
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
