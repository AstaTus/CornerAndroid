package com.astatus.cornerandroid.viewholder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.activity.CommentActivity;
import com.astatus.cornerandroid.adapder.PersonalRecyclerAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.math.BigInteger;

/**
 * Created by AstaTus on 2016/4/4.
 */
public class UserArticleViewHolder extends RecyclerView.ViewHolder {

    public RoundedImageView  mHeadImage;
    public TextView mArtistName;
    public TextView mTime;
    public ImageView mArticleImage;
    public TextView mFeelText;
    public TextView mLocationName;
    public TextView mLocationDistance;
    public ImageView mUpView;
    public ImageView mCommentView;
    public ImageView mMoreView;
    public TextView mUpCount;
    public TextView mReadCount;

    public Button mAttentionBtn;
    private ArticleUpClickListener mUpClickListener;

    private static CommentBtnClickListener mCommentBtnListener;
    private static MoreBtnClickListener mMoreBtnListener;
    private static UpBtnClickListener mUpBtnListener;


    public interface ArticleUpClickListener {
        public void onUpClick(BigInteger guid);
    }

    public UserArticleViewHolder(View v, ArticleUpClickListener listener) {
        super(v);

        mUpClickListener = listener;

        mHeadImage = (RoundedImageView)v.findViewById(R.id.widget_user_article_card_head_image);
        mArtistName = (TextView)v.findViewById(R.id.widget_user_article_card_artist);
        mTime = (TextView)v.findViewById(R.id.widget_user_article_card_time);
        mArticleImage = (ImageView)v.findViewById(R.id.widget_user_article_card_image);

        mFeelText = (TextView)v.findViewById(R.id.widget_user_article_card_text);
        mLocationName = (TextView)v.findViewById(R.id.widget_user_article_card_corner_name);
        mLocationDistance = (TextView)v.findViewById(R.id.widget_user_article_card_corner_distance);

        mUpCount = (TextView)v.findViewById(R.id.widget_user_article_card_up_text);
        mReadCount = (TextView)v.findViewById(R.id.widget_user_article_card_read_text);

        mUpView = (ImageView) v.findViewById(R.id.widget_user_article_card_up_btn);
        mUpView.setOnClickListener(mUpBtnListener);

        mCommentView = (ImageView) v.findViewById(R.id.widget_user_article_card_comment_btn);
        mCommentView.setOnClickListener(mCommentBtnListener);

        mMoreView = (ImageView) v.findViewById(R.id.widget_user_article_card_more_btn);
        mMoreView.setOnClickListener(mMoreBtnListener);
    }

    class CommentBtnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            BigInteger guid = (BigInteger)v.getTag();
            Intent commentIntent = new Intent(v.getContext(), CommentActivity.class);
            commentIntent.putExtra("ARTICLE_GUID", guid.toString());
            v.getContext().startActivity(commentIntent);
        }
    }

    class MoreBtnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

        }
    }

    class UpBtnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            BigInteger articlGuid = (BigInteger)v.getTag();
            if (articlGuid != null && mUpClickListener != null){
                mUpClickListener.onUpClick(articlGuid);
            }

        }
    }
}
