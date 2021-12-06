package com.sososhopping.customer.search.dto;

import com.sososhopping.customer.search.model.ShopInfoShortModel;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageableShopListDto {

    ArrayList<ShopInfoShortModel> content = new ArrayList<>();
    Pageable pageable;
    Sort sort;
    int numberOfElements;
    int size;
    boolean empty;

    @Getter
    @Setter
    public class Pageable {
        int offset; //여기서부터
        int pageNumber;
        int pageSize; //여기까지
        boolean paged;
    }

    @Getter
    @Setter
    class Sort{
        boolean empty;
        boolean sorted;
        boolean unsored;
    }

}
