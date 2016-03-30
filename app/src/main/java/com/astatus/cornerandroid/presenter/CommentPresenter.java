package com.astatus.cornerandroid.presenter;

import com.astatus.cornerandroid.adapder.CommentRecyclerAdapter;
import com.astatus.cornerandroid.entity.CommentEntity;
import com.astatus.cornerandroid.http.okhttp.CmdListener;
import com.astatus.cornerandroid.http.okhttp.CommentAddCmd;
import com.astatus.cornerandroid.http.okhttp.CommentObtainCmd;
import com.astatus.cornerandroid.http.okhttp.CommentRemoveCmd;
import com.astatus.cornerandroid.message.CommentAddMsg;
import com.astatus.cornerandroid.message.CommentBlock;
import com.astatus.cornerandroid.message.CommentObtainMsg;
import com.astatus.cornerandroid.message.CommentRemoveMsg;
import com.astatus.cornerandroid.model.CommentModel;
import com.astatus.cornerandroid.view.ICommentView;

import org.w3c.dom.Comment;

import java.math.BigInteger;

/**
 * Created by AstaTus on 2016/2/28.
 */
public class CommentPresenter {

    public static final int COMMENT_LIST_STATE_IS_LOADING = 0;
    public static final int COMMENT_LIST_STATE_NO_MORE = 0;

    private CommentModel mModel;
    private ICommentView mCommentView;

    private BigInteger mArticleGuid = BigInteger.valueOf(0);

    public void setArticleGuid(BigInteger articleGuid){
        mArticleGuid = articleGuid;
    }

    public BigInteger getArticleGuid(){

        return mArticleGuid;
    }

    public CommentPresenter(ICommentView view) {
        mModel = new CommentModel();
        mCommentView = view;
    }

    public void bindArticleListData(){
        mCommentView.bindArticleListData(mModel.getCommentList());
    }


    public void loadNewPage() {

        CommentObtainCmd cmd = CommentObtainCmd.create(
                new LoadNewPageCommentCmdListener(),
                mArticleGuid,
                mModel.getFrontGuid(),
                CommentObtainCmd.REQUEST_DIRECTION_UP);
        cmd.excute();
    }

    public void loadNextPage() {
        CommentObtainCmd cmd = CommentObtainCmd.create(
                new LoadNextPageCommentCmdListener(),
                mArticleGuid,
                mModel.getLastGuid(),
                CommentObtainCmd.REQUEST_DIRECTION_DOWN);
        cmd.excute();
    }

    public void sendComment(BigInteger targetGuid, String comment) {

        CommentAddCmdListener listener = new CommentAddCmdListener();
        CommentAddCmd cmd = CommentAddCmd.create(
                listener,
                mArticleGuid,
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
            mCommentView.loadNewPageFailed();
        }

        @Override
        public void onResponseFailed(int code) {

        }
    }


    class LoadNextPageCommentCmdListener implements CmdListener<CommentObtainMsg> {

        @Override
        public void onSuccess(CommentObtainMsg result) {

            CommentBlock block;
            CommentEntity entity;
            for (int i = 0; i < result.mCommentBlocks.size(); ++i) {

                block = result.mCommentBlocks.get(i);
                entity = builderCommentEntity(block);
                mModel.addComment(entity, CommentModel.ADD_COMMENT_LOCATION_BACK);
            }

            if (result.mCommentBlocks.size() < CommentObtainCmd.REQUEST_COMMENT_MAX_COUNT) {

                mCommentView.changeRecyclerViewFootStyle(false);
            } else {
                mCommentView.changeRecyclerViewFootStyle(true);
            }

            mCommentView.showNextPage();
        }


        @Override
        public void onFailed() {
            mCommentView.loadNextPageFailed();
        }

        @Override
        public void onResponseFailed(int code) {

        }
    }

    class CommentAddCmdListener implements CmdListener<CommentAddMsg> {

        @Override
        public void onSuccess(CommentAddMsg result) {

            CommentEntity entity = new CommentEntity();
            CommentBlock block = result.mBlock;

            entity.mGuid = block.mGuid;
            entity.mTime = block.mTime;
            entity.mTargetName = block.mTargetName;
            entity.mReplyGuid = block.mReplyGuid;
            entity.mReplyHeadUrl = block.mHeadUrl;
            entity.mReplyName = block.mReplyName;
            entity.mFeelText = block.mText;

            int pos = mModel.addComment(entity, CommentModel.ADD_COMMENT_LOCATION_BACK);
            mCommentView.notifyCommentAdd(pos);
        }

        @Override
        public void onFailed() {

        }

        @Override
        public void onResponseFailed(int code) {

        }
    }

    class CommentRemoveCmdListener implements CmdListener<CommentRemoveMsg> {

        @Override
        public void onSuccess(CommentRemoveMsg result) {

            int index = mModel.removeComment(result.mGuid);
            mCommentView.notifyCommentRemove(index);

        }

        @Override
        public void onFailed() {

        }

        @Override
        public void onResponseFailed(int code) {

        }
    }
}
