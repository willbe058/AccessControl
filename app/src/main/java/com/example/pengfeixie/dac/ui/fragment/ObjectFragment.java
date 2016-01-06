package com.example.pengfeixie.dac.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pengfeixie.dac.R;
import com.example.pengfeixie.dac.base.BaseRecyclerViewMvpFragment;
import com.example.pengfeixie.dac.dao.RealmHelper;
import com.example.pengfeixie.dac.event.BusProvider;
import com.example.pengfeixie.dac.model.CentralizedObject;
import com.example.pengfeixie.dac.model.CentralizedSubject;
import com.example.pengfeixie.dac.model.Power;
import com.example.pengfeixie.dac.presenter.ObjectPresenter;
import com.example.pengfeixie.dac.ui.adapter.SelfObjectAdapter;
import com.example.pengfeixie.dac.utils.PreferenceUtil;
import com.example.pengfeixie.dac.view.ObjectView;

import java.util.List;

/**
 * Created by pengfeixie on 16/1/6.
 */
public class ObjectFragment extends BaseRecyclerViewMvpFragment<ObjectView, ObjectPresenter, RecyclerView> implements ObjectView {

    private TextView emptyText;

    private ImageView emptyImage;

    private RelativeLayout emptyView;

    private SelfObjectAdapter adapter;

    private CentralizedSubject subject;

    public static ObjectFragment getInstance(String name) {
        ObjectFragment fragment = new ObjectFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
        if (getArguments() != null) {
            this.subject = RealmHelper.getInstance().getUser(getArguments().getString("name"));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected void setupViews(View contentView) {
        emptyView = ((RelativeLayout) contentView.findViewById(R.id.empty_view));
        emptyText = ((TextView) contentView.findViewById(R.id.empty_text));
        emptyImage = ((ImageView) contentView.findViewById(R.id.empty_image));

        adapter = new SelfObjectAdapter(getActivity());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.addItemDecoration(new MarginDecoration(CommonUtils.convertDimenToPix(2)));
        mRecyclerView.setAdapter(adapter);
    }

    @NonNull
    @Override
    public ObjectPresenter createPresenter() {
        return new ObjectPresenter(PreferenceUtil.getPreString("user", ""));
    }

    @Override
    public void setData(List<Power> objects) {
        if (objects.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            emptyText.setText("还没有任何客体");
        } else {
            emptyView.setVisibility(View.GONE);
            adapter.addAll(objects);
        }
    }

    @Override
    public void setError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }
}
