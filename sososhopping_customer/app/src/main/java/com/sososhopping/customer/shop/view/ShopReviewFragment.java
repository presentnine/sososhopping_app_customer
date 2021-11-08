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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.ShopReviewBinding;
import com.sososhopping.customer.shop.view.adapter.ShopReviewAdapter;
import com.sososhopping.customer.shop.model.ReviewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class ShopReviewFragment extends Fragment {
    private NavController navController;
    private ShopReviewAdapter shopReviewAdapter = new ShopReviewAdapter();
    private ShopReviewBinding binding;

    public static ShopReviewFragment newInstance(){return new ShopReviewFragment();}

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = ShopReviewBinding.inflate(inflater, container,false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recyclerViewReview.setLayoutManager(layoutManager);

        //Adapter연결
        ArrayList<ReviewModel> dummyItems = new ArrayList<>();
        dummyItems.add(new ReviewModel(1,1,"김손님", "가상의 리뷰입니다",4.0F));
        dummyItems.add(new ReviewModel(1,1,"이손님", "가상의 리뷰입니다\n두 줄도 확인해보겠습니다.",3.0F));
        dummyItems.add(new ReviewModel(1,1,"박손님", "가상의 리뷰입니다\n두 줄도 확인해보겠습니다.\n세 줄도 확인해보겠습니다.",2.0F));
        dummyItems.add(new ReviewModel(1,1,"최손님", "가상의 리뷰입니다\n두 줄도 확인해보겠습니다. \n세 줄도 확인해보겠습니다. \n네 줄도 확인해보겠습니다.",1.0F));
        dummyItems.add(new ReviewModel(1,1,"조손님", "가상의 리뷰입니다\n두 줄도 확인해보겠습니다.",5.0F));
        shopReviewAdapter.setReviewModels(dummyItems);
        binding.recyclerViewReview.setAdapter(shopReviewAdapter);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.buttonAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //리뷰 작성 Dialog
                navController.navigate(R.id.action_shopReviewFragment_to_shopReviewAddDialogFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}