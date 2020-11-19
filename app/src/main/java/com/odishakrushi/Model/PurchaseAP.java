package com.odishakrushi.Model;

/**
 * Created by RatnaDev008 on 8/17/2018.
 */

public class PurchaseAP {

    private String sale_id;
    private String seller_user_id;
    private String product_type;
    private String quantity;
    private String variety;
    private String price;
    private String first_name;
    private String phone;
    private String village;
    private String photo_url;
    private String district_name;
    private String block_name;

    // extra

    private  String year_of_purchase;
    private String make;
    private String model;
    private String condition;
    private String image;

    public PurchaseAP(String sale_id, String seller_user_id, String product_type, String quantity, String variety, String price, String first_name, String phone, String village, String photo_url, String district_name, String block_name
    ,String year_of_purchase,String make,String model,String condition, String image) {
        this.sale_id = sale_id;
        this.seller_user_id = seller_user_id;
        this.product_type = product_type;
        this.quantity = quantity;
        this.variety = variety;
        this.price = price;
        this.first_name = first_name;
        this.phone = phone;
        this.village = village;
        this.photo_url = photo_url;
        this.district_name = district_name;
        this.block_name = block_name;
        this.year_of_purchase=year_of_purchase;
        this.make=make;
        this.model=model;
        this.condition=condition;
        this.image=image;
    }

    public String getSale_id() {
        return sale_id;
    }

    public String getSeller_user_id() {
        return seller_user_id;
    }

    public String getProduct_type() {
        return product_type;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getVariety() {
        return variety;
    }

    public String getPrice() {
        return price;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getPhone() {
        return phone;
    }

    public String getVillage() {
        return village;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public String getBlock_name() {
        return block_name;
    }

    public String getYear_of_purchase() {
        return year_of_purchase;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getCondition() {
        return condition;
    }

    public String getImage() {
        return image;
    }
}
