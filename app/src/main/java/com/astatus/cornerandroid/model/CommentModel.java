package com.astatus.cornerandroid.model;

import com.astatus.cornerandroid.entity.ArticleEntity;
import com.astatus.cornerandroid.entity.CommentEntity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AstaTus on 2016/3/1.
 */
public class CommentModel {

    public static final int ADD_COMMENT_LOCATION_FRONT = 1;
    public static final int ADD_COMMENT_LOCATION_BACK = 2;

    private List<CommentEntity> mComments;

    private BigInteger mArticleGuid = BigInteger.valueOf(0);;

    public CommentModel(){
        mComments = new ArrayList<CommentEntity>();
    }

    public List<CommentEntity> getArticleList(){
        return mComments;
    }

    public void setArticleGuid(BigInteger articleGuid){
        mArticleGuid = articleGuid;
    }

    public BigInteger getArticleGuid(){

        return mArticleGuid;
    }

    public void addComment(CommentEntity entity, int position){

    }

    public BigInteger getFrontGuid(){

        if (mComments.size() > 0){
            CommentEntity entity = mComments.get(0);

            return entity.mGuid;
        }

        return BigInteger.valueOf(0);
    }

    public BigInteger getLastGuid(){
        if (mComments.size() > 0){
            CommentEntity entity = mComments.get(mComments.size() - 1);

            return entity.mGuid;
        }

        return BigInteger.valueOf(0);
    }



    public void resetData(){
        mComments.clear();
    }
}
