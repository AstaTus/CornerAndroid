package com.astatus.cornerandroid.cornerandroid.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.util.NumberUtil;
import com.astatus.cornerandroid.widget.*;
import com.astatus.cornerandroid.widget.FloatingActionMenuButton;

import java.util.List;

/**
 * Created by AstaTus on 2016/1/19.
 */
@CoordinatorLayout.DefaultBehavior(FloatingActionMenuLayout.Behavior.class)
public class FloatingActionMenuLayout extends ViewGroup {
    public static final int EXPAND_TOP_LEFT = 0;
    public static final int EXPAND_TOP_RIGHT = 1;
    public static final int EXPAND_BOTTOM_LEFT = 2;
    public static final int EXPAND_BOTTOM_RIGHT = 3;

    private static final int ANIMATION_DURATION = 300;
    private static final float COLLAPSED_PLUS_ROTATION = 0f;
    private static final float EXPANDED_PLUS_ROTATION = 90f + 45f;

    private int mExpandLocation;

    private int mFabCenterX = 0;
    private int mFabTopY = 0;
    private int mFabBottomY = 0;
    private View mFillViewContent;

    private int mButtonSpacing;
    private int mLabelsMargin;
    private int mLabelsVerticalOffset;
    private int mLabelTextStyle;
    private int mLabelbackgroundDrawable;
    private int mLabelsPosition;
    private int mButtonsCount;

    private int mMaxButtonWidth = 0;
    private int mMaxButtonHeight = 0;

    private com.astatus.cornerandroid.widget.TouchDelegateGroup mTouchDelegateGroup;
    private boolean mExpanded = false;
    private FloatingActionButton mFab;

    private OnFloatingActionsMenuChangeListener mListener;
    private AnimatorSet mExpandAnimation = new AnimatorSet().setDuration(ANIMATION_DURATION);
    private AnimatorSet mCollapseAnimation = new AnimatorSet().setDuration(ANIMATION_DURATION);

    public interface OnFloatingActionsMenuChangeListener {
        void onMenuExpanded();
        void onMenuCollapsed();
    }

    public FloatingActionMenuLayout(Context context) {
        this(context, null);
    }

    public FloatingActionMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingActionMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }



    public void attachMenuButton(FloatingActionButton fab){
        mFab = fab;
    }

    public void setOriginLocation(int centerX, int topY, int bottomY){

        if ((mFabCenterX != centerX) || (mFabTopY != topY) || (mFabBottomY != bottomY)){
            mFabCenterX = centerX;
            mFabTopY = topY;
            mFabBottomY = bottomY;

            requestLayout();
        }
    }

    private void init(Context context, AttributeSet attributeSet) {
        mButtonSpacing = getResources().getDimensionPixelOffset(R.dimen.fab_actions_spacing);
        mLabelsMargin = getResources().getDimensionPixelSize(R.dimen.fab_labels_margin);
        mLabelsVerticalOffset = getResources().getDimensionPixelSize(R.dimen.fab_shadow_offset);

        mTouchDelegateGroup = new com.astatus.cornerandroid.widget.TouchDelegateGroup(this);
        setTouchDelegate(mTouchDelegateGroup);

        TypedArray attr = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionMenuLayout, 0, 0);

        mExpandLocation = attr.getInt(R.styleable.FloatingActionMenuLayout_fab_expandDLocation, EXPAND_BOTTOM_RIGHT);
        mLabelTextStyle = attr.getResourceId(R.styleable.FloatingActionMenuLayout_fab_labelTextStyle, 0);
        mLabelbackgroundDrawable = attr.getResourceId(R.styleable.FloatingActionMenuLayout_fab_backgroundDrawable, 0);


        attr.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        /*if (mMenuButtonId != 0){
            mMenuButton = findViewById(mMenuButtonId);

            if (mMenuButton == null){
                throw new NullPointerException("FloatingActionMenu:MenuButton is not set");
            }
        }*/
    }

    public void setOnFloatingActionsMenuChangeListener(OnFloatingActionsMenuChangeListener listener) {
        mListener = listener;
    }


    public void addButton(com.astatus.cornerandroid.widget.FloatingActionMenuButton button) {
        addView(button, mButtonsCount - 1);
        mButtonsCount++;

        if (mLabelTextStyle != 0) {
            createLabels();
        }
    }

    public void removeButton(com.astatus.cornerandroid.widget.FloatingActionMenuButton button) {
        removeView(button.getLabelView());
        removeView(button);
        button.setTag(R.id.fab_label, null);
        mButtonsCount--;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = widthSize;
        int height = heightSize;

        for (int i = 0; i < mButtonsCount; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == GONE) {
                continue;
            }

            mMaxButtonWidth = Math.max(mMaxButtonWidth, child.getMeasuredWidth());
            mMaxButtonHeight = Math.max(mMaxButtonHeight, child.getMeasuredHeight());
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int buttonsHorizontalCenter = 0;
        int labelOffset = 0;
        if (changed) {
            mTouchDelegateGroup.clearTouchDelegates();
        }

        switch (mExpandLocation) {
            case EXPAND_BOTTOM_LEFT:
            case EXPAND_TOP_LEFT:
                buttonsHorizontalCenter = mFabCenterX;
                labelOffset = buttonsHorizontalCenter + mMaxButtonWidth / 2 + mLabelsMargin;
                break;
            case EXPAND_BOTTOM_RIGHT:
            case EXPAND_TOP_RIGHT:
                buttonsHorizontalCenter = mFabCenterX;
                labelOffset = buttonsHorizontalCenter - mMaxButtonWidth / 2 - mLabelsMargin;
                break;
            default:
                //throw new NoSuchFieldException("FloatingActionMenu:onLayout ExpandLocation error");
        }

        int nextY = 0;
        switch (mExpandLocation){
            case EXPAND_BOTTOM_LEFT:
            case EXPAND_BOTTOM_RIGHT:
                nextY = mFabTopY;
                break;
            case EXPAND_TOP_LEFT:
            case EXPAND_TOP_RIGHT:
                nextY = mFabBottomY;
        }

        for (int i = mButtonsCount - 1; i >= 0; i--) {
            final View child = getChildAt(i);

            if (child.getVisibility() == GONE)
                continue;

            int childX = buttonsHorizontalCenter - child.getMeasuredWidth() / 2;
            int childY = 0;
            switch (mExpandLocation){
                case EXPAND_BOTTOM_LEFT:
                case EXPAND_BOTTOM_RIGHT:
                    childY = nextY - child.getMeasuredHeight();
                    nextY = childY - mButtonSpacing;
                    break;
                case EXPAND_TOP_LEFT:
                case EXPAND_TOP_RIGHT:
                    childY = nextY;
                    nextY = childY + child.getMeasuredHeight() + mButtonSpacing;
            }

            child.layout(childX, childY, childX + child.getMeasuredWidth(), childY + child.getMeasuredHeight());

            float collapsedTranslation = 0f;
            float expandedTranslation = 0f;

            switch (mExpandLocation){
                case EXPAND_BOTTOM_LEFT:
                case EXPAND_BOTTOM_RIGHT:
                    collapsedTranslation = mFabBottomY - childY;
                    break;
                case EXPAND_TOP_LEFT:
                case EXPAND_TOP_RIGHT:
                    collapsedTranslation = mFabTopY - childY;
            }

            int labelLeft = 0;
            int labelRight = 0;

            child.setTranslationY(mExpanded ? expandedTranslation : collapsedTranslation);
            child.setAlpha(mExpanded ? 1f : 0f);

            LayoutParams params = (LayoutParams) child.getLayoutParams();
            params.mCollapseDir.setFloatValues(expandedTranslation, collapsedTranslation);
            params.mExpandDir.setFloatValues(collapsedTranslation, expandedTranslation);
            params.setAnimationsTarget(child);

            View label = (View) child.getTag(R.id.fab_label);
            if (label != null) {
                switch (mExpandLocation){
                    case EXPAND_BOTTOM_LEFT:
                    case EXPAND_TOP_LEFT:
                        labelLeft = labelOffset;
                        labelRight = labelOffset + label.getMeasuredWidth();
                        break;
                    case EXPAND_BOTTOM_RIGHT:
                    case EXPAND_TOP_RIGHT:
                        labelLeft = labelOffset - label.getMeasuredWidth();
                        labelRight = labelOffset;
                        break;
                }

                int labelTop = childY - mLabelsVerticalOffset + (child.getMeasuredHeight() - label.getMeasuredHeight()) / 2;
                label.layout(labelLeft, labelTop, labelRight, labelTop + label.getMeasuredHeight());

                Rect touchArea = new Rect(
                        Math.min(childX, labelLeft),
                        childY - mButtonSpacing / 2,
                        Math.max(childX + child.getMeasuredWidth(), labelRight),
                        childY + child.getMeasuredHeight() + mButtonSpacing / 2);
                mTouchDelegateGroup.addTouchDelegate(new TouchDelegate(touchArea, child));

                label.setTranslationY(mExpanded ? expandedTranslation : collapsedTranslation);
                label.setAlpha(mExpanded ? 1f : 0f);

                LayoutParams labelParams = (LayoutParams) label.getLayoutParams();
                labelParams.mCollapseDir.setFloatValues(expandedTranslation, collapsedTranslation);
                labelParams.mExpandDir.setFloatValues(collapsedTranslation, expandedTranslation);
                labelParams.setAnimationsTarget(label);
            }
        }
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(super.generateDefaultLayoutParams());
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(super.generateLayoutParams(attrs));
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(super.generateLayoutParams(p));
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return super.checkLayoutParams(p);
    }

    private static Interpolator sExpandInterpolator = new OvershootInterpolator();
    private static Interpolator sCollapseInterpolator = new DecelerateInterpolator(3f);
    private static Interpolator sAlphaExpandInterpolator = new DecelerateInterpolator();

    private class LayoutParams extends ViewGroup.LayoutParams {

        private ObjectAnimator mExpandDir = new ObjectAnimator();
        private ObjectAnimator mExpandAlpha = new ObjectAnimator();
        private ObjectAnimator mCollapseDir = new ObjectAnimator();
        private ObjectAnimator mCollapseAlpha = new ObjectAnimator();
        private boolean animationsSetToPlay;

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);

            mExpandDir.setInterpolator(sExpandInterpolator);
            mExpandAlpha.setInterpolator(sAlphaExpandInterpolator);
            mCollapseDir.setInterpolator(sCollapseInterpolator);
            mCollapseAlpha.setInterpolator(sCollapseInterpolator);

            mCollapseAlpha.setProperty(View.ALPHA);
            mCollapseAlpha.setFloatValues(1f, 0f);

            mExpandAlpha.setProperty(View.ALPHA);
            mExpandAlpha.setFloatValues(0f, 1f);

            mCollapseDir.setProperty(View.TRANSLATION_Y);
            mExpandDir.setProperty(View.TRANSLATION_Y);
        }

        public void setAnimationsTarget(View view) {
            mCollapseAlpha.setTarget(view);
            mCollapseDir.setTarget(view);
            mExpandAlpha.setTarget(view);
            mExpandDir.setTarget(view);

            // Now that the animations have targets, set them to be played
            if (!animationsSetToPlay) {
                addLayerTypeListener(mExpandDir, view);
                addLayerTypeListener(mCollapseDir, view);

                mCollapseAnimation.play(mCollapseAlpha);
                mCollapseAnimation.play(mCollapseDir);
                mExpandAnimation.play(mExpandAlpha);
                mExpandAnimation.play(mExpandDir);
                animationsSetToPlay = true;
            }
        }

        private void addLayerTypeListener(Animator animator, final View view) {
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setLayerType(LAYER_TYPE_NONE, null);
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    view.setLayerType(LAYER_TYPE_HARDWARE, null);
                }
            });
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

//        bringChildToFront(mAddButton);
        mButtonsCount = getChildCount();

        if (mLabelTextStyle != 0) {
            createLabels();
        }
    }

    private void createLabels() {

        for (int i = 0; i < mButtonsCount; i++) {
            com.astatus.cornerandroid.widget.FloatingActionMenuButton button = (FloatingActionMenuButton) getChildAt(i);
            String title = button.getTitle();

            if ( title == null ||
                    button.getTag(R.id.fab_label) != null) continue;

            TextView label = new TextView(getContext());
            if (Build.VERSION.SDK_INT < 23) {
                label.setTextAppearance(getContext(), mLabelTextStyle);
            } else {
                //label.setTextAppearance(mLabelTextStyle);
            }

            if(mLabelbackgroundDrawable != 0){
                label.setBackgroundResource(mLabelbackgroundDrawable);
            }

            label.setText(button.getTitle());
            addView(label);

            try {
                button.setTag(R.id.fab_label, label);
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public void collapse() {
        collapse(false);
    }

    public void collapseImmediately() {
        collapse(true);
    }

    private void collapse(boolean immediately) {
        if (mExpanded) {
            mExpanded = false;
            mTouchDelegateGroup.setEnabled(false);
            mCollapseAnimation.setDuration(immediately ? 0 : ANIMATION_DURATION);
            mCollapseAnimation.start();
            mExpandAnimation.cancel();

            if (mListener != null) {
                mListener.onMenuCollapsed();
            }
        }
    }

    public void toggle() {
        if (mExpanded) {
            collapse();
        } else {
            expand();
        }
    }

    public void expand() {
        if (!mExpanded) {
            mExpanded = true;
            mTouchDelegateGroup.setEnabled(true);
            mCollapseAnimation.cancel();
            mExpandAnimation.start();

            if (mListener != null) {
                mListener.onMenuExpanded();
            }
        }
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.mExpanded = mExpanded;

        return savedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            SavedState savedState = (SavedState) state;
            mExpanded = savedState.mExpanded;
            mTouchDelegateGroup.setEnabled(mExpanded);

            /*if (mRotatingDrawable != null) {
                mRotatingDrawable.setRotation(mExpanded ? EXPANDED_PLUS_ROTATION : COLLAPSED_PLUS_ROTATION);
            }*/

            super.onRestoreInstanceState(savedState.getSuperState());
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    public static class Behavior extends CoordinatorLayout.Behavior<FloatingActionMenuLayout> {
        // We only support the FAB <> Snackbar shift movement on Honeycomb and above. This is
        // because we can use view translation properties which greatly simplifies the code.
        private static final boolean FAB_MENU_BEHAVIOR_ENABLED = Build.VERSION.SDK_INT >= 11;


        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent,
                                       FloatingActionMenuLayout child, View dependency) {
            // We're dependent on all SnackbarLayouts (if enabled)
            boolean result = false;
            result = FAB_MENU_BEHAVIOR_ENABLED && (dependency instanceof FloatingActionButton);
            return result;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionMenuLayout child,
                                              View dependency) {
            if (dependency instanceof FloatingActionButton) {
                updateMenuLayout(child, (FloatingActionButton)dependency);
            }
            return false;
        }

        private boolean updateMenuLayout(FloatingActionMenuLayout child, FloatingActionButton dependency){

            Rect r = new Rect();
            dependency.getGlobalVisibleRect(r);

            child.setOriginLocation(
                    dependency.getLeft() + (dependency.getRight() - dependency.getLeft()) / 2,
                    dependency.getTop(), dependency.getBottom());
            return true;
        }

        @Override
        public boolean onLayoutChild(CoordinatorLayout parent, FloatingActionMenuLayout child, int layoutDirection) {

            final List<View> dependencies = parent.getDependencies(child);
            for (int i = 0, count = dependencies.size(); i < count; i++) {
                final View dependency = dependencies.get(i);
                if (dependency instanceof FloatingActionButton
                        && updateMenuLayout(child, (FloatingActionButton) dependency)) {
                    break;
                }
            }
            return false;
        }

        /*@Override
        public boolean onLayoutChild(CoordinatorLayout parent, FloatingActionButton child,
                                     int layoutDirection) {
            // First, lets make sure that the visibility of the FAB is consistent
            final List<View> dependencies = parent.getDependencies(child);

            // Now let the CoordinatorLayout lay out the FAB
            parent.onLayoutChild(child, layoutDirection);
            // Now offset it if needed

            return true;
        }*/
    }

    public static class SavedState extends BaseSavedState {
        public boolean mExpanded;

        public SavedState(Parcelable parcel) {
            super(parcel);
        }

        private SavedState(Parcel in) {
            super(in);
            mExpanded = in.readInt() == 1;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(mExpanded ? 1 : 0);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {

            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };


    }
}
