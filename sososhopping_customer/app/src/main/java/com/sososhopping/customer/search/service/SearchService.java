package com.sososhopping.customer.search.service;

import com.sososhopping.customer.search.dto.ShopListDto;
import com.sososhopping.customer.search.model.ShopInfoShortModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SearchService {

    @GET("stores")
    Call<ShopListDto> searchByCategory(@Header ("token") String token,
                                       @Query(value = "type") String type,
                                       @Query(value = "lat") Double lat,
                                       @Query(value = "lng") Double lng,
                                       @Query(value = "radius") Integer radius);

    @GET("search")
    Call<ShopListDto> searchBySearch(@Header ("token") String token,
                                     @Query(value = "type") String type,
                                     @Query(value = "lat") Double lat,
                                     @Query(value = "lng") Double lng,
                                     @Query(value = "radius") Integer radius,
                                     @Query(value = "q") String q);

    @GET("search")
    Call<ShopListDto> searchBySearch(@Query(value = "type") String type,
                                     @Query(value = "lat") Double lat,
                                     @Query(value = "lng") Double lng,
                                     @Query(value = "radius") Integer radius,
                                     @Query(value = "q") String q);
}
