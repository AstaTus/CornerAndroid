package com.astatus.cornerandroid.model;

import com.astatus.cornerandroid.entity.UserArticleEntity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AstaTus on 2016/2/23.
 */
public class ArticleModel {

    public static final int ADD_ARTICLE_LOCATION_FRONT = 1;
    public static final int ADD_ARTICLE_LOCATION_BACK = 2;

    private List<UserArticleEntity> mArticles;

    public ArticleModel(){
        mArticles = new ArrayList<UserArticleEntity>();
    }

    public void resetData(){
        mArticles.clear();
    }

    public BigInteger getFrontGuid(){

        if (mArticles.size() > 0){
            UserArticleEntity entity = mArticles.get(0);

            return entity.mGuid;
        }

        return BigInteger.valueOf(0);
    }

    public BigInteger getLastGuid(){
        if (mArticles.size() > 0){
            UserArticleEntity entity = mArticles.get(mArticles.size() - 1);

            return entity.mGuid;
        }

        return BigInteger.valueOf(0);
    }

    public int changeUpState(BigInteger articleGuid, boolean isUp){

        UserArticleEntity entity;
        for (int i = 0; i < mArticles.size(); ++i){
            entity = mArticles.get(i);
            if (entity.mGuid.compareTo(articleGuid) == 0){
                entity.mIsUp = isUp;
                entity.mNeedUpdateUp = true;

                return i;

            }
        }

        return -1;
    }

    public void addArticle(UserArticleEntity entity, int location){
        if (location == ADD_ARTICLE_LOCATION_FRONT){
            mArticles.add(0, entity);
        }else{
            mArticles.add(entity);
        }
    }

    public List<UserArticleEntity> getArticleList(){
        return mArticles;
    }
}
