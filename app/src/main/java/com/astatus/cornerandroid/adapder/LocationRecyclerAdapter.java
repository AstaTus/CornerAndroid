package com.astatus.cornerandroid.adapder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.dialog.CreateCornerDialog;
import com.astatus.cornerandroid.entity.CornerEntity;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by AstaTus on 2016/3/17.
 */
public class LocationRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<CornerEntity> mCorners;
    private OnCornerClickListener mCornerClickListener;

    public interface OnCornerClickListener{
        void onClick(BigInteger guid, String name);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_location_item, parent, false);
        return new LocationViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LocationViewHolder lvHolder = (LocationViewHolder)holder;
        final CornerEntity entity = mCorners.get(position);
        if (entity != null){
            lvHolder.mLocationText.setText(entity.mName);
            lvHolder.mRoot.setTag(entity);
            lvHolder.mRoot.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if (mCornerClickListener != null){
                        mCornerClickListener.onClick(entity.mGuid, entity.mName);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCorners.size();
    }

    public LocationRecyclerAdapter(Context context
            , OnCornerClickListener clickListener) {
        super();

        mContext = context;
        mCornerClickListener = clickListener;
    }

    public void resetData(List<CornerEntity> list){
        mCorners = list;
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {

        protected View mRoot;
        protected TextView mLocationText;

        public LocationViewHolder(View v) {
            super(v);

            mRoot = v.findViewById(R.id.location_item_root);
            mLocationText = (TextView)v.findViewById(R.id.location_item_name_text);
        }
    }
}
