package com.sososhopping.customer.shop.model.enumType;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public enum WritingType {
    EVENT("이벤트"), PROMOTION("소식");

    private final String value;

    WritingType(String value){
        this.value = value;
    }
    public String getValue(){ return value; }
}
