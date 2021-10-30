package com.sososhopping.customer.account;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.AccountLogInDialogBinding;

public class LogInDialogFragment extends DialogFragment {

    private NavController navController;
    private AccountLogInDialogBinding binding;
    private String email;
    private String password;

    public static LogInDialogFragment newInstance() {
        return new LogInDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.account_log_in_dialog, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        navController = Navigation.findNavController()

        //아이디찾기
        binding.buttonFindEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_logInDialogFragment_to_findEmailFragment);
            }
        });

        //비밀번호찾기
        binding.buttonFindPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_logInDialogFragment_to_findPasswordFragment);
            }
        });

        //로그인
        binding.buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLayoutEmpty();

                if(binding.textFieldLogInEmail.getError() != null ||
                        binding.textFieldLogInEmail.getError() != null){
                    Toast.makeText(getContext(),getResources().getString(R.string.signup_error_process), Toast.LENGTH_SHORT).show();
                    return;
                }

                //로그인 프로세스
                email = binding.editTextLogInEmail.getText().toString().trim();
                password = binding.editTextLogInPassword.getText().toString().trim();
            }
        });
    }

    public void checkLayoutEmpty(){
        if(TextUtils.isEmpty(binding.editTextLogInEmail.getText().toString())){
            binding.textFieldLogInEmail.setError(getResources().getString(R.string.login_email));
        }
        if(TextUtils.isEmpty(binding.editTextLogInPassword.getText().toString())){
            binding.textFieldLogInEmail.setError(getResources().getString(R.string.login_password));
        }
    }
}