package com.example.pengfeixie.dac.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pengfeixie.dac.R;
import com.example.pengfeixie.dac.base.BaseAdapter;
import com.example.pengfeixie.dac.holder.RevokeHolder;
import com.example.pengfeixie.dac.model.Power;
import com.example.pengfeixie.dac.model.Right;

/**
 * Created by pengfeixie on 16/1/18.
 */
public class RevokeAdapter extends BaseAdapter<RevokeHolder, Power> {

    private Context mContext;

    private OnRevokeClickListener listener;

    public void setOnRevokeClickListener(OnRevokeClickListener listener) {
        this.listener = listener;
    }

    public RevokeAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public RevokeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View root = LayoutInflater.from(mContext).inflate(R.layout.item_grant, parent, false);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.click(view, ((Power) root.getTag()));
                }
            }
        });
        return new RevokeHolder(root);
    }

    @Override
    public void onBindViewHolder(RevokeHolder holder, int position) {
        Power power = getItem(position);
        holder.itemView.setTag(power);

        holder.subjectName.setText(power.getsName());
        holder.objectName.setText(power.getoName());
        StringBuilder sb = new StringBuilder();
        sb.append("权限");

        for (int i = 0; i < power.getRights().size(); i++) {
            if (i == power.getRights().size() - 1) {
                sb.append(power.getRights().get(i).getName());
            } else {
                sb.append(power.getRights().get(i).getName() + ",");
            }
        }
        holder.rihgts.setText(sb.toString());
    }

    public interface OnRevokeClickListener {
        void click(View view, Power power);
    }
}
