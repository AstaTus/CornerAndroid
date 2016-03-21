package com.astatus.cornerandroid.adapder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.dialog.CreateCornerDialog;
import com.astatus.cornerandroid.viewholder.CreateFootViewHolder;
import com.astatus.cornerandroid.viewholder.NormalFootViewHolder;
import com.astatus.cornerandroid.widget.HeadFootAdapter;

/**
 * Created by AstaTus on 2016/3/18.
 */
public class CreateFootAdapter extends HeadFootAdapter {


    public interface OnCreateClickListener{
        void onClick();
    }

    private OnCreateClickListener mListener;
    public CreateFootAdapter(OnCreateClickListener listener) {
        super(R.layout.widget_recylerview_create_foot);
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CreateFootViewHolder fvHolder = (CreateFootViewHolder)holder;
        fvHolder.mCreateTextView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClick();
                }
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(mResId, parent, false);
        return new CreateFootViewHolder(v);
    }
}
