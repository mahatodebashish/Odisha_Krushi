package com.odishakrushi.Model;

/**
 * Created by RatnaDev008 on 9/19/2018.
 */

public class ListServices {
    private String sale_id, variety;

    public ListServices(String sale_id, String variety) {
        this.sale_id = sale_id;
        this.variety = variety;
    }

    public String getSale_id() {
        return sale_id;
    }

    public String getVariety() {
        return variety;
    }
}
