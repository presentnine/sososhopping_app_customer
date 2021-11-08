package com.sososhopping.customer.shop.model;

import com.sososhopping.customer.shop.model.enumType.WritingType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDetailModel {
    int writingId;
    String title;
    String content;
    WritingType writingType;
    String date;
    //추후 여러장 가능하게
    String imgUrl;
}
