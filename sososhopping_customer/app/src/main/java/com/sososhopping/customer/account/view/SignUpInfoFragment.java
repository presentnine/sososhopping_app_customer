package com.sososhopping.customer.account.view;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
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
import com.sososhopping.customer.account.view.textValidate.NameWatcher;
import com.sososhopping.customer.account.view.textValidate.NickNameWatcher;
import com.sososhopping.customer.account.view.textValidate.PhoneWatcher;
import com.sososhopping.customer.databinding.AccountSignUpInfoBinding;


public class SignUpInfoFragment extends Fragment {

    private NavController navController;
    private SignUpViewModel mViewModel;
    private AccountSignUpInfoBinding binding;

    private Boolean dupChecked = false;
    private Boolean phoneChecked = false;

    public static SignUpInfoFragment newInstance() {
        return new SignUpInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.account_sign_up_info, container, false);
        binding.textViewDupChecked.setVisibility(View.INVISIBLE);
        binding.textViewPhoneCheck.setVisibility(View.INVISIBLE);

        //도로명 검색 API와 연결해야 함

        binding.textFieldSignUpName.getEditText().addTextChangedListener(new NameWatcher(binding.textFieldSignUpName, getResources().getString(R.string.signup_error_name)));

        binding.textFieldSignUpPhone.setCounterMaxLength(11);
        binding.textFieldSignUpPhone.getEditText().setInputType(InputType.TYPE_CLASS_PHONE);
        /*binding.textFieldSignUpPhone.getEditText()
                .addTextChangedListener(new PhoneNumberFormattingTextWatcher());*/
        binding.textFieldSignUpPhone.getEditText()
                .addTextChangedListener(new PhoneWatcher(binding.textFieldSignUpPhone,getResources().getString(R.string.signup_error_phone)));

        binding.textFieldSignUpNickname.setCounterMaxLength(8);
        binding.textFieldSignUpNickname.getEditText()
                .addTextChangedListener(new NickNameWatcher(binding.textFieldSignUpNickname,
                        binding.textViewDupChecked, getResources().getString(R.string.signup_error_nickname)));

        binding.textViewDupChecked.setVisibility(View.INVISIBLE);
        binding.textViewPhoneCheck.setVisibility(View.INVISIBLE);

        binding.buttonDupCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.editTextSignUpNickname.getError() != null){
                    Toast.makeText(getContext(),"별명을 정확히 입력하여야 합니다",Toast.LENGTH_SHORT).show();
                    return;
                }

                //중복확인 API 구현해야함
                dupChecked = true;

                if(!dupChecked){
                    binding.textViewDupChecked.setText("사용이 불가능합니다.");
                }
                else{
                    binding.textViewDupChecked.setText("사용 가능");
                }
                binding.textViewDupChecked.setVisibility(View.VISIBLE);
            }
        });

        binding.buttonPhoneCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.textFieldSignUpPhone.getError() != null) {
                    Toast.makeText(getContext(),"전화번호를 정확히 입력하여야 합니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                //형식상 번호인증 + 중복여부 확인
                phoneChecked = true;

                //번호가 가입되었는지도 확인해야함
                if(!phoneChecked){
                    binding.textViewPhoneCheck.setText("이미 가입한 번호입니다");
                }
                else{
                    binding.editTextSignUpPhone.setFocusable(false);
                    binding.editTextSignUpPhone.setClickable(false);
                    binding.textViewPhoneCheck.setText("인증완료");
                }
                binding.textViewPhoneCheck.setVisibility(View.VISIBLE);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        mViewModel = new ViewModelProvider(requireParentFragment()).get(SignUpViewModel.class);
        // TODO: Use the ViewModel

        //다음화면으로
        binding.buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkLayoutEmpty();

                //변경되었었으면 false로
                if(binding.textViewDupChecked.getVisibility() == View.INVISIBLE){
                    dupChecked = false;
                }

                if(!phoneChecked || !dupChecked ||
                        binding.textFieldSignUpName.getError() != null ||
                        binding.textFieldSignUpPhone.getError() != null ||
                        binding.textFieldSignUpNickname.getError() != null ||
                        binding.textFieldSignUpRoadAddress.getError() != null ||
                        binding.textFieldSignUpDetailAddress.getError() != null) {
                    Toast.makeText(getContext(),getResources().getString(R.string.signup_error_process), Toast.LENGTH_SHORT).show();
                    return;
                }

                mViewModel.setName(binding.editTextSignUpName.getText().toString());
                mViewModel.setPhone(binding.editTextSignUpPhone.getText().toString());
                mViewModel.setNickName(binding.editTextSignUpNickname.getText().toString());
                mViewModel.setRoadAddress(binding.editTextSignUpRoadAddress.getText().toString());
                mViewModel.setDetailAddress(binding.editTextSignUpDetailAddress.getText().toString());

                //회원가입 절차 진행
                Log.d("회원가입 요청 : ", mViewModel.printVal());

                //다음 fragment로 이동
                navController.navigate(R.id.action_signUpInfoFragment_to_signUpStartFragment);
            }
        });
    }

    public void checkLayoutEmpty(){
        if(TextUtils.isEmpty(binding.editTextSignUpName.getText().toString())){
            binding.textFieldSignUpName.setError(getResources().getString(R.string.signup_error_name));
        }
        if(TextUtils.isEmpty(binding.editTextSignUpPhone.getText().toString())){
            binding.textFieldSignUpPhone.setError(getResources().getString(R.string.signup_error_phone));
        }
        if(TextUtils.isEmpty(binding.editTextSignUpNickname.getText().toString())){
            binding.textFieldSignUpNickname.setError(getResources().getString(R.string.signup_error_nickname));
        }
        if(TextUtils.isEmpty(binding.editTextSignUpRoadAddress.getText().toString())){
            binding.textFieldSignUpRoadAddress.setError(getResources().getString(R.string.signup_error_roadAddress));
        }
        if(TextUtils.isEmpty(binding.editTextSignUpDetailAddress.getText().toString())){
            binding.textFieldSignUpDetailAddress.setError(getResources().getString(R.string.signup_error_detailAddress));
        }
    }

}