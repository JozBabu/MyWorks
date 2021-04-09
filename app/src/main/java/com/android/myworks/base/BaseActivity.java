package com.android.myworks.base;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentManager;


import com.android.myworks.common_interface.OnInternetConnectionCallback;
import com.android.myworks.dialogs.ProgressDialogFragment;
import com.android.myworks.network_check.NoInternetDialogFragment;


/**
 * @author Jose
 * @since 06/01/2021
 */

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity
        implements OnInternetConnectionCallback {

    private ProgressDialogFragment mProgressDialog;
    private T mViewDataBinding;
    private NoInternetDialogFragment mNoInternetDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        performDataBinding();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialogFragment.newInstance();
            mProgressDialog.show(getSupportFragmentManager(), "progress_dialog");
        }
    }

    public void dismissProgress() {
        try {
            if (mProgressDialog != null && !isFinishing()) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
    }

    protected void showNoInternetDialog(FragmentManager fm){
        if (mNoInternetDialog != null)
            mNoInternetDialog.dismiss();

        mNoInternetDialog = NoInternetDialogFragment.newInstance();
        mNoInternetDialog.setCancelable(false);
        mNoInternetDialog.show(fm, "no_internet_dialog");
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

}
