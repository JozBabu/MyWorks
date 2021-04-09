package com.android.myworks.ui.login;


import com.android.myworks.model.LoginResponse;

public interface ILoginView {
    void onLoginSuccess(LoginResponse body);

    void onLoginError();

    void onServerError();
}
