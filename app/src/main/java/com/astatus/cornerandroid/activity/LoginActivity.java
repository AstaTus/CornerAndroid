package com.astatus.cornerandroid.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.application.CornerApplication;
import com.astatus.cornerandroid.http.LoginCmd;
import com.astatus.cornerandroid.message.LoginMsg;
import com.astatus.cornerandroid.model.SharedPreferenceDef;
import com.astatus.cornerandroid.util.ToastUtil;
import com.astatus.cornerandroid.util.VerifyUtil;

import org.json.JSONObject;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailEdit;
    private EditText mPasswordEdit;
    private Button mLoginBtn;

    private ProgressDialog mProgressDialog;

    private String mEmail;
    private String mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        archiveLoginInfoFromSharedPreferences();
        tryAutoLogin();
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

    private void initView(){
        mEmailEdit = (EditText)findViewById(R.id.login_email_textedit);
        mPasswordEdit = (EditText)findViewById(R.id.login_password_textedit);

        mLoginBtn = (Button)findViewById(R.id.login_enter_btn);
        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String email = mEmailEdit.getText().toString();
                String password = mPasswordEdit.getText().toString();
                if(!VerifyUtil.VerifyEmail(email)){

                    ToastUtil.showText(LoginActivity.this,
                            LoginActivity.this.getString(R.string.register_email_error));

                    return;
                }

                if (!VerifyUtil.VerifyPassword(password)){
                    ToastUtil.showText(LoginActivity.this,
                            LoginActivity.this.getString(R.string.register_email_error));

                    return;
                }

                login(email, password);
            }
        });

        Button btn = (Button)findViewById(R.id.login_register_btn);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().hide();
    }


    private void login(String email, String password){
        LoginCmd cmd = LoginCmd.create(
                new LoginResponseListener(), new LoginErrorListener(), email, password);
        cmd.excute();
        showLoginProgress();
    }

    private void saveLoginInfoIntoSharedPreferences(){
        SharedPreferences ps = CornerApplication.getSingleton().getSharedPreferences();
        SharedPreferences.Editor editor = ps.edit();
        editor.putString(SharedPreferenceDef.SHARED_USER_EMAIL, mEmail);
        editor.putString(SharedPreferenceDef.SHARED_USER_PASSWORD, mPassword);
        editor.commit();
    }

    private void tryAutoLogin(){
        if (mEmail != "" && mPassword != ""){
            login(mEmail, mPassword);
        }
    }

    private void archiveLoginInfoFromSharedPreferences(){
        SharedPreferences ps = CornerApplication.getSingleton().getSharedPreferences();
        mEmail = ps.getString(SharedPreferenceDef.SHARED_USER_EMAIL, "");
        mPassword = ps.getString(SharedPreferenceDef.SHARED_USER_PASSWORD, "");

        mEmailEdit.setText(mEmail);
        mPasswordEdit.setText(mPassword);
    }

    class LoginResponseListener implements Response.Listener<LoginMsg>{
        @Override
        public void onResponse(LoginMsg msg) {
            if (msg.result){

                if (msg.code == 0){
                    Log.i("test", "login ok");
                    saveLoginInfoIntoSharedPreferences();
                }else{
                    Log.i("test", "login failed");
                }
            }

            hideLoginProgress();

        }
    }



    class LoginErrorListener implements Response.ErrorListener{
        @Override
        public void onErrorResponse(VolleyError error) {

            hideLoginProgress();
        }
    }
}
