package com.sososhopping.customer.account;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SingUpViewModel extends ViewModel {
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> password = new MutableLiveData<>();
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> phone = new MutableLiveData<>();
    private MutableLiveData<String> nickName = new MutableLiveData<>();
    private MutableLiveData<String> roadAddress = new MutableLiveData<>();
    private MutableLiveData<String> detailAddress = new MutableLiveData<>();

    public MutableLiveData<String> getEmail() {
        return email;
    }
    public void setEmail(String e) {
        email.setValue(e);
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public void setPassword(String p) {
        this.password.setValue(p);
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public void setName(String n) {
        this.name.setValue(n);
    }

    public MutableLiveData<String> getPhone() {
        return phone;
    }

    public void setPhone(String p) {
        this.phone.setValue(p.replaceAll("-",""));
    }

    public MutableLiveData<String> getNickName() {
        return nickName;
    }

    public void setNickName(String n) {
        this.nickName.setValue(n);
    }

    public MutableLiveData<String> getRoadAddress() {
        return roadAddress;
    }

    public void setRoadAddress(String rA) {
        this.roadAddress.setValue(rA);
    }

    public MutableLiveData<String> getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String dA) {
        this.detailAddress.setValue(dA);
    }

    public String printVal(){
        return email.getValue() + " " +
                password.getValue() + " " +
                name.getValue() + " " +
                phone.getValue() + " " +
                nickName.getValue() + " " +
                roadAddress.getValue() + " " +
                detailAddress.getValue();
    }
}