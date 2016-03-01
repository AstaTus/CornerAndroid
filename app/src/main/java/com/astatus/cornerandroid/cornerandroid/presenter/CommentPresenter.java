package com.astatus.cornerandroid.cornerandroid.presenter;

import com.astatus.cornerandroid.adapder.CommentRecyclerAdapter;
import com.astatus.cornerandroid.adapder.PersonalRecyclerAdapter;
import com.astatus.cornerandroid.http.okhttp.ArticleCmd;
import com.astatus.cornerandroid.http.okhttp.CmdListener;
import com.astatus.cornerandroid.message.ArticleMsg;
import com.astatus.cornerandroid.model.ArticleModel;
import com.astatus.cornerandroid.model.CommentModel;
import com.astatus.cornerandroid.view.IArticleView;

import com.astatus.cornerandroid.adapder.PersonalRecyclerAdapter;
import com.astatus.cornerandroid.http.okhttp.ArticleCmd;
import com.astatus.cornerandroid.http.okhttp.CmdListener;
import com.astatus.cornerandroid.message.ArticleMsg;
import com.astatus.cornerandroid.message.MessagePacket;
import com.astatus.cornerandroid.model.ArticleModel;
import com.astatus.cornerandroid.view.IArticleView;
import com.astatus.cornerandroid.view.ICommentView;

import java.util.Date;

/**
 * Created by AstaTus on 2016/2/28.
 */
public class CommentPresenter {

    private CommentModel mModel;
    private ICommentView mCommentView;
    private CommentRecyclerAdapter mAdpater;

    public CommentPresenter(ICommentView view, CommentRecyclerAdapter adpater){
        mModel = new CommentModel();
        mCommentView = view;
        mAdpater = adpater;

        mAdpater.resetData(mModel.getArticleList());
    }


    public void loadComments(){

    }

    public void sendComment(String comment){

    }


}
