package com.astatus.cornerandroid.activity;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.astatus.cornerandroid.R;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;

import org.joda.time.DateTime;

public class RegisterActivity extends AppCompatActivity
        implements CalendarDatePickerDialogFragment.OnDateSetListener{

    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mNicknameEditText;
    private TextView mBirthdayTextView;

    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }


    private void initView(){
        /*Toolbar commonToolbar = (Toolbar) findViewById(R.id.common_toolbar);
        setSupportActionBar(commonToolbar);*/

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        mEmailEditText = (EditText)findViewById(R.id.register_email_edittext);
        mPasswordEditText = (EditText)findViewById(R.id.register_password_edittext);
        mNicknameEditText = (EditText)findViewById(R.id.register_nickname_edittext);
        mBirthdayTextView = (TextView)findViewById(R.id.register_birthday_textview);
        mBirthdayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Fragment prev = getFragmentManager().findFragmentByTag(FRAG_TAG_DATE_PICKER);
                if (prev == null){
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




    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {

        mBirthdayTextView.setText(String.valueOf(year)
                + '/' + String.valueOf(monthOfYear)
                + '/' + String.valueOf(dayOfMonth));
    }
}
