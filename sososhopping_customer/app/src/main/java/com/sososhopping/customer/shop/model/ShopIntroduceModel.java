package com.sososhopping.customer.shop.model;


import com.sososhopping.customer.shop.model.common.BusinessDays;
import com.sososhopping.customer.shop.model.common.Location;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopIntroduceModel {
    int storeId;
    String name;
    //대표이미지
    String imgUrl;
    String description;
    String extraBusinessDay;
    String phone;
    boolean businessStatus;
    boolean localCurrencyStatus;
    boolean pickupStatus;
    boolean deliveryStatus;
    Integer minimumOrderPrice;
    Double saveRate;
    String streetAddress;
    String detailedAddress;
    ArrayList<BusinessDays> businessDays = new ArrayList<>();
    ArrayList<String> storeImages = new ArrayList<>();
    Location location;
    float score;
}



