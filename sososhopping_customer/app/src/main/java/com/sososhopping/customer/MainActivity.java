package com.sososhopping.customer;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationBarView;
import com.sososhopping.customer.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {


    public NavController navController;
    private NavHostFragment navHostFragment;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Sososhopping_customer_NoActionBar);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        navController.addOnDestinationChangedListener(new CustomDestinationChangedListener(binding,this));

        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);

        //만약 로그인 안되어있으면 맨 밑을 플로팅 버튼으로 막고 시작
        Boolean logIn = true;
        //logIn = false;
        initLoginButton(logIn);
        binding.buttonAccountLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_home2_to_signUpStartFragment);
            }
        });

        setSupportActionBar(binding.topAppBar);
        binding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void initLoginButton(Boolean state){
        if (state) {
            binding.buttonAccountLogIn.setVisibility(View.GONE);
        } else {
            binding.buttonAccountLogIn.setVisibility(View.VISIBLE);
        }
    }

    public ActivityMainBinding getBinding(){
        return binding;
    }
}