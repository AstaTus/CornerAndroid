package com.astatus.cornerandroid.adapder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.viewholder.NormalFootViewHolder;
import com.astatus.cornerandroid.widget.HeadFootAdapter;

/**
 * Created by AstaTus on 2016/3/18.
 */
public class NoMoreFootAdapter extends HeadFootAdapter {
    public NoMoreFootAdapter() {
        super(R.layout.widget_recylerview_nomore_foot);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(mResId, parent, false);
        return new NormalFootViewHolder(v);
    }
}
