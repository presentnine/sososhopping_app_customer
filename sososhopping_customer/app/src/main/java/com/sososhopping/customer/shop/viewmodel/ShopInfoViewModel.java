package com.sososhopping.customer.shop.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sososhopping.customer.common.types.Location;
import com.sososhopping.customer.shop.dto.ReportInputDto;
import com.sososhopping.customer.shop.repository.ShopRepository;

import lombok.Getter;


@Getter
public class ShopInfoViewModel extends ViewModel {
    MutableLiveData<Integer> shopId = new MutableLiveData<>();
    MutableLiveData<String> shopName = new MutableLiveData<>();
    MutableLiveData<String> phone = new MutableLiveData<>();
    MutableLiveData<Location> location = new MutableLiveData<>();

    private ShopRepository shopRepository = ShopRepository.getInstance();

    public void setShopId(int i){
        this.shopId.setValue(i);
    }

    public void setShopName(String name){
        this.shopName.setValue(name);
    }

    public void setPhone(String phone){
        this.phone.setValue(phone);
    }

    public void setLocation(Location location){
        this.location.setValue(location);
    }

    public void setShopInfoViewModel(int shopId, String shopName, String phone, Location location){
        setShopId(shopId);
        setShopName(shopName);
        setPhone(phone);
        setLocation(location);
    }

    public void inputReport(String token, int shopId, String report,
                            Runnable onSuccess,
                            Runnable onLogInFailed,
                            Runnable onFailed,
                            Runnable onError){
        shopRepository.inputReport(token, shopId, ReportInputDto.builder().report(report).build(),
                onSuccess, onLogInFailed, onFailed, onError);
    }
}
