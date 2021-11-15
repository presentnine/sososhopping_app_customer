package com.sososhopping.customer.favorite.service;

import com.sososhopping.customer.search.dto.ShopListDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface FavoriteService {
    @GET("my/interest_store")
    Call<ShopListDto> requestFavoriteList(@Header("token") String token);
}
