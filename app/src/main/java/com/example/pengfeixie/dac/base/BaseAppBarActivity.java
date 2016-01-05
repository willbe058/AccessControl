package com.example.pengfeixie.dac.base;

import android.support.v7.widget.Toolbar;

import com.example.pengfeixie.dac.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pengfeixie on 16/1/5.
 */
public abstract class BaseAppBarActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void setupActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    protected void tintStatusBarApi21() {

    }

    @Override
    protected void tintStatusBarApi19() {

    }
}
