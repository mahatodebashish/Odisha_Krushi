package com.odishakrushi.Model;

/**
 * Created by RatnaDev008 on 9/21/2018.
 */

public class ListSearchresults {

    // for my story , qna id
    private String dropdown;
    private String questext;
    private String district;
    private String block;
    private String phone;
    private String farmer_name;
    private String village;
 //   private String url;
    private String category_name;
    private String sub_category_name;

    // for sales [ more data to be added ]
    private String product_type;
    private String make;
    private String model;
    private String image_url;
   // private String video_url;

    public ListSearchresults(String dropdown, String questext, String district, String block, String phone, String farmer_name, String village, String category_name, String sub_category_name, String product_type, String make, String model, String image_url) { // String url, String video_url
        this.dropdown = dropdown;
        this.questext = questext;
        this.district = district;
        this.block = block;
        this.phone = phone;
        this.farmer_name = farmer_name;
        this.village = village;
      //  this.url = url;
        this.category_name = category_name;
        this.sub_category_name = sub_category_name;
        this.product_type = product_type;
        this.make = make;
        this.model = model;
        this.image_url = image_url;
       // this.video_url = video_url;
    }

    public String getDropdown() {
        return dropdown;
    }

    public String getQuestext() {
        return questext;
    }

    public String getDistrict() {
        return district;
    }

    public String getBlock() {
        return block;
    }

    public String getPhone() {
        return phone;
    }

    public String getFarmer_name() {
        return farmer_name;
    }

    public String getVillage() {
        return village;
    }

  /*  public String getUrl() {
        return url;
    }*/

    public String getCategory_name() {
        return category_name;
    }

    public String getSub_category_name() {
        return sub_category_name;
    }

    public String getProduct_type() {
        return product_type;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getImage_url() {
        return image_url;
    }

   /* public String getVideo_url() {
        return video_url;
    }*/
}
