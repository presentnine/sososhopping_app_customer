package com.sososhopping.customer.mysoso.model;

import com.google.gson.annotations.SerializedName;
import com.sososhopping.customer.common.types.enumType.OrderStatus;

import lombok.Getter;

@Getter
public class OrderRecordShortModel {

    @SerializedName("orderId")
    long orderId;

    @SerializedName("storeName")
    String storeName;

    @SerializedName("orderDescription")
    String orderDescription;

    @SerializedName("imgUrl")
    String imgUrl;

    @SerializedName("finalPrice")
    int finalPrice;

    @SerializedName("orderStatus")
    OrderStatus orderStatus;

    @SerializedName("createdAt")
    String createdAt;

}
