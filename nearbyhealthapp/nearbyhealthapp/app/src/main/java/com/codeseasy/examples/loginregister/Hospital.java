package com.codeseasy.examples.loginregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hospital {

    @SerializedName("locationID")
    @Expose
    public String locationID;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("phoneNo")
    @Expose
    public String phoneNo;
    @SerializedName("district")
    @Expose
    public String district;
    @SerializedName("postcode")
    @Expose
    public String postcode;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("latitude")
    @Expose
    public String latitude;
    @SerializedName("longitude")
    @Expose
    public String longitude;
}