package com.example.pengfeixie.dac.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pengfeixie.dac.R;

import butterknife.ButterKnife;

/**
 * Created by pengfeixie on 16/1/5.
 */
public abstract class BaseRecyclerViewFragment<RV extends RecyclerView> extends BaseFragment {

    protected View mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContentView = inflater.inflate(getLayoutId(), null);
        mRecyclerView = findRecyclerView(mContentView);
        setupViews(mContentView);
        return mContentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this); // avoid mem leak
        // TODO: clear view's listeners and drawables
    }

    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    protected RV mRecyclerView;

    public RV getRecyclerView() {
        return mRecyclerView;
    }

    @SuppressWarnings("unchecked")
    protected RV findRecyclerView(View contentView) {
        return (RV) contentView.findViewById(R.id.recyclerView);
    }

    protected abstract void setupViews(View contentView);
}
