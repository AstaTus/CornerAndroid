package com.astatus.cornerandroid.view;

import com.astatus.cornerandroid.entity.CornerEntity;

import java.util.List;

/**
 * Created by AstaTus on 2016/3/16.
 */
public interface ILocationView {

    void showFinishLocation();

    void showLocationFailed();

    void showNextPageCorner();

    void showLoadNearbyCornerFailed();

    void showFinishCreateCorner();

    void showCreateCornerFailed();

    void notifyCreateCornerSuccess();

    void changeRecyclerViewFoot(int style);

    void bindCornerData(List<CornerEntity> list);
}
