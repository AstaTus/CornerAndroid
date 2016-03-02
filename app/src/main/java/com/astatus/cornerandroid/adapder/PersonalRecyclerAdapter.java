package com.astatus.cornerandroid.adapder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.entity.ArticleEntity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by AstaTus on 2016/2/23.
 */
public class PersonalRecyclerAdapter extends RecyclerView.Adapter<PersonalRecyclerAdapter.PersonalViewHolder> {

    private List<ArticleEntity> mData;
    private Context mContext;
    public PersonalRecyclerAdapter(Context context){
        mContext = context;
    }

    @Override
    public PersonalRecyclerAdapter.PersonalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_image_card, parent, false);
        return new PersonalRecyclerAdapter.PersonalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PersonalRecyclerAdapter.PersonalViewHolder holder, int position) {
        ArticleEntity entity = mData.get(position);
        if (entity != null){
            //头像
            /*Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.BLACK)
                    .borderWidthDp(3)
                    .cornerRadiusDp(30)
                    .oval(false)
                    .build();

            Picasso.with(mContext)
                    .load(entity.mHeadUrl)
                    .fit()
                    .transform(transformation)
                    .into(holder.mHeadImage);

            holder.mArtistName.setText(entity.mUserName);
            holder.mTime.setText(entity.mTime.toString());

            Picasso.with(mContext)
                    .load(entity.mImageUrl)
                    .fit()
                    .transform(transformation)
                    .into(holder.mPostImage);

            holder.mFeelText.setText(entity.mFeelText);
            holder.mLocationName.setText(entity.mLocationName);
            holder.mLocationDistance.setText("3km");

            if (entity.mIsUp){
                holder.mUpView.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);

            }else{
                holder.mUpView.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
            }

            holder.mUpCount.setText(NumberUtil.GetSimplifyString(entity.mUpCount));
            holder.mReadCount.setText(NumberUtil.GetSimplifyString(entity.mReadCount));*/
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void restData(List<ArticleEntity> data){
        mData = data;
        notifyDataSetChanged();
    }


    public static class PersonalViewHolder extends RecyclerView.ViewHolder {

        protected RoundedImageView mHeadImage;
        protected TextView mArtistName;
        protected TextView mTime;
        protected ImageView mPostImage;
        protected TextView mAttentionCount;
        protected TextView mFanCount;
        protected TextView mFeelText;
        protected TextView mLocationName;
        protected TextView mLocationDistance;
        protected ImageView mUpView;
        protected TextView mUpCount;
        protected TextView mReadCount;

        private static CommentBtnClickListener sCommentBtnListener = new CommentBtnClickListener();
        private static MoreBtnClickListener sMoreBtnListener = new MoreBtnClickListener();
        private static UpBtnClickListener sUpBtnListener = new UpBtnClickListener();

        public PersonalViewHolder(View v) {
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


        static class CommentBtnClickListener implements View.OnClickListener{
            @Override
            public void onClick(View v) {

            }
        }

        static class MoreBtnClickListener implements View.OnClickListener{
            @Override
            public void onClick(View v) {

            }
        }

        static class UpBtnClickListener implements View.OnClickListener{
            @Override
            public void onClick(View v) {

            }
        }
    }
}
