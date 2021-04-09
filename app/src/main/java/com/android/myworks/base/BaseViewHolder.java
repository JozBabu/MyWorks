package com.android.myworks.base;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Jose Babu
 * @since 30/12/2020
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBind(int position);
}