package com.sososhopping.customer.common.types.enumType;

public enum CategoryType {
    GROCERY("마트"), MEAT("정육점"), FISH("수산물"), BAKERY("베이커리"), CAFE("카페"),
    HAIRSHOP("헤어숍"), LAUNDRY("세탁소"), BOOK("서점"),
    OFFICE("사무용품"), ACADEMY("학원"), PHOTO("사진관"), FLOWER("꽃집"), CLOTHE("의류"),
    MAP("지도");

    private final String value;

    CategoryType(String value){
        this.value = value;
    }

    public String getValue(){ return value; }
}
