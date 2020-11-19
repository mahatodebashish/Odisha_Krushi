package com.odishakrushi.Model;

/**
 * Created by RatnaDev008 on 5/23/2018.
 */

public class ListTakeOnHire {

        private String description,operational_area,profile_img,first_name,phone,start_date;

    public ListTakeOnHire(String description, String operational_area, String profile_img, String first_name, String phone, String start_date) {

        this.description = description;
        this.operational_area = operational_area;
        this.profile_img = profile_img;
        this.first_name = first_name;
        this.phone = phone;
        this.start_date = start_date;

    }

    public String getDescription() {
        return description;
    }

    public String getOperational_area() {
        return operational_area;
    }

    public String getProfile_img() {
        return profile_img;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getPhone() {
        return phone;
    }

    public String getStart_date() {
        return start_date;
    }
}
