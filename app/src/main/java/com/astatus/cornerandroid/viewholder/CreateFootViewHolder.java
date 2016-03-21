package com.astatus.cornerandroid.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.adapder.CreateFootAdapter;
import com.astatus.cornerandroid.adapder.LocationRecyclerAdapter;
import com.astatus.cornerandroid.dialog.CreateCornerDialog;

import java.math.BigInteger;

/**
 * Created by AstaTus on 2016/3/18.
 */
public class CreateFootViewHolder extends RecyclerView.ViewHolder {

    public TextView mCreateTextView;

    public CreateFootViewHolder(View itemView) {
        super(itemView);
        mCreateTextView = (TextView)itemView.findViewById(R.id.widget_foot_create_text);
    }
}
