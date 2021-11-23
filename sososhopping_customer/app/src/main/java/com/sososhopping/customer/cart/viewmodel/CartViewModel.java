package com.sososhopping.customer.cart.viewmodel;

import com.sososhopping.customer.cart.dto.CartDto;
import com.sososhopping.customer.cart.dto.CartItemDto;
import com.sososhopping.customer.cart.dto.CartStoreDto;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartViewModel {

    ArrayList<CartStoreDto> stores;
    int totalPrice;

    public int calTotalStore( ){
        return stores.size();
    }

    public int calTotalPrice(){
        totalPrice = 0;
        for (CartStoreDto c : stores){
            totalPrice += c.getTotalPrice();
        }
        return this.totalPrice;
    }

    public int calTotalStorePrice(int position){
        int price = 0;

        for(CartItemDto d : stores.get(position).getCartItems()){
            price += d.getPrice() * d.getNum();
        }

        stores.get(position).setTotalPrice(price);
        return price;
    }
}
