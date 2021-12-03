package com.sososhopping.customer.mysoso.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.sososhopping.customer.MainActivity;
import com.sososhopping.customer.R;
import com.sososhopping.customer.common.Constant;
import com.sososhopping.customer.common.sharedpreferences.SharedPreferenceManager;
import com.sososhopping.customer.databinding.MysosoSettingBinding;

import org.jetbrains.annotations.Nullable;

public class MysosoSettingFragment extends Fragment {

    public static MysosoSettingFragment newInstance() {return new MysosoSettingFragment();}
    MysosoSettingBinding binding;
    NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        //binding 설정
        binding = MysosoSettingBinding.inflate(inflater,container,false);

        //viewmodel 설정


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        //TODO : 이벤트 알림 설정
        binding.switchEvent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
        binding.switchEvent.setEnabled(false);

        //TODO : 채팅 알림 설정
        binding.switchChat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
        binding.switchChat.setEnabled(false);

        //로그아웃
        binding.constraintLayoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그인 정보 삭제
                try {
                    Snackbar.make(binding.getRoot(), getResources().getString(R.string.setting_logout_ask), Snackbar.LENGTH_SHORT)
                            .setAction(R.string.setting_logout, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    logOut();
                                    Snackbar.make(binding.getRoot(), getResources().getString(R.string.setting_logout_success), Snackbar.LENGTH_SHORT).show();
                                }
                            }).show();
                }catch (Exception e){
                    //실패 메시지
                    Snackbar.make(binding.getRoot(), getResources().getString(R.string.setting_logout_failed), Snackbar.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        //데이터 삭제


        //회원탈퇴
        binding.constraintLayoutQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onResume() {
        //상단바
        ((MainActivity)getActivity()).showTopAppBar();
        ((MainActivity)getActivity()).setTopAppBarTitle(getResources().getString(R.string.setting));

        //하단바
        ((MainActivity)getActivity()).hideBottomNavigation();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void logOut(){
        SharedPreferenceManager.deleteString(getContext(), Constant.SHARED_PREFERENCE_KEY_ID);
        SharedPreferenceManager.deleteString(getContext(), Constant.SHARED_PREFERENCE_KEY_PASSWORD);
        ((MainActivity)getActivity()).setLoginToken(null);
        ((MainActivity)getActivity()).setIsLogIn(false);

        ((MainActivity) getActivity()).bottomItemClicked(R.id.menu_home);
    }

}
