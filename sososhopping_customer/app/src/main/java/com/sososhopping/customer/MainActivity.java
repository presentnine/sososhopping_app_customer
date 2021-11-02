package com.sososhopping.customer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

import com.sososhopping.customer.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    static final int[] noNavigationList = {R.id.signUpStartFragment, R.id.signUpIdFragment, R.id.signUpInfoFragment,
            R.id.logInDialogFragment, R.id.findEmailFragment, R.id.findPasswordFragment};

    static final int[] noAppBarList = {R.id.signUpStartFragment, R.id.signUpIdFragment, R.id.signUpInfoFragment,
            R.id.logInDialogFragment, R.id.findEmailFragment, R.id.findPasswordFragment, R.id.home2};

    public NavController navController;
    private NavHostFragment navHostFragment;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Sososhopping_customer_NoActionBar);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        initNavigation();
        binding.bottomNavigation.setSelectedItemId(R.id.menu_home);

        //만약 로그인 안되어있으면 맨 밑을 플로팅 버튼으로 막고 시작
        Boolean logIn = true;
        logIn = false;
        initLoginButton(logIn);
        binding.buttonAccountLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_home2_to_signUpStartFragment);
            }
        });
    }

    public void initLoginButton(Boolean state){
        if (state) {
            binding.buttonAccountLogIn.setVisibility(View.INVISIBLE);
        } else {
            binding.buttonAccountLogIn.setVisibility(View.VISIBLE);
        }
    }

    public boolean checkNoNav(int id){
        for(Integer i : noNavigationList){
            if(i == id){
                return true;
            }
        }
        return false;
    }

    public boolean checkNoAppBar(int id){
        for(Integer i : noAppBarList){
            if(i == id){
                return true;
            }
        }
        return false;
    }

    public void initNavigation(){
        navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {

            @Override
            public void onDestinationChanged(@NonNull @NotNull NavController controller,
                                             @NonNull @NotNull NavDestination destination, @Nullable @org.jetbrains.annotations.Nullable Bundle arguments) {

                if(checkNoNav(destination.getId())){
                    binding.bottomNavigation.setVisibility(View.GONE);
                }else{
                    binding.bottomNavigation.setVisibility(View.VISIBLE);
                }

                if(checkNoAppBar(destination.getId())){
                    binding.appBarLayout.setVisibility(View.GONE);
                }
                else {
                    binding.appBarLayout.setVisibility(View.VISIBLE);
                }

            }
        });

    }
}