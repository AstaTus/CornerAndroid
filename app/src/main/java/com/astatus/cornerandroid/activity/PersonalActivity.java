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
import com.astatus.cornerandroid.presenter.ArticlePresenter;
import com.astatus.cornerandroid.view.IArticleView;
import com.astatus.cornerandroid.widget.HeadFootRecyclerView;
import com.astatus.cornerandroid.widget.ProlateSwipeRefreshLayout;

public class PersonalActivity extends AppCompatActivity implements IArticleView {

    private HeadFootRecyclerView mRecyclerView;
    private ProlateSwipeRefreshLayout mSwipeRefreshLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private AppBarLayout mAppbarLayout;
    private TextView mTitleTextView;
    private ActionBar mActionBar;

    private ArticlePresenter mArticlePresenter;

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
        mSwipeRefreshLayout.autoRefresh();
    }

    private void init(){
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

            /*private void updateSwipeRefreshEnable(int verticalOffset){
                if (mSwipeRefreshLayout.isEnabled()){
                    if (mIsRecyclerViewTouchDown && verticalOffset != 0){
                        mSwipeRefreshLayout.setEnabled(false);
                    }
                }
            }*/

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
        PersonalRecyclerAdapter adapter = new PersonalRecyclerAdapter(this);
        mRecyclerView.setAdapter(adapter);
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

                mArticlePresenter.loadNewPage();
            }
        });



        /*List<ArticleEntity> entities = new ArrayList<ArticleEntity>();
        entities.add(new ArticleEntity());
        entities.add(new ArticleEntity());
        entities.add(new ArticleEntity());
        entities.add(new ArticleEntity());

        mAdapter = new PersonalRecyclerAdapter(entities, this);

        mRecyclerView.setAdapter(mAdapter);*/

        mArticlePresenter = new ArticlePresenter(this, adapter);
    }

    @Override
    public void showNextPage() {

        mRecyclerView.setLoadMore(false);
    }

    @Override
    public void showNewerPage() {
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void showLoadFailedToast() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
