package com.astatus.cornerandroid.activity;

import android.content.res.Resources;
import android.os.Bundle;
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
import com.astatus.cornerandroid.application.CornerApplication;
import com.astatus.cornerandroid.cache.UserCache;
import com.astatus.cornerandroid.entity.CommentEntity;
import com.astatus.cornerandroid.presenter.CommentPresenter;
import com.astatus.cornerandroid.view.ICommentView;
import com.astatus.cornerandroid.widget.CommentBottomMenu;
import com.astatus.cornerandroid.widget.HeadFootRecyclerView;

import java.math.BigInteger;
import java.util.List;

public class CommentActivity extends AppCompatActivity implements ICommentView {

    private CommentPresenter mPresenter;
    private HeadFootRecyclerView mRecyclerView;
    private EditText mCommentEditText;
    private ActionBar mActionBar;
    private BigInteger mTargetGuid = BigInteger.valueOf(0);
    CommentRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        init();

        BigInteger articleGuid = new BigInteger(getIntent().getStringExtra("ARTICLE_GUID"));

        mPresenter.setArticleGuid(articleGuid);
        mPresenter.bindArticleListData();
        mPresenter.loadNewPage();

    }

    private void init(){

        final Toolbar toolbar = (Toolbar) findViewById(R.id.comment_toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (HeadFootRecyclerView)findViewById(R.id.comment_recyclerView);
        mAdapter = new CommentRecyclerAdapter(this);
        mAdapter.setCommentItenClickListener(new CommentRecyclerAdapter.CommentItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {

                UserCache user = CornerApplication.getSingleton().getUserCache();
                final CommentEntity entity = (CommentEntity) view.getTag();
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


        mPresenter = new CommentPresenter(this);
    }

    @Override
    public void showNextPage() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNewPage() {

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadNewPageFailed() {

    }

    @Override
    public void loadNextPageFailed() {

    }

    @Override
    public void notifyCommentAdd(int pos) {
        mAdapter.notifyItemInserted(pos);
    }

    @Override
    public void notifyCommentRemove(int pos) {
        mAdapter.notifyItemRemoved(pos);
    }

    @Override
    public void changeRecyclerViewFootStyle(int style) {

    }

    @Override
    public void bindArticleListData(List<CommentEntity> list) {

        mAdapter.resetData(list);
    }
}
