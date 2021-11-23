package com.sososhopping.customer.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    int itemId;
    String name;
    String description;
    String imgUrl;
    int price;
    Boolean saleStatus;
    int num;
    boolean purchase;
}
