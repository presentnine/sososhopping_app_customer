package com.sososhopping.customer.account.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sososhopping.customer.R;
import com.sososhopping.customer.account.SignUpViewModel;
import com.sososhopping.customer.account.view.textValidate.EmailWatcher;
import com.sososhopping.customer.account.view.textValidate.PasswordDupWatcher;
import com.sososhopping.customer.account.view.textValidate.PasswordWatcher;
import com.sososhopping.customer.databinding.AccountSignUpEmailBinding;

public class SignUpEmailFragment extends Fragment {

    private NavController navController;
    private SignUpViewModel mViewModel;

    private AccountSignUpEmailBinding binding;
    private Boolean dupChecked = false;

    public static SignUpEmailFragment newInstance() {
        return new SignUpEmailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.account_sign_up_email, container, false);
        binding.textViewDupChecked.setVisibility(View.INVISIBLE);

        binding.textFieldSignUpEmail.getEditText()
                .addTextChangedListener(new EmailWatcher(binding.textFieldSignUpEmail,
                        binding.textViewDupChecked, getResources().getString(R.string.signup_error_email)));
        binding.textFieldSignUpPassword.getEditText()
                .addTextChangedListener(new PasswordWatcher(binding.textFieldSignUpPassword, getResources().getString(R.string.signup_error_password)));
        binding.textFieldSignUpPasswordDup.getEditText()
                .addTextChangedListener(new PasswordDupWatcher(binding.textFieldSignUpPassword, binding.textFieldSignUpPasswordDup, getResources().getString(R.string.signup_error_passwordDup)));

        binding.buttonDupCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.textFieldSignUpEmail.getError() != null){
                    Toast.makeText(getContext(),"이메일을 정확히 입력하셔야 합니다",Toast.LENGTH_SHORT).show();
                    return;
                }

                //중복확인 코드 구현해야함
                dupChecked = true;

                if(!dupChecked){
                    binding.textViewDupChecked.setText("사용이 불가능합니다.");
                }
                binding.textViewDupChecked.setVisibility(View.VISIBLE);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireParentFragment()).get(SignUpViewModel.class);
        navController = Navigation.findNavController(view);
        // TODO: Use the ViewModel

        //다음 화면으로
        binding.buttonNextSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkLayoutEmpty();
                //변경되었었으면 false로
                if(binding.textViewDupChecked.getVisibility() == View.INVISIBLE){
                    dupChecked = false;
                }

                if(!dupChecked ||
                        binding.textFieldSignUpEmail.getError() != null ||
                        binding.textFieldSignUpPassword.getError() != null ||
                        binding.textFieldSignUpPasswordDup.getError() != null){
                    Toast.makeText(getContext(),getResources().getString(R.string.signup_error_process),Toast.LENGTH_SHORT).show();
                    return;
                }

                mViewModel.setEmail(binding.textFieldSignUpEmail.getEditText().getText().toString());
                mViewModel.setPassword(binding.textFieldSignUpPassword.getEditText().getText().toString());

                //다음 fragment로 이동
                navController.navigate(R.id.action_signUpIdFragment_to_signUpInfoFragment);
            }
        });
    }

    public void checkLayoutEmpty(){
        if(TextUtils.isEmpty(binding.textFieldSignUpEmail.getEditText().getText().toString())){
            binding.textFieldSignUpEmail.setError(getResources().getString(R.string.signup_error_name));
        }
        if(TextUtils.isEmpty(binding.textFieldSignUpPassword.getEditText().getText().toString())){
            binding.textFieldSignUpPassword.setError(getResources().getString(R.string.signup_error_phone));
        }
        if(TextUtils.isEmpty(binding.textFieldSignUpPasswordDup.getEditText().getText().toString())){
            binding.textFieldSignUpPasswordDup.setError(getResources().getString(R.string.signup_error_nickname));
        }
    }
}