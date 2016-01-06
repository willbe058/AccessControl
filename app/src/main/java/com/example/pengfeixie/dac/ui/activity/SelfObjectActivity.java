package com.example.pengfeixie.dac.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.pengfeixie.dac.R;
import com.example.pengfeixie.dac.base.BaseAppBarActivity;
import com.example.pengfeixie.dac.dao.RealmHelper;
import com.example.pengfeixie.dac.event.BusProvider;
import com.example.pengfeixie.dac.event.CreateObjectEvent;
import com.example.pengfeixie.dac.model.CentralizedObject;
import com.example.pengfeixie.dac.model.CentralizedSubject;
import com.example.pengfeixie.dac.model.Power;
import com.example.pengfeixie.dac.ui.fragment.ObjectFragment;
import com.example.pengfeixie.dac.utils.PreferenceUtil;
import com.github.clans.fab.FloatingActionButton;
import com.xpf.me.architect.app.AppData;

import java.util.UUID;

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
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);

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
            public void onClick(final View view) {
                new MaterialDialog.Builder(SelfObjectActivity.this)
                        .title("创建客体")
                        .customView(R.layout.dialog_create_object, false)
                        .autoDismiss(false)
                        .positiveText("确定")
                        .negativeText("取消")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                String name = ((EditText) dialog.getCustomView().findViewById(R.id.create_obj_name)).getText().toString();
                                String info = ((EditText) dialog.getCustomView().findViewById(R.id.create_info_pass)).getText().toString();
                                if (TextUtils.isEmpty(name)) {
                                    Toast.makeText(view.getContext(), "请填写客体名称", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                CentralizedObject object = new CentralizedObject();
                                object.setName(name);
                                object.setInfo(info);
                                CentralizedSubject creator = RealmHelper.getInstance().getUser(PreferenceUtil.getPreString("user", ""));
                                Log.i("shit", creator.getName());
                                object.setCreator(creator);
                                Power power = new Power();
                                power.setId(UUID.randomUUID().toString());
                                power.setGrantor(creator);
                                power.setsName(PreferenceUtil.getPreString("user", ""));
                                RealmHelper.getInstance().addObject(object, power);
                                BusProvider.getInstance().post(new CreateObjectEvent());
                                dialog.dismiss();
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                dialog.dismiss();
                            }
                        }).build().show();
            }
        });
    }
}
