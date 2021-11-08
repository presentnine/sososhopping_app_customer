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

import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.SearchShopDialogBinding;
import com.sososhopping.customer.home.HomeViewModel;

public class SearchDialogFragment extends DialogFragment {
    private NavController navController;
    private SearchShopDialogBinding binding;
    private HomeViewModel homeViewModel;

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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        navController = Navigation.findNavController(getParentFragment().getView());
        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        binding.switchShopOrItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //검색조건 : 상품
                    binding.textViewShop.setTextColor(getResources().getColor(R.color.text_0));
                    binding.textViewItem.setTextColor(getResources().getColor(R.color.text_400));
                    homeViewModel.setSearchType(isChecked);
                }
                else{
                    //검색조건 : 상점
                    binding.textViewItem.setTextColor(getResources().getColor(R.color.text_0));
                    binding.textViewShop.setTextColor(getResources().getColor(R.color.text_400));
                    homeViewModel.setSearchType(!isChecked);
                }
            }
        });

        binding.textFieldSearch.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.setSearchContent(binding.editTextSearch.getText().toString());
                navController.navigate(R.id.action_searchDialogFragment_to_shopListFragment);
            }
        });
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding = null;
    }
}
