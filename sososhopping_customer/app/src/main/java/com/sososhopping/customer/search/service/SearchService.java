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
    Call<ShopListDto> searchByCategory(@Query(value = "type") String type );

    @GET("stores")
    Call<ShopListDto> searchByCategory(@Header ("token") String token, @Query(value = "type") String type );
}
