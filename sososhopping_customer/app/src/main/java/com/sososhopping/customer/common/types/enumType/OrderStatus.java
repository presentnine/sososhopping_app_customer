package com.sososhopping.customer.common.types.enumType;

import androidx.annotation.NonNull;

public enum OrderStatus{

    PENDING("승인대기"),
    APPROVE_ALL("전체"), APPROVE("준비중"),  READY("준비완료"),
    CANCEL_ALL("전체"), CANCEL("주문취소"), REJECT("주문거절"),
    DONE("주문완료");

    private final String value;

    OrderStatus(String value){
        this.value = value;
    }

    public String getValue(){ return value; }


    @NonNull
    @Override
    public String toString() {
        return getValue();
    }
}
