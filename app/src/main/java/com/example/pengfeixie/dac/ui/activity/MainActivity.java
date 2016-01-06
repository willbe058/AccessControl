package com.example.pengfeixie.dac.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.pengfeixie.dac.R;
import com.example.pengfeixie.dac.base.BaseAppBarActivity;
import com.example.pengfeixie.dac.dao.RealmHelper;
import com.example.pengfeixie.dac.event.BusProvider;
import com.example.pengfeixie.dac.event.CreateSubjectEvent;
import com.example.pengfeixie.dac.model.CentralizedSubject;
import com.example.pengfeixie.dac.ui.fragment.SubjectFragment;
import com.example.pengfeixie.dac.utils.PreferenceUtil;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.xpf.me.architect.activity.MvpActivity;
import com.xpf.me.architect.app.AppData;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import io.realm.Realm;

public class MainActivity extends BaseAppBarActivity {

    @Bind(R.id.subjectName)
    TextView currentUser;

    @Bind(R.id.subject)
    FloatingActionButton addSubject;

    @Bind(R.id.object)
    FloatingActionButton addObject;

    @Bind(R.id.power)
    FloatingActionButton addPower;

    @Bind(R.id.menu)
    FloatingActionMenu menu;

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
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setupActionBar() {
        setSupportActionBar(toolbar);
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        if (PreferenceUtil.getPreString("user", "").equals("root")) {
            currentUser.setText("当前用户: 超级管理员");
        } else {
            currentUser.setText("当前用户: " + PreferenceUtil.getPreString("user", ""));
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SubjectFragment()).commit();
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PreferenceUtil.getPreString("user", "").equals("root")) {
                    Toast.makeText(AppData.getContext(), "只有管理员用户才能创建主体!", Toast.LENGTH_LONG).show();
                    return;
                }
                menu.close(true);
                new MaterialDialog.Builder(MainActivity.this)
                        .customView(R.layout.dialog_add_subject, false)
                        .title("创建新的用户(主体)")
                        .positiveText("确定")
                        .negativeText("取消")
                        .autoDismiss(false)
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                String name = ((EditText) dialog.getCustomView().findViewById(R.id.create_sub_name)).getText().toString();
                                String passwd = ((EditText) dialog.getCustomView().findViewById(R.id.create_sub_pass)).getText().toString();
                                String info = ((EditText) dialog.getCustomView().findViewById(R.id.create_info_pass)).getText().toString();
                                if (TextUtils.isEmpty(name)) {
                                    Toast.makeText(AppData.getContext(), "请填写用户名", Toast.LENGTH_LONG).show();
                                    return;
                                } else if (TextUtils.isEmpty(passwd)) {
                                    Toast.makeText(AppData.getContext(), "请填写密码", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                CentralizedSubject subject = new CentralizedSubject();
                                subject.setName(name);
                                subject.setPasswd(passwd);
                                if (TextUtils.isEmpty(info)) {
                                    subject.setInfo("默认信息");
                                } else {
                                    subject.setInfo(info);
                                }
                                RealmHelper.getInstance().addSubject(subject);
                                BusProvider.getInstance().post(new CreateSubjectEvent());
                                dialog.dismiss();
                            }

                            @Override
                            public void onNegative(MaterialDialog dialog) {
                                dialog.dismiss();
                            }
                        }).build().show();
            }
        });

        addObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SelfObjectActivity.class));
            }
        });
    }

    public void exportDatabase() {

        // init realm
        Realm realm = Realm.getInstance(this);

        File exportRealmFile = null;
        try {
            // get or create an "export.realm" file
            exportRealmFile = new File(getExternalCacheDir(), "export.realm");

            // if "export.realm" already exists, delete
            exportRealmFile.delete();

            // copy current realm to "export.realm"
            realm.writeCopyTo(exportRealmFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        realm.close();

        // init email intent and add export.realm as attachment
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, "willbe058@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "REALM");
        intent.putExtra(Intent.EXTRA_TEXT, "haha");
        Uri u = Uri.fromFile(exportRealmFile);
        intent.putExtra(Intent.EXTRA_STREAM, u);

        // start email intent
        startActivity(Intent.createChooser(intent, "YOUR CHOOSER TITLE"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
