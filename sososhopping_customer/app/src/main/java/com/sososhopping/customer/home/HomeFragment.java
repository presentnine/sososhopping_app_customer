package com.sososhopping.customer.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.sososhopping.customer.R;
import com.sososhopping.customer.databinding.HomeBinding;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    private NavController navController;
    private CategoryAdapter categoryAdapter = new CategoryAdapter();
    HomeBinding binding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home, container, false);

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
                }
                else{
                    //검색조건 : 상점
                    binding.textViewItem.setTextColor(getResources().getColor(R.color.text_0));
                    binding.textViewShop.setTextColor(getResources().getColor(R.color.text_400));
                }
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        navController = Navigation.findNavController(view);

        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                String category = categoryAdapter.getCategoryName(pos);

                //해당 카테고리를 담아서 검색 navigate하기
                Log.d("카테고리 검색", category);
            }
        });
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

    public ArrayList<String> getCategoryDetail(){
        ArrayList<String> iconDetail = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.category_icon_explain)));
        iconDetail.add(getResources().getString(R.string.icon_map_explain));
        return iconDetail;
    }

}