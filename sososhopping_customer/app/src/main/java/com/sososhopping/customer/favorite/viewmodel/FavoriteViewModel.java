package com.sososhopping.customer.favorite.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sososhopping.customer.common.gps.CalculateDistance;
import com.sososhopping.customer.common.gps.GPSTracker;
import com.sososhopping.customer.favorite.repository.FavoriteRepository;
import com.sososhopping.customer.search.dto.ShopListDto;
import com.sososhopping.customer.search.model.ShopInfoShortModel;

import java.util.ArrayList;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteViewModel extends ViewModel {

    private MutableLiveData<ArrayList<ShopInfoShortModel>> favoriteList = new MutableLiveData<>();
    private FavoriteRepository favoriteRepository = FavoriteRepository.getInstance();

    public void requestFavorite(String token,
                                Consumer<ShopListDto> onSuccess,
                                Runnable onFailedLogIn,
                                Runnable onError){

        favoriteRepository.requestFavorite(token,onSuccess,onFailedLogIn,onError);
    }

    public void calDistance(Context context, ArrayList<ShopInfoShortModel> shopList){

        GPSTracker gpsTracker = GPSTracker.getInstance(context);

        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            if(shopList != null){
                for(ShopInfoShortModel s : shopList){
                    s.setDistance(  CalculateDistance.distance(latitude, s.getLocation().getLat(),
                            longitude, s.getLocation().getLng()) );
                }
            }
        }
        gpsTracker.stopUsingGPS();
    }
}
