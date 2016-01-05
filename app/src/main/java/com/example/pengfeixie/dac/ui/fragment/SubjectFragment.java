package com.example.pengfeixie.dac.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pengfeixie.dac.R;
import com.example.pengfeixie.dac.base.BaseRecyclerViewFragment;
import com.example.pengfeixie.dac.base.BaseRecyclerViewMvpFragment;
import com.example.pengfeixie.dac.event.BusProvider;
import com.example.pengfeixie.dac.event.CreateSubjectEvent;
import com.example.pengfeixie.dac.model.CentralizedSubject;
import com.example.pengfeixie.dac.presenter.SubjectPresenter;
import com.example.pengfeixie.dac.ui.adapter.SubjectsAdapter;
import com.example.pengfeixie.dac.view.SubjectView;
import com.github.clans.fab.FloatingActionButton;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by pengfeixie on 16/1/5.
 */
public class SubjectFragment extends BaseRecyclerViewMvpFragment<SubjectView, SubjectPresenter, RecyclerView> implements SubjectView {


    private TextView emptyText;

    private ImageView emptyImage;

    private RelativeLayout emptyView;

    private SubjectsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onCreateSubject(CreateSubjectEvent event) {
        adapter.clear();
        presenter.loadData();
    }

    @Override
    protected void setupViews(View contentView) {
        emptyView = ((RelativeLayout) contentView.findViewById(R.id.empty_view));
        emptyText = ((TextView) contentView.findViewById(R.id.empty_text));
        emptyImage = ((ImageView) contentView.findViewById(R.id.empty_image));

        adapter = new SubjectsAdapter(getActivity());
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.addItemDecoration(new MarginDecoration(CommonUtils.convertDimenToPix(2)));
        mRecyclerView.setAdapter(adapter);
    }

    @NonNull
    @Override
    public SubjectPresenter createPresenter() {
        return new SubjectPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.loadData();
    }

    @Override
    public void setData(List<CentralizedSubject> subjects) {
        if (subjects.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            emptyText.setText("还没有任何用户");
        } else {
            emptyView.setVisibility(View.GONE);
            adapter.addAll(subjects);
        }
    }

    @Override
    public void setError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }
}
