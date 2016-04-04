package com.astatus.cornerandroid.activity;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
//import android.support.v7.recyclerview;
import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.adapder.HeadFootRecyclerAdapter;
import com.astatus.cornerandroid.adapder.HomeRecyclerAdapter;
import com.astatus.cornerandroid.presenter.HomePresenter;
import com.astatus.cornerandroid.util.ToastUtil;
import com.astatus.cornerandroid.view.IHomeView;
import com.astatus.cornerandroid.viewholder.CornerArticleViewHolder;
import com.astatus.cornerandroid.viewholder.UserArticleViewHolder;
import com.astatus.cornerandroid.widget.FloatingActionMenuLayout;
import com.astatus.cornerandroid.widget.FloatingActionMenuButton;
import com.astatus.cornerandroid.widget.HeadFootRecyclerView;
import com.astatus.cornerandroid.widget.ProlateSwipeRefreshLayout;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.math.BigInteger;
import java.util.List;


/**
 * Created by AstaTus on 2016/1/14.
 */
public class HomeActivity extends AppCompatActivity implements IHomeView {

    private static final int REQUEST_CODE_PICK_PHOTO = 1;
    private static final int REQUEST_CODE_CAMERA = 2;

    private FloatingActionButton mFab;
    private ObjectAnimator mExpandMenuAnimator;
    private ObjectAnimator mCollapsedMenuAnimator;
    private FloatingActionMenuLayout mAddMenu;
    private CoordinatorLayout mRootLayout;
    private RoundedImageView mHeadImageView;
    private HeadFootRecyclerView mRecyclerView;
    private HeadFootRecyclerAdapter mAdapter;
    private HomeRecyclerAdapter mDataAdapter;
    private ProlateSwipeRefreshLayout mRefreshLayout;
    private HomePresenter mPresenter;

    private static int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_home);
        }catch (Exception e){
            e.printStackTrace();
        }

        initView();

        mPresenter = new HomePresenter(this, this);
        mRefreshLayout.autoRefresh();

        processExtraData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar)findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button

        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.fragment_home_actionbar);

        mRootLayout = (CoordinatorLayout)findViewById(R.id.home_content);
        mExpandMenuAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.fab_clockwise_animator);
        mCollapsedMenuAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.fab_anticlockwise_animator);
        mFab = (FloatingActionButton)findViewById(R.id.home_fab);
        mExpandMenuAnimator.setTarget(mFab);
        mCollapsedMenuAnimator.setTarget(mFab);
        mAddMenu = (FloatingActionMenuLayout)findViewById(R.id.home_menu);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mAddMenu.isExpanded()) {
                    mAddMenu.collapse();
                    mCollapsedMenuAnimator.start();
                } else {
                    mAddMenu.expand();
                    mExpandMenuAnimator.start();
                }
            }
        });


        FloatingActionMenuButton btn = (FloatingActionMenuButton)findViewById(R.id.home_menu_camara_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select image"), REQUEST_CODE_PICK_PHOTO);
            }
        });

        btn = (FloatingActionMenuButton)findViewById(R.id.home_menu_photo_btn);

        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File dir = new File(Environment.getExternalStorageDirectory() + "/Images");
                if(!dir.exists()){
                    dir.mkdirs();
                }
                Uri url = Uri.fromFile(
                        new File(Environment.getExternalStorageDirectory() + "/Images/",
                                "cameraImg" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, url);
                cameraIntent.putExtra("return-data", true);
                startActivity(cameraIntent);
            }
        });

        mRecyclerView = (HeadFootRecyclerView)findViewById(R.id.home_recyclerView);
        mRecyclerView.setOnLoadMoreListener(new HeadFootRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (mPresenter.isReLocation()) {
                    mPresenter.startLocation();
                } else {
                    mPresenter.loadNextArticles();
                }
            }
        });

        mDataAdapter = new HomeRecyclerAdapter(
                new CornerArticleViewHolder.CornerWantClickListener() {

                    @Override
                    public void onWantClick(BigInteger guid) {

                    }
                }
                , new UserArticleViewHolder.ArticleUpClickListener() {
                    @Override
                    public void onUpClick(BigInteger guid) {

                    }
                });

        mAdapter = new HeadFootRecyclerAdapter(false, true, mDataAdapter, null);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout = (ProlateSwipeRefreshLayout)findViewById(R.id.home_swiperefresh);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(mPresenter.isReLocation()){
                    mPresenter.startLocation();
                }else{
                    mPresenter.loadNewArticles();
                }
            }
        });


        mHeadImageView = (RoundedImageView)findViewById(R.id.home_head_image);
        mHeadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent personalIntent = new Intent(HomeActivity.this, PersonalActivity.class);
                startActivity(personalIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_home_actionbar, menu);
        return true;
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
//        mDrawerToggle.syncState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_PICK_PHOTO) {
            if (resultCode == Activity.RESULT_OK){
                Uri uri = data.getData();
                ToastUtil.showText(this, uri.toString());
                Intent pictureIntent = new Intent(this, PictureEditActivity.class);
                pictureIntent.putExtra("uri", uri.toString());
                startActivity(pictureIntent);
            }
            else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }//onActivityResult

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        setIntent(intent);

        processExtraData();
    }

    private void processExtraData(){

        Intent intent = getIntent();
        int param = intent.getIntExtra(ActivityDef.IINTENT_PARAM1, ActivityDef.IP_UNDEFINE);
        if (intent != null && param == ActivityDef.IP_SEND_SUCCESS) {

            String tip = this.getResources().getString(R.string.home_send_success_tip);
            Snackbar.make(mRootLayout, tip, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public void showLocationFailed() {

    }

    @Override
    public void showFinishSearchCorner() {

        //new
        if (mRefreshLayout.isRefreshing()){

            mPresenter.loadNewArticles();
        }//next
        else{
            mPresenter.loadNextArticles();
        }
    }

    @Override
    public void showSearchCornerFailed() {

    }

    @Override
    public void showNextPage() {

    }

    @Override
    public void showNewPage() {

    }

    @Override
    public void changeRecyclerViewLoadingFoot() {

    }

    @Override
    public void changeRecyclerViewNoMoreFoot() {

    }

    @Override
    public void bindCornerData(List<Object> list) {

    }
}
