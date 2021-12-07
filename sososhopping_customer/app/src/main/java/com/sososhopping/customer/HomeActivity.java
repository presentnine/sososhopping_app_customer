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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sososhopping.customer.account.dto.LogInResponseDto;
import com.sososhopping.customer.account.viewmodel.LogInViewModel;
import com.sososhopping.customer.common.Constant;
import com.sososhopping.customer.common.sharedpreferences.SharedPreferenceManager;
import com.sososhopping.customer.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HomeActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    public NavController navController;
    private NavHostFragment navHostFragment;
    private ActivityMainBinding binding;

    //로그인용
    Boolean isLogIn = false;
    MutableLiveData<String> loginToken = new MutableLiveData<>();

    //권한용
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    //파이어베이스 기능
    public FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public FirebaseUser user;
    public FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    public DatabaseReference ref;
    public String firebaseToken;
    public boolean afterLogin = false;
    public boolean firebaseConnection = false;

    public ActivityMainBinding getBinding() {
        return binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //자동로그인 시도
        autoLogIn();

        setTheme(R.style.Theme_Sososhopping_customer_NoActionBar);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        loginToken.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                initLoginButton();
            }
        });

        //권한 요청
        getPermission();

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        loginToken.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                initLoginButton();
            }
        });

        //하단바 -> 그냥 커스텀으로 사용하기
        binding.bottomNavigation.getMenu().findItem(R.id.menu_home).setChecked(true);
        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_home: {
                        getViewModelStore().clear();
                        binding.bottomNavigation.getMenu().findItem(R.id.menu_home).setChecked(true);
                        navController.navigate(R.id.home2, null, new NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build());
                        break;
                    }
                    case R.id.menu_chat: {
                        getViewModelStore().clear();
                        binding.bottomNavigation.getMenu().findItem(R.id.menu_chat).setChecked(true);
                        navController.navigate(R.id.chatFragment, null, new NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build());
                        break;
                    }

                    case R.id.menu_interest: {
                        getViewModelStore().clear();
                        binding.bottomNavigation.getMenu().findItem(R.id.menu_interest).setChecked(true);
                        navController.navigate(R.id.interestShopListFragment, null, new NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build());
                        break;
                    }

                    case R.id.menu_cart:{
                        getViewModelStore().clear();
                        binding.bottomNavigation.getMenu().findItem(R.id.menu_cart).setChecked(true);
                        navController.navigate(R.id.cartMainFragment, null, new NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build());
                        break;
                    }

                    case R.id.menu_mysoso: {
                        getViewModelStore().clear();
                        binding.bottomNavigation.getMenu().findItem(R.id.menu_mysoso).setChecked(true);
                        navController.navigate(R.id.mysosoMainFragment, null, new NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build());
                        break;
                    }
                }
                return false;
            }
        });

        binding.bottomNavigation.setOnItemReselectedListener(new NavigationBarView.OnItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull @NotNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_home: {
                        getViewModelStore().clear();
                        navController.navigate(R.id.home2, null, new NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build());
                        break;
                    }
                    case R.id.menu_chat: {
                        getViewModelStore().clear();
                        navController.navigate(R.id.chatFragment, null, new NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build());
                        break;
                    }

                    case R.id.menu_interest: {
                        getViewModelStore().clear();
                        navController.navigate(R.id.interestShopListFragment, null, new NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build());
                        break;
                    }

                    case R.id.menu_cart:{
                        getViewModelStore().clear();
                        navController.navigate(R.id.cartMainFragment, null, new NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build());
                        break;
                    }

                    case R.id.menu_mysoso: {
                        getViewModelStore().clear();
                        navController.navigate(R.id.mysosoMainFragment, null, new NavOptions.Builder().setPopUpTo(R.id.nav_graph, true).build());
                        break;
                    }
                }
            }
        });

        //약 로그인 안되어있으면 맨 밑을 플로팅 버튼으로 막고 시작
        initLoginButton();
        binding.buttonAccountLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_global_navigation_login);
            }
        });

        //상단바
        setSupportActionBar(binding.topAppBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getAppKeyHash();
    }


    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    //뒤로가기
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        Log.e("BackStack 수", navHostFragment.getChildFragmentManager().getBackStackEntryCount() + "");
        int start = Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination().getId();
        if(start == R.id.home2){
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(HomeActivity.this, "종료하시려면 한번 더 눌러주세요", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
        else if(start == R.id.conversationFragment){
            bottomItemClicked(R.id.menu_chat);
        }
        else if(navHostFragment.getChildFragmentManager().getBackStackEntryCount() < 1){
            binding.bottomNavigation.setSelectedItemId(R.id.menu_home);
        }
        else{
            super.onBackPressed();
        }
    }


    private void autoLogIn() {
        String id = SharedPreferenceManager.getString(getApplicationContext(), Constant.SHARED_PREFERENCE_KEY_ID);
        String password = SharedPreferenceManager.getString(getApplicationContext(), Constant.SHARED_PREFERENCE_KEY_PASSWORD);

        if (id != null && password != null) {
            LogInViewModel logInViewModel = new LogInViewModel();
            logInViewModel.autoLogin(id, password, this::onLoggedIn, this::onLoginFailed);
        }
    }

    private void onLoginFailed() {
    }

    private void onLoggedIn(LogInResponseDto responseDto) {
        //토큰
        setLoginToken(responseDto.getToken());
        this.setIsLogIn(true);
        afterLoginSuccessFirebaseInit(responseDto.getFirebaseToken());
        initLoginButton();
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_success), Toast.LENGTH_SHORT).show();
    }

    public void initLoginButton() {
        if (isLogIn) {
            binding.buttonAccountLogIn.setVisibility(View.GONE);
            binding.bottomNavigation.setClickable(true);
        } else {
            binding.buttonAccountLogIn.setVisibility(View.VISIBLE);
            binding.bottomNavigation.setClickable(false);
        }
    }
    public void hideLoginButton() {
        binding.buttonAccountLogIn.setVisibility(View.GONE);
    }

    public void showBottomNavigation() {
        binding.bottomNavigation.setVisibility(View.VISIBLE);
    }

    public void hideBottomNavigation() {
        binding.bottomNavigation.setVisibility(View.GONE);
    }

    public void showTopAppBar() {
        binding.topAppBar.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setTopAppBarHome(String title){
        binding.topAppBar.setTitle(title);
        binding.topAppBar.setOnClickListener(null);
        binding.topAppBar.setTitleCentered(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
    public void setTopAppBarNotHome(boolean b){getSupportActionBar().setDisplayHomeAsUpEnabled(b);}

    public void hideTopAppBar() {
        binding.topAppBar.setVisibility(View.GONE);
    }

    public String getLoginToken() {
        return loginToken.getValue();
    }

    public void setLoginToken(String loginToken) {
        this.loginToken.setValue(loginToken);
    }

    public void setIsLogIn(boolean is) {
        this.isLogIn = is;
    }

    public boolean getIsLogIn() {
        return this.isLogIn;
    }

    public void setTopAppBarTitle(String title){
        binding.topAppBar.setTitle(title);
    }

    public void bottomItemClicked(int id){
        binding.bottomNavigation.setSelectedItemId(id);
    }

    public void getPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) { //포그라운드 위치 권한 확인
            Snackbar.make(getBinding().getRoot(), "앱을 사용하기 위해서는 위치정보 접근 권한이 필요합니다.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //위치 권한 요청
                            ActivityCompat.requestPermissions(HomeActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
                        }
                    }).show();
        }
    }

    //권한없으면 꺼버리기
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE && grantResults.length == REQUIRED_PERMISSIONS.length) {
            boolean check_result = true;

            for(int result : grantResults){
                if(result != PackageManager.PERMISSION_GRANTED){
                    check_result = false;
                    break;
                }
            }

            if(check_result){
                return;
            }

            for(String s : permissions){
                //다시보지 않기
                if(!ActivityCompat.shouldShowRequestPermissionRationale(this,s)){
                    Snackbar.make(binding.getRoot(),"권한이 거부되었습니다.\n설정(앱 정보)에서 권한을 허용해야 합니다.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("확인", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                }
                            }).show();
                }
            }

            Snackbar.make(binding.getRoot(),"권한이 거부되었습니다.\n앱을 다시 실행하여 권한을 허용해 주세요.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    }).show();

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

    //앱이 다시 켜지면 firebase 재인증
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuthentication();
    }

    //앱이 백으로 옮겨지는 경우 오프라인 설정
    @Override
    protected void onStop() {
        super.onStop();

        //오프라인 설정
        if (afterLogin == true) {
            ref.child("User").child(this.user.getUid()).child("connection").setValue(false);
            ref.child("User").child(this.user.getUid()).child("lastOnline").setValue(ServerValue.TIMESTAMP);
            mAuth.signOut();
            firebaseConnection = false;
        }
    }

    //수동, 자동 로그인 성공 이후 파이어베이스 초기화
    public void afterLoginSuccessFirebaseInit(String firebaseToken) {
        this.firebaseToken = firebaseToken;

        afterLogin = true;

        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCustomToken(firebaseToken)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = mAuth.getCurrentUser();
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            ref = firebaseDatabase.getReference();

                            //FcmId 설정
                            FirebaseMessaging.getInstance().getToken()
                                    .addOnCompleteListener(new OnCompleteListener<String>() {
                                        @Override
                                        public void onComplete(@NonNull Task<String> task) {
                                            if (!task.isSuccessful()) {
                                                Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                                                return;
                                            }

                                            ref.child("FcmId").child(user.getUid()).setValue(task.getResult());
                                        }
                                    });

                            //온라인 설정
                            ref.child("User").child(user.getUid()).child("connection").setValue(true);
                            firebaseConnection = true;
                        }
                    }
                });
    }

    //파이어베이스 재연결용 인증 (+ 온라인 설정)
    public void firebaseAuthentication() {
        if (afterLogin == true) {
            user = mAuth.getCurrentUser();
            if (user == null) {
                mAuth.signInWithCustomToken(firebaseToken)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    user = mAuth.getCurrentUser();
                                    ref.child("User").child(user.getUid()).child("connection").setValue(true);
                                    firebaseConnection = true;
                                }
                            }
                        });
            }
        }
    }
}