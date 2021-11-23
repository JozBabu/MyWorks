package com.android.myworks.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("Result")
    private String Result;
    @SerializedName("CustomerId")
    private String CustomerId;

    public String getResult() {
        return Result;
    }

    public String getCustomerId() {
        return CustomerId;
    }
}
