package com.sososhopping.customer.search;

public class ShopInfo {
    private int shopId;
    private String shopName;
    private String shopDescription;
    private float rating;
    private boolean localPay;
    private boolean delivery;
    private int distance;
    private String shopURL;

    public ShopInfo(){}

    public ShopInfo(int shopId, String shopName, String shopDescription, float rating, boolean localPay, boolean delivery, int distance, String shopURL) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopDescription = shopDescription;
        this.rating = rating;
        this.localPay = localPay;
        this.delivery = delivery;
        this.distance = distance;
        this.shopURL = shopURL;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isLocalPay() {
        return localPay;
    }

    public void setLocalPay(boolean localPay) {
        this.localPay = localPay;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getShopURL() {
        return shopURL;
    }

    public void setShopURL(String shopURL) {
        this.shopURL = shopURL;
    }
}
