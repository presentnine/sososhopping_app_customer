package com.sososhopping.customer.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ShopViewModel extends ViewModel {
    private int shopId;
    private MutableLiveData<ArrayList<ShopInfoShort>> recyclerViewList = new MutableLiveData<>();
}
