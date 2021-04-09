package com.android.api;

import com.android.myworks.model.LoginRequestBody;
import com.android.myworks.model.LoginResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {




    @POST("login")
    Call<LoginResponse> login(@Body LoginRequestBody requestBody);


}
