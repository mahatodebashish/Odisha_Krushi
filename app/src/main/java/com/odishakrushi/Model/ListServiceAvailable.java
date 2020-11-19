package com.odishakrushi.Model;

/**
 * Created by RatnaDev008 on 5/23/2018.
 */

public class ListServiceAvailable {

    private String businessman_name;
    private String contact_no;
    private String email;
    private String website;
    private String name_of_farm;
    private String area_of_business;
    private String deals_in_product;

    public ListServiceAvailable(String businessman_name, String contact_no, String email, String website, String name_of_farm, String area_of_business, String deals_in_product) {
        this.businessman_name = businessman_name;
        this.contact_no = contact_no;
        this.email = email;
        this.website = website;
        this.name_of_farm = name_of_farm;
        this.area_of_business = area_of_business;
        this.deals_in_product = deals_in_product;
    }

    public String getBusinessman_name() {
        return businessman_name;
    }

    public String getContact_no() {
        return contact_no;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    public String getName_of_farm() {
        return name_of_farm;
    }



    public String getArea_of_business() {
        return area_of_business;
    }

    public String getDeals_in_product() {
        return deals_in_product;
    }
}
