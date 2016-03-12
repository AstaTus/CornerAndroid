package com.astatus.cornerandroid.view;

/**
 * Created by AstaTus on 2016/3/1.
 */
public interface ICommentView {
    void showNextPage();

    void showNewPage();

    void loadNewPageFailed();

    void loadNextPageFailed();

    void notifyCommentAdd();

    void notifyCommentRemove(int index);

    void changeRecyclerViewFootStyle(int style);



}
