package com.sososhopping.customer.mysoso.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sososhopping.customer.MainActivity;
import com.sososhopping.customer.databinding.MysosoMyinfoBinding;

import org.jetbrains.annotations.Nullable;

public class MysosoMyInfoFragment extends Fragment {

    MysosoMyinfoBinding binding;
    public static MysosoMyInfoFragment newInstance() {return new MysosoMyInfoFragment();}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        //binding 설정
        binding = MysosoMyinfoBinding.inflate(inflater,container,false);

        //viewmodel 설정

        //Controller 설정

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        //상단바
        ((MainActivity)getActivity()).showTopAppBar();

        //하단바
        super.onResume();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
