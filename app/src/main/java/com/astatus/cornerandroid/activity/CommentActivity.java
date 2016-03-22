package com.astatus.cornerandroid.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.adapder.CommentRecyclerAdapter;
import com.astatus.cornerandroid.adapder.HeadFootRecyclerAdapter;
import com.astatus.cornerandroid.application.CornerApplication;
import com.astatus.cornerandroid.cache.UserCache;
import com.astatus.cornerandroid.entity.CommentEntity;
import com.astatus.cornerandroid.presenter.CommentPresenter;
import com.astatus.cornerandroid.view.ICommentView;
import com.astatus.cornerandroid.widget.CommentBottomMenu;
import com.astatus.cornerandroid.widget.HeadFootRecyclerView;
import com.astatus.cornerandroid.widget.ProlateSwipeRefreshLayout;

import java.math.BigInteger;
import java.util.List;

public class CommentActivity extends AppCompatActivity implements ICommentView {

    private CommentPresenter mPresenter;
    private HeadFootRecyclerView mRecyclerView;
    private ProlateSwipeRefreshLayout mSwipeRefreshLayout;
    private EditText mCommentEditText;
    private ActionBar mActionBar;
    private BigInteger mTargetGuid = BigInteger.valueOf(0);
    CommentRecyclerAdapter mDataAdapter;
    HeadFootRecyclerAdapter mAdapter;
    private boolean mIsFirstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        init();

        BigInteger articleGuid = new BigInteger(getIntent().getStringExtra("ARTICLE_GUID"));

        mPresenter.setArticleGuid(articleGuid);
        mPresenter.bindArticleListData();
        mSwipeRefreshLayout.autoRefresh();

    }

    private void init(){

        final Toolbar toolbar = (Toolbar) findViewById(R.id.comment_toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (HeadFootRecyclerView)findViewById(R.id.comment_recyclerView);
        mDataAdapter = new CommentRecyclerAdapter(this, new CommentRecyclerAdapter.CommentItemClickListener() {
            @Override
            public void onCommentClick(final CommentEntity entity) {

                UserCache user = CornerApplication.getSingleton().getUserCache();
                if (entity != null) {

                    if (entity.mReplyGuid.compareTo(user.getMainUserGuid()) == 0) {

                        CommentBottomMenu menu =
                                new CommentBottomMenu(CommentActivity.this, new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        mPresenter.removeComment(entity.mGuid);
                                    }
                                });

                        menu.showAtLocation(
                                CommentActivity.this.findViewById(R.id.comment_root),
                                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

                    } else {
                        String hint = CommentActivity.this.getString(R.string.comment_input_hint_reply);
                        hint += " " + entity.mReplyName + ":";
                        mCommentEditText.setHint(hint);
                        mCommentEditText.requestFocus();
                    }
                }
            }
        });

        mAdapter = new HeadFootRecyclerAdapter(false, true, mDataAdapter, null);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setLoadMoreEnable(true);
        mRecyclerView.setOnLoadMoreListener(new HeadFootRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.loadNextPage();
            }
        });

        mPresenter = new CommentPresenter(this);

        mSwipeRefreshLayout = (ProlateSwipeRefreshLayout)findViewById(R.id.comment_swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Your refresh code here

                if (mIsFirstLoad){
                    mPresenter.loadNextPage();
                }else{
                    mPresenter.loadNewPage();
                }

            }
        });
        mCommentEditText = (EditText)findViewById(R.id.comment_edit_text);
        Button btn = (Button)findViewById(R.id.comment_send_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCommentEditText.getText().length() > 0){
                    mPresenter.sendComment(mTargetGuid
                            , mCommentEditText.getText().toString());
                }
            }
        });
    }

    @Override
    public void showNextPage() {
        if (mIsFirstLoad){
            mIsFirstLoad = false;
            mSwipeRefreshLayout.setRefreshing(false);
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNewPage() {

        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadNewPageFailed() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void loadNextPageFailed() {

        if (mIsFirstLoad){
            mIsFirstLoad = false;
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void notifyCommentAdd(int pos) {
        mAdapter.notifyItemInserted(pos);
    }

    @Override
    public void commentAddFailed() {

    }

    @Override
    public void notifyCommentRemove(int pos) {
        mAdapter.notifyItemRemoved(pos);
    }

    @Override
    public void commentRemoveFailed() {

    }

    @Override
    public void changeRecyclerViewFootStyle(boolean isLoadMore) {

        mRecyclerView.setLoadMoreEnable(isLoadMore);
    }

    @Override
    public void bindArticleListData(List<CommentEntity> list) {

        mDataAdapter.resetData(list);
        mAdapter.notifyDataSetChanged();
    }
}
