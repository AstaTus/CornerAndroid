package com.astatus.cornerandroid.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.astatus.cornerandroid.adapder.HeadFootRecyclerAdapter;

/**
 * Created by AstaTus on 2016/3/8.
 */
public class LoadMoreRecyclerView extends RecyclerView {

    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean mIsLoadMore;

    public LoadMoreRecyclerView(Context context) {
        super(context);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void SetOnLoadMoreListener(OnLoadMoreListener listener){
        mOnLoadMoreListener = listener;
    }

    private void  init(){
        this.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private int mLastItemPos;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mOnLoadMoreListener != null) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE
                            && mLastItemPos + 1 == getAdapter().getItemCount()) {
                        setLoadMore(true);
                        mOnLoadMoreListener.onLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutMgr = (LinearLayoutManager) (getLayoutManager());
                mLastItemPos = layoutMgr.findLastVisibleItemPosition();
            }
        });

        /*super.addOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (null != mOnLoadMoreListener && dy > 0) {
                    int lastVisiblePosition = getLastVisiblePosition();
                    if (lastVisiblePosition + 1 == getAdapter().getItemCount()) {
                        setLoadMore(true);

                        mOnLoadMoreListener.onLoadMore();
                    }
                }
            }
        });*/
    }


    private void setLoadMore(boolean isLoadMore){
        if (isLoadMore){
            HeadFootRecyclerAdapter adapter = (HeadFootRecyclerAdapter)getAdapter();
            adapter.showFootView();
        }else{
            HeadFootRecyclerAdapter adapter = (HeadFootRecyclerAdapter)getAdapter();
            adapter.hideFootView();
        }
        mIsLoadMore = isLoadMore;
    }

    public boolean getLoadMore(){
        return mIsLoadMore;
    }

    /**
     * 获取最后一条展示的位置
     *
     * @return
     */
    private int getLastVisiblePosition() {
        int position;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else if (getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }



    public interface OnLoadMoreListener {
        /**
         * 加载更多
         */
        void onLoadMore();
    }
}
