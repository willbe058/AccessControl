package com.example.pengfeixie.dac.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pengfeixie.dac.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pengfeixie on 16/1/5.
 */
public class SubjectHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.sub_name)
    public TextView name;

    public SubjectHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
