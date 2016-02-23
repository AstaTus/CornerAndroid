package com.astatus.cornerandroid.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.adapder.PersonalRecyclerAdapter;
import com.astatus.cornerandroid.entity.PostEntity;

import java.util.ArrayList;
import java.util.List;

public class PersonalActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PersonalRecyclerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        initView();
    }

    private void initView(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.personal_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView)findViewById(R.id.personal_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.personal_swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Your refresh code here
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        List<PostEntity> entities = new ArrayList<PostEntity>();
        entities.add(new PostEntity());
        entities.add(new PostEntity());
        entities.add(new PostEntity());
        entities.add(new PostEntity());

        mAdapter = new PersonalRecyclerAdapter(entities);

        mRecyclerView.setAdapter(mAdapter);

    }
}
