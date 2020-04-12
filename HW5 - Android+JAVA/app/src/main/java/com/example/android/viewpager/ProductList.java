package com.example.android.viewpager;

import android.net.Uri;

public class ProductList {
    private String imgLink;
    private String productTitle;
    private String zipCode;
    private String shippingType;
    private String conditionType;
    private String price;
    private String productID;
    private String keyword;
    private String shipInfo;
    private String ProductUrl;


    public ProductList(){
        this.imgLink = imgLink;
        this.productTitle = productTitle;
        this.zipCode = zipCode;
        this.shippingType = shippingType;
        this.conditionType = conditionType;
        this.price = price;
        this.productID = productID;
        this.keyword = keyword;
        this.shipInfo = shipInfo;
        this.ProductUrl = ProductUrl;
    }

    public String getKeyword(){
        return keyword;
    }
    public Uri getImgLink() {
        return Uri.parse(imgLink);
    }

    public String getProductID() {
        return productID;
    }

    public String getproductTitle() {
        return productTitle;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getShippingType() {
        return shippingType;
    }

    public String getConditionType() {
        return conditionType;
    }

    public String getPrice() {
        return price;
    }

    public String getshipInfo() {
        return shipInfo;
    }

    public String getProductUrl(){
        return (ProductUrl);
    }

    public void setKeyword(String keyword){
        this.keyword = keyword;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public void setProductID(String productID){
        this.productID = productID;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setshipInfo(String shipInfo) {
        this.shipInfo = shipInfo;
    }

    public void setProductUrl(String ProductUrl){
        this.ProductUrl = ProductUrl;
    }
}
