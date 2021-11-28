package com.sososhopping.customer.cart.view.adapter;

import com.sososhopping.customer.cart.dto.CartItemDto;
import com.sososhopping.customer.cart.dto.CartStoreDto;
import com.sososhopping.customer.cart.dto.CartUpdateDto;

import java.util.ArrayList;

//클릭 이벤트
public interface OnItemClickListenerCartStore{
    void onButtonPurchaseClick(CartStoreDto cartStoreDto, ArrayList<CartUpdateDto> purchaseList);
    void onButtonStoreClick(int storeId);
    void itemDelete(int storePos, int itemPos, CartItemDto cartItemDto);
    void itemCountChanged(int val);

}