package com.android.myworks.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.android.myworks.common_interface.FragmentInteractionListener;
import com.android.myworks.common_interface.OnInternetConnectionCallback;
import com.android.myworks.dialogs.ProgressDialogFragment;
import com.android.myworks.network_check.NoInternetDialogFragment;


/**
 * @author Jose
 * @since 06/01/2021
 */
public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment
        implements OnInternetConnectionCallback {

    protected FragmentInteractionListener mFragmentIListener;
    private T mViewDataBinding;
    private ProgressDialogFragment mProgressDialog;
    private NoInternetDialogFragment mNoInternetDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            mFragmentIListener = (FragmentInteractionListener) context;
        }
    }

    public BroadcastReceiver broadcastReceiverNetwork = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Boolean status = getConnectivityStatus(context);
            checkNetwork(status);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(false);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);

        return mViewDataBinding.getRoot();
    }

    @Override
    public void onDetach() {
        mFragmentIListener = null;
        super.onDetach();
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();


    /**
     * @Check network Status
     */
    public abstract
    void checkNetwork(boolean networkStatus);

    public void showProgress() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialogFragment.newInstance();
            mProgressDialog.show(getFragmentManager(), "progress_dialog");
        }
    }

    public void dismissProgress() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    protected void showNoInternetDialog(FragmentManager fm, Fragment fragment){
        if (mNoInternetDialog != null)
            mNoInternetDialog.dismiss();

        mNoInternetDialog = NoInternetDialogFragment.newInstance();
        mNoInternetDialog.setTargetFragment(fragment, 100);
        mNoInternetDialog.setCancelable(false);
        mNoInternetDialog.show(fm, "no_internet_dialog");
    }

    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public boolean getConnectivityStatus(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }


    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(broadcastReceiverNetwork);
    }


    @Override
    public void onResume() {
        super.onResume();
        registerInternetCheckReceiver();
    }

    private void registerInternetCheckReceiver() {
        IntentFilter internetFilter = new IntentFilter();
        internetFilter.addAction("android.net.wifi.STATE_CHANGE");
        internetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        getContext().registerReceiver(broadcastReceiverNetwork, internetFilter);
    }


}
