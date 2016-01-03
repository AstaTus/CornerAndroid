package com.astatus.cornerandroid.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.astatus.cornerandroid.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*Toolbar commonToolbar = (Toolbar) findViewById(R.id.common_toolbar);
        setSupportActionBar(commonToolbar);*/

        // Get a support ActionBar corresponding to this toolbar

        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Register");
    }
}
