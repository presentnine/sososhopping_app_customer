package com.sososhopping.customer.account.service;

import com.sososhopping.customer.account.dto.LogInRequestDto;
import com.sososhopping.customer.account.dto.LogInResponseDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LogInService {

    @POST("auth/login")
    Call<LogInResponseDto> requestLogin(@Body LogInRequestDto dto);
}
