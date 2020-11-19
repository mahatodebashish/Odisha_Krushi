package com.odishakrushi.Model;

/**
 * Created by RatnaDev008 on 9/14/2018.
 */

public class ListPestiOnSale {
    private String sale_id;
    private String brand;
    private String str_product_type;

    public ListPestiOnSale(String sale_id,String brand, String str_product_type) {
        this.sale_id=sale_id;
        this.brand = brand;
        this.str_product_type=str_product_type;
    }

    public String getSale_id() {
        return sale_id;
    }

    public String getBrand() {
        return brand;
    }

    public String getStr_product_type() {
        return str_product_type;
    }
}
