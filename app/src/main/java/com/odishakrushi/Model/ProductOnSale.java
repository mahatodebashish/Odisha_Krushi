package com.odishakrushi.Model;

/**
 * Created by RatnaDev008 on 8/9/2018.
 */

public class ProductOnSale {

    private String sale_id;
    private String quantity;
    private String variety;
    private String price;
    private String product_type;

    public ProductOnSale( String sale_id,String quantity, String variety, String price,String product_type) {

        this.sale_id = sale_id;
        this.quantity = quantity;
        this.variety = variety;
        this.price = price;
        this.product_type=product_type;
    }

    public String getProduct_type() {
        return product_type;
    }

    public String getSale_id() {
        return sale_id;
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
}
