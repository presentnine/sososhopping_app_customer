package com.sososhopping.customer.mysoso.viemodel;

import com.sososhopping.customer.mysoso.dto.MyReviewsDto;
import com.sososhopping.customer.mysoso.repository.MysosoMyReviewsRepository;

import java.util.function.Consumer;

public class MyReviewViewModel {
    private MysosoMyReviewsRepository myReviewsRepository = MysosoMyReviewsRepository.getInstance();

    public void requestMyReview(String token,
                                Consumer<MyReviewsDto> onSuccess,
                                Runnable onFailedLogin,
                                Runnable onFailed,
                                Runnable onError){
        myReviewsRepository.requestMyReviews(token,onSuccess,onFailedLogin,onFailed,onError);
    }
}
