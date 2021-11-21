package com.sososhopping.customer.search.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.sososhopping.customer.MainActivity;
import com.sososhopping.customer.R;
import com.sososhopping.customer.common.types.enumType.CategoryType;
import com.sososhopping.customer.databinding.HomeBinding;
import com.sososhopping.customer.search.dto.ShopListDto;
import com.sososhopping.customer.search.model.ShopInfoShortModel;
import com.sososhopping.customer.search.view.adapter.CategoryAdapter;
import com.sososhopping.customer.search.HomeViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment{

    private NavController navController;
    private CategoryAdapter categoryAdapter = new CategoryAdapter();
    private HomeViewModel homeViewModel;

    HomeBinding binding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        ((MainActivity)getActivity()).hideTopAppBar();
        ((MainActivity)getActivity()).showBottomNavigation();
        ((MainActivity)getActivity()).initLoginButton();
        super.onResume();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomeBinding.inflate(inflater, container, false);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),5);
        binding.recyclerViewCategory.setLayoutManager(gridLayoutManager);
        categoryAdapter.setCategory(getCategoryDetail(), getCategoryIconId());
        binding.recyclerViewCategory.setAdapter(categoryAdapter);

        binding.switchShopOrItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //검색조건 : 상품
                    binding.textViewShop.setTextColor(getResources().getColor(R.color.text_0));
                    binding.textViewItem.setTextColor(getResources().getColor(R.color.text_400));
                    homeViewModel.setSearchType(isChecked);
                }
                else{
                    //검색조건 : 상점
                    binding.textViewItem.setTextColor(getResources().getColor(R.color.text_0));
                    binding.textViewShop.setTextColor(getResources().getColor(R.color.text_400));
                    homeViewModel.setSearchType(!isChecked);
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        navController = Navigation.findNavController(view);

        //다른데서 왔으면 바로 토스해주기
        /*if(getArguments() != null){
            if(getArguments().getParcelable("shopInfo") != null){
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.shop_graph, getArguments());
            }
        }*/



        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                String category = categoryAdapter.getCategoryName(pos);

                if(category.equals(CategoryType.MAP.toString())){
                    //전체검색으로 넘어가게
                    homeViewModel.getAskType().setValue(0);
                    navController.navigate(HomeFragmentDirections.actionHome2ToShopMapFragment(R.id.home2));
                }
                else{
                    //해당 카테고리를 담아서 검색 navigate하기
                    Log.d("카테고리 검색", category);

                    //ViewModel 설정 후 이동
                    homeViewModel.getAskType().setValue(1);
                    homeViewModel.setCategory(category);
                    homeViewModel.setSearchContent(null);
                    navController.navigate(R.id.action_home2_to_shopListFragment);
                }

            }
        });

        binding.textFieldSearch.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.getAskType().setValue(0);
                homeViewModel.setSearchContent(binding.editTextSearch.getText().toString());
                navController.navigate(R.id.action_home2_to_shopListFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public ArrayList<Integer> getCategoryIconId(){
        ArrayList<Integer> iconId = new ArrayList<>();
        ArrayList<String> iconResName = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.category_icon_detail)));
        //지도까지 추가
        iconResName.add(getResources().getString(R.string.icon_map));
        String packName = this.getContext().getPackageName();

        for(String s : iconResName){
            iconId.add(getResources().getIdentifier(s,"drawable",packName));
        }
        return iconId;
    }

    public ArrayList<CategoryType> getCategoryDetail(){
        ArrayList<CategoryType> iconDetail = new ArrayList<>(Arrays.asList(CategoryType.values()));
        return iconDetail;
    }
}