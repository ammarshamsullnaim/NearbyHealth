package com.codeseasy.examples.loginregister;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("userID")
    @Expose
    public String userID;
    @SerializedName("fullName")
    @Expose
    public String fullName;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("contact")
    @Expose
    public String contact;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("userAgent")
    @Expose
    public String userAgent;
    @SerializedName("locationID")
    @Expose
    public Object locationID;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("loginTime")
    @Expose
    public String loginTime;

}