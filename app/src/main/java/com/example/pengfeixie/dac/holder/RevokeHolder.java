package com.example.pengfeixie.dac.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.pengfeixie.dac.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pengfeixie on 16/1/18.
 */
public class RevokeHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.subject)
    public TextView subjectName;

    @Bind(R.id.object)
    public TextView objectName;

    @Bind(R.id.rights)
    public TextView rihgts;

    public RevokeHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
