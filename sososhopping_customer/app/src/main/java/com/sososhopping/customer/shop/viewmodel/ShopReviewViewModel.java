package com.sososhopping.customer.shop.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sososhopping.customer.shop.dto.ReviewInputDto;
import com.sososhopping.customer.shop.dto.ReviewListDto;
import com.sososhopping.customer.shop.model.ReviewModel;
import com.sososhopping.customer.shop.model.ShopIntroduceModel;
import com.sososhopping.customer.shop.repository.ShopRepository;
import com.sososhopping.customer.shop.repository.ShopReviewRepository;

import java.util.ArrayList;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopReviewViewModel extends ViewModel {
    private final ShopReviewRepository shopRepository = ShopReviewRepository.getInstance();

    ArrayList<ReviewModel> reviewModels = new ArrayList<>();

    public void requestShopReviews(int storeId,
                                   Consumer<ReviewListDto> onSuccess,
                                   Runnable onFailed,
                                   Runnable onError){
        shopRepository.requestShopReviews(storeId, onSuccess, onFailed, onError);
    }

    public ReviewInputDto getReviewInputDto(int score, String content){
        return new ReviewInputDto(score, content);
    }

    public void inputShopReviews(String token, int storeId, ReviewInputDto reviewInputDto,
                                 Runnable onSuccess,
                                 Runnable onFailedLogIn,
                                 Runnable onFailed,
                                 Runnable onError){
        shopRepository.inputReview(token, storeId, reviewInputDto, onSuccess, onFailedLogIn, onFailed, onError);
    }

    public void checkShopReview(String token, int storeId,
                                Runnable onDup,
                                Runnable onFailed,
                                Runnable onError){
        shopRepository.checkReviews(token, storeId, onDup, onFailed, onError);
    }


}
