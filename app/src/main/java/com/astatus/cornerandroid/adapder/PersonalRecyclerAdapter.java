package com.astatus.cornerandroid.adapder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.entity.ArticleEntity;
import com.astatus.cornerandroid.util.NumberUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

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
            try{
                //头像
                Transformation transformation = new RoundedTransformationBuilder()
                        .borderColor(Color.BLACK)
                        .borderWidthDp(3)
                        .cornerRadiusDp(30)
                        .oval(false)
                        .build();

                if (entity.mHeadUrl.length() == 0){
                    holder.mHeadImage.setBackgroundResource(R.drawable.ic_account_circle_black_48dp);
                }else{
                    Picasso.with(mContext)
                            .load(entity.mHeadUrl)
                            .fit()
                            .transform(transformation)
                            .into(holder.mHeadImage);
                }


                holder.mArtistName.setText(entity.mUserName);
                holder.mTime.setText(entity.mTime.toString());

                Picasso.with(mContext)
                        .load(entity.mImageUrl)
                        .into(holder.mArticleImage);

                holder.mFeelText.setText(entity.mFeelText);
                holder.mLocationName.setText(entity.mLocationName);
                holder.mLocationDistance.setText("3km");

                if (entity.mIsUp){
                    holder.mUpView.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);

                }else{
                    holder.mUpView.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                }

                holder.mUpCount.setText(NumberUtil.GetSimplifyString(entity.mUpCount));
                holder.mReadCount.setText(NumberUtil.GetSimplifyString(entity.mReadCount));
            }catch (Exception e){
                e.printStackTrace();
            }
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
        protected ImageView mArticleImage;
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

            mHeadImage = (RoundedImageView)v.findViewById(R.id.image_card_head_image);
            mArtistName = (TextView)v.findViewById(R.id.image_card_artist);
            mTime = (TextView)v.findViewById(R.id.image_card_time);
            mArticleImage = (ImageView)v.findViewById(R.id.image_card_image);

            mFeelText = (TextView)v.findViewById(R.id.image_card_text);
            mLocationName = (TextView)v.findViewById(R.id.image_card_corner_name);
            mLocationDistance = (TextView)v.findViewById(R.id.image_card_corner_distance);
            mUpView = (ImageView)v.findViewById(R.id.image_card_up_btn);
            mUpCount = (TextView)v.findViewById(R.id.image_card_up_text);
            mReadCount = (TextView)v.findViewById(R.id.image_card_read_text);

            ImageView btn = (ImageView) v.findViewById(R.id.image_card_up_btn);
            btn.setOnClickListener(sUpBtnListener);
            btn = (ImageView) v.findViewById(R.id.image_card_comment_btn);
            btn.setOnClickListener(sCommentBtnListener);

            btn = (ImageView) v.findViewById(R.id.image_card_more_btn);
            btn.setOnClickListener(sMoreBtnListener);
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
