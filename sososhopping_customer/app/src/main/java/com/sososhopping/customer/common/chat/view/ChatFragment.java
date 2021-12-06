package com.sososhopping.customer.common.chat.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sososhopping.customer.HomeActivity;
import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.ChatListBinding;

import org.jetbrains.annotations.Nullable;

public class ChatFragment extends Fragment {


    public static ChatFragment newInstance() {return new ChatFragment();}
    ChatListBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //메뉴 변경 확인
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu,inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_top_none, menu);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        //binding 설정
        binding = ChatListBinding.inflate(inflater, container, false);

        //viewmodel 설정

        //Controller 설정

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        //상단바
        ((HomeActivity)getActivity()).showTopAppBar();
        ((HomeActivity)getActivity()).getBinding().topAppBar.setTitle("채팅");
        ((HomeActivity)getActivity()).getBinding().topAppBar.setOnClickListener(null);
        ((HomeActivity)getActivity()).setTopAppBarHome(false);

        //하단바
        ((HomeActivity)getActivity()).showBottomNavigation();
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
