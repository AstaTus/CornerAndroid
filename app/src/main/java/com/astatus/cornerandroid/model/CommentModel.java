package com.astatus.cornerandroid.model;

import com.astatus.cornerandroid.entity.CommentEntity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AstaTus on 2016/3/1.
 */
public class CommentModel {

    public static final int ADD_COMMENT_LOCATION_FRONT = 1;
    public static final int ADD_COMMENT_LOCATION_BACK = 2;

    private List<CommentEntity> mComments;



    public CommentModel(){
        mComments = new ArrayList<CommentEntity>();
    }

    public List<CommentEntity> getCommentList(){
        return mComments;
    }

    public CommentEntity getComment(BigInteger guid){
        CommentEntity ce;
        for(int i = 0; i < mComments.size(); ++i){
            ce = mComments.get(i);
            if (ce.mGuid.compareTo(guid) == 0){
                return ce;
            }
        }

        return null;
    }

    public int addComment(CommentEntity entity, int position){
        mComments.add(entity);
        return mComments.size() - 1;
    }

    public int removeComment(BigInteger guid){
        for (int i = 0; i < mComments.size(); ++i){
            CommentEntity entity = mComments.get(i);
            if (entity.mGuid.compareTo(guid) == 0){
                mComments.remove(i);
                return i;
            }
        }

        return -1;
    }

    public BigInteger getFrontGuid(){

        if (mComments.size() > 0){
            CommentEntity entity = mComments.get(0);

            return entity.mGuid;
        }

        return BigInteger.valueOf(0);
    }

    public BigInteger getLastGuid(){
        if (mComments.size() > 0){
            CommentEntity entity = mComments.get(mComments.size() - 1);

            return entity.mGuid;
        }

        return BigInteger.valueOf(0);
    }



    public void resetData(){
        mComments.clear();
    }
}
