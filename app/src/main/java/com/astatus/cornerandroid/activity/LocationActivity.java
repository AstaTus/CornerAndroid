package com.astatus.cornerandroid.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.amap.api.services.core.AMapException;
import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.adapder.HeadFootRecyclerAdapter;
import com.astatus.cornerandroid.adapder.LocationRecyclerAdapter;
import com.astatus.cornerandroid.dialog.CreateCornerDialog;
import com.astatus.cornerandroid.entity.CornerEntity;
import com.astatus.cornerandroid.presenter.LocationPresenter;
import com.astatus.cornerandroid.view.ILocationView;
import com.astatus.cornerandroid.widget.HeadFootRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.math.BigInteger;
import java.util.List;

public class LocationActivity extends AppCompatActivity
        implements ILocationView {

    private LocationPresenter mPresenter;
    private HeadFootRecyclerView mRecyclerView;
    private LocationRecyclerAdapter mDataAdapter;
    private HeadFootRecyclerAdapter mAdapter;
    private EditText mSearchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        init();
        mPresenter = new LocationPresenter(this, this);
        mPresenter.bindCornerListData();
        mPresenter.startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.location_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button

        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        lp.setMargins(20, 20, 20, 20);
        View actionBarView = LayoutInflater.from(this).inflate(R.layout.widget_location_head, null);
        actionBar.setCustomView(actionBarView, lp);

        mRecyclerView = (HeadFootRecyclerView) findViewById(R.id.location_recyclerView);

        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this).build());

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setLoadMoreEnable(true);
        mRecyclerView.setOnLoadMoreListener(new HeadFootRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.queryCornerNextPage();
            }
        });



        mDataAdapter = new LocationRecyclerAdapter(this, new LocationRecyclerAdapter.OnCornerClickListener() {
            @Override
            public void onClick(BigInteger guid, String name) {
                Intent intent=new Intent();
                intent.putExtra("name", name.toString());
                intent.putExtra("guid", guid.toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        mAdapter = new HeadFootRecyclerAdapter(false, true, mDataAdapter, new HeadFootRecyclerAdapter.OnCreateClickListener() {
            @Override
            public void onClick() {
                showCreateCornerDialog();
            }
        });

        mAdapter.wrapAdapter(mDataAdapter);
        mRecyclerView.setAdapter(mAdapter);

        mSearchEditText = (EditText)findViewById(R.id.location_search_edit_text);
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    mPresenter.querySearchCorner(s.toString());
                } catch (AMapException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void showCreateCornerDialog(){

        CreateCornerDialog dialog = new CreateCornerDialog(LocationActivity.this, new CreateCornerDialog.OnCreateCornerListener() {
            @Override
            public void onSure(String name) {
                mPresenter.createNewCorner(name);
            }
        });


        dialog.show();
    }



    @Override
    public void showFinishLocation() {

    }

    @Override
    public void showLocationFailed() {

    }

    @Override
    public void showNextPageCorner(int start, int count) {
        mDataAdapter.notifyItemRangeInserted(start, count);
    }

    @Override
    public void showNewSearchCorner() {
        mAdapter.showFootView();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCreateCornerFailed() {

    }

    @Override
    public void notifyCreateCornerSuccess(CornerEntity entity) {
        Intent intent=new Intent();
        intent.putExtra("name", entity.mName.toString());
        intent.putExtra("guid", entity.mGuid.toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void changeRecyclerViewFoot(int type){

        mAdapter.changeFootType(type);
    }

    @Override
    public void bindCornerData(List<CornerEntity> list) {

        mDataAdapter.resetData(list);
        mAdapter.notifyDataSetChanged();
    }
}

