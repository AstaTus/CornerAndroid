package com.astatus.cornerandroid.presenter;

import com.astatus.cornerandroid.application.CornerApplication;
import com.astatus.cornerandroid.cache.UserCache;
import com.astatus.cornerandroid.entity.UserArticleEntity;
import com.astatus.cornerandroid.entity.CommentEntity;
import com.astatus.cornerandroid.http.okhttp.ArticleCmd;
import com.astatus.cornerandroid.http.okhttp.CmdListener;
import com.astatus.cornerandroid.http.okhttp.UpChangeStateCmd;
import com.astatus.cornerandroid.message.ArticleMsg;
import com.astatus.cornerandroid.message.CommentBlock;
import com.astatus.cornerandroid.message.UpChangeStateMsg;
import com.astatus.cornerandroid.message.UserArticleBlock;
import com.astatus.cornerandroid.model.ArticleModel;
import com.astatus.cornerandroid.view.IArticleView;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by AstaTus on 2016/2/28.
 */
public class ArticlePresenter {

    private ArticleModel mModel;
    private IArticleView mArticleView;

    public ArticlePresenter(IArticleView view){
        mModel = new ArticleModel();
        mArticleView = view;
    }

    public void bindArticleListData(){
        mArticleView.bindArticleListData(mModel.getArticleList());
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


    public void changeUpState(BigInteger articleGuid){

        UpChangeStateCmd cmd = UpChangeStateCmd.create(
                new UpChangeStateCmdListener(),
                articleGuid);
        cmd.excute();
    }

    class UpChangeStateCmdListener implements CmdListener<UpChangeStateMsg>{

        @Override
        public void onSuccess(UpChangeStateMsg result) {

            int index = mModel.changeUpState(result.mArticleGuid, result.mIsUp);
            mArticleView.notifyUpState(index);
        }

        @Override
        public void onFailed() {

        }

        @Override
        public void onResponseFailed(int code) {

        }
    }

    class LoadNewPageArticleCmdListener implements CmdListener<ArticleMsg> {
        @Override
        public void onSuccess(ArticleMsg result) {

            try {
                if (result.mIsTimeOut){
                    mModel.resetData();
                }

                UserArticleEntity entity;
                for (int i = result.mArticles.size() - 1; i >= 0; --i){

                    entity = builderArticleEntity(result.mArticles.get(i));
                    mModel.addArticle(entity, ArticleModel.ADD_ARTICLE_LOCATION_FRONT);
                }


                mArticleView.showNewPage();
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void onFailed() {
            mArticleView.loadNextPageFailed();
        }

        @Override
        public void onResponseFailed(int code) {

        }
    }


    class LoadNextPageArticleCmdListener implements CmdListener<ArticleMsg> {

        @Override
        public void onSuccess(ArticleMsg result) {

            UserArticleEntity entity;
            for (int i = 0; i < result.mArticles.size(); ++i){
                entity = builderArticleEntity(result.mArticles.get(i));
                mModel.addArticle(entity, ArticleModel.ADD_ARTICLE_LOCATION_BACK);
            }

            if (result.mArticles.size() < ArticleCmd.REQUEST_ARTICLE_MAX_COUNT){

                mArticleView.changeRecyclerViewFootStyle(false);
            }else{
                mArticleView.changeRecyclerViewFootStyle(true);
            }

            mArticleView.showNextPage();
        }

        @Override
        public void onFailed() {
            mArticleView.loadNextPageFailed();
        }

        @Override
        public void onResponseFailed(int code) {

        }
    }

    public UserArticleEntity builderArticleEntity(UserArticleBlock block){
        UserArticleEntity entity = new UserArticleEntity();
        entity.mGuid = block.mGuid;
        entity.mTime = block.mTime;
        entity.mFeelText = block.mFeelText;
        entity.mHeadUrl = block.mUser.mHeadPath;
        entity.mImagePath = block.mImagePath;
        entity.mIsUp = block.mIsUp;
        entity.mLocationGuid = block.mCorner.mGuid;
        entity.mLocationName = block.mCorner.mName;
        entity.mUserName = block.mUser.mName;
        entity.mReadCount = block.mReadCount;
        entity.mUpCount = block.mUpCount;

        ArrayList<CommentBlock> blocks = block.mComments;
        for (int i = 0; i < blocks.size(); ++i){
            CommentBlock commentBlock = blocks.get(i);

            if (block.mGuid.compareTo(BigInteger.valueOf(0)) != 0){
                CommentEntity comment = new CommentEntity();
                comment.mGuid = commentBlock.mGuid;
                comment.mReplyGuid = commentBlock.mReplyGuid;
                comment.mReplyHeadUrl = commentBlock.mHeadUrl;
                comment.mTargetName = commentBlock.mTargetName;
                comment.mReplyName = commentBlock.mReplyName;
                comment.mTime = commentBlock.mTime;
                comment.mFeelText = commentBlock.mText;

                entity.mComments.add(comment);
            }
        }

        return entity;
    }
}
