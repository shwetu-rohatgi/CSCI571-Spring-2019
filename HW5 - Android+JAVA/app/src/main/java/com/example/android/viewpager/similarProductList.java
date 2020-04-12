package com.example.android.viewpager;

import android.net.Uri;

public class similarProductList {

    private String imgLink;
    private String productTitle;
    private String shippingType;
    private String price;
    private String ebayLink;
    private String daysLeft;
    private String daysLeftInt;

    public similarProductList(){
        this.imgLink = imgLink;
        this.productTitle = productTitle;
        this.shippingType = shippingType;
        this.daysLeft = daysLeft;
        this.price = price;
        this.ebayLink = ebayLink;
        this.daysLeftInt = daysLeftInt;
    }

    public Uri getImgLink() {
        return Uri.parse(imgLink);
    }

    public String getebayLink() {
        return ebayLink;
    }

    public String getproductTitle() {
        return productTitle;
    }

    public String getShippingType() {
        return shippingType;
    }

    public String getPrice() {
        return price;
    }

    public  String getdaysLeft(){ return daysLeft; }

    public  String getdaysLeftInt(){ return daysLeftInt; }


    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public void setebayLink(String ebayLink){
        this.ebayLink = ebayLink;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public void setDaysLeft(String daysLeft) {
        this.daysLeft = daysLeft;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setdaysLeftInt(String days){ this.daysLeftInt = daysLeftInt; }
}
