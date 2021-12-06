package com.sososhopping.customer.shop.model;

import com.sososhopping.customer.common.types.enumType.CouponType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponModel {
    long couponId;
    String storeName;
    String couponName;
    String couponCode;
    int minimumOrderPrice;
    String startDate;
    String endDate;
    String expiryDate;
    CouponType couponType;
    int fixAmount;
    Double rateAmount;

    public String amount(){
        if(couponType == CouponType.FIX){
            return fixAmount+"원";
        }
        else if(couponType == CouponType.RATE){
            return rateAmount+"%";
        }
        return null;
    }

    public CouponModel(String storeName){
        this.storeName = storeName;
    }
}
