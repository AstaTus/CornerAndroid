package com.astatus.cornerandroid.adapder;

import android.content.Context;
import android.location.Location;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.entity.CornerEntity;
import com.astatus.cornerandroid.widget.HeadFootAdapter;

import java.util.List;

/**
 * Created by AstaTus on 2016/3/17.
 */
public class LocationRecyclerAdapter extends HeadFootRecyclerAdapter{

    private Context mContext;
    private List<CornerEntity> mCorners;
    private View.OnClickListener mLocationItemClickListener;

    private LocationFootAdapter mFootAdapter;


    public LocationRecyclerAdapter(Context context, View.OnClickListener listener) {
        super(false, true);

        mFootAdapter = new LocationFootAdapter(R.layout.widget_recylerview_loading_foot);
        setFootAdapter(mFootAdapter);
        mContext = context;
        mLocationItemClickListener = listener;
    }

    public void resetData(List<CornerEntity> list){
        mCorners = list;
        notifyDataSetChanged();
    }

    public void changeFootStyle(int style){
        mFootAdapter.changeStyle(style);
        notifyItemChanged(this.getItemCount() - 1);
    }

    @Override
    public int getDataItemCount() {
        return mCorners.size();
    }

    @Override
    public void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position) {
        LocationViewHolder lvHolder = (LocationViewHolder)holder;
        CornerEntity entity = mCorners.get(position);
        if (entity != null){
            lvHolder.mLocationText.setText(entity.mName);
            lvHolder.mRoot.setTag(entity);
            lvHolder.mRoot.setOnClickListener(mLocationItemClickListener);
        }
    }

    private class LocationRootClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_location_item, parent, false);
        return new LocationViewHolder(v);
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

    class LocationFootAdapter extends HeadFootAdapter{

        private int mStyle = 0;
        public LocationFootAdapter(@LayoutRes int id) {
            super(id);
        }

        public void changeStyle(int style){
            mStyle = style;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {

            View v;
            if (mStyle == 0){
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_recylerview_loading_foot, parent, false);
            }else if (mStyle == 1){
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_recylerview_nomore_foot, parent, false);
            }else{
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_recylerview_create_foot, parent, false);
            }


            return new FootViewHolder(v);
        }
    }

    public static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }
}
