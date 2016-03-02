package com.astatus.cornerandroid.model;

import com.astatus.cornerandroid.entity.ArticleEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by AstaTus on 2016/2/23.
 */
public class ArticleModel {

    public static final int ADD_ARTICLE_LOCATION_FRONT = 1;
    public static final int ADD_ARTICLE_LOCATION_BACK = 2;

    private List<ArticleEntity> mArticles;

    public ArticleModel(){
        mArticles = new ArrayList<ArticleEntity>();
    }

    public void resetData(){
        mArticles.clear();
    }

    public Date getFrontDate(){

        if (mArticles.size() > 0){
            ArticleEntity entity = mArticles.get(0);

            return entity.mTime;
        }

        return null;
    }

    public Date getLastDate(){
        if (mArticles.size() > 0){
            ArticleEntity entity = mArticles.get(mArticles.size() - 1);

            return entity.mTime;
        }

        return null;
    }

    public void addArticle(ArticleEntity entity, int location){
        if (location == ADD_ARTICLE_LOCATION_FRONT){
            mArticles.add(0, entity);
        }else{
            mArticles.add(entity);
        }
    }

    //article inc order
    public void addArticles(List<ArticleEntity> articles, int location) {

        if (location == ADD_ARTICLE_LOCATION_FRONT){
            mArticles.addAll(0, articles);
        }else{
            mArticles.addAll(articles);
        }
    }

    public List<ArticleEntity> getArticleList(){
        return mArticles;
    }
}
