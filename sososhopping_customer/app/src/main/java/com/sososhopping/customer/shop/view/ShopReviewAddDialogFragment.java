package com.sososhopping.customer.shop.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.ShopReviewAddDialogBinding;

public class ShopReviewAddDialogFragment extends DialogFragment {
    private ShopReviewAddDialogBinding binding;

    public static ShopReviewAddDialogFragment newInstance(){return new ShopReviewAddDialogFragment();}

    @Override
    public void onStart(){
        super.onStart();
        int width =  getResources().getDimensionPixelSize(R.dimen.popup_width);
        getDialog().getWindow().setLayout(width,  ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = ShopReviewAddDialogBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        binding.buttonWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //제출 시나리오
                String content = binding.editTextReviewContent.getText().toString();
                Float score = binding.ratingBarReview.getRating();

                //종료
                dismiss();
            }
        });
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}
