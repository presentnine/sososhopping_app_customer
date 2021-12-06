package com.sososhopping.customer.search.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sososhopping.customer.HomeActivity;
import com.sososhopping.customer.R;
import com.sososhopping.customer.common.types.enumType.AskType;
import com.sososhopping.customer.common.types.enumType.SearchType;
import com.sososhopping.customer.databinding.SearchShopDialogBinding;
import com.sososhopping.customer.search.HomeViewModel;
import com.sososhopping.customer.search.dto.PageableShopListDto;
import com.sososhopping.customer.search.dto.ShopListDto;

public class SearchDialogFragment extends DialogFragment {
    private NavController navController;
    private SearchShopDialogBinding binding;
    private HomeViewModel homeViewModel;

    private int navigateTo;

    public static SearchDialogFragment newInstance(){return newInstance();}

    @Override
    public void onStart(){
        super.onStart();
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        getDialog().getWindow().setLayout(width,  ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        binding = SearchShopDialogBinding.inflate(inflater,container,false);
        navigateTo = SearchDialogFragmentArgs.fromBundle(getArguments()).getNavigateFrom();
        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        navController = Navigation.findNavController(getParentFragment().getView());
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        //초기 조건 설정
        boolean isChecked = homeViewModel.getSearchType().getValue().equals(SearchType.ITEM);
        binding.switchShopOrItem.setChecked(isChecked);
        switchSetting(isChecked);

        binding.switchShopOrItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchSetting(isChecked);
            }
        });

        binding.textFieldSearch.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //검색모드
                homeViewModel.getAskType().setValue(AskType.Search);
                homeViewModel.setSearchContent(binding.editTextSearch.getText().toString());

                //offset 초기화
                homeViewModel.resetPage();

                homeViewModel.search(
                        ((HomeActivity)getActivity()).getLoginToken(),
                        homeViewModel.getLocation(getContext()),
                        null,
                        0,
                        null,
                        SearchDialogFragment.this::onSearchSuccessedPage,
                        SearchDialogFragment.this::onNetworkError
                );
            }

        });
    }

    public void switchSetting(boolean isChecked){
        if(isChecked){
            //검색조건 : 상품
            binding.textViewShop.setTextColor(getResources().getColor(R.color.text_0));
            binding.textViewItem.setTextColor(getResources().getColor(R.color.text_400));
        }
        else{
            //검색조건 : 상점
            binding.textViewItem.setTextColor(getResources().getColor(R.color.text_0));
            binding.textViewShop.setTextColor(getResources().getColor(R.color.text_400));
        }
        homeViewModel.setSearchType(isChecked);
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }

    private void onSearchSuccessedPage(PageableShopListDto success, Integer navigate){
        homeViewModel.getShopList().setValue(success.getContent());
        homeViewModel.setOffset(success.getPageable().getOffset() + success.getNumberOfElements());

        //리스트로 복귀
        ((HomeActivity)getActivity()).setTopAppBarTitle(homeViewModel.getSearchContent().getValue());
        dismiss();
    }

    private void onNetworkError() {
        navController.navigate(R.id.action_global_networkErrorDialog);
    }

}
