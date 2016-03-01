package com.astatus.cornerandroid.cornerandroid.model;

import com.astatus.cornerandroid.entity.SelfEntity;

/**
 * Created by AstaTus on 2016/2/24.
 */
public class SelfModel {
    private SelfEntity mSelfEntity;

    public void setSelf(SelfEntity entity){
        mSelfEntity = entity;
    }

    public SelfEntity getSelf(){
        return mSelfEntity;
    }
}
