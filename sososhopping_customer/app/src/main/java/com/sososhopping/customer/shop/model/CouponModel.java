package com.sososhopping.customer.shop.model;

import com.sososhopping.customer.shop.model.enumType.CouponType;

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
    int couponId;
    String StoreName;
    String couponName;
    String couponCode;
    Integer minimumOrderPrice;
    String startDate;
    String endDate;
    CouponType couponType;
    Integer fixAmount;
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
}
