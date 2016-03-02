package com.astatus.cornerandroid.presenter;

import com.astatus.cornerandroid.adapder.PersonalRecyclerAdapter;
import com.astatus.cornerandroid.entity.ArticleEntity;
import com.astatus.cornerandroid.http.okhttp.ArticleCmd;
import com.astatus.cornerandroid.http.okhttp.CmdListener;
import com.astatus.cornerandroid.message.ArticleMsg;
import com.astatus.cornerandroid.model.ArticleModel;
import com.astatus.cornerandroid.view.IArticleView;

import java.math.BigInteger;

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

        mAdpater.restData(mModel.getArticleList());
    }

    public void loadNextPage(){

        ArticleCmd cmd = ArticleCmd.create(
                new LoadNextPageArticleCmdListener(),
                BigInteger.valueOf(0), ArticleCmd.REQUEST_TYPE_PERSONAL,
                ArticleCmd.REQUEST_DIRECTION_DOWN, mModel.getLastDate());
        cmd.excute();
    }

    public void loadNewerPage(){
        ArticleCmd cmd = ArticleCmd.create(
                new LoadNewerPageArticleCmdListener(),
                BigInteger.valueOf(0), ArticleCmd.REQUEST_TYPE_PERSONAL,
                ArticleCmd.REQUEST_DIRECTION_DOWN, mModel.getFrontDate());
        cmd.excute();
    }

    class LoadNewerPageArticleCmdListener implements CmdListener<ArticleMsg> {
        @Override
        public void onSuccess(ArticleMsg result) {

            if (result.mIsTimeOut){
                mModel.resetData();
            }

            ArticleEntity entity;
            for (int i = result.mGuids.size() - 1; i >= 0; --i){

                entity = builderArticleEntity(result, i);
                mModel.addArticle(entity, ArticleModel.ADD_ARTICLE_LOCATION_FRONT);
            }

            mAdpater.restData(mModel.getArticleList());

            mArticleView.showNewerPage();
        }

        @Override
        public void onFailed() {
            mArticleView.showLoadFailedToast();
        }
    }


    class LoadNextPageArticleCmdListener implements CmdListener<ArticleMsg> {

        @Override
        public void onSuccess(ArticleMsg result) {

            ArticleEntity entity;
            for (int i = 0; i < result.mGuids.size(); ++i){
                entity = builderArticleEntity(result, i);
                mModel.addArticle(entity, ArticleModel.ADD_ARTICLE_LOCATION_BACK);
            }

            mAdpater.restData(mModel.getArticleList());
            mArticleView.showNextPage();
        }

        @Override
        public void onFailed() {
            mArticleView.showLoadFailedToast();
        }
    }

    public ArticleEntity builderArticleEntity(ArticleMsg result, int index){
        ArticleEntity entity = new ArticleEntity();
        entity.mGuid = result.mGuids.get(index);
        entity.mTime = result.mTimes.get(index);
        entity.mFeelText = result.mFeelTexts.get(index);
        entity.mHeadUrl = result.mHeadUrls.get(index);
        entity.mImageUrl = result.mImageUrls.get(index);
        entity.mIsUp = result.mIsUps.get(index);
        entity.mLocationGuid = result.mLocationGuids.get(index);
        entity.mLocationName = result.mLocationNames.get(index);
        entity.mUserName = result.mUserNames.get(index);
        entity.mReadCount = result.mReadCounts.get(index);
        entity.mUpCount = result.mReadCounts.get(index);
        return entity;
    }
}
