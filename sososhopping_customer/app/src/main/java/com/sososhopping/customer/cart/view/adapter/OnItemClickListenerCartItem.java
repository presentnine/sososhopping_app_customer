package com.sososhopping.customer.cart.view.adapter;

import com.sososhopping.customer.cart.dto.CartItemDto;

public interface OnItemClickListenerCartItem {
    void itemDelete(int pos);
    void itemCountChanged(int pos, int val);
}
