package com.example.pengfeixie.dac.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pengfeixie.dac.R;
import com.example.pengfeixie.dac.base.BaseAppBarActivity;
import com.example.pengfeixie.dac.ui.fragment.ObjectFragment;
import com.example.pengfeixie.dac.utils.PreferenceUtil;
import com.github.clans.fab.FloatingActionButton;

import butterknife.Bind;

/**
 * Created by pengfeixie on 16/1/6.
 */
public class SelfObjectActivity extends BaseAppBarActivity {

    @Bind(R.id.subjectName)
    TextView name;

    @Bind(R.id.add_object)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_object_control);
    }

    @Override
    protected void setupActionBar() {
        super.setupActionBar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        name.setText("客体管理: " + PreferenceUtil.getPreString("user", ""));
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ObjectFragment()).commit();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
