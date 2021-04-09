package com.android.myworks.ui.login;



import android.os.Bundle;

import com.android.myworks.R;
import com.android.myworks.base.BaseActivity;
import com.android.myworks.databinding.ActivityLoginBinding;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {

    ActivityLoginBinding mBinding;
    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = getViewDataBinding();

        mPresenter.performLogin("mobile");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public <E extends Enum<E>> void onRetry(E w) {

    }
}