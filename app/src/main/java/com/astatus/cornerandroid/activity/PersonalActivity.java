package com.astatus.cornerandroid.activity;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.adapder.PersonalRecyclerAdapter;
import com.astatus.cornerandroid.entity.ArticleEntity;
import com.astatus.cornerandroid.presenter.ArticlePresenter;
import com.astatus.cornerandroid.view.IArticleView;
import com.astatus.cornerandroid.widget.HeadFootRecyclerView;
import com.astatus.cornerandroid.widget.ProlateSwipeRefreshLayout;

import java.util.List;

public class PersonalActivity extends AppCompatActivity implements IArticleView {

    private HeadFootRecyclerView mRecyclerView;
    private ProlateSwipeRefreshLayout mSwipeRefreshLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private AppBarLayout mAppbarLayout;
    private TextView mTitleTextView;
    private ActionBar mActionBar;
    private PersonalRecyclerAdapter mAdpater;

    private ArticlePresenter mArticlePresenter;
    private boolean mIsFirstLoad = true;

    //private boolean mIsRecyclerViewTouchDown = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        init();

        /*mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mSwipeRefreshLayout.setRefreshing(true);

        mArticlePresenter.loadNewPage();*/
        mIsFirstLoad = true;
        mSwipeRefreshLayout.autoRefresh();
    }

    private void init(){
        mArticlePresenter = new ArticlePresenter(this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.personal_toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(R.layout.fragment_personal_actionbar);

        mTitleTextView = (TextView)findViewById(R.id.personal_actionbar_title);
        mAppbarLayout = (AppBarLayout)findViewById(R.id.personal_appbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.personal_collapsing_toolbar_layout);

        mAppbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                titleAlphaChange(verticalOffset);
                //updateSwipeRefreshEnable(verticalOffset);
            }

            private void titleAlphaChange(int verticalOffset) {
                float collapsingToolbarLayoutMiniHeight = ViewCompat.
                        getMinimumHeight(mCollapsingToolbarLayout);
                float collapsingToolbarLayoutHeight = mCollapsingToolbarLayout.getHeight();
                int alpha = (int) (255 * (1 -
                        (collapsingToolbarLayoutHeight - collapsingToolbarLayoutMiniHeight + verticalOffset) /
                                (collapsingToolbarLayoutHeight - collapsingToolbarLayoutMiniHeight)));

                mTitleTextView.setTextColor(Color.argb(alpha, 255, 255, 255));
            }
        });

        mRecyclerView = (HeadFootRecyclerView)findViewById(R.id.personal_recyclerView);
        mAdpater = new PersonalRecyclerAdapter(this, mArticlePresenter);
        mRecyclerView.setAdapter(mAdpater);
        /*mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    mIsRecyclerViewTouchDown = true;
                }else if (event.getAction() == MotionEvent.ACTION_UP){
                    mIsRecyclerViewTouchDown = false;
                    mSwipeRefreshLayout.setEnabled(true);
                }
                return false;
            }
        });*/

        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setLoadMoreEnable(true);
        mRecyclerView.setOnLoadMoreListener(new HeadFootRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mArticlePresenter.loadNextPage();
            }
        });

        mSwipeRefreshLayout = (ProlateSwipeRefreshLayout)findViewById(R.id.personal_swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Your refresh code here

                if (mIsFirstLoad){
                    mArticlePresenter.loadNextPage();
                }else{
                    mArticlePresenter.loadNewPage();
                }

            }
        });

        mArticlePresenter.bindArticleListData();
    }

    @Override
    public void showNextPage() {
        if (mIsFirstLoad){
            mIsFirstLoad = false;
            mSwipeRefreshLayout.setRefreshing(false);
        }
        mAdpater.notifyDataSetChanged();
    }

    @Override
    public void showNewPage() {
        mAdpater.showFootView();
        mAdpater.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void updateAllArticles() {
        mAdpater.notifyDataSetChanged();
    }

    @Override
    public void bindArticleListData(List<ArticleEntity> list) {
        mAdpater.restData(list);
    }

    @Override
    public void changeRecyclerViewFootStyle(boolean isLoadMoreStyle) {
        mRecyclerView.setLoadMoreEnable(isLoadMoreStyle);
    }

    @Override
    public void notifyUpState(int index) {
        mAdpater.notifyItemChanged(index);
    }

    @Override
    public void loadNextPageFailed() {

        if (mIsFirstLoad){
            mIsFirstLoad = false;
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void loadNewPageFailed() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
