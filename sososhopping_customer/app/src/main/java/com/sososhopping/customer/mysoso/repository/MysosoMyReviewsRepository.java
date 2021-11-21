package com.sososhopping.customer.mysoso.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.sososhopping.customer.common.retrofit.ApiServiceFactory;
import com.sososhopping.customer.mysoso.dto.MyReviewsDto;
import com.sososhopping.customer.mysoso.service.MysosoService;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MysosoMyReviewsRepository {
    private static MysosoMyReviewsRepository instance;
    private final MysosoService mysosoService;

    private MysosoMyReviewsRepository(){
        this.mysosoService = ApiServiceFactory.createService(MysosoService.class);
    }

    public static synchronized MysosoMyReviewsRepository getInstance(){
        if(instance == null){
            instance = new MysosoMyReviewsRepository();
        }
        return instance;
    }

    public void requestMyReviews(String token,
                                 Consumer<MyReviewsDto> onSuccess,
                                 Runnable onFailedLogIn,
                                 Runnable onFailed,
                                 Runnable onError){
        mysosoService.requestMyReviews(token).enqueue(new Callback<MyReviewsDto>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<MyReviewsDto> call, Response<MyReviewsDto> response) {
                switch (response.code()) {
                    case 200: {
                        onSuccess.accept(response.body());
                        break;
                    }
                    case 403: {
                        onFailedLogIn.run();
                        break;
                    }
                    //검색 없음
                    default: {
                        onFailed.run();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<MyReviewsDto> call, Throwable t) {
                onError.run();
            }
        });

    }
}
