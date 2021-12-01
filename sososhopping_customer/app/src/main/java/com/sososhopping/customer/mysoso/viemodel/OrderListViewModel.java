package com.sososhopping.customer.mysoso.viemodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sososhopping.customer.common.types.enumType.OrderStatus;
import com.sososhopping.customer.mysoso.dto.OrderListDto;
import com.sososhopping.customer.mysoso.model.OrderRecordShortModel;
import com.sososhopping.customer.mysoso.repository.MysosoOrderRepository;

import java.util.ArrayList;
import java.util.List;
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

    public String changeOrderStatus(OrderStatus orderStatuses){
        StringBuilder sb = new StringBuilder();
        switch (orderStatuses){
            case APPROVE_ALL:
                sb.append(OrderStatus.APPROVE.getStat());
                sb.append(",");
                sb.append(OrderStatus.READY.getStat());
                break;

            case CANCEL_ALL:
                sb.append(OrderStatus.CANCEL.getStat());
                sb.append(",");
                sb.append(OrderStatus.REJECT.getStat());
                break;

            default:
                sb.append(orderStatuses.getStat());
                break;
        }

        return sb.toString();
    }
}
