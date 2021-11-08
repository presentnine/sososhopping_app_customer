package com.sososhopping.customer.account.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
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
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_search_height);
        getDialog().getWindow().setLayout(width, height);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = AccountLogInDialogBinding.inflate(inflater, container, false);
        binding.editTextLogInEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                binding.textFieldLogInEmail.setError(null);
                binding.textFieldLogInEmail.setErrorEnabled(false);
            }
        });

        binding.editTextLogInPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                binding.textFieldLogInPassword.setError(null);
                binding.textFieldLogInPassword.setErrorEnabled(false);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        navController = Navigation.findNavController(getParentFragment().getView());

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
                navController.navigate(R.id.action_logInDialogFragment_to_home2);
            }
        });
    }

    public void checkLayoutEmpty(){
        if(TextUtils.isEmpty(binding.editTextLogInEmail.getText().toString())){
            binding.textFieldLogInEmail.setError(getResources().getString(R.string.login_email));
        }
        if(TextUtils.isEmpty(binding.editTextLogInPassword.getText().toString())){
            binding.textFieldLogInPassword.setError(getResources().getString(R.string.login_password));
        }
    }
}