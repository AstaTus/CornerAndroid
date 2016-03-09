package com.astatus.cornerandroid.adapder;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.viewholder.SwipeRefreshFootViewHolder;

/**
 * Created by AstaTus on 2016/3/8.
 */
public abstract class HeadFootRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_HEAD = 1;
    protected static final int TYPE_FOOT = 2;

    private boolean mIsFootVisible = false;
    private boolean mIsHeadVisible = false;
    private boolean mIsFootEnable = false;
    private boolean mIsHeadEnable = false;

    private int mFootRes;
    private int mHeadRes;

    public HeadFootRecyclerAdapter(boolean headEnable, boolean footEnalbe){

        mIsHeadEnable = headEnable;
        mIsFootEnable = footEnalbe;
    }

    public boolean isFootViewShow(){
        return mIsFootVisible;
    }

    public boolean isHeadViewShow(){
        return mIsHeadVisible;
    }

    public void showHeadView(){
        if (mIsHeadEnable){
            if (mIsHeadVisible == false){
                mIsHeadVisible = true;
                notifyItemInserted(0);
            }
        }
    }

    public void hideHeadView(){
        if (mIsHeadVisible == true){
            notifyItemRemoved(0);
            mIsHeadVisible = false;
        }
    }

    public void showFootView(){
        if (mIsFootEnable){
            if (mIsFootVisible == false){
                mIsFootVisible = true;
                notifyItemInserted(getItemCount() - 1);
            }
        }
    }

    public void hideFootView(){
        if (mIsFootVisible == true){
            notifyItemRemoved(getItemCount() - 1);
            mIsFootVisible = false;
        }
    }

    public void setFootViewHolder(@LayoutRes int resource){
        mFootRes = resource;
        if (mIsFootVisible == true){
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public void setHeadViewHolder(@LayoutRes int resource){

        mHeadRes = resource;
        if (mIsHeadVisible == true){
            notifyItemChanged(0);
        }
    }

    public int getRealPosition(int dataPos){
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEAD){
            View v = LayoutInflater.from(parent.getContext()).inflate(mHeadRes, parent, false);
            return new HeadViewHolder(v);
        }else if (viewType == TYPE_FOOT){
            View v = LayoutInflater.from(parent.getContext()).inflate(mFootRes, parent, false);
            return new FootViewHolder(v);
        }else{
            return onCreateDataViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if ((type != TYPE_HEAD) && (type != TYPE_FOOT)){
            onBindDataViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (isFootViewShow())
            ++count;
        if (isHeadViewShow())
            ++count;
        count += getDataItemCount();
        return count;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0 && isHeadViewShow())
            return TYPE_HEAD;
        else if ((position + 1 == getItemCount()) && isFootViewShow())
            return TYPE_FOOT;
        // 最后一个item设置为footerView
        else
            return TYPE_ITEM;
    }

    public static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }
    public static class HeadViewHolder extends RecyclerView.ViewHolder {

        public HeadViewHolder(View itemView) {
            super(itemView);
        }
    }

    public abstract int getDataItemCount();

    public abstract void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position);

    public abstract RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType);
}
