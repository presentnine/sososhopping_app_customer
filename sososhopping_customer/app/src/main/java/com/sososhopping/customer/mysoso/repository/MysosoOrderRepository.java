package com.sososhopping.customer.mysoso.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.sososhopping.customer.common.retrofit.ApiServiceFactory;
import com.sososhopping.customer.mysoso.dto.OrderListDto;
import com.sososhopping.customer.mysoso.service.MysosoService;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MysosoOrderRepository {

    private static MysosoOrderRepository instance;
    private final MysosoService mysosoService;

    private MysosoOrderRepository(){
        this.mysosoService = ApiServiceFactory.createService(MysosoService.class);
    }

    public static synchronized MysosoOrderRepository getInstance(){
        if(instance == null){
            instance = new MysosoOrderRepository();
        }
        return instance;
    }

    public void requestMyOrderLists(String token,
                                    String[] statuses,
                                    Consumer<OrderListDto> onSuccess,
                                    Runnable onLogInFailed,
                                    Runnable onFailed,
                                    Runnable onError){
        mysosoService.requestMyOrders(token, statuses).enqueue(new Callback<OrderListDto>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<OrderListDto> call, Response<OrderListDto> response) {
                switch (response.code()){
                    case 200:{
                        onSuccess.accept(response.body());
                        break;
                    }

                    case 403:{
                        onLogInFailed.run();
                        break;
                    }

                    default:
                        onFailed.run();
                        break;
                }
            }

            @Override
            public void onFailure(Call<OrderListDto> call, Throwable t) {
                onError.run();
            }
        });
    }

}
