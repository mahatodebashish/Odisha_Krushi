package com.odishakrushi.Model;

/**
 * Created by Deb on 2/3/2019.
 */

public class GiveOnHireModel {

    private  String description,operational_area,start_date,end_date,hirer_id,machine_name,id;

    public GiveOnHireModel(String description, String operational_area, String start_date, String end_date, String hirer_id, String machine_name, String id) {
        this.description = description;
        this.operational_area = operational_area;
        this.start_date = start_date;
        this.end_date = end_date;
        this.hirer_id = hirer_id;
        this.machine_name = machine_name;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getOperational_area() {
        return operational_area;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getHirer_id() {
        return hirer_id;
    }

    public String getMachine_name() {
        return machine_name;
    }

    public String getId() {
        return id;
    }
}
