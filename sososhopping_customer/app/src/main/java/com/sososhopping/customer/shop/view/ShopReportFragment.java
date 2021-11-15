package com.sososhopping.customer.shop.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.sososhopping.customer.MainActivity;
import com.sososhopping.customer.NavGraphDirections;
import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.ShopReportBinding;
import com.sososhopping.customer.shop.viewmodel.ShopInfoViewModel;

import org.jetbrains.annotations.Nullable;

public class ShopReportFragment extends Fragment {

    private ShopReportBinding binding;
    private ShopInfoViewModel shopInfoViewModel;
    private NavController navController;

    public static ShopReportFragment newInstance() {return new ShopReportFragment();}

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
        binding = ShopReportBinding.inflate(inflater,container,false);

        //viewmodel 설정
        shopInfoViewModel = new ViewModelProvider(getActivity()).get(ShopInfoViewModel.class);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Controller 설정
        navController = Navigation.findNavController(view);

        //위치
        binding.buttonShopMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shopInfoViewModel.getLocation().getValue() != null){
                    navController.navigate(ShopReportFragmentDirections.actionShopReportFragmentToShopMapFragment(R.id.shopMainFragment)
                            .setLat((float)shopInfoViewModel.getLocation().getValue().getLat())
                            .setLng((float)shopInfoViewModel.getLocation().getValue().getLng()));
                }
            }
        });


        //전화하기
        binding.buttonShopCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shopInfoViewModel.getPhone().getValue() != null){
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+shopInfoViewModel.getPhone().getValue())));
                }
            }
        });

        //채팅하기
        binding.buttonShopChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //신고 전송
        binding.buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.editTextReportContent.getText().length() < 29){
                    Toast.makeText(getContext(),getResources().getString(R.string.report_warning), Toast.LENGTH_SHORT).show();
                    return;
                }

                shopInfoViewModel.inputReport(
                        ((MainActivity)getActivity()).getLoginToken(),
                        shopInfoViewModel.getShopId().getValue(),
                        binding.editTextReportContent.getText().toString(),
                        ShopReportFragment.this::onSuccess,
                        ShopReportFragment.this::onFailedLogIn,
                        ShopReportFragment.this::onFailed,
                        ShopReportFragment.this::onNetworkError
                );

            }
        });
    }

    @Override
    public void onResume() {
        //상단바
        ((MainActivity)getActivity()).showTopAppBar();
        ((MainActivity)getActivity()).getBinding().topAppBar.setTitle(getResources().getString(R.string.report_title));

        //하단바
        ((MainActivity)getActivity()).hideBottomNavigation();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onSuccess(){
        Toast.makeText(getContext(),getResources().getString(R.string.report_input), Toast.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }

    private void onFailedLogIn(){
        NavHostFragment.findNavController(getParentFragment().getParentFragment())
                .navigate(NavGraphDirections.actionGlobalLogInRequiredDialog().setErrorMsgId(R.string.login_error_token));
    }

    private void onFailed() {
        Toast.makeText(getContext(),getResources().getString(R.string.shop_error), Toast.LENGTH_LONG).show();
    }

    private void onNetworkError() {
        NavHostFragment.findNavController(getParentFragment().getParentFragment()).navigate(R.id.action_global_networkErrorDialog);
    }

}
