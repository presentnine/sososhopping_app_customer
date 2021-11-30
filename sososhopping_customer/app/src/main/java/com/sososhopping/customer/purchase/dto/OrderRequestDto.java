package com.sososhopping.customer.purchase.dto;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.sososhopping.customer.common.types.enumType.OrderType;
import com.sososhopping.customer.common.types.enumType.PaymentType;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDto {
    @SerializedName("storeId")
    int storeId;

    @SerializedName("orderItems")
    ArrayList<CartUpdateDto> orderItems = new ArrayList<>();

    @SerializedName("orderType")
    OrderType orderType;

    @SerializedName("paymentType")
    PaymentType paymentType;

    @SerializedName("usedPoint")
    int usedPoint;

    @SerializedName("couponId")
    Long couponId;

    @SerializedName("finalPrice")
    int finalPrice;

    @SerializedName("ordererName")
    String ordererName;

    @SerializedName("ordererPhone")
    String ordererPhone;

    @SerializedName("visitDate")
    String visitDate;

    @SerializedName("deliveryStreetAddress")
    String deliveryStreetAddress;

    @SerializedName("deliveryDetailedAddress")
    String deliveryDetailedAddress;

    @NonNull
    @NotNull
    @Override
    public String toString() {

        String tostr = storeId + " \n" +
                orderItems.toString() + " \n" +
                orderType + " \n" +
                paymentType + " \n" +
                usedPoint + " \n" +
                couponId + " \n" +
                finalPrice + " \n" +
                ordererName + " \n" +
                ordererPhone + " \n" +
                visitDate + " \n" +
                deliveryStreetAddress + " \n" +
                deliveryDetailedAddress + " \n";

        return tostr;
    }
}
