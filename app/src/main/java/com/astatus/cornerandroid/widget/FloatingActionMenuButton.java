package com.astatus.cornerandroid.widget;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.astatus.cornerandroid.R;

/**
 * Created by AstaTus on 2016/1/19.
 */
public class FloatingActionMenuButton extends FloatingActionButton {

    /*private TextView mTextView;
    private Button mBtn;*/
    private String mTitle;

    public FloatingActionMenuButton(Context context) {
        this(context, null);
    }

    public FloatingActionMenuButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingActionMenuButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray attr = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionMenuButton, 0, 0);
        mTitle = attr.getString(R.styleable.FloatingActionMenuButton_fab_label);

        attr.recycle();
    }

    public void setTitle(String title) {
        mTitle = title;
        TextView label = getLabelView();
        if (label != null) {
            label.setText(title);
        }
    }

    TextView getLabelView() {
        return (TextView) getTag(R.id.fab_label);
    }

    public String getTitle() {
        return mTitle;
    }
}
