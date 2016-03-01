package com.astatus.cornerandroid.cornerandroid.activity;

import android.app.Fragment;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.http.volley.RegisterCmd;
import com.astatus.cornerandroid.message.RegisterMsg;
import com.astatus.cornerandroid.util.ToastUtil;
import com.astatus.cornerandroid.util.VerifyUtil;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

import org.joda.time.DateTime;

public class RegisterActivity extends AppCompatActivity
        implements CalendarDatePickerDialogFragment.OnDateSetListener{

    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    private EditText mEmailEdit;
    private EditText mPasswordEdit;
    private TextInputLayout mNicknameEditLayout;
    private TextView mBirthdayText;

    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }


    private void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar actionBar = getSupportActionBar();
        // Enable the Up button
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.sett
        mEmailEdit = (EditText)findViewById(R.id.register_email_edittext);
        mPasswordEdit = (EditText)findViewById(R.id.register_password_edittext);
        mNicknameEditLayout = (TextInputLayout)findViewById(R.id.register_nickname_layout);
        mBirthdayText = (TextView)findViewById(R.id.register_birthday_textview);
        mBirthdayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment prev = getFragmentManager().findFragmentByTag(FRAG_TAG_DATE_PICKER);
                if (prev == null) {
                    DateTime now = DateTime.now();
                    CalendarDatePickerDialogFragment calendarDatePickerDialogFragment =
                            CalendarDatePickerDialogFragment.newInstance(
                                    RegisterActivity.this, now.getYear(), now.getMonthOfYear() - 1,
                                    now.getDayOfMonth());
                    calendarDatePickerDialogFragment.setDateRange(null, null);
                    //calendarDatePickerDialogFragment.setThemeCustom(R.style.MyCustomBetterPickersRadialTimePickerDialog);
                    calendarDatePickerDialogFragment.show(fm, FRAG_TAG_DATE_PICKER);
                }
            }
        });
        mRadioGroup = (RadioGroup)findViewById(R.id.register_sex_radiogroup);

        Button btn = (Button)findViewById(R.id.register_join_btn);
        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                String email = mEmailEdit.getText().toString();
                String password = mPasswordEdit.getText().toString();
                String nickname = mNicknameEditLayout.getEditText().getText().toString();
                String birthday = mBirthdayText.getText().toString();
                int sex = 0;
                if (mRadioGroup.getCheckedRadioButtonId() == R.id.register_sex_boy_radio){
                    sex = 1;
                }
                if(!VerifyUtil.VerifyEmail(email)){

                    ToastUtil.showText(RegisterActivity.this,
                            RegisterActivity.this.getString(R.string.register_email_error));

                    return;
                }

                if (!VerifyUtil.VerifyPassword(password)){
                    ToastUtil.showText(RegisterActivity.this,
                            RegisterActivity.this.getString(R.string.register_password_error));

                    return;
                }

                if (!VerifyUtil.VerifyNickname(password)){
                    ToastUtil.showText(RegisterActivity.this,
                            RegisterActivity.this.getString(R.string.register_nickname_error));

                    return;
                }

                if (!VerifyUtil.VerifyBirthday(birthday)){
                    ToastUtil.showText(RegisterActivity.this,
                            RegisterActivity.this.getString(R.string.register_email_error));

                    return;
                }

                register(email, password, nickname, birthday, sex);
            }
        });

    }

    private void register(String email, String password, String nickname, String birthday, int sex){

        RegisterCmd cmd = RegisterCmd.create(new RegisterResponseListener(), new RegisterErrorListener(),
                email, password, nickname, birthday, sex);
        cmd.excute();
    }

    class RegisterResponseListener implements Response.Listener<RegisterMsg>{
        @Override
        public void onResponse(RegisterMsg msg) {

            if (msg.mResult){
                Log.i("test", "register ok");
            }else{
                Log.i("test", "register failed");
            }
        }
    }

    class RegisterErrorListener implements Response.ErrorListener{
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {

        mBirthdayText.setText(String.valueOf(year)
                + '/' + String.valueOf(monthOfYear + 1)
                + '/' + String.valueOf(dayOfMonth));
    }
}
