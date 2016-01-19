package com.example.pengfeixie.dac.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.pengfeixie.dac.R;
import com.example.pengfeixie.dac.base.BaseAppBarActivity;
import com.example.pengfeixie.dac.dao.RealmHelper;
import com.example.pengfeixie.dac.event.BusProvider;
import com.example.pengfeixie.dac.event.RevokePowerEvent;
import com.example.pengfeixie.dac.model.Power;
import com.example.pengfeixie.dac.ui.adapter.RevokeAdapter;
import com.example.pengfeixie.dac.utils.PreferenceUtil;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import io.realm.RealmResults;

/**
 * Created by pengfeixie on 16/1/18.
 */
public class RevokeActivity extends BaseAppBarActivity {

    @Bind(R.id.grant_list)
    RecyclerView recyclerView;

    private RevokeAdapter adapter;

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_revoke);
    }

    @Override
    protected void setupActionBar() {
        super.setupActionBar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("撤销授权");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onRevoke(RevokePowerEvent event) {

    }


    @Override
    protected void setupViews() {
        super.setupViews();
        adapter = new RevokeAdapter(this);
        adapter.setOnRevokeClickListener(new RevokeAdapter.OnRevokeClickListener() {
            @Override
            public void click(View view, final Power power) {
                new MaterialDialog.Builder(view.getContext())
                        .title("确定撤销?")
                        .positiveText("是")
                        .negativeText("否")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                RealmHelper.getInstance().revoke(
                                        RealmHelper.getInstance().getUser(power.getsName())
                                        , RealmHelper.getInstance().getObject(power.getoName())
                                        , RealmHelper.getInstance().getUser(PreferenceUtil.getPreString("user", "")));
                                BusProvider.getInstance().post(new RevokePowerEvent());
                                adapter.resetList(RealmHelper.getInstance().getTransferedPowers(PreferenceUtil.getPreString("user", "")));
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                super.onNegative(dialog);
                            }
                        }).show();
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.addAll(RealmHelper.getInstance().getTransferedPowers(PreferenceUtil.getPreString("user", "")));
    }
}
