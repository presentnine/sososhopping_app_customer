package com.sososhopping.customer.search;

import android.content.Context;
import android.location.LocationManager;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sososhopping.customer.common.gps.CalculateDistance;
import com.sososhopping.customer.common.gps.GPSTracker;
import com.sososhopping.customer.common.types.Location;
import com.sososhopping.customer.common.types.enumType.CategoryType;
import com.sososhopping.customer.search.dto.ShopListDto;
import com.sososhopping.customer.search.model.ShopInfoShortModel;
import com.sososhopping.customer.search.repository.SearchRepository;
import com.sososhopping.customer.shop.model.ShopItemModel;

import java.util.ArrayList;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class HomeViewModel extends ViewModel {
    //1 : 카테고리 / 0 : 검색
    private MutableLiveData<Integer> askType = new MutableLiveData<>();

    private MutableLiveData<Integer> searchType = new MutableLiveData<>();
    private MutableLiveData<String> searchContent = new MutableLiveData<>();
    private MutableLiveData<String> category = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ShopInfoShortModel>> shopList= new MutableLiveData<>();

    private final SearchRepository searchRepository = SearchRepository.getInstance();

    public void setCategory(String s){
        category.setValue(s);
    }
    public void setShopList(ArrayList<ShopInfoShortModel> s) {shopList.setValue(s);}

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

    public void searchCategory(String token,
                               String category,
                               Consumer<ShopListDto> onSuccess,
                               Runnable onError){
        searchRepository.searchCategory(token, category,onSuccess,onError);
    }

    public void calDistance(Context context){

        GPSTracker gpsTracker = GPSTracker.getInstance(context);

        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            if(shopList.getValue() != null){
                for(ShopInfoShortModel s : shopList.getValue()){
                    s.setDistance(CalculateDistance.distance(latitude, s.getLocation().getLat(),
                            longitude, s.getLocation().getLng()) );
                }
            }
        }
        gpsTracker.stopUsingGPS();
    }

}