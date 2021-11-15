package com.sososhopping.customer.shop.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.ShopEventBinding;
import com.sososhopping.customer.shop.dto.CouponListDto;
import com.sososhopping.customer.shop.dto.EventItemListDto;
import com.sososhopping.customer.shop.model.CouponModel;
import com.sososhopping.customer.shop.model.EventItemModel;
import com.sososhopping.customer.common.types.enumType.CouponType;
import com.sososhopping.customer.common.types.enumType.WritingType;
import com.sososhopping.customer.shop.view.adapter.ShopEventBoardAdapter;
import com.sososhopping.customer.shop.view.adapter.ShopEventCouponAdapter;
import com.sososhopping.customer.shop.viewmodel.ShopEventViewModel;
import com.sososhopping.customer.shop.viewmodel.ShopInfoViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ShopEventFragment extends Fragment {
    private ShopEventBoardAdapter shopEventBoardAdapter = new ShopEventBoardAdapter();
    private ShopEventCouponAdapter shopEventCouponAdapter = new ShopEventCouponAdapter();
    ShopEventBinding binding;

    private ShopInfoViewModel shopInfoViewModel;
    private ShopEventViewModel shopEventViewModel;

    public static ShopEventFragment newInstance() { return new ShopEventFragment();   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ShopEventBinding.inflate(inflater,container,false);

        shopEventViewModel = new ShopEventViewModel();
        shopInfoViewModel = new ViewModelProvider(requireParentFragment().requireParentFragment()).get(ShopInfoViewModel.class);

        LinearLayoutManager layoutManager_coupon = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        LinearLayoutManager layoutManager_event = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recyclerViewBoard.setLayoutManager(layoutManager_event);
        binding.recyclerViewCoupon.setLayoutManager(layoutManager_coupon);

        binding.recyclerViewCoupon.setAdapter(shopEventCouponAdapter);
        binding.recyclerViewBoard.setAdapter(shopEventBoardAdapter);

        //Adapter 연결
        /*ArrayList<CouponModel> dummyCouponItems = new ArrayList<>();
        dummyCouponItems.add(new CouponModel(1, "상점", "가입기념 쿠폰","QBAC12ABCDDIIGPH",10000,"2021-11-03T23:44:51.316992","2021-11-30T00:00:00.113", CouponType.FIX,10000,null));
        dummyCouponItems.add(new CouponModel(1, "상점", "단골손님 쿠폰","QBAC12ABCDDIIGPH",10000,"2021-11-03T23:44:51.316992","2021-12-30T00:00:00.123",CouponType.RATE,null,30.0));
        dummyCouponItems.add(new CouponModel(1, "상점", "100번째 가입손님 쿠폰","1234567812345678",10000,null,null,CouponType.FIX,20000,null));
        dummyCouponItems.add(new CouponModel(1, "상점", "가입기념쿠폰","999999999999",null,null,null,CouponType.RATE,null,10.0));
        shopEventCouponAdapter.setCouponModels(dummyCouponItems);

        //Adapter 연결
        ArrayList<EventItemModel> dummyboardItems = new ArrayList<>();
        dummyboardItems.add(new EventItemModel(1,"샘플 제목1입니다","샘플 작성글 설명입니다. \n 오늘의 이벤트에 대한 소식입니다. \n 최대 50글자 까지 보여줍니다. \n 최대 50글자 까지 보여줍니다.", "2021-11-03T23:44:51.316992", WritingType.EVENT, null));
        dummyboardItems.add(new EventItemModel(1,"샘플 제목2입니다","샘플 작성글 설명입니다. \n 오늘의 이벤트에 대한 소식입니다. \n 최대 50글자 까지 보여줍니다.", "2021-11-04T12:44:51.316992", WritingType.PROMOTION, null));
        dummyboardItems.add(new EventItemModel(1,"샘플 제목3입니다","샘플 작성글 설명입니다. \n 오늘의 이벤트에 대한 소식입니다. \n 최대 50글자 까지 보여줍니다.", "2021-11-05T13:14:51.316992", WritingType.PROMOTION, null));
        dummyboardItems.add(new EventItemModel(1,"샘플 제목4입니다","샘플 작성글 설명입니다. \n 오늘의 이벤트에 대한 소식입니다. \n 최대 50글자 까지 보여줍니다. \n 최대 50글자 까지 보여줍니다.", "2021-11-06T11:11:51.316992", WritingType.EVENT, null));
        dummyboardItems.add(new EventItemModel(1,"샘플 제목5입니다","샘플 작성글 설명입니다. \n 오늘의 이벤트에 대한 소식입니다. \n 최대 50글자 까지 보여줍니다. \n 최대 50글자 까지 보여줍니다.", "2021-11-07T07:12:51.316992", WritingType.PROMOTION, null));
        shopEventBoardAdapter.setShopBoardItemModels(dummyboardItems);*/


        binding.imageButtonBoardArrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //내리기
                binding.imageButtonBoardArrowDown.setVisibility(View.GONE);
                binding.imageButtonBoardArrowUp.setVisibility(View.VISIBLE);
                binding.recyclerViewBoard.setVisibility(View.GONE);
            }
        });

        binding.imageButtonBoardArrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //올리기
                binding.imageButtonBoardArrowDown.setVisibility(View.VISIBLE);
                binding.imageButtonBoardArrowUp.setVisibility(View.GONE);
                binding.recyclerViewBoard.setVisibility(View.VISIBLE);
            }
        });

        binding.imageButtonCouponArrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //내리기
                binding.imageButtonCouponArrowDown.setVisibility(View.GONE);
                binding.imageButtonCouponArrowUp.setVisibility(View.VISIBLE);
                binding.recyclerViewCoupon.setVisibility(View.GONE);
            }
        });

        binding.imageButtonCouponArrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //올리기
                binding.imageButtonCouponArrowDown.setVisibility(View.VISIBLE);
                binding.imageButtonCouponArrowUp.setVisibility(View.GONE);
                binding.recyclerViewCoupon.setVisibility(View.VISIBLE);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ViewModel Setting -> Main에서 유지되게
        shopEventBoardAdapter.setOnItemClickListener(new ShopEventBoardAdapter.OnItemClickListenerBoard() {
            @Override
            public void onItemClick(int writingId) {

                //게시판 상세정보로 이동
                NavHostFragment.findNavController(getParentFragment().getParentFragment())
                        .navigate(ShopMainFragmentDirections.actionShopMainFragmentToShopEventDetailFragment(
                                shopInfoViewModel.getShopId().getValue()
                                , writingId, shopInfoViewModel.getShopName().getValue()));
            }
        });
    }

    @Override
    public void onResume(){
        int storeId = new ViewModelProvider(getParentFragment().getParentFragment()).get(ShopInfoViewModel.class).getShopId().getValue();

        shopEventViewModel.requestShopCoupon(storeId,
                ShopEventFragment.this::onSuccessCoupon,
                ShopEventFragment.this::onFailed,
                ShopEventFragment.this::onNetworkError);

        shopEventViewModel.requestShopEvent(storeId,
                ShopEventFragment.this::onSuccessEvent,
                ShopEventFragment.this::onFailed,
                ShopEventFragment.this::onNetworkError);

        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onSuccessCoupon(CouponListDto couponModels){
        shopEventCouponAdapter.storeName = shopInfoViewModel.getShopName().getValue();
        if(couponModels.getCouponModels() != null){
            shopEventViewModel.setCouponModels(couponModels.getCouponModels());
        }
        shopEventCouponAdapter.setCouponModels(shopEventViewModel.getCouponModels());
        shopEventCouponAdapter.notifyDataSetChanged();
    }

    private void onSuccessEvent(EventItemListDto eventItemModels){
        if(eventItemModels.getEventItemModels() != null){
            shopEventViewModel.setEventItemModels(eventItemModels.getEventItemModels());
        }
        shopEventBoardAdapter.setShopBoardItemModels(shopEventViewModel.getEventItemModels());
        shopEventBoardAdapter.notifyDataSetChanged();
    }

    private void onFailed() {
        Toast.makeText(getContext(),getResources().getString(R.string.shop_error), Toast.LENGTH_LONG).show();
    }

    private void onNetworkError() {
        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_global_networkErrorDialog);
    }
}