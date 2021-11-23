package com.sososhopping.customer.cart.dto;

import androidx.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto{
    Integer itemId;
    int num;

    @Override
    public boolean equals(@Nullable @org.jetbrains.annotations.Nullable Object obj) {
        PurchaseDto a = (PurchaseDto) obj;
        return a.itemId == this.itemId;
    }

    @Override
    public int hashCode() {
        return itemId.hashCode();
    }
}
