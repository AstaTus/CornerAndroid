package com.astatus.cornerandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.adapder.CommentRecyclerAdapter;
import com.astatus.cornerandroid.presenter.CommentPresenter;
import com.astatus.cornerandroid.view.ICommentView;
import com.astatus.cornerandroid.widget.HeadFootRecyclerView;

public class CommentActivity extends AppCompatActivity implements ICommentView {

    private CommentPresenter mPresenter;
    private HeadFootRecyclerView mRecyclerView;
    private EditText mCommentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        init();

    }

    private void init(){

        mRecyclerView = (HeadFootRecyclerView)findViewById(R.id.comment_recyclerView);
        CommentRecyclerAdapter adapter = new CommentRecyclerAdapter(this);
        mRecyclerView.setAdapter(adapter);

        mCommentEditText = (EditText)findViewById(R.id.comment_edit_text);
        Button btn = (Button)findViewById(R.id.comment_send_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        mPresenter = new CommentPresenter(this, adapter);
    }
}
