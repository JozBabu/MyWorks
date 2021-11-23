package com.android.myworks.network_check;

import android.app.Dialog;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.android.myworks.R;
import com.android.myworks.common_interface.OnInternetConnectionCallback;
import com.android.myworks.databinding.LayoutNoInternetBinding;
import com.android.myworks.utils.NetworkUtils;


public class NoInternetDialogFragment extends DialogFragment
        implements View.OnClickListener {

    private LayoutNoInternetBinding mBinding;

    private OnInternetConnectionCallback mListener;

    public static NoInternetDialogFragment newInstance() {
        NoInternetDialogFragment dialog = new NoInternetDialogFragment();
        Bundle args = new Bundle();
        dialog.setArguments(args);
        return dialog;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getTargetFragment() instanceof OnInternetConnectionCallback) {
            mListener = (OnInternetConnectionCallback) getTargetFragment();
        } else if (context instanceof OnInternetConnectionCallback) {
            mListener = (OnInternetConnectionCallback) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnInternetConnectionCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Fullscreen);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.layout_no_internet, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.btnRetry.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_retry) {
            if (getActivity() != null && NetworkUtils.isNetworkConnected(getActivity())) {
                mListener.onRetry(null);
                dismiss();
            }
        }
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

    public boolean getConnectivityStatus(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    private void checkNetwork(boolean networkStatus) {
        if (networkStatus) {
            mListener.onRetry(null);
            dismiss();
        }
    }
}
