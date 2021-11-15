package com.sososhopping.customer.shop.repository;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.sososhopping.customer.R;
import com.sososhopping.customer.common.retrofit.ApiServiceFactory;
import com.sososhopping.customer.shop.dto.CouponListDto;
import com.sososhopping.customer.shop.dto.EventItemListDto;
import com.sososhopping.customer.shop.dto.ItemListDto;
import com.sososhopping.customer.shop.dto.ReportInputDto;
import com.sososhopping.customer.shop.dto.ReviewInputDto;
import com.sososhopping.customer.shop.dto.ReviewListDto;
import com.sososhopping.customer.shop.dto.StoreIdDto;
import com.sososhopping.customer.shop.model.EventDetailModel;
import com.sososhopping.customer.shop.model.ShopIntroduceModel;
import com.sososhopping.customer.shop.service.ShopService;

import java.util.function.Consumer;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopRepository {
    private static ShopRepository instance;
    private final ShopService shopService;

    private ShopRepository(){
        this.shopService = ApiServiceFactory.createService(ShopService.class);
    }

    public static synchronized ShopRepository getInstance() {
        if(instance == null){
            instance = new ShopRepository();
        }
        return instance;
    }

    public void requestShopIntroduce(String token, int storeId,
                                     Consumer<ShopIntroduceModel> shopIntroduceModel,
                                     Runnable onFailed,
                                     Runnable onError){
        if(token == null){
            requestShopIntroduce(storeId, shopIntroduceModel, onFailed, onError);
            return;
        }
        shopService.requestShopIntroduce(token, storeId).enqueue(new Callback<ShopIntroduceModel>() {
            @SneakyThrows
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ShopIntroduceModel> call, Response<ShopIntroduceModel> response) {
                switch (response.code()){
                    case 200:{
                        shopIntroduceModel.accept(response.body());
                        break;
                    }
                    //검색 없음
                    case 404:{
                        onFailed.run();
                        break;
                    }
                    default:{
                        onError.run();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<ShopIntroduceModel> call, Throwable t) {
                t.printStackTrace();
                onError.run();
            }
        });
    }

    public void requestShopIntroduce(int storeId,
                                     Consumer<ShopIntroduceModel> shopIntroduceModel,
                                     Runnable onFailed,
                                     Runnable onError) {
        shopService.requestShopIntroduce(storeId).enqueue(new Callback<ShopIntroduceModel>() {
            @SneakyThrows
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ShopIntroduceModel> call, Response<ShopIntroduceModel> response) {
                switch (response.code()){
                    case 200:{
                        shopIntroduceModel.accept(response.body());
                        break;
                    }
                    //검색 없음
                    case 404:{
                        onFailed.run();
                        break;
                    }
                    default:{
                        onError.run();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<ShopIntroduceModel> call, Throwable t) {
                t.printStackTrace();
                onError.run();
            }
        });
    }

    public void requestShopItem(int storeId, Consumer<ItemListDto> item,
                                Runnable onFailed,
                                Runnable onError){
        shopService.requestShopItem(storeId).enqueue(new Callback<ItemListDto>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ItemListDto> call, Response<ItemListDto> response) {
                switch (response.code()){
                    case 200:{
                        item.accept(response.body());
                        break;
                    }
                    //검색 없음
                    case 404:{
                        onFailed.run();
                        break;
                    }
                    default:{
                        onError.run();
                    }
                }
            }

            @Override
            public void onFailure(Call<ItemListDto> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void requestShopCoupon(int storeId, Consumer<CouponListDto> couponModel,
                                  Runnable onFailed,
                                  Runnable onError) {

        shopService.requestCoupons(storeId).enqueue(new Callback<CouponListDto>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<CouponListDto> call, Response<CouponListDto> response) {
                switch (response.code()){
                    case 200:{
                        couponModel.accept(response.body());
                        break;
                    }
                    //검색 없음
                    case 404:{
                        onFailed.run();
                        break;
                    }
                    default:{
                        onError.run();;
                    }
                }
            }

            @Override
            public void onFailure(Call<CouponListDto> call, Throwable t) {
                onError.run();;
            }
        });

    }

    public void requestShopWriting(int storeId, Consumer<EventItemListDto> eventModel,
                                   Runnable onFailed,
                                   Runnable onError) {

        shopService.requestWritings(storeId).enqueue(new Callback<EventItemListDto>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<EventItemListDto> call, Response<EventItemListDto> response) {
                switch (response.code()) {
                    case 200: {
                        eventModel.accept(response.body());
                        break;
                    }
                    //검색 없음
                    case 404: {
                        onFailed.run();
                        break;
                    }
                    default: {
                        onError.run();
                    }
                }
            }

            @Override
            public void onFailure(Call<EventItemListDto> call, Throwable t) {
                onError.run();
            }
        });
    }


    public void requestShopReviews(int storeId, Consumer<ReviewListDto> reviewModel,
                                   Runnable onFailed,
                                   Runnable onError) {
        shopService.requestReviews(storeId).enqueue(new Callback<ReviewListDto>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<ReviewListDto> call, Response<ReviewListDto> response) {
                switch (response.code()) {
                    case 200: {
                        reviewModel.accept(response.body());
                        break;
                    }
                    //검색 없음
                    case 404: {
                        onFailed.run();
                        break;
                    }
                    default: {
                        onError.run();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReviewListDto> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void requestShopEventDetail(int storeId, int writingId,
                                       Consumer<EventDetailModel> eventDetailModel,
                                       Runnable onFailed,
                                       Runnable onError) {
        shopService.requestWritingsDetail(storeId, writingId).enqueue(new Callback<EventDetailModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<EventDetailModel> call, Response<EventDetailModel> response) {
                switch (response.code()) {
                    case 200: {
                        eventDetailModel.accept(response.body());
                        break;
                    }
                    //검색 없음
                    case 400:
                    case 404: {
                        onFailed.run();
                        break;
                    }

                    default: {
                        onError.run();
                    }
                }
            }
            @Override
            public void onFailure(Call<EventDetailModel> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void inputReview(String token,
                            int storeId, ReviewInputDto reviewInputDto,
                            Runnable onSuccess,
                            Runnable onFailedLogIn,
                            Runnable onFailed,
                            Runnable onError){

        shopService.inputReviews(token, storeId, reviewInputDto).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 200:
                    //작성 성공
                    case 201:{
                        onSuccess.run();
                        break;
                    }

                    //내용 오류
                    case 400:

                    //토큰 검증 실패
                    case 401:{
                        onFailedLogIn.run();
                        break;
                    }

                    //점포 없음
                    case 404:

                    //리뷰 중복 작성
                    case 409: {
                        onFailed.run();
                        break;
                    }

                    default: {
                        onError.run();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void changeInterest(String token,
                               int storeId,
                               Runnable onSuccess,
                               Runnable onLogInFailed,
                               Runnable onFailed,
                               Runnable onError){
        shopService.changeInterest(token, new StoreIdDto(storeId)).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 200:
                        //작성 성공
                    case 201:{
                        onSuccess.run();
                        break;
                    }

                    //토큰 검증 실패
                    case 401:{
                        onLogInFailed.run();
                        break;
                    }

                    //내용 오류
                    case 400:

                    //점포 없음
                    case 404:{
                        onFailed.run();
                        Log.d("error Log", response.raw()+"");
                        break;
                    }

                    default: {
                        onError.run();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onError.run();
            }
        });
    }

    public void inputReport(String token, int storeId, ReportInputDto content,
                            Runnable onSuccess,
                            Runnable onLogInFailed,
                            Runnable onFailed,
                            Runnable onError){
        shopService.inputReports(token, storeId, content).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 200:
                        //작성 성공
                    case 201:{
                        onSuccess.run();
                        break;
                    }

                    //토큰 검증 실패
                    case 401:{
                        onLogInFailed.run();
                        break;
                    }

                    //내용 오류
                    case 400:

                    //점포 없음
                    case 404:{
                        onFailed.run();
                        Log.d("error Log", response.raw()+"");
                        break;
                    }

                    default: {
                        onError.run();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                onError.run();
            }
        });
    }
}
