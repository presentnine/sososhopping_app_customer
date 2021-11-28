package com.sososhopping.customer.cart.view;

import android.content.Context;
import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.navigation.NavController;

import com.sososhopping.customer.R;
import com.sososhopping.customer.cart.viewmodel.PurchaseViewModel;
import com.sososhopping.customer.databinding.PurchaseMainBinding;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PurchaseFragment_Point {

    PurchaseMainBinding binding;
    PurchaseViewModel purchaseViewModel;
    Context context;

    protected void setPointLayout(Resources rs, NavController navController){

        binding.includeLayoutPoint.linearLayoutCouponInfo.setVisibility(View.GONE);

        binding.includeLayoutPoint.buttonCouponUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_purchaseFragment_to_purchaseCouponDialogFragment);
            }
        });

        binding.includeLayoutPoint.buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                purchaseViewModel.getUseCoupon().setValue(null);
            }
        });

        binding.includeLayoutPoint.buttonUseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int myPoint = purchaseViewModel.getShopInfo().getValue().getMyPoint();
                purchaseViewModel.getUsePoint().setValue(-myPoint);
                binding.includeLayoutPoint.editTextUsePoint.setText(String.valueOf(myPoint));
            }
        });

        binding.includeLayoutPoint.editTextUsePoint.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                int maxUse = Math.min(purchaseViewModel.getShopInfo().getValue().getMyPoint(),
                        purchaseViewModel.getTotalPrice().getValue() - purchaseViewModel.getCouponDiscount().getValue());
                if(editable.length() > 0){
                    int usePoint = Integer.parseInt(editable.toString());
                    if(usePoint > maxUse){
                        binding.includeLayoutPoint.textFieldUsePoint.setError(rs.getString(R.string.point_use_err));
                        binding.includeLayoutPoint.textFieldUsePoint.setErrorEnabled(true);
                    }else{
                        binding.includeLayoutPoint.textFieldUsePoint.setError(null);
                        binding.includeLayoutPoint.textFieldUsePoint.setErrorEnabled(false);
                    }
                    purchaseViewModel.getUsePoint().setValue(-usePoint);
                }
            }
        });
    }
}
