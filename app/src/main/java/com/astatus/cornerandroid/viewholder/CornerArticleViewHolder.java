package com.astatus.cornerandroid.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.astatus.cornerandroid.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.math.BigInteger;

/**
 * Created by AstaTus on 2016/4/4.
 */
public class CornerArticleViewHolder extends RecyclerView.ViewHolder {

    protected TextView mCornerName;
    protected TextView mWantToBtn;
    protected ImageView mArticleImage;
    protected TextView mFeelText;
    protected TextView mLocationDistance;
    public TextView mBeenToNumberText;
    public TextView mWantToNumberText;
    public TextView mCommentText;

    public CornerWantClickListener mCornerWantClickListener;


    public interface CornerWantClickListener {
        public void onWantClick(BigInteger guid);
    }


    public CornerArticleViewHolder(View v, CornerWantClickListener listener) {
        super(v);

        mCornerWantClickListener = listener;
        mCornerName = (TextView)v.findViewById(R.id.corner_article_card_name_text);
        mWantToBtn = (Button)v.findViewById(R.id.corner_article_card_want_to_btn);
        mWantToBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigInteger cornerGuid = (BigInteger)v.getTag();
                mCornerWantClickListener.onWantClick(cornerGuid);
            }
        });
        mArticleImage = (ImageView)v.findViewById(R.id.corner_article_card_image);
        mFeelText = (TextView)v.findViewById(R.id.corner_article_card_feel_text);
        mLocationDistance = (TextView)v.findViewById(R.id.corner_article_card_distance_text);
        mBeenToNumberText = (TextView)v.findViewById(R.id.corner_article_card_been_to_number_text);
        mWantToNumberText = (TextView)v.findViewById(R.id.corner_article_card_want_to_number_text);
        mCommentText = (TextView)v.findViewById(R.id.corner_article_card_comment_text);

    }
}