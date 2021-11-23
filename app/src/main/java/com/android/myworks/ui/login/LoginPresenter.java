package com.android.myworks.ui.login;




import android.content.Context;

import com.android.api.ApiClient;
import com.android.api.ApiInterface;
import com.android.myworks.model.LoginRequestBody;
import com.android.myworks.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {

    private Context mContext;
    private ILoginView mView;

    public LoginPresenter(Context mContext, ILoginView mView) {
        this.mContext = mContext;
        this.mView = mView;
    }


    public void performLogin(String mobile) {
//        LoginRequestBody requestBody = new LoginRequestBody(mobile);
//        Call<LoginResponse> call = ApiClient.getClient().create(ApiInterface.class)
//                .login("682037");
//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                if(response.isSuccessful() && response.body() != null)
//                    mView.onLoginSuccess(response.body());
//                else
//                    mView.onLoginError();
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                mView.onServerError();
//            }
//        });
    }
}
