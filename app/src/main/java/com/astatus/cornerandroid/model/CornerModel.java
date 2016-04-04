package com.astatus.cornerandroid.model;

import com.astatus.cornerandroid.entity.CornerEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AstaTus on 2016/3/16.
 */
public class CornerModel {
    private List<CornerEntity> mCorners;

    public CornerModel(){
        mCorners = new ArrayList<CornerEntity>();
    }

    public void resetData(){
        mCorners.clear();
    }

    public List<CornerEntity> getCornerList(){
        return mCorners;
    }

    public void addCorner(CornerEntity entity){
        mCorners.add(entity);
    }

    public void clear(){
        mCorners.clear();
    }
}
