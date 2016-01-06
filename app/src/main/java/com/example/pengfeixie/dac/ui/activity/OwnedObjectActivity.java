package com.example.pengfeixie.dac.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.pengfeixie.dac.R;
import com.example.pengfeixie.dac.base.BaseActivity;
import com.example.pengfeixie.dac.dao.RealmHelper;
import com.example.pengfeixie.dac.model.CentralizedSubject;
import com.example.pengfeixie.dac.model.Power;
import com.example.pengfeixie.dac.ui.adapter.ObjectAdapter;
import com.example.pengfeixie.dac.utils.PreferenceUtil;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.ButterKnife;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

/**
 * Created by pengfeixie on 16/1/6.
 */
public class OwnedObjectActivity extends BaseActivity {

    @Bind(R.id.table)
    TableView<Power> tableView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.subjectName)
    TextView textView;

    private ObjectAdapter adapter;

    private CentralizedSubject subject;

    public static void getInstance(Activity activity, String name) {
        Intent intent = new Intent(activity, OwnedObjectActivity.class);
        intent.putExtra("name", name);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getIntent() != null) {
            this.subject = RealmHelper.getInstance().getUser(getIntent().getStringExtra("name"));
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_owned_object);
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        textView.setText("正在查看用户: " + subject.getName());
        adapter = new ObjectAdapter(this, RealmHelper.getInstance().getPowers(subject.getName()));
        tableView.setDataAdapter(adapter);
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, "客体名称", "控制权", "读", "写", "删除", "添加", "执行", "拥有", "授权者"));
    }

    @Override
    protected void setupActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void tintStatusBarApi21() {

    }

    @Override
    protected void tintStatusBarApi19() {

    }
}
