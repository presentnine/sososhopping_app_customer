package com.sososhopping.customer.mysoso.service;

import com.sososhopping.customer.mysoso.dto.AddCouponDto;
import com.sososhopping.customer.mysoso.dto.MyCouponsDto;
import com.sososhopping.customer.mysoso.dto.MyReviewsDto;
import com.sososhopping.customer.mysoso.dto.PointDetailDto;
import com.sososhopping.customer.mysoso.dto.PointListDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MysosoService {
    @GET("my/points")
    Call<PointListDto> requestPointList(@Header("token") String token);

    @GET("my/points/{storeId}")
    Call<PointDetailDto> requestPointDetailList(@Header("token") String token, @Path("storeId") int storeId, @Query("at") String date);

    @GET("my/reviews")
    Call<MyReviewsDto> requestMyReviews(@Header("token") String token);

    @GET("my/coupons")
    Call<MyCouponsDto> requestMyCoupons(@Header("token") String token);

    @POST("my/coupons")
    Call<Void> addCoupons(@Header("token") String token, @Body AddCouponDto addCouponDto);

}
