package com.developerxy.sqli_test.adapters.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * A custom ViewHolder.
 */
public abstract class BinderViewHolder<T> extends RecyclerView.ViewHolder {
    public BinderViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(T item);
}
