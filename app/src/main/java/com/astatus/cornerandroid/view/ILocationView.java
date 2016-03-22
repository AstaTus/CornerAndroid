package com.astatus.cornerandroid.view;

import com.astatus.cornerandroid.entity.CornerEntity;

import java.util.List;

/**
 * Created by AstaTus on 2016/3/16.
 */
public interface ILocationView {

    void showFinishLocation();

    void showLocationFailed();

    void showNextPageCorner(int start, int count);

    void showNewSearchCorner();

    void showCreateCornerFailed();

    void notifyCreateCornerSuccess(CornerEntity entity);

    void changeRecyclerViewLoadingFoot();
    void changeRecyclerViewNoMoreFoot();
    void changeRecyclerViewCreateFoot();

    void bindCornerData(List<CornerEntity> list);
}
