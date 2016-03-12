package com.astatus.cornerandroid.presenter;

import com.astatus.cornerandroid.adapder.CommentRecyclerAdapter;
import com.astatus.cornerandroid.entity.CommentEntity;
import com.astatus.cornerandroid.http.okhttp.ArticleCmd;
import com.astatus.cornerandroid.http.okhttp.CmdListener;
import com.astatus.cornerandroid.http.okhttp.CommentAddCmd;
import com.astatus.cornerandroid.http.okhttp.CommentObtainCmd;
import com.astatus.cornerandroid.http.okhttp.CommentRemoveCmd;
import com.astatus.cornerandroid.message.CommentAddMsg;
import com.astatus.cornerandroid.message.CommentBlock;
import com.astatus.cornerandroid.message.CommentObtainMsg;
import com.astatus.cornerandroid.message.CommentRemoveMsg;
import com.astatus.cornerandroid.model.ArticleModel;
import com.astatus.cornerandroid.model.CommentModel;
import com.astatus.cornerandroid.view.ICommentView;

import java.math.BigInteger;

/**
 * Created by AstaTus on 2016/2/28.
 */
public class CommentPresenter {

    private CommentModel mModel;
    private ICommentView mCommentView;
    private CommentRecyclerAdapter mAdpater;

    public CommentPresenter(ICommentView view, CommentRecyclerAdapter adpater) {
        mModel = new CommentModel();
        mCommentView = view;
        mAdpater = adpater;

        mAdpater.resetData(mModel.getArticleList());
    }


    public void loadNewPage() {

        CommentObtainCmd cmd = CommentObtainCmd.create(
                new LoadNewPageCommentCmdListener(),
                mModel.getArticleGuid(),
                mModel.getFrontGuid(),
                CommentObtainCmd.REQUEST_DIRECTION_UP);
        cmd.excute();
    }

    public void loadNextPage() {
        CommentObtainCmd cmd = CommentObtainCmd.create(
                new LoadNextPageCommentCmdListener(),
                mModel.getArticleGuid(),
                mModel.getLastGuid(),
                CommentObtainCmd.REQUEST_DIRECTION_DOWN);
        cmd.excute();

    }

    public void sendComment(BigInteger targetGuid, String comment) {
        CommentAddCmd cmd = CommentAddCmd.create(
                new CommentAddCmdListener(),
                mModel.getArticleGuid(),
                targetGuid,
                comment);
        cmd.excute();
    }

    public void removeComment(BigInteger commentGuid) {
        CommentRemoveCmd cmd = CommentRemoveCmd.create(
                new CommentRemoveCmdListener(),
                commentGuid);
        cmd.excute();
    }


    private CommentEntity builderCommentEntity(CommentBlock block) {
        CommentEntity comment = new CommentEntity();
        comment.mGuid = block.mGuid;
        comment.mFeelText = block.mText;
        comment.mReplyGuid = block.mReplyGuid;
        comment.mReplyHeadUrl = block.mHeadUrl;
        comment.mReplyName = block.mReplyName;
        comment.mTargetName = block.mTargetName;
        comment.mTime = block.mTime;


        return comment;
    }

    class LoadNewPageCommentCmdListener implements CmdListener<CommentObtainMsg> {
        @Override
        public void onSuccess(CommentObtainMsg result) {

            try {
                if (result.mIsTimeOut) {
                    mModel.resetData();
                }

                CommentBlock block;
                CommentEntity entity;
                for (int i = result.mCommentBlocks.size() - 1; i >= 0; --i) {

                    block = result.mCommentBlocks.get(i);
                    entity = builderCommentEntity(block);
                    mModel.addComment(entity, CommentModel.ADD_COMMENT_LOCATION_FRONT);
                }

                mCommentView.showNewPage();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailed() {
            mCommentView.loadNextPageFailed();
        }
    }


    class LoadNextPageCommentCmdListener implements CmdListener<CommentObtainMsg> {

        @Override
        public void onSuccess(CommentObtainMsg result) {

            CommentBlock block;
            CommentEntity entity;
            for (int i = result.mCommentBlocks.size() - 1; i >= 0; --i) {

                block = result.mCommentBlocks.get(i);
                entity = builderCommentEntity(block);
                mModel.addComment(entity, CommentModel.ADD_COMMENT_LOCATION_BACK);
            }

            if (result.mCommentBlocks.size() < CommentObtainCmd.REQUEST_COMMENT_MAX_COUNT) {

                mCommentView.changeRecyclerViewFootStyle(0);
            } else {
                mCommentView.changeRecyclerViewFootStyle(0);
            }

            mCommentView.showNextPage();
        }


        @Override
        public void onFailed() {
            mCommentView.loadNextPageFailed();
        }
    }

    class CommentAddCmdListener implements CmdListener<CommentAddMsg> {

        @Override
        public void onSuccess(CommentAddMsg result) {

        }

        @Override
        public void onFailed() {

        }
    }

    class CommentRemoveCmdListener implements CmdListener<CommentRemoveMsg> {

        @Override
        public void onSuccess(CommentRemoveMsg result) {

        }

        @Override
        public void onFailed() {

        }
    }
}
