package com.astatus.cornerandroid.adapder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astatus.cornerandroid.entity.CommentEntity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by AstaTus on 2016/3/1.
 */
public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.CommentViewHolder> {

    private List<CommentEntity> mData;
    private Context mContext;

    CommentRecyclerAdapter(Context context){
        mContext = context;
    }
    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(CommentRecyclerAdapter.CommentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void resetData(List<CommentEntity> data){
        mData = data;
        notifyDataSetChanged();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        protected RoundedImageView mHeadImage;
        protected TextView mNameText;
        protected TextView mReplyText;
        protected TextView mReplyNameText;
        protected TextView mDateText;
        protected TextView mCommentText;

        public CommentViewHolder(View v) {
            super(v);

            /*mHeadImage = (RoundedImageView)v.findViewById(R.id.image_card_head_image);
            mArtistName = (TextView)v.findViewById(R.id.image_card_artist);
            mTime = (TextView)v.findViewById(R.id.image_card_time);
            mPostImage = (ImageView)v.findViewById(R.id.image_card_image);

            mAttentionCount = (TextView)v.findViewById(R.id.image_card_head_image);
            mFanCount = (TextView)v.findViewById(R.id.image_card_head_image);

            mFeelText = (TextView)v.findViewById(R.id.image_card_text);
            mLocationName = (TextView)v.findViewById(R.id.image_card_corner_name);
            mLocationDistance = (TextView)v.findViewById(R.id.image_card_corner_distance);
            mUpView = (ImageView)v.findViewById(R.id.image_card_up_btn);
            mUpCount = (TextView)v.findViewById(R.id.image_card_up_text);
            mReadCount = (TextView)v.findViewById(R.id.image_card_head_image);

            Button btn = (Button) v.findViewById(R.id.image_card_up_btn);
            btn.setOnClickListener(sUpBtnListener);
            btn = (Button) v.findViewById(R.id.image_card_comment_btn);
            btn.setOnClickListener(sCommentBtnListener);

            btn = (Button) v.findViewById(R.id.image_card_more_btn);
            btn.setOnClickListener(sMoreBtnListener);*/
        }
    }
}
