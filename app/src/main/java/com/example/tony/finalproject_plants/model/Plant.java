package com.example.tony.finalproject_plants.model;

import java.io.Serializable;

/**
 * Created by SHIYONG on 2017/10/13.
 */

public class Plant implements Serializable{
    private String plant_name;
    private String short_info;
    private String latitude;
    private String longitude;
    private int plant_id;
    private int image_id;
    public Plant(){
    }
    public String getPlant_name(){
        return this.plant_name;
    }
    public String getShort_info(){
       return this.short_info;
    }
    public void setPlant_name(String plant_name){
        this.plant_name=plant_name;
    }

    public void setShort_info(String short_info) {
        this.short_info = short_info;
    }

    public int getPlant_id() {
        return plant_id;
    }

    public void setPlant_id(int plant_id) {
        this.plant_id = plant_id;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
