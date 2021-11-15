package com.sososhopping.customer.shop.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sososhopping.customer.shop.dto.ItemListDto;
import com.sososhopping.customer.shop.model.ShopItemModel;
import com.sososhopping.customer.shop.repository.ShopRepository;

import java.util.ArrayList;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopItemViewModel extends ViewModel {
    private final ShopRepository shopRepository = ShopRepository.getInstance();

    MutableLiveData<ArrayList<ShopItemModel>> shopItem = new MutableLiveData<>();

    public void requestShopItem(int storeId,
                                Consumer<ItemListDto> onSuccess,
                                Runnable onFailed,
                                Runnable onError){
        shopRepository.requestShopItem(storeId,onSuccess,onFailed, onError);
    }
}
