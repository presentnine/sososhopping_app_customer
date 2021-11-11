package com.sososhopping.customer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.sososhopping.customer.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

public class CustomDestinationChangedListener implements NavController.OnDestinationChangedListener {

    static final int[] noNavigationList = {R.id.signUpStartFragment, R.id.signUpIdFragment, R.id.signUpInfoFragment,
            R.id.logInDialogFragment, R.id.findEmailFragment, R.id.findPasswordFragment, R.id.shopMainFragment, R.id.shopEventDetailFragment, R.id.shopMapFragment};

    static final int[] noAppBarList = {R.id.signUpStartFragment, R.id.signUpIdFragment, R.id.signUpInfoFragment,
            R.id.logInDialogFragment, R.id.findEmailFragment, R.id.findPasswordFragment, R.id.home2};

    public ActivityMainBinding binding;
    public MainActivity activity;

    public CustomDestinationChangedListener(ActivityMainBinding binding, MainActivity activity) {
        this.activity = activity;
        this.binding = binding;
    }

    @Override
    public void onDestinationChanged(@NonNull @NotNull NavController controller,
                                     @NonNull @NotNull NavDestination destination,
                                     @Nullable @org.jetbrains.annotations.Nullable Bundle arguments) {

        if(checkList(destination.getId(), noNavigationList)){
            binding.bottomNavigation.setVisibility(View.GONE);
        }else{
            binding.bottomNavigation.setVisibility(View.VISIBLE);
        }

        if(checkList(destination.getId(), noAppBarList)){
            binding.appBarLayout.setVisibility(View.GONE);
        }
        else {
            binding.appBarLayout.setVisibility(View.VISIBLE);
        }

        if(destination.getId() != R.id.home2){
            activity.getViewModelStore().clear();
        }else{
            binding.bottomNavigation.getMenu().getItem(0).setChecked(true);
        }
    }

    public boolean checkList(int id, int[] list){
        for(Integer i : list){
            if(i == id){
                return true;
            }
        }
        return false;
    }
}
