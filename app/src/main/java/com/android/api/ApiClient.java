package com.android.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

//   private static String BASE_URL = "https://api.postalpincode.in/"; //Staging

    private static String BASE_URL = "http://192.168.234.2:8071/api/"; //LocalHost


    private static OkHttpClient.Builder httpClient = null;

    public static Retrofit getClient() {

        return new Retrofit.Builder().baseUrl(ApiClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getHttpClient().build())
                .build();

    }

    private static OkHttpClient.Builder getHttpClient() {
        if (httpClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(60000, TimeUnit.SECONDS);
            httpClient.readTimeout(60000, TimeUnit.SECONDS).build();
            httpClient.addInterceptor(logging);
        }
        return httpClient;
    }

}
