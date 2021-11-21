package com.sososhopping.customer.mysoso.dto;

import com.google.gson.annotations.SerializedName;
import com.sososhopping.customer.mysoso.model.PointInfoModel;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PointListDto {
    @SerializedName("results")
    ArrayList<PointInfoModel> pointListFavorite = new ArrayList<>();

    @SerializedName("result")
    ArrayList<PointInfoModel> pointListNotFavorite = new ArrayList<>();
}
