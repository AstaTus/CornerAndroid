package com.astatus.cornerandroid.adapder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.activity.CommentActivity;
import com.astatus.cornerandroid.entity.UserArticleEntity;
import com.astatus.cornerandroid.util.HttpUtil;
import com.astatus.cornerandroid.util.NumberUtil;
import com.astatus.cornerandroid.viewholder.UserArticleViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by AstaTus on 2016/2/23.
 */
public class PersonalRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserArticleEntity> mData;
    private Context mContext;

    private UserArticleViewHolder.ArticleUpClickListener mUpClickListener;


    public PersonalRecyclerAdapter(Context context, UserArticleViewHolder.ArticleUpClickListener listener){
        super();
        mContext = context;
        mUpClickListener = listener;
    }

    public void restData(List<UserArticleEntity> data){
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_user_article_card, parent, false);
        return new UserArticleViewHolder(v, mUpClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserArticleViewHolder pvHolder = (UserArticleViewHolder)holder;
        UserArticleEntity entity = mData.get(position);
        if (entity != null){
            //根据entity  item中的控件可做到局部更新
            try{

                //头像
                Transformation transformation = new RoundedTransformationBuilder()
                        .borderColor(Color.BLACK)
                        .borderWidthDp(3)
                        .cornerRadiusDp(30)
                        .oval(false)
                        .build();

                if (entity.mHeadUrl.length() == 0){
                    pvHolder.mHeadImage.setBackgroundResource(R.drawable.ic_account_circle_black_48dp);
                }else{
                    Picasso.with(mContext)
                            .load(entity.mHeadUrl)
                            .fit()
                            .transform(transformation)
                            .into(pvHolder.mHeadImage);
                }


                pvHolder.mArtistName.setText(entity.mUserName);
                pvHolder.mTime.setText(entity.mTime.toString());

                Picasso.with(mContext)
                        .load(HttpUtil.getImageUrl(entity.mImagePath, HttpUtil.IMAGE_TYPE_PREVIEW))
                        .into(pvHolder.mArticleImage);

                pvHolder.mFeelText.setText(entity.mFeelText);
                pvHolder.mLocationName.setText(entity.mLocationName);
                pvHolder.mLocationDistance.setText("3km");

                if (entity.mIsUp){
                    pvHolder.mUpView.setBackgroundResource(R.drawable.ic_favorite_black_24dp);

                }else{
                    pvHolder.mUpView.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                }
                pvHolder.mUpView.setTag(entity.mGuid);
                pvHolder.mCommentView.setTag(entity.mGuid);
                pvHolder.mMoreView.setTag(entity.mGuid);
                pvHolder.mUpCount.setText(NumberUtil.GetSimplifyString(entity.mUpCount));
                pvHolder.mReadCount.setText(NumberUtil.GetSimplifyString(entity.mReadCount));


            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




    /*public class PersonalViewHolder extends RecyclerView.ViewHolder {

        protected RoundedImageView mHeadImage;
        protected TextView mArtistName;
        protected TextView mTime;
        protected ImageView mArticleImage;
        protected TextView mFeelText;
        protected TextView mLocationName;
        protected TextView mLocationDistance;
        protected ImageView mUpView;
        protected ImageView mCommentView;
        protected ImageView mMoreView;
        protected TextView mUpCount;
        protected TextView mReadCount;


        public PersonalViewHolder(View v) {
            super(v);


        }
    }*/
}
