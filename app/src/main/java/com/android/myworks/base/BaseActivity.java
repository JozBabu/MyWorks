package com.android.myworks.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    public BroadcastReceiver broadcastReceiverNetwork = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Boolean status = getConnectivityStatus(context);

            checkNetwork(status);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        performDataBinding();


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerInternetCheckReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiverNetwork);
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

    public boolean getConnectivityStatus(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    private void registerInternetCheckReceiver() {
        IntentFilter internetFilter = new IntentFilter();
        internetFilter.addAction("android.net.wifi.STATE_CHANGE");
        internetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiverNetwork, internetFilter);
    }


    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    public abstract
    void checkNetwork(boolean networkStatus);

}
