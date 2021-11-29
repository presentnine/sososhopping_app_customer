package com.sososhopping.customer.purchase.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.customer.MainActivity;
import com.sososhopping.customer.NavGraphDirections;
import com.sososhopping.customer.R;
import com.sososhopping.customer.purchase.viewmodel.PurchaseCouponViewModel;
import com.sososhopping.customer.purchase.viewmodel.PurchaseViewModel;
import com.sososhopping.customer.databinding.PurchaseCouponBinding;
import com.sososhopping.customer.mysoso.dto.MyCouponsDto;
import com.sososhopping.customer.mysoso.view.adapter.ExpandableCouponData;
import com.sososhopping.customer.mysoso.view.adapter.MysosoCouponAdapter;
import com.sososhopping.customer.shop.model.CouponModel;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class PurchaseCouponDialogFragment extends DialogFragment {

    private PurchaseCouponBinding binding;
    private MysosoCouponAdapter mysosoCouponAdapter;
    private PurchaseCouponViewModel purchaseCouponViewModel;
    private PurchaseViewModel purchaseViewModel;

    public static PurchaseCouponDialogFragment newInstance() {return new PurchaseCouponDialogFragment();}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        //binding 설정
        binding = PurchaseCouponBinding.inflate(inflater,container,false);
        LinearLayoutManager layoutManager_coupon = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recyclerViewCoupon.setLayoutManager(layoutManager_coupon);

        mysosoCouponAdapter = new MysosoCouponAdapter(new ArrayList<ExpandableCouponData>());
        binding.recyclerViewCoupon.setAdapter(mysosoCouponAdapter);

        //viewmodel 설정

        purchaseViewModel = new ViewModelProvider(getActivity()).get(PurchaseViewModel.class);
        purchaseCouponViewModel = new ViewModelProvider(this).get(PurchaseCouponViewModel.class);
        purchaseCouponViewModel.requestCoupons(
                ((MainActivity)getActivity()).getLoginToken(),
                purchaseViewModel.getShopInfo().getValue().getStoreId(),
                this::onSuccess,
                this::onFailedLogIn,
                this::onFailed,
                this::onNetworkError
        );



        //등록
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = binding.editTextAddCode.getText().toString();

                if(code.length() < 16){
                    Toast.makeText(getContext(),getResources().getString(R.string.event_coupon_shortLength),Toast.LENGTH_SHORT).show();
                    return;
                }

                int msgCode[]  = new int[2];
                msgCode[0] = R.string.event_coupon_addSucc;
                msgCode[1] = R.string.event_coupon_addFail;

                //쿠폰 저장
                purchaseCouponViewModel.addShopCoupon(((MainActivity)getActivity()).getLoginToken(),
                        binding.editTextAddCode.getText().toString(),
                        msgCode,
                        PurchaseCouponDialogFragment.this::onResult,
                        PurchaseCouponDialogFragment.this::onFailedLogIn,
                        PurchaseCouponDialogFragment.this::onNetworkError
                );
            }
        });

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
                dismiss();
            }
        });

        mysosoCouponAdapter.setOnItemClickListener(new MysosoCouponAdapter.OnItemClickListenerChild() {
            @Override
            public void onItemClick(CouponModel couponModel) {
                //돌아가기
                purchaseViewModel.getUseCoupon().setValue(couponModel);
                getActivity().onBackPressed();
                dismiss();
            }

            @Override
            public void onItemLongClick(CouponModel couponModel) {
            }
        });



    }

    @Override
    public void onStart(){
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(R.drawable.drawable_round_background));
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void onSuccess(MyCouponsDto dto){
        if(dto != null){
            purchaseCouponViewModel.setMyCoupons(dto.getCoupons());
            mysosoCouponAdapter.setItems(purchaseCouponViewModel.parser());
            mysosoCouponAdapter.notifyDataSetChanged();
        }
    }

    private void onResult(int msgCode) {
        Toast.makeText(getContext(),getResources().getString(msgCode), Toast.LENGTH_SHORT).show();

        //다시 불러오기
        purchaseCouponViewModel.requestCoupons(
                ((MainActivity)getActivity()).getLoginToken(),
                purchaseViewModel.getShopInfo().getValue().getStoreId(),
                this::onSuccess,
                this::onFailedLogIn,
                this::onFailed,
                this::onNetworkError
        );
    }

    private void onFailedLogIn(){
        NavHostFragment.findNavController(this)
                .navigate(NavGraphDirections.actionGlobalLogInRequiredDialog().setErrorMsgId(R.string.login_error_token));
    }

    private void onFailed() {
        Toast.makeText(getContext(),getResources().getString(R.string.shop_error), Toast.LENGTH_LONG).show();
    }

    private void onNetworkError() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_networkErrorDialog);
    }


}
