package com.android.myworks.ui.login;


import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.myworks.R;
import com.android.myworks.base.BaseActivity;
import com.android.myworks.databinding.ActivityLoginBinding;
import com.android.myworks.model.LoginResponse;
import com.android.myworks.ui.bottom_sheet.BottomSheetFragment;
import com.android.myworks.ui.progress_button.TransitionButton;


import java.util.ArrayList;


public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements ILoginView {

    ActivityLoginBinding mBinding;
    private LoginPresenter mPresenter;
    BottomSheetFragment bottomSheetFragment;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        mBinding.rlLogin.setButtonState(true);

        mBinding.rlLogin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {


                if (mBinding.rlLogin.getButtonState()) {

                    mBinding.rlLogin.startAnimation();
                    // Do your networking task or background work here.
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            boolean isSuccessful = true;

                            // Choose a stop animation if your call was succesful or not
                            if (isSuccessful) {
                                mBinding.rlLogin.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND, new TransitionButton.OnAnimationStopEndListener() {
                                    @Override
                                    public void onAnimationStopEnd() {
                                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                mBinding.rlLogin.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                            }
                        }
                    }, 2000);

                }
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

        Log.e("TAG", "onRetry: ");

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

    private void showSpinner() {

        BottomSheetFragment.newInstance(getSupportFragmentManager(), R.layout.item_layout);

    }
}