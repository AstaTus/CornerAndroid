package com.astatus.cornerandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.adapder.CommentRecyclerAdapter;
import com.astatus.cornerandroid.presenter.CommentPresenter;
import com.astatus.cornerandroid.view.ICommentView;

public class CommentActivity extends AppCompatActivity implements ICommentView {

    private CommentPresenter mPresenter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);


    }

    private void init(){

        mRecyclerView = (RecyclerView)findViewById(R.id.comment_recyclerView);
        CommentRecyclerAdapter adapter = new CommentRecyclerAdapter(this);
        mRecyclerView.setAdapter(adapter);
        mPresenter = new CommentPresenter(this, adapter);
    }
}
