package com.odishakrushi.Model;

/**
 * Created by RatnaDev008 on 8/23/2018.
 */

public class MedicineFeedPurchaseModel {
    private String seller_name, medicine, medicine_for, feed, net, mobile, district_name, block_name, village_name;

    public MedicineFeedPurchaseModel(String seller_name, String medicine, String medicine_for, String feed, String net, String mobile, String district_name, String block_name, String village_name) {
        this.seller_name = seller_name;
        this.medicine = medicine;
        this.medicine_for = medicine_for;
        this.feed = feed;
        this.net = net;
        this.mobile = mobile;
        this.district_name = district_name;
        this.block_name = block_name;
        this.village_name = village_name;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getMedicine_for() {
        return medicine_for;
    }

    public String getFeed() {
        return feed;
    }

    public String getNet() {
        return net;
    }

    public String getMobile() {
        return mobile;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public String getBlock_name() {
        return block_name;
    }

    public String getVillage_name() {
        return village_name;
    }
}
