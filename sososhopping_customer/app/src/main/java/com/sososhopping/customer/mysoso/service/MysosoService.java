package com.sososhopping.customer.mysoso.service;

import com.sososhopping.customer.mysoso.dto.MyReviewsDto;
import com.sososhopping.customer.mysoso.dto.PointDetailDto;
import com.sososhopping.customer.mysoso.dto.PointListDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MysosoService {
    @GET("my/points")
    Call<PointListDto> requestPointList(@Header("token") String token);

    @GET("my/points/{storeId}")
    Call<PointDetailDto> requestPointDetailList(@Header("token") String token, @Path("storeId") int storeId, @Query("at") String date);

    @GET("my/reviews")
    Call<MyReviewsDto> requestMyReviews(@Header("token") String token);
}
