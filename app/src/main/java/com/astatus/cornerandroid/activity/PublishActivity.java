package com.astatus.cornerandroid.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.http.okhttp.CmdListener;
import com.astatus.cornerandroid.http.okhttp.PublishCmd;
import com.astatus.cornerandroid.message.PublishMsg;
import com.astatus.cornerandroid.util.ImageUtil;
import com.astatus.cornerandroid.util.PathUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;

public class PublishActivity extends AppCompatActivity {
    private ImageView mImageView;
    private TextView mLocationView;
    private BigInteger mCornerGuid;
    private EditText mEditText;

    private View mContainer;
    private Uri mUri;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        initView();
    }

    private void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.publish_toolbar);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);

        mContainer = findViewById(R.id.publish_root);
        mImageView = (ImageView)findViewById(R.id.publish_imageview);
        mLocationView = (TextView)findViewById(R.id.publish_location_text);
        mEditText = (EditText)findViewById(R.id.publish_edit_text);
        View locationRoot = findViewById(R.id.publish_location_root);
        locationRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublishActivity.this, LocationActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        try {
            Intent intent = getIntent();
            String uriStr = intent.getStringExtra("uri");
            mUri = Uri.parse(uriStr);
            Bitmap image = ImageUtil.decodeBitmapFromFile(mUri, ImageUtil.SHOW_ALL_IMAGE_SIZE);
            mImageView.setImageBitmap(image);
        }catch(Exception e){
             e.printStackTrace();
        }
    }

    private void showLoginProgress(){

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
    }

    private void hideLoginProgress(){
        mProgressDialog.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_send_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send_action_finish:

                if (mCornerGuid == null){

                    Snackbar.make(mContainer, getResources().getString(R.string.publish_location_is_null), Snackbar.LENGTH_LONG).show();
                    return false;
                }else{
                    try {

                        File image = new File(PathUtil.ConvertUriToPath(mUri, MediaStore.Images.Media.DATA));
                        PublishCmd cmd = PublishCmd.create(new PublishCmdListener(),
                                image, mEditText.getText().toString(),
                                mCornerGuid);

                        cmd.excute();
                        showLoginProgress();

                    } catch (FileNotFoundException e) {
                        //文件不存在
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    return true;
                }



        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        switch ( resultCode ) {
            case RESULT_OK :
                mLocationView.setText(data.getExtras().getString("name"));
                mCornerGuid = BigInteger.valueOf(Long.valueOf(data.getExtras().getString("guid"))) ;
                break;
            default :
                break;
        }
    }

    class PublishCmdListener implements CmdListener<PublishMsg>{

        @Override
        public void onSuccess(PublishMsg result) {

            hideLoginProgress();

            if (result.mResult){

                Intent homeIntent = new Intent(PublishActivity.this, HomeActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                homeIntent.putExtra(ActivityDef.IINTENT_PARAM1, ActivityDef.IP_SEND_SUCCESS);
                startActivity(homeIntent);
                Log.i("test", "send ok");
            }else{
                Log.i("test", "send failed");
            }
        }

        @Override
        public void onFailed() {
            hideLoginProgress();

        }
    }
}
