package com.astatus.cornerandroid.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.astatus.cornerandroid.R;

/**
 * Created by AstaTus on 2016/3/18.
 */
public class CreateCornerDialog extends Dialog{

    private Button mSureBtn;
    private Button mCancelBtn;
    private EditText mNameEditText;
    private OnCreateCornerListener mListener;

    public interface OnCreateCornerListener{
        void onSure(String name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_create_corner);

        mNameEditText = (EditText) findViewById(R.id.create_corner_name_edit);
        mSureBtn = (Button) findViewById(R.id.dialog_create_corner_sure_btn);
        mSureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListener != null){
                    mListener.onSure(mNameEditText.getText().toString());
                }
            }
        });
        mCancelBtn = (Button) findViewById(R.id.dialog_create_corner_cancel_btn);
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();
            }
        });
    }

    public CreateCornerDialog(Context context, OnCreateCornerListener listener) {
        super(context);
        mListener = listener;
    }

    public CreateCornerDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CreateCornerDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
