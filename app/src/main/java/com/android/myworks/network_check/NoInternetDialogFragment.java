package com.android.myworks.network_check;

import android.app.Dialog;
import android.content.Context;
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
}
