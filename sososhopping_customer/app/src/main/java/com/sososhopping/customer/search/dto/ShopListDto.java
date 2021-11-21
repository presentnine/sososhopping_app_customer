package com.sososhopping.customer.search.dto;

import com.google.gson.annotations.SerializedName;
import com.sososhopping.customer.search.model.ShopInfoShortModel;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopListDto {
    @SerializedName("results")
    ArrayList<ShopInfoShortModel> shopInfoShortModels = new ArrayList<>();
}
