package com.astatus.cornerandroid.view;

import com.astatus.cornerandroid.entity.CommentEntity;

import java.util.List;

/**
 * Created by AstaTus on 2016/3/1.
 */
public interface ICommentView {
    void showNextPage();

    void showNewPage();

    void loadNewPageFailed();

    void loadNextPageFailed();

    void notifyCommentAdd(int pos);

    void commentAddFailed();

    void notifyCommentRemove(int index);

    void commentRemoveFailed();

    void changeRecyclerViewFootStyle(boolean isLoadMoreStyle);


    void bindArticleListData(List<CommentEntity> list);

}
