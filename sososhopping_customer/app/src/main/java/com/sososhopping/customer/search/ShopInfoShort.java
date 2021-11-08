package com.sososhopping.customer.search;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShopInfoShort implements Parcelable {
    private int shopId;
    private String shopName;
    private String shopDescription;
    private double rating;
    private boolean localPay;
    private boolean delivery;
    private int distance;
    private String shopImageURL;
    private boolean favorite;

    protected ShopInfoShort(Parcel in) {
        shopId = in.readInt();
        shopName = in.readString();
        shopDescription = in.readString();
        rating = in.readDouble();
        localPay = in.readByte() != 0;
        delivery = in.readByte() != 0;
        distance = in.readInt();
        shopImageURL = in.readString();
        byte tmpIsFavorite = in.readByte();
        favorite = tmpIsFavorite == 0 ? null : tmpIsFavorite == 1;
    }

    public static final Creator<ShopInfoShort> CREATOR = new Creator<ShopInfoShort>() {
        @Override
        public ShopInfoShort createFromParcel(Parcel in) {
            return new ShopInfoShort(in);
        }
        @Override
        public ShopInfoShort[] newArray(int size) {
            return new ShopInfoShort[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(shopId);
        dest.writeString(shopName);
        dest.writeString(shopDescription);
        dest.writeDouble(rating);
        dest.writeByte((byte) (localPay ? 1 : 0));
        dest.writeByte((byte) (delivery ? 1 : 0));
        dest.writeInt(distance);
        dest.writeString(shopImageURL);
        dest.writeByte((byte) (favorite ? 1 : 0));
    }
}
