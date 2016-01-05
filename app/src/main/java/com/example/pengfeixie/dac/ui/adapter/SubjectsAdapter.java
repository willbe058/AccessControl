package com.example.pengfeixie.dac.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pengfeixie.dac.R;
import com.example.pengfeixie.dac.base.BaseAdapter;
import com.example.pengfeixie.dac.holder.SubjectHolder;
import com.example.pengfeixie.dac.model.CentralizedSubject;

/**
 * Created by pengfeixie on 16/1/5.
 */
public class SubjectsAdapter extends BaseAdapter<SubjectHolder, CentralizedSubject> {

    private Context mContext;

    private OnSubjectClickListener listener;

    public void setOnSubjectClickListener(OnSubjectClickListener listener) {
        this.listener = listener;
    }

    public SubjectsAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public SubjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View root = LayoutInflater.from(mContext).inflate(R.layout.item_subject, parent, false);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.clickSubject(view, ((CentralizedSubject) root.getTag()));
                }
            }
        });
        return null;
    }

    @Override
    public void onBindViewHolder(SubjectHolder holder, int position) {
        CentralizedSubject subject = mDataList.get(position);
        holder.itemView.setTag(subject);

        holder.name.setText(subject.getName());
    }

    public interface OnSubjectClickListener {
        void clickSubject(View view, CentralizedSubject subject);
    }
}
