package com.example.pengfeixie.dac.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pengfeixie.dac.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pengfeixie on 16/1/6.
 */
public class ObjectHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.obj_name)
    public TextView name;

    @Bind(R.id.info)
    public TextView info;

    @Bind(R.id.creator)
    public TextView creator;

    public ObjectHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
