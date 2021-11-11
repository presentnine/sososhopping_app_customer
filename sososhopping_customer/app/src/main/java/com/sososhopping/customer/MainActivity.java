package com.sososhopping.customer;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.google.android.material.navigation.NavigationBarView;
import com.sososhopping.customer.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public NavController navController;
    private NavHostFragment navHostFragment;
    private ActivityMainBinding binding;

    //로그인용
    Boolean isLogIn = false;
    String loginToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Sososhopping_customer_NoActionBar);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        //navController.addOnDestinationChangedListener(new CustomDestinationChangedListener(binding,this));


        //하단바
       NavigationUI.setupWithNavController(binding.bottomNavigation,navController);
       binding.bottomNavigation.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
           @Override
           public void onNavigationItemReselected(@NonNull @NotNull MenuItem item) {

               switch (item.getItemId()){
                   case R.id.navigation_shop:{
                       getViewModelStore().clear();
                       navController.navigate(R.id.navigation_shop, null, new NavOptions.Builder().setPopUpTo(R.id.nav_shop_graph,true).build());
                       break;
                   }

                   case R.id.signUpStartFragment:{
                       break;
                   }
               }
           }
       });

        //약 로그인 안되어있으면 맨 밑을 플로팅 버튼으로 막고 시작
        initLoginButton(isLogIn);
        binding.buttonAccountLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_global_navigation_login);
            }
        });

        setSupportActionBar(binding.topAppBar);
        binding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getAppKeyHash();
        //권한 요청
        getPermission();
    }


    public ActivityMainBinding getBinding(){
        return binding;
    }

    public void getPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck == PackageManager.PERMISSION_DENIED){ //포그라운드 위치 권한 확인
            //위치 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
    }

    //해시 키 값 구하기
    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("name not found", e.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    //뒤로가기
    private boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        int start = Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination().getId();
        if (start == R.id.home2) {
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(MainActivity.this, "종료하시려면 한번 더 눌러주세요", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
        else {
            super.onBackPressed();
        }
    }

    public void initLoginButton(Boolean state){
        if (state) {
            binding.buttonAccountLogIn.setVisibility(View.GONE);
        } else {
            binding.buttonAccountLogIn.setVisibility(View.VISIBLE);
        }
        this.isLogIn = state;
    }

    public void showBottomNavigation(){
        binding.bottomNavigation.setVisibility(View.VISIBLE);
    }
    public void hideBottomNavigation(){
        binding.bottomNavigation.setVisibility(View.GONE);
    }
    public void showTopAppBar(){
        binding.topAppBar.setVisibility(View.VISIBLE);
    }
    public void hideTopAppBar(){
        binding.topAppBar.setVisibility(View.GONE);
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public void setIsLogIn(boolean is){
        this.isLogIn = is;
    }
}