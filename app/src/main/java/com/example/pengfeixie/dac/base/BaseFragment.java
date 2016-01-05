package com.example.pengfeixie.dac.base;

import android.support.v4.app.Fragment;

/**
 * Created by pengfeixie on 16/1/5.
 */
public abstract class BaseFragment extends Fragment {

    protected String getSelfTag() {
        return BaseFragment.this.getClass().getSimpleName();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}