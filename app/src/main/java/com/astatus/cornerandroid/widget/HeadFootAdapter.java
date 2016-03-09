package com.astatus.cornerandroid.widget;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by AstaTus on 2016/3/9.
 */
public abstract class HeadFootAdapter {
    protected int mResId;

    public HeadFootAdapter(@LayoutRes int id){
        mResId = id;
    }

    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    public abstract RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType);
}
