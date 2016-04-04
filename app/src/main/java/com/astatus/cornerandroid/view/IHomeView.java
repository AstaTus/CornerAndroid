package com.astatus.cornerandroid.view;

import com.astatus.cornerandroid.entity.CornerEntity;

import java.util.List;

/**
 * Created by AstaTus on 2016/3/30.
 */
public interface IHomeView {

    void showLocationFailed();

    void showFinishSearchCorner();

    void showSearchCornerFailed();

    void showNextPage();

    void showNewPage();

    void changeRecyclerViewLoadingFoot();

    void changeRecyclerViewNoMoreFoot();

    void bindCornerData(List<Object> list);

}
