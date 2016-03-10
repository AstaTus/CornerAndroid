package com.astatus.cornerandroid.presenter;

import com.astatus.cornerandroid.adapder.PersonalRecyclerAdapter;
import com.astatus.cornerandroid.application.CornerApplication;
import com.astatus.cornerandroid.cache.UserCache;
import com.astatus.cornerandroid.entity.ArticleEntity;
import com.astatus.cornerandroid.entity.CommentEntity;
import com.astatus.cornerandroid.http.okhttp.ArticleCmd;
import com.astatus.cornerandroid.http.okhttp.CmdListener;
import com.astatus.cornerandroid.message.ArticleMsg;
import com.astatus.cornerandroid.message.CommentObtainMsg;
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
                new LoadNextPageArticleCmdListener()
                , BigInteger.valueOf(0)
                , mModel.getLastGuid()
                , ArticleCmd.REQUEST_TYPE_PERSONAL,
                ArticleCmd.REQUEST_DIRECTION_DOWN);
        cmd.excute();
    }

    public void loadNewPage(){

        UserCache user = CornerApplication.getSingleton().getUserCache();
        ArticleCmd cmd = ArticleCmd.create(
                new LoadNewPageArticleCmdListener()
                ,BigInteger.valueOf(0)
                ,mModel.getFrontGuid(),
                ArticleCmd.REQUEST_TYPE_PERSONAL,
                ArticleCmd.REQUEST_DIRECTION_DOWN);
        cmd.excute();
    }


    public void changeUpState(){

    }

    class LoadNewPageArticleCmdListener implements CmdListener<ArticleMsg> {
        @Override
        public void onSuccess(ArticleMsg result) {

            try {
                if (result.mIsTimeOut){
                    mModel.resetData();
                }

                ArticleEntity entity;
                for (int i = result.mGuids.size() - 1; i >= 0; --i){

                    entity = builderArticleEntity(result, i);
                    mModel.addArticle(entity, ArticleModel.ADD_ARTICLE_LOCATION_FRONT);
                }


                mAdpater.restData(mModel.getArticleList());
                mAdpater.showFootView();
                mArticleView.showNewPage();
            }catch (Exception e){
                e.printStackTrace();
            }

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

            if (result.mGuids.size() < ArticleCmd.REQUEST_ARTICLE_MAX_COUNT){
                mAdpater.setHaveMore(false);
                mArticleView.setNoMoreArticle();
            }else{
                mAdpater.setHaveMore(true);
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
        entity.mImagePath = result.mImagePaths.get(index);
        entity.mIsUp = result.mIsUps.get(index);
        entity.mLocationGuid = result.mLocationGuids.get(index);
        entity.mLocationName = result.mLocationNames.get(index);
        entity.mUserName = result.mUserNames.get(index);
        entity.mReadCount = result.mReadCounts.get(index).intValue();
        entity.mUpCount = result.mReadCounts.get(index).intValue();

        CommentObtainMsg msg = result.mComments.get(index);
        for (int i = 0; i < msg.mGuids.size(); ++i){

            if (msg != null && msg.mGuids != null
                    && msg.mGuids.get(i).compareTo(BigInteger.valueOf(0)) != 0){
                CommentEntity comment = new CommentEntity();
                comment.mGuid = msg.mGuids.get(i);
                comment.mReplyGuid = msg.mReplyGuids.get(i);
                comment.mReplyHeadUrl = msg.mHeadUrls.get(i);
                comment.mTargetName = msg.mTargetNames.get(i);
                comment.mReplyName = msg.mReplyNames.get(i);
                comment.mTime = msg.mTimes.get(i);
                comment.mFeelText = msg.mTexts.get(i);

                entity.mComments.add(comment);
            }
        }

        return entity;
    }
}
