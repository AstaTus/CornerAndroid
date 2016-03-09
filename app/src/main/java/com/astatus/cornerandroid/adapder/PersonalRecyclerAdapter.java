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
import com.astatus.cornerandroid.util.HttpUtil;
import com.astatus.cornerandroid.util.NumberUtil;
import com.astatus.cornerandroid.widget.HeadFootAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by AstaTus on 2016/2/23.
 */
public class PersonalRecyclerAdapter extends HeadFootRecyclerAdapter {

    private List<ArticleEntity> mData;
    private Context mContext;

    public PersonalRecyclerAdapter(Context context){
        super(false, true);
        mContext = context;
        setFootAdapter(new LoadMoreAdapter());
    }

    public void setHaveMore(boolean more){

        LoadMoreAdapter adapter = (LoadMoreAdapter)getFootAdapter();

        if (more !=  adapter.getHaveMore()){
            adapter.setHaveMore(more);
            notifyItemChanged(getItemCount() - 1);
        }
    }

    @Override
    public int getDataItemCount() {
        return mData.size();
    }

    @Override
    public void onBindDataViewHolder(RecyclerView.ViewHolder holder, int position) {
        PersonalViewHolder pvHolder = (PersonalViewHolder)holder;
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
                    pvHolder.mUpView.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);

                }else{
                    pvHolder.mUpView.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                }

                pvHolder.mUpCount.setText(NumberUtil.GetSimplifyString(entity.mUpCount));
                pvHolder.mReadCount.setText(NumberUtil.GetSimplifyString(entity.mReadCount));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_image_card, parent, false);
        return new PersonalRecyclerAdapter.PersonalViewHolder(v);
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

    public static class LoadMoreAdapter extends HeadFootAdapter{

        private boolean mHaveMore = true;
        public LoadMoreAdapter() {
            super(R.layout.widget_recyleview_foot);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(mHaveMore){

            }else{

            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(mResId, parent, false);
            return new FootViewHolder(v);
        }

        public void setHaveMore(boolean more){

            mHaveMore = more;
        }

        public boolean getHaveMore(){
            return mHaveMore;
        }
    }

    public static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View itemView) {
            super(itemView);
        }
    }
}
