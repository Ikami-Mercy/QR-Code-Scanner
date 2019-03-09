package com.ikami.pojo;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("success")
    private boolean success;

    @SerializedName("msg")
    private String msg;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
