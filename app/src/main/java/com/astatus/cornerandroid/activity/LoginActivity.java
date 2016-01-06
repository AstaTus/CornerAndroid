package com.astatus.cornerandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.http.LoginCmd;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailEdit;
    private EditText mPasswordEdit;
    private Button mLoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView(){
        mEmailEdit = (EditText)findViewById(R.id.login_email_textedit);
        mPasswordEdit = (EditText)findViewById(R.id.login_password_textedit);

        mLoginBtn = (Button)findViewById(R.id.login_enter_btn);
        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

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
    }

    class LoginResponseListener implements Response.Listener<JSONObject>{
        @Override
        public void onResponse(JSONObject response) {

        }
    }

    class LoginErrorListener implements Response.ErrorListener{
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }







}
