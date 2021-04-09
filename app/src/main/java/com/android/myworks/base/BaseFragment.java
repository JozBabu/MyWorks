package com.android.myworks.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
}
