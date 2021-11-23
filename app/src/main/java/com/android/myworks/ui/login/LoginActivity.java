package com.android.myworks.ui.login;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.myworks.R;
import com.android.myworks.base.BaseActivity;
import com.android.myworks.databinding.ActivityLoginBinding;
import com.android.myworks.model.LoginResponse;
import com.android.myworks.ui.bottom_sheet.BottomSheetFragment;
import com.android.myworks.ui.bottom_tab.RegistrationActivity;
import com.android.myworks.ui.spinnerwheel.RotateSpinnerActivity;
import com.android.myworks.ui.toggle.ToggleActivity;
import com.fedserv.searchablelist.SearchFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements ILoginView {

    ActivityLoginBinding mBinding;
    private LoginPresenter mPresenter;
    BottomSheetFragment bottomSheetFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = getViewDataBinding();

        mPresenter = new LoginPresenter(this, this);
        mPresenter.performLogin("mobile");

        ArrayList<String> mList = new ArrayList<>();
        mList.add("Test1");
        mList.add("Test2");
        mList.add("Test3");
        mList.add("Test4");
        mList.add("Test5");



        mBinding.rlLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), ToggleActivity.class);
//                startActivity(intent);


                showSpinner();
            }
        });


//        showProgress();
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                dismissProgress();
//            }
//        }, 2000);


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void checkNetwork(boolean networkStatus) {
        if (!networkStatus) {
            showNoInternetDialog(getSupportFragmentManager());
        }
    }


    @Override
    public <E extends Enum<E>> void onRetry(E w) {

        Log.e("TAG", "onRetry: " );

    }

    @Override
    public void onLoginSuccess(LoginResponse body) {

    }

    @Override
    public void onLoginError() {

    }

    @Override
    public void onServerError() {

    }

    private void showSpinner(){

        BottomSheetFragment.newInstance(getSupportFragmentManager(),R.layout.item_layout);

    }
}