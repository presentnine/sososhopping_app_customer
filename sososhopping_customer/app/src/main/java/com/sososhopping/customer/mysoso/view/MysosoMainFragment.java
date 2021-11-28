package com.sososhopping.customer.mysoso.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sososhopping.customer.MainActivity;
import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.MysosoMainBinding;

import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;

public class
MysosoMainFragment extends Fragment {

    MysosoMainBinding binding;
    NavController navConroller;
    public static MysosoMainFragment newInstance() {return new MysosoMainFragment();}

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
        binding = MysosoMainBinding.inflate(inflater,container,false);

        //viewmodel 설정



        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Controller 설정
        navConroller = Navigation.findNavController(view);

        //내정보
        binding.constraintLayoutMyinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navConroller.navigate(R.id.action_mysosoMainFragment_to_mysosoMyInfoFragment);
            }
        });


        //관심가게
        binding.constraintLayoutFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //하단바에 관심가게 클릭한 효과
                ((MainActivity) getActivity()).getBinding().bottomNavigation.setSelectedItemId(R.id.menu_interest);
            }
        });

        /*6개 메뉴*/
        binding.imageViewMyPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navConroller.navigate(R.id.action_mysosoMainFragment_to_mysosoPointFragment);
            }
        });

        binding.imageViewMyRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navConroller.navigate(R.id.action_mysosoMainFragment_to_mysosoReviewFragment);
            }
        });

        //쿠폰
        binding.imageViewMyCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navConroller.navigate(R.id.action_mysosoMainFragment_to_mysosoCouponFragment);
            }
        });

        //장바구니
        binding.imageViewShoppingBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //하단바 클릭한 효과
                ((MainActivity) getActivity()).getBinding().bottomNavigation.setSelectedItemId(R.id.menu_cart);
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        //상단바
        ((MainActivity)getActivity()).showTopAppBar();
        ((MainActivity)getActivity()).getBinding().topAppBar.setTitle(getResources().getString(R.string.mysoso));
        ((MainActivity)getActivity()).getBinding().topAppBar.setTitleCentered(true);

        //하단바
        ((MainActivity)getActivity()).showBottomNavigation();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
