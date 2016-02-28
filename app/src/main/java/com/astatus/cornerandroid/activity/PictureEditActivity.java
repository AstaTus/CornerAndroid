package com.astatus.cornerandroid.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.util.ImageUtil;

/**
 * Created by AstaTus on 2016/2/4.
 */
public class PictureEditActivity extends AppCompatActivity {

    private ImageView mImageView;

    private String mUriStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_picture_edit);
        } catch (Exception e) {
            e.printStackTrace();
        }

        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.picture_edit_toolbar);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);

        mImageView = (ImageView) findViewById(R.id.picture_edit_imageview);

        try {
            Intent intent = getIntent();
            mUriStr = intent.getStringExtra("uri");
            Uri uri = Uri.parse(mUriStr);

            Bitmap image = ImageUtil.decodeBitmapFromFile(uri, ImageUtil.SHOW_SYSTEM_MAX_IMAGE_SIZE);
            mImageView.setImageBitmap(image);
                    /*((BitmapDrawable) mImageView.getDrawable()).getBitmap().recycle();
            mImageView.setImageURI(uri);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // do something on back.
            showCancelDialog();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void showCancelDialog() {
        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.picture_edit_cancel_tip))
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                finish();
                            }
                        })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                    }
                })
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_picture_edit_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.picture_edit_action_next:
                try {
                    Intent publishIntent = new Intent(this, PublishActivity.class);
                    publishIntent.putExtra("uri", mUriStr.toString());
                    startActivity(publishIntent);
                }catch (Exception e){
                    e.printStackTrace();
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
