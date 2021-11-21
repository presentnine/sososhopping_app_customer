package com.sososhopping.customer.search.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.sososhopping.customer.common.types.Location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShopInfoShortModel implements Parcelable {
    private int storeId;
    private String storeType;
    private String name;
    private String description;
    private String phone;
    private String imgUrl;

    private boolean businessStatus;
    private boolean localCurrencyStatus;
    private boolean pickupStatus;
    private boolean deliveryStatus;

    private Location location = new Location();
    private double distance;

    private double score;
    private boolean isInterestStore;


    protected ShopInfoShortModel(Parcel in) {
        storeId = in.readInt();
        storeType = in.readString();
        name = in.readString();
        imgUrl = in.readString();
        description = in.readString();
        businessStatus = in.readByte() != 0;
        localCurrencyStatus = in.readByte() != 0;
        pickupStatus = in.readByte() != 0;
        deliveryStatus = in.readByte() != 0;
        distance = in.readInt();
        score = in.readDouble();
        phone = in.readString();
        isInterestStore = in.readByte() != 0;
    }

    public static final Creator<ShopInfoShortModel> CREATOR = new Creator<ShopInfoShortModel>() {
        @Override
        public ShopInfoShortModel createFromParcel(Parcel in) {
            return new ShopInfoShortModel(in);
        }

        @Override
        public ShopInfoShortModel[] newArray(int size) {
            return new ShopInfoShortModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(storeId);
        parcel.writeString(storeType);
        parcel.writeString(name);
        parcel.writeString(imgUrl);
        parcel.writeString(description);
        parcel.writeByte((byte) (businessStatus ? 1 : 0));
        parcel.writeByte((byte) (localCurrencyStatus ? 1 : 0));
        parcel.writeByte((byte) (pickupStatus ? 1 : 0));
        parcel.writeByte((byte) (deliveryStatus ? 1 : 0));
        parcel.writeDouble(distance);
        parcel.writeDouble(score);
        parcel.writeString(phone);
        parcel.writeByte((byte) (isInterestStore ? 1 : 0));
    }
}
