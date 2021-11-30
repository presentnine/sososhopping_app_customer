package com.sososhopping.customer.mysoso.viemodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sososhopping.customer.common.types.enumType.OrderStatus;
import com.sososhopping.customer.mysoso.dto.OrderListDto;
import com.sososhopping.customer.mysoso.model.OrderRecordShortModel;
import com.sososhopping.customer.mysoso.repository.MysosoOrderRepository;

import java.util.ArrayList;
import java.util.function.Consumer;

import lombok.Getter;

@Getter
public class OrderListViewModel extends ViewModel {

    MysosoOrderRepository mysosoOrderRepository = MysosoOrderRepository.getInstance();

    MutableLiveData<ArrayList<OrderRecordShortModel>> orderList = new MutableLiveData<>();

    public OrderListViewModel() {
        orderList.setValue(new ArrayList<>());
    }

    public void requestMyOrderLists(String token,
                                    OrderStatus orderStatuses,
                                    Consumer<OrderListDto> onSuccess,
                                    Runnable onLogInFailed,
                                    Runnable onFailed,
                                    Runnable onError){

        mysosoOrderRepository.requestMyOrderLists(token, changeOrderStatus(orderStatuses), onSuccess, onLogInFailed, onFailed, onError);
    }

    public String[] changeOrderStatus(OrderStatus orderStatuses){
        ArrayList<String> results = new ArrayList<>();
        switch (orderStatuses){
            case APPROVE_ALL:
                results.add(OrderStatus.APPROVE.getValue());
                results.add(OrderStatus.READY.getValue());
                break;

            case CANCEL_ALL:
                results.add(OrderStatus.CANCEL.getValue());
                results.add(OrderStatus.REJECT.getValue());
                break;

            default:
                results.add(orderStatuses.getValue());
                break;
        }

        return results.toArray(new String[results.size()]);
    }
}
