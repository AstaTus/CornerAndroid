package com.astatus.cornerandroid.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.astatus.cornerandroid.R;

/**
 * Created by AstaTus on 2016/3/14.
 */
public class CommentBottomMenu extends PopupWindow {
    private View mMenuView;

    private Button mDeleteBtn;
    private Button mCancelBtn;

    public CommentBottomMenu(Activity context, View.OnClickListener deleteListener) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.widget_comment_bottom_menu, null);
        mDeleteBtn = (Button) mMenuView.findViewById(R.id.comment_menu_delete);
        mDeleteBtn.setOnClickListener(deleteListener);

        //取消按钮
        mCancelBtn = (Button) mMenuView.findViewById(R.id.comment_menu_cancel);
        mCancelBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
/*        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);*/
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.commentBottomMenuAnimStyle);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.comment_menu_root).getTop();
                int y=(int) event.getY();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if( y < height ){
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
}
