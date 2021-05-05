package com.onimaskesi.onistadiumfinder.model;

import androidx.room.*;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Stadium")
public class Stadium{

    @PrimaryKey
    @ColumnInfo(name = "Id")
    @SerializedName("Id")
    private int id;

    @ColumnInfo(name = "Name")
    @SerializedName("Name")
    private String name;

    @ColumnInfo(name = "Nation")
    @SerializedName("Nation")
    private String nation;

    @ColumnInfo(name = "Town")
    @SerializedName("Town")
    private String town;

    @ColumnInfo(name = "Latitude")
    @SerializedName("Latitude")
    private Float latitude;

    @ColumnInfo(name = "Longitude")
    @SerializedName("Longitude")
    private Float longitude;

    @ColumnInfo(name = "Capacity")
    @SerializedName("Capacity")
    private String capacity;

    public Stadium(int id, String name, String nation, String town, Float latitude, Float longitude, String capacity) {
        this.id = id;
        this.name = name;
        this.nation = nation;
        this.town = town;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacity = capacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

}
