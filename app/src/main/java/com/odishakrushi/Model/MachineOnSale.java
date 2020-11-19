package com.odishakrushi.Model;

/**
 * Created by RatnaDev008 on 8/27/2018.
 */

public class MachineOnSale {

    private String sale_id,product_type, yr_of_purchase,  make,  model,machine_condition,capacity,power_source,
            sutable_for_crop,subsidy,remark,image_url,video_url;

    public MachineOnSale(String sale_id, String product_type, String yr_of_purchase, String make, String model, String machine_condition, String capacity, String power_source, String sutable_for_crop, String subsidy, String remark, String image_url, String video_url) {
        this.sale_id = sale_id;
        this.product_type = product_type;
        this.yr_of_purchase = yr_of_purchase;
        this.make = make;
        this.model = model;
        this.machine_condition = machine_condition;
        this.capacity = capacity;
        this.power_source = power_source;
        this.sutable_for_crop = sutable_for_crop;
        this.subsidy = subsidy;
        this.remark = remark;
        this.image_url = image_url;
        this.video_url = video_url;
    }

    public String getSale_id() {
        return sale_id;
    }

    public String getProduct_type() {
        return product_type;
    }

    public String getYr_of_purchase() {
        return yr_of_purchase;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getMachine_condition() {
        return machine_condition;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getPower_source() {
        return power_source;
    }

    public String getSutable_for_crop() {
        return sutable_for_crop;
    }

    public String getSubsidy() {
        return subsidy;
    }

    public String getRemark() {
        return remark;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getVideo_url() {
        return video_url;
    }
}
