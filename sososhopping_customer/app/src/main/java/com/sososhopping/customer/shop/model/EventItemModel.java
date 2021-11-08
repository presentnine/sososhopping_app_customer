package com.sososhopping.customer.shop.model;

import com.sososhopping.customer.shop.model.enumType.WritingType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventItemModel {
    int writingId;
    String title;
    String description;
    String date;
    WritingType writingType;
    String imgUrl;

}
