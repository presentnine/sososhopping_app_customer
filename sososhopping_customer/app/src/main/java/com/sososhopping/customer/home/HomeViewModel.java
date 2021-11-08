package com.sososhopping.customer.home;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sososhopping.customer.search.ShopInfoShort;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
public class HomeViewModel extends ViewModel {
    private MutableLiveData<Integer> searchType = new MutableLiveData<>();
    private MutableLiveData<String> searchContent = new MutableLiveData<>();
    private MutableLiveData<String> category = new MutableLiveData<>();

    public void setCategory(String s){
        category.setValue(s);
    }

    //1 : 상품 / 0 : 상점
    public void setSearchType(Boolean checked){
        if(checked){
            searchType.setValue(1);
        }else{
            searchType.setValue(0);
        }
    }

    public void setSearchContent(String content){
        if(content != null){
            if(!TextUtils.isEmpty(content)){
                searchContent.setValue(content);
            }
        }
        else{
            searchContent.setValue(null);
        }
    }
}