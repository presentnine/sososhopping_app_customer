package com.sososhopping.customer.shop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopItemModel {
    int storeId;
    String name;
    String description;
    String purchaseUnit;
    int price;
    String imgUrl;
    Boolean saleStatus;
}
