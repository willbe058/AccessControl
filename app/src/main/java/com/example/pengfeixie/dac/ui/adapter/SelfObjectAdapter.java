package com.example.pengfeixie.dac.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pengfeixie.dac.R;
import com.example.pengfeixie.dac.base.BaseAdapter;
import com.example.pengfeixie.dac.dao.RealmHelper;
import com.example.pengfeixie.dac.holder.ObjectHolder;
import com.example.pengfeixie.dac.model.CentralizedObject;
import com.example.pengfeixie.dac.model.Power;

/**
 * Created by pengfeixie on 16/1/6.
 */
public class SelfObjectAdapter extends BaseAdapter<ObjectHolder, Power> {

    private Context mContext;

    public SelfObjectAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public ObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_object, parent, false);
        return new ObjectHolder(root);
    }

    @Override
    public void onBindViewHolder(ObjectHolder holder, int position) {
        Power power = getItem(position);
        holder.itemView.setTag(power);

        holder.name.setText(power.getoName());
        holder.info.setText(RealmHelper.getInstance().getObject(power.getoName()).getInfo());
        holder.creator.setText("创建者:" + power.getGrantor().getName());
    }
}
