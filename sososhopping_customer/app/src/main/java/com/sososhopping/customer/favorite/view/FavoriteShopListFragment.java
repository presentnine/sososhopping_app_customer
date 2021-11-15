package com.sososhopping.customer.favorite.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sososhopping.customer.MainActivity;
import com.sososhopping.customer.NavGraphDirections;
import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.FavoriteShopListBinding;
import com.sososhopping.customer.favorite.viewmodel.FavoriteViewModel;
import com.sososhopping.customer.search.dto.ShopListDto;
import com.sososhopping.customer.search.view.adapter.ShopListAdapter;

import org.jetbrains.annotations.Nullable;

public class FavoriteShopListFragment extends Fragment {

    private FavoriteShopListBinding binding;
    private FavoriteViewModel favoriteViewModel;
    private NavController navController;


    //shoplist와 동일 사용
    private ShopListAdapter shopListAdapter = new ShopListAdapter();

    public static FavoriteShopListFragment newInstance() {return new FavoriteShopListFragment();}

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
        binding = FavoriteShopListBinding.inflate(inflater,container,false);

        //Viewmodel 설정
        favoriteViewModel = new ViewModelProvider(getActivity()).get(FavoriteViewModel.class);

        //뷰 선언
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recyclerViewShopList.setLayoutManager(layoutManager);
        if(favoriteViewModel.getFavoriteList().getValue() != null){
            shopListAdapter.setShopLists(favoriteViewModel.getFavoriteList().getValue());
        }
        binding.recyclerViewShopList.setAdapter(shopListAdapter);

        //검색
        favoriteViewModel.requestFavorite(
                ((MainActivity)getActivity()).getLoginToken(),
                this::onSearchSuccessed,
                this::onFailedLogIn,
                this::onNetworkError
        );

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Controller 설정
        navController = Navigation.findNavController(view);
        shopListAdapter.setOnItemClickListener(new ShopListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //검색조건으로 이동
                Bundle bundle =  new Bundle();
                bundle.putParcelable("shopInfo", shopListAdapter.getShopLists().get(pos));

            }

            @Override
            public void onFavoriteClick(View v, int pos) {
                //즐겨찾기 해제
            }
        });
    }

    @Override
    public void onResume() {
        //상단바
        ((MainActivity)getActivity()).showTopAppBar();
        ((MainActivity)getActivity()).getBinding().topAppBar.setTitle("관심가게");

        //하단바
        ((MainActivity)getActivity()).showBottomNavigation();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onSearchSuccessed(ShopListDto success){
        favoriteViewModel.getFavoriteList().setValue(success.getShopInfoShortModels());
        favoriteViewModel.calDistance(getContext(), favoriteViewModel.getFavoriteList().getValue());
        shopListAdapter.setShopLists(success.getShopInfoShortModels());
        shopListAdapter.notifyDataSetChanged();
    }

    private void onFailedLogIn(){
        navController.navigate(NavGraphDirections.actionGlobalLogInRequiredDialog().setErrorMsgId(R.string.login_error_token));
    }

    private void onNetworkError() {
        navController.navigate(R.id.action_global_networkErrorDialog);
    }

}
