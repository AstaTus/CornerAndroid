package com.astatus.cornerandroid.adapder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.entity.ArticleEntity;
import com.astatus.cornerandroid.entity.CommentEntity;
import com.astatus.cornerandroid.widget.HeadFootAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by AstaTus on 2016/3/1.
 */
public class CommentRecyclerAdapter extends HeadFootRecyclerAdapter {

    private List<CommentEntity> mData;
    private Context mContext;

    public CommentRecyclerAdapter(Context context){
        super(false, true);
        setFootAdapter(new LoadMoreAdapter(R.layout.widget_recyleview_foot));
        mContext = context;
    }


    @Override
    public int getDataItemCount() {
        return mData.size();
    }

    @Override
    public void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommentViewHolder cvHolder = (CommentViewHolder)holder;
        CommentEntity entity = mData.get(position);
        if (entity != null){


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_comment_item, parent, false);
        return new CommentViewHolder(v);
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
            mArticleImage = (ImageView)v.findViewById(R.id.image_card_image);

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

    private static class LoadMoreAdapter extends HeadFootAdapter{

        public LoadMoreAdapter(@LayoutRes int id) {
            super(id);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        }

        @Override
        public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(mResId, parent, false);
            return new FootViewHolder(v);
        }
    }

    public static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }
}
