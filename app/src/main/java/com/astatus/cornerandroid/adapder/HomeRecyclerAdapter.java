package com.astatus.cornerandroid.adapder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.entity.UserArticleEntity;
import com.astatus.cornerandroid.viewholder.CornerArticleViewHolder;
import com.astatus.cornerandroid.viewholder.UserArticleViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

/**
 * Created by AstaTus on 2016/4/3.
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static final int ARTICLE_TYPE_USER_TYPE = 10;
    public static final int ARTICLE_TYPE_CORNER_TYPE = 11;

    private ArrayList<Object> mArticles;
    CornerArticleViewHolder.CornerWantClickListener mCornerWantClickListener;
    UserArticleViewHolder.ArticleUpClickListener mArticleUpClickListener;

    public HomeRecyclerAdapter(CornerArticleViewHolder.CornerWantClickListener wantClickListener
            , UserArticleViewHolder.ArticleUpClickListener upClickListener){
        mCornerWantClickListener = wantClickListener;
        mArticleUpClickListener = upClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ARTICLE_TYPE_USER_TYPE){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_user_article_card, parent, false);
            return new UserArticleViewHolder(v, mArticleUpClickListener);
        }else{
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_user_article_card, parent, false);
            return new CornerArticleViewHolder(v, mCornerWantClickListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }



    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object article = mArticles.get(position);
        if (article instanceof UserArticleEntity)
            return ARTICLE_TYPE_USER_TYPE;
        else
            return ARTICLE_TYPE_CORNER_TYPE;
    }
}
