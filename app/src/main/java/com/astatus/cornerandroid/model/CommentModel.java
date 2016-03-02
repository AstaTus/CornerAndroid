package com.astatus.cornerandroid.model;

import com.astatus.cornerandroid.entity.CommentEntity;

import java.util.List;

/**
 * Created by AstaTus on 2016/3/1.
 */
public class CommentModel {

    private List<CommentEntity> mComments;
    public List<CommentEntity> getArticleList(){
        return mComments;
    }

}
