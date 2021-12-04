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
import com.sososhopping.customer.common.types.enumType.SearchType;
import com.sososhopping.customer.search.dto.ShopListDto;
import com.sososhopping.customer.search.model.ShopInfoShortModel;
import com.sososhopping.customer.search.repository.SearchRepository;
import com.sososhopping.customer.shop.model.ShopItemModel;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class HomeViewModel extends ViewModel {

    private final int defaultRadius = 100000000;

    //1 : 카테고리 / 0 : 검색
    private MutableLiveData<Integer> askType = new MutableLiveData<>();
    private MutableLiveData<SearchType> searchType = new MutableLiveData<>();
    private MutableLiveData<String> searchContent = new MutableLiveData<>();
    private MutableLiveData<String> category = new MutableLiveData<>();

    //검색결과
    private MutableLiveData<ArrayList<ShopInfoShortModel>> shopList= new MutableLiveData<>();

    private final SearchRepository searchRepository = SearchRepository.getInstance();

    public void setCategory(String s){
        category.setValue(s);
    }
    public void setShopList(ArrayList<ShopInfoShortModel> s) {shopList.setValue(s);}

    public void initHome(){
        this.shopList.setValue(new ArrayList<>());
        this.setSearchType(false);
        this.searchContent.setValue("");
    }

    //1 : 상품 / 0 : 상점
    public void setSearchType(Boolean checked){
        if(checked){
            searchType.setValue(SearchType.ITEM);
        }else{
            searchType.setValue(SearchType.STORE);
        }
    }

    public void setSearchContent(String content){
        searchContent.setValue(content);
    }

    public void searchCategory(String token,
                               String category,
                               Location location,
                               Integer radius,
                               BiConsumer<ShopListDto, Boolean> onSuccess,
                               Runnable onError){

        if(radius == null){
            radius = defaultRadius;
        }
        searchRepository.searchCategory(token, category, location.getLat(), location.getLng(), radius, onSuccess, onError);
    }

    public void searchSearch(String token, SearchType type, String q, Location location, Integer radius, Boolean b,
                             BiConsumer<ShopListDto, Boolean> onSuccess,
                             Runnable onError){
        if(radius == null){
            radius = defaultRadius;
        }
        searchRepository.searchSearch(token, type.toString(), q, location.getLat(), location.getLng(), radius, b, onSuccess,  onError);
    }

    public Location getLocation(Context context){
        GPSTracker gpsTracker = GPSTracker.getInstance(context);
        Location location = null;

        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            location = new Location(latitude, longitude);
        }
        gpsTracker.stopUsingGPS();
        return location;
    }

}