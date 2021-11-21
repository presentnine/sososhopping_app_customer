package com.sososhopping.customer.mysoso.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.customer.MainActivity;
import com.sososhopping.customer.NavGraphDirections;
import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.MysosoMyreviewBinding;
import com.sososhopping.customer.mysoso.dto.MyReviewsDto;
import com.sososhopping.customer.mysoso.dto.PointListDto;
import com.sososhopping.customer.mysoso.model.MyreviewModel;
import com.sososhopping.customer.mysoso.viemodel.MyReviewViewModel;
import com.sososhopping.customer.mysoso.view.adapter.MysosoReviewAdapter;

public class MysosoReviewFragment extends Fragment {
    MysosoMyreviewBinding binding;
    NavController navConroller;

    private MysosoReviewAdapter mysosoReviewAdapter = new MysosoReviewAdapter();
    private MyReviewViewModel myReviewViewModel;

    public static MysosoReviewFragment newInstance() {return new MysosoReviewFragment();}

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
        binding = MysosoMyreviewBinding.inflate(inflater,container,false);

        binding.recyclerViewReview.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false)
        );
        binding.recyclerViewReview.setAdapter(mysosoReviewAdapter);

        //viewmodel 설정
        myReviewViewModel = new MyReviewViewModel();
        myReviewViewModel.requestMyReview(((MainActivity)getActivity()).getLoginToken(),
                this::onSuccess,
                this::onFailedLogIn,
                this::onFailed,
                this::onNetworkError);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Controller 설정
        navConroller = Navigation.findNavController(view);
        super.onViewCreated(view, savedInstanceState);

        mysosoReviewAdapter.setOnItemClickListener(new MysosoReviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MyreviewModel reviewModel) {
                //삭제 / 이동할지 선택하기
            }
        });
    }

    @Override
    public void onResume() {
        //상단바
        ((MainActivity)getActivity()).showTopAppBar();
        ((MainActivity)getActivity()).getBinding().topAppBar.setTitle(getResources().getString(R.string.mysoso_myRating));

        //하단바
        ((MainActivity)getActivity()).showBottomNavigation();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onSuccess(MyReviewsDto dto){
        if(dto != null){
            mysosoReviewAdapter.setReviewModels(dto.getMyreviews());
        }
        mysosoReviewAdapter.notifyDataSetChanged();
    }

    private void onFailedLogIn(){
        NavHostFragment.findNavController(this)
                .navigate(NavGraphDirections.actionGlobalLogInRequiredDialog().setErrorMsgId(R.string.login_error_token));
    }

    private void onFailed() {
        Toast.makeText(getContext(),getResources().getString(R.string.shop_error), Toast.LENGTH_LONG).show();
    }

    private void onNetworkError() {
        NavHostFragment.findNavController(this).navigate(R.id.action_global_networkErrorDialog);
        getActivity().onBackPressed();
    }
}
