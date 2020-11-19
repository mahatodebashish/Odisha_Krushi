package com.odishakrushi.Model;

/**
 * Created by RatnaDev008 on 8/22/2018.
 */

public class MedicineFeed {

    private String sale_id;
    private String medicine;
    private String medicinefor;
    private String feed;
    private String net;


    public MedicineFeed( String sale_id, String medicine, String medicinefor, String feed, String net) {
        this.sale_id=sale_id;
        this.medicine = medicine;
        this.medicinefor = medicinefor;
        this.feed = feed;
        this.net = net;
    }

    public String getSale_id() {
        return sale_id;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getMedicinefor() {
        return medicinefor;
    }

    public String getFeed() {
        return feed;
    }

    public String getNet() {
        return net;
    }
}
