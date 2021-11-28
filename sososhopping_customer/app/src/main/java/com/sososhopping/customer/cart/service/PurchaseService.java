package com.sososhopping.customer.cart.service;

import com.sososhopping.customer.cart.dto.OrderRequestDto;
import com.sososhopping.customer.error.model.ErrorMsg;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PurchaseService {
    @POST("orders")
    Call<Void> requestOrders(@Header("token") String token, @Body OrderRequestDto dto);
}
