package com.astatus.cornerandroid.adapder;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.entity.CommentEntity;
import com.astatus.cornerandroid.widget.HeadFootAdapter;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by AstaTus on 2016/3/1.
 */
public class CommentRecyclerAdapter extends HeadFootRecyclerAdapter {

    private List<CommentEntity> mData;
    private Context mContext;
    private CommentItemClickListener mCommentItemClickListener;

    public interface CommentItemClickListener {
        public void onItemClick(View view,int postion);
    }

    public CommentRecyclerAdapter(Context context){
        super(false, true);
        mContext = context;
        setFootAdapter(new LoadMoreAdapter(R.layout.widget_recylerview_loading_foot));

    }



    public void setCommentItenClickListener(CommentItemClickListener listener){
        mCommentItemClickListener = listener;
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

            cvHolder.mRoot.setTag(entity);

            Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.BLACK)
                    .borderWidthDp(3)
                    .cornerRadiusDp(30)
                    .oval(false)
                    .build();

            if (entity.mReplyHeadUrl != null && entity.mReplyHeadUrl.length() == 0){
                Picasso.with(mContext)
                        .load(entity.mReplyHeadUrl)
                        .fit()
                        .transform(transformation)
                        .into(cvHolder.mHeadImage);
            }else{
                cvHolder.mHeadImage.setBackgroundResource(R.drawable.ic_account_circle_black_48dp);
            }

            cvHolder.mReplyNameText.setText(entity.mReplyName);

            if (entity.mTargetName == null || entity.mTargetName.length() == 0){
                cvHolder.mTargetNameText.setVisibility(View.GONE);
                cvHolder.mReplyText.setVisibility(View.GONE);
            }else{
                cvHolder.mTargetNameText.setVisibility(View.VISIBLE);
                cvHolder.mReplyText.setVisibility(View.VISIBLE);

                cvHolder.mTargetNameText.setText(entity.mTargetName);
            }

            cvHolder.mDateText.setText(entity.mTime.toString());

            cvHolder.mCommentText.setText(entity.mFeelText);
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

        protected View mRoot;
        protected RoundedImageView mHeadImage;
        protected TextView mReplyNameText;
        protected TextView mTargetNameText;
        protected TextView mReplyText;

        protected TextView mDateText;
        protected TextView mCommentText;

        public CommentViewHolder(View v) {
            super(v);

            mRoot = v.findViewById(R.id.comment_item_root);
            mHeadImage = (RoundedImageView)v.findViewById(R.id.comment_item_head_image);
            mReplyNameText = (TextView)v.findViewById(R.id.comment_item_reply_name_text);
            mTargetNameText = (TextView)v.findViewById(R.id.comment_item_target_name_text);
            mReplyText = (TextView)v.findViewById(R.id.comment_item_reply_text);

            mDateText = (TextView)v.findViewById(R.id.comment_item_date_text);
            mCommentText = (TextView)v.findViewById(R.id.comment_item_comment_text);

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
