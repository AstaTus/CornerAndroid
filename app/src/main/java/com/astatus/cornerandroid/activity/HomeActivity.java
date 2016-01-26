package com.astatus.cornerandroid.activity;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
//import android.support.v7.recyclerview;
import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.widget.FloatingActionMenu;


/**
 * Created by AstaTus on 2016/1/14.
 */
public class HomeActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private FloatingActionButton mFab;
    private ObjectAnimator mExpandMenuAnimator;
    private ObjectAnimator mCollapsedMenuAnimator;
    private FloatingActionMenu mAddMenu;
//  private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_home);
        }catch (Exception e){
            e.printStackTrace();
        }

        initView();
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


        mExpandMenuAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.fab_clockwise_animator);
        mCollapsedMenuAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.fab_anticlockwise_animator);
        mFab = (FloatingActionButton)findViewById(R.id.home_fab);
        mExpandMenuAnimator.setTarget(mFab);
        mCollapsedMenuAnimator.setTarget(mFab);

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

        mAddMenu = (FloatingActionMenu)findViewById(R.id.home_menu);
        /*mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();*/
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
