package com.astatus.cornerandroid.adapder;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.astatus.cornerandroid.widget.HeadFootAdapter;

import java.util.List;

/**
 * Created by AstaTus on 2016/3/8.
 */
public class HeadFootRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int FOOT_STYLE_NOMORE = 0;
    public static final int FOOT_STYLE_LOADING = 1;
    public static final int FOOT_STYLE_CREATE = 2;

    private int mFootStyle;

    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_HEAD = 1;
    protected static final int TYPE_FOOT_NOMORE = 2;
    protected static final int TYPE_FOOT_LOADING = 3;
    protected static final int TYPE_FOOT_CREATE = 4;

    public interface OnCreateClickListener{
        void onClick();
    }

    private boolean mIsFootVisible = false;
    private boolean mIsHeadVisible = false;
    private boolean mIsFootEnable = false;
    private boolean mIsHeadEnable = false;


    private CreateFootAdapter mCreateFootAdapter;
    private NoMoreFootAdapter mNoMoreFootAdapter;
    private LoadMoreFootAdapter mLoadMoreFootAdapter;

    private HeadFootAdapter mHeadAdapter = null;
    private HeadFootAdapter mFootAdapter = null;

    private RecyclerView.Adapter mInnerAdapter;
    private OnCreateClickListener mCreateClickListener;

    public HeadFootRecyclerAdapter(boolean headEnable, boolean footEnalbe
            , RecyclerView.Adapter adapter, OnCreateClickListener listener){

        mIsHeadEnable = headEnable;
        mIsFootEnable = footEnalbe;
        mInnerAdapter = adapter;
        mCreateClickListener = listener;

       /* if (mIsHeadEnable)
            setHeadAdapter(head);

        if (mIsFootEnable)
            changeFootType(foot);*/

        mCreateFootAdapter = new CreateFootAdapter(new CreateFootAdapter.OnCreateClickListener() {
            @Override
            public void onClick() {
                if (mCreateClickListener != null){
                    mCreateClickListener.onClick();
                }
            }
        });

        mNoMoreFootAdapter = new NoMoreFootAdapter();
        mLoadMoreFootAdapter = new LoadMoreFootAdapter();
        mFootStyle = FOOT_STYLE_LOADING;
        mFootAdapter = mLoadMoreFootAdapter;
    }

    public void wrapAdapter(RecyclerView.Adapter adapter){
        mInnerAdapter = adapter;
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

    public void changeFootType(int type) {

        if (mFootStyle != type){
            /*switch (type){
                case FOOT_STYLE_CREATE :
                    mFootAdapter = mCreateFootAdapter;
                    break;
                case FOOT_STYLE_LOADING:
                    mFootAdapter = mLoadMoreFootAdapter;
                    break;
                case FOOT_STYLE_NOMORE:
                    mFootAdapter = mNoMoreFootAdapter;
                    break;
                default:
                    throw new Exception("HeadFootRecyclerAdapter:changeFootType: type is error");
            }*/

            mFootStyle = type;

            if (mIsFootVisible == true)
                notifyItemChanged(getItemCount() - 1);
        }
    }

    public void setHeadAdapter(HeadFootAdapter head){
        mHeadAdapter = head;
        if (mIsHeadVisible == true){
            notifyItemChanged(0);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEAD){
            return mHeadAdapter.onCreateDataViewHolder(parent, viewType);
        }else if (viewType == TYPE_FOOT_NOMORE ){
            return mNoMoreFootAdapter.onCreateDataViewHolder(parent, viewType);
        }else if (viewType == TYPE_FOOT_CREATE){
            return mCreateFootAdapter.onCreateDataViewHolder(parent, viewType);
        }else if (viewType == TYPE_FOOT_LOADING){
            return mLoadMoreFootAdapter.onCreateDataViewHolder(parent, viewType);
        }else{
            return mInnerAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = holder.getItemViewType();

        if (type == TYPE_HEAD){
            mHeadAdapter.onBindViewHolder(holder, position);
        }else if(type == TYPE_FOOT_NOMORE){
            mNoMoreFootAdapter.onBindViewHolder(holder, position);
        }else if(type == TYPE_FOOT_CREATE){
            mCreateFootAdapter.onBindViewHolder(holder, position);
        }else if(type == TYPE_FOOT_LOADING){
            mLoadMoreFootAdapter.onBindViewHolder(holder, position);
        }else{
            int dataPos = isHeadViewShow() ? position - 1 : position;
            mInnerAdapter.onBindViewHolder(holder, dataPos);
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (isFootViewShow())
            ++count;
        if (isHeadViewShow())
            ++count;
        count += mInnerAdapter.getItemCount();
        return count;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0 && isHeadViewShow())
            return TYPE_HEAD;
        else if ((position + 1 == getItemCount()) && isFootViewShow()){
            switch (mFootStyle){
                case FOOT_STYLE_NOMORE:
                    return TYPE_FOOT_NOMORE;
                case FOOT_STYLE_CREATE:
                    return TYPE_FOOT_CREATE;
                case FOOT_STYLE_LOADING:
                    return TYPE_FOOT_LOADING;
                default:
                    return TYPE_FOOT_LOADING;
            }
        }
        else{

            int dataPos = isHeadViewShow() ? position - 1 : position;
            return mInnerAdapter.getItemViewType(dataPos);
        }
    }
}
