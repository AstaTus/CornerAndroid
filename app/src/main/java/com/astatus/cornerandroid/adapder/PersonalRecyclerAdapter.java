package com.astatus.cornerandroid.adapder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astatus.cornerandroid.R;
import com.astatus.cornerandroid.entity.PostEntity;

import java.util.List;

/**
 * Created by AstaTus on 2016/2/23.
 */
public class PersonalRecyclerAdapter extends RecyclerView.Adapter<PersonalRecyclerAdapter.PersonalViewHolder> {

    private List<PostEntity> mData;

    public PersonalRecyclerAdapter(List<PostEntity> data){
        mData = data;
    }

    @Override
    public PersonalRecyclerAdapter.PersonalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.widget_image_card, parent, false);
        return new PersonalRecyclerAdapter.PersonalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PersonalRecyclerAdapter.PersonalViewHolder holder, int position) {
        PostEntity entity = mData.get(position);
        if (entity != null){

        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void RestData(){

    }


    public static class PersonalViewHolder extends RecyclerView.ViewHolder {

        public PersonalViewHolder(View v) {
            super(v);
        }
    }
}
