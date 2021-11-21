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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.ShopReviewBinding;
import com.sososhopping.customer.shop.dto.ReviewListDto;
import com.sososhopping.customer.shop.model.ReviewModel;
import com.sososhopping.customer.shop.view.adapter.ShopReviewAdapter;
import com.sososhopping.customer.shop.viewmodel.ShopInfoViewModel;
import com.sososhopping.customer.shop.viewmodel.ShopReviewViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class ShopReviewFragment extends Fragment {
    private NavController navController;
    private ShopReviewAdapter shopReviewAdapter = new ShopReviewAdapter();
    private ShopReviewViewModel reviewViewModel = new ShopReviewViewModel();
    private ShopReviewBinding binding;

    int storeId = -1;

    public static ShopReviewFragment newInstance(){return new ShopReviewFragment();}

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = ShopReviewBinding.inflate(inflater, container,false);
        storeId = new ViewModelProvider(getActivity()).get(ShopInfoViewModel.class).getShopId().getValue();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recyclerViewReview.setLayoutManager(layoutManager);
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
                navController.navigate(ShopReviewFragmentDirections.actionShopReviewFragmentToShopReviewAddDialogFragment(storeId));
            }
        });
    }

    @Override
    public void onResume() {
        reviewViewModel.requestShopReviews(storeId,
                this::onSuccess,
                this::onFailed,
                this::onNetworkError);

        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void onSuccess(ReviewListDto reviewModels){
        if(reviewModels.getReviewModels() != null){
            reviewViewModel.setReviewModels(reviewModels.getReviewModels());
        }
        shopReviewAdapter.setReviewModels(reviewViewModel.getReviewModels());
        shopReviewAdapter.notifyDataSetChanged();
    }

    private void onFailed() {
        Toast.makeText(getContext(),getResources().getString(R.string.shop_error), Toast.LENGTH_LONG).show();
    }

    private void onNetworkError() {
        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_global_networkErrorDialog);
    }
}