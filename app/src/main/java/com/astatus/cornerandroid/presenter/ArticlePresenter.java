package com.astatus.cornerandroid.presenter;

import com.astatus.cornerandroid.adapder.PersonalRecyclerAdapter;
import com.astatus.cornerandroid.http.okhttp.ArticleCmd;
import com.astatus.cornerandroid.http.okhttp.CmdListener;
import com.astatus.cornerandroid.message.ArticleMsg;
import com.astatus.cornerandroid.message.MessagePacket;
import com.astatus.cornerandroid.model.ArticleModel;
import com.astatus.cornerandroid.view.IArticleView;

import java.util.Date;

/**
 * Created by AstaTus on 2016/2/28.
 */
public class ArticlePresenter {

    private ArticleModel mModel;
    private IArticleView mArticleView;
    private PersonalRecyclerAdapter mAdpater;

    public ArticlePresenter(IArticleView view, PersonalRecyclerAdapter adpater){
        mModel = new ArticleModel();
        mArticleView = view;
        mAdpater = adpater;

        mAdpater.RestData(mModel.getArticleList());
    }

    public void loadNextPage(){

        ArticleCmd cmd = ArticleCmd.create(
                new LoadNextPageArticleCmdListener(), ArticleCmd.REQUEST_TYPE_PERSONAL,
                ArticleCmd.REQUEST_DIRECTION_DOWN, mModel.getLastDate());
        cmd.excute();
    }

    public void loadNewerPage(){
        ArticleCmd cmd = ArticleCmd.create(
                new LoadNewerPageArticleCmdListener(), ArticleCmd.REQUEST_TYPE_PERSONAL,
                ArticleCmd.REQUEST_DIRECTION_DOWN, mModel.getFrontDate());
        cmd.excute();
    }

    class LoadNewerPageArticleCmdListener implements CmdListener<ArticleMsg> {
        @Override
        public void onSuccess(ArticleMsg result) {

            if (result.isTimeOut){
                mModel.resetData();
            }

            mModel.addArticles(result.articleList, ArticleModel.ADD_ARTICLE_LOCATION_FRONT);

            mArticleView.showNextPage();
        }

        @Override
        public void onFailed() {
            mArticleView.showLoadFailedToast();
        }
    }


    class LoadNextPageArticleCmdListener implements CmdListener<ArticleMsg> {

        @Override
        public void onSuccess(ArticleMsg result) {

            mArticleView.showNextPage();
        }

        @Override
        public void onFailed() {
            mArticleView.showLoadFailedToast();
        }
    }
}
