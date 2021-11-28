package com.sososhopping.customer.cart.repository;

import android.util.Log;

import com.sososhopping.customer.cart.dto.OrderRequestDto;
import com.sososhopping.customer.cart.service.CartService;
import com.sososhopping.customer.cart.service.PurchaseService;
import com.sososhopping.customer.common.retrofit.ApiServiceFactory;
import com.sososhopping.customer.error.model.ErrorMsg;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseRepository {

    private static PurchaseRepository instance;
    private final PurchaseService purchaseService;

    private PurchaseRepository(){
        this.purchaseService = ApiServiceFactory.createService(PurchaseService.class);
    }

    public static synchronized PurchaseRepository getInstance(){
        if(instance == null){
            instance = new PurchaseRepository();
        }
        return instance;
    }
    public void requestOrders(String token,
                              OrderRequestDto dto,
                              Runnable onSuccess,
                              Runnable onFailed,
                              Runnable onError){
        purchaseService.requestOrders(token,dto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                Log.e("errormsg", response.raw().toString());
                Log.e("errormsg", dto.toString());

                switch (response.code()){
                    case 200 :
                    case 201 :
                        onSuccess.run();
                        break;

                    case 400:
                    case 404:
                        onFailed.run();
                        break;
                    default:
                        onError.run();
                        break;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
                onError.run();
            }
        });
    }
}
