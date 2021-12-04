package com.sososhopping.customer.search.repository;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.sososhopping.customer.common.retrofit.ApiServiceFactory;
import com.sososhopping.customer.common.types.Location;
import com.sososhopping.customer.search.dto.ShopListDto;
import com.sososhopping.customer.search.model.ShopInfoShortModel;
import com.sososhopping.customer.search.service.SearchService;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {
    private static SearchRepository instance;
    private final SearchService searchService;

    private SearchRepository(){
        this.searchService = ApiServiceFactory.createService(SearchService.class);
    }

    public static synchronized SearchRepository getInstance(){
        if(instance == null){
            instance = new SearchRepository();
        }
        return instance;
    }

    public void searchCategory(String token,
                               String category,
                               Double lat, Double lng,
                               Integer radius,
                               BiConsumer<ShopListDto, Boolean> onSuccess,
                               Runnable onError){
        searchService.searchByCategory(token, category, lat, lng, radius).enqueue(new Callback<ShopListDto>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ShopListDto> call, Response<ShopListDto> response) {
                Log.d("response", response.raw().toString());
                if (response.code() == 200) {
                    onSuccess.accept(response.body(), true);
                } else {
                    onError.run();
                }
            }
            @Override
            public void onFailure(Call<ShopListDto> call, Throwable t) {
                t.printStackTrace();
                onError.run();
            }
        });
    }

    public void searchSearch(String token, String type, String q, Double lat, Double lng, Integer radius,
                             boolean b,
                             BiConsumer<ShopListDto, Boolean> onSuccess,
                             Runnable onError){
        searchService.searchBySearch(token, type, lat, lng, radius, q).enqueue(new Callback<ShopListDto>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ShopListDto> call, Response<ShopListDto> response) {
                if (response.code() == 200) {
                    onSuccess.accept(response.body(),b);
                } else {
                    Log.d("log", response.raw().toString());
                    Log.d("log", response.errorBody().toString());
                    onError.run();
                }
            }

            @Override
            public void onFailure(Call<ShopListDto> call, Throwable t) {
                t.printStackTrace();
                onError.run();
            }
        });

    }
}
