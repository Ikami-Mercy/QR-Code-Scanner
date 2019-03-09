package com.ikami.pojo;

import com.google.gson.annotations.SerializedName;

public class Scan {

    @SerializedName("name")
    private String name;

    @SerializedName("token")
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
