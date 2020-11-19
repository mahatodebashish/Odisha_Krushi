package com.odishakrushi.Model;

/**
 * Created by RatnaDev008 on 5/26/2018.
 */

public class ListFishForSale {

    private String fish_id;
    private String category_name;
    private String fish_type;
    private String user_id;
    private String medicine;
    private String feed;
    private String net;
    private String others;
   /// private String quantity;

    public ListFishForSale(String fish_id,String category_name,String fish_type, String user_id, String medicine, String feed, String net, String others) {//, String quantity

        this.fish_id = fish_id;
        this.category_name = category_name;
        this.fish_type = fish_type;
        this.user_id = user_id;
        this.medicine = medicine;
        this.feed = feed;
        this.net = net;
        this.others = others;
        //this.quantity = quantity;
    }

    public String getFish_type() {
        return fish_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getFeed() {
        return feed;
    }

    public String getNet() {
        return net;
    }

    public String getOthers() {
        return others;
    }

   /* public String getQuantity() {
        return quantity;
    }*/

    public String getFish_id() {
        return fish_id;
    }

    public String getCategory_name() {
        return category_name;
    }
}
