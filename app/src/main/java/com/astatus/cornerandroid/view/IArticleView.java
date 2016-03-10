package com.astatus.cornerandroid.view;

import com.astatus.cornerandroid.entity.ArticleEntity;

import java.util.List;

/**
 * Created by AstaTus on 2016/2/28.
 */
public interface IArticleView {

    void showNextPage();

    void showNewPage();

    void showLoadFailedToast();

    void setNoMoreArticle();
}
