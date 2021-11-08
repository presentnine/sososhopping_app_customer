package com.sososhopping.customer.shop.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import lombok.Getter;


@Getter
public class ShopInfoViewModel extends ViewModel {
    MutableLiveData<Integer> shopId = new MutableLiveData<>();
    MutableLiveData<String> shopName = new MutableLiveData<>();

    public void setShopId(int i){
        this.shopId.setValue(i);
    }

    public void setShopName(String name){
        this.shopName.setValue(name);
    }
}
