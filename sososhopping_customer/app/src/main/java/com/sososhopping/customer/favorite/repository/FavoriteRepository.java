package com.sososhopping.customer.favorite.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.sososhopping.customer.common.retrofit.ApiServiceFactory;
import com.sososhopping.customer.favorite.service.FavoriteService;
import com.sososhopping.customer.search.dto.ShopListDto;
import com.sososhopping.customer.search.repository.SearchRepository;
import com.sososhopping.customer.search.service.SearchService;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteRepository {

    private static FavoriteRepository instance;
    private final FavoriteService searchService;

    private FavoriteRepository(){
        this.searchService = ApiServiceFactory.createService(FavoriteService.class);
    }

    public static synchronized FavoriteRepository getInstance(){
        if(instance == null){
            instance = new FavoriteRepository();
        }
        return instance;
    }

    public void requestFavorite(String token,
                                Consumer<ShopListDto> onSuccess,
                                Runnable onFailedLogIn,
                                Runnable onError){

        searchService.requestFavoriteList(token).enqueue(new Callback<ShopListDto>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ShopListDto> call, Response<ShopListDto> response) {
                switch (response.code()) {
                    case 200:{
                        onSuccess.accept(response.body());
                        break;
                    }
                        //토큰 검증 실패
                    case 401:{
                        onFailedLogIn.run();
                        break;
                    }

                    default: {
                        onError.run();
                    }
                }
            }

            @Override
            public void onFailure(Call<ShopListDto> call, Throwable t) {
                onError.run();
            }
        });
    }
}
