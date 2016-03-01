package com.astatus.cornerandroid.cornerandroid.widget;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

import com.astatus.cornerandroid.R;

/**
 * Created by AstaTus on 2016/2/2.
 */
public class RotationFloatActionButton extends FloatingActionButton {

    private ObjectAnimator mExpandMenuAnimator;
    private ObjectAnimator mCollapsedMenuAnimator;
    private Drawable mSrcDrawable;

    private int mExpandMenuAnimatorId;
    private int mCollapsedMenuAnimatorId;

    public RotationFloatActionButton(Context context) {
        this(context, null);
    }


    public RotationFloatActionButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotationFloatActionButton(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.RotationFloatActionButton, 0, 0);

        mExpandMenuAnimatorId = attr.getResourceId(
                R.styleable.RotationFloatActionButton_fab_expand_anim,
                R.animator.fab_clockwise_animator);
        mCollapsedMenuAnimatorId = attr.getResourceId(
                R.styleable.RotationFloatActionButton_fab_collapsed_anim,
                R.animator.fab_anticlockwise_animator);
        mSrcDrawable = attr.getDrawable(R.styleable.RotationFloatActionButton_fab_src);
        attr.recycle();

        mExpandMenuAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(context, mExpandMenuAnimatorId);
        mCollapsedMenuAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(context, mCollapsedMenuAnimatorId);

        /*mExpandMenuAnimator.setTarget(mSrcDrawable);
        mCollapsedMenuAnimator.setTarget(mSrcDrawable);*/


    }

    public void Expand(){
        mExpandMenuAnimator.start();
    }

    public void Collapse(){
        mCollapsedMenuAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0x550055);
        canvas.drawRect(0, 0, 50, 50, p);
    }
}
