package com.sososhopping.customer.account.service;

import com.sososhopping.customer.account.dto.EmailDupCheckRequestDto;
import com.sososhopping.customer.account.dto.SignUpRequestDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpService {
    @POST("auth/signup")
    Call<Void> requestSignUp(@Body SignUpRequestDto dto);

    @POST("auth/signup/validation")
    Call<Void> requestEmailDuplicationCheck(@Body EmailDupCheckRequestDto dto);

    //닉네임 중복 api 필요
}
