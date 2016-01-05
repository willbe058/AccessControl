package com.example.pengfeixie.dac.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pengfeixie.dac.R;
import com.example.pengfeixie.dac.base.BaseActivity;
import com.example.pengfeixie.dac.dao.RealmHelper;
import com.example.pengfeixie.dac.utils.PreferenceUtil;
import com.xpf.me.architect.app.AppData;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by pengfeixie on 16/1/5.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.title)
    AppCompatSpinner title;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.user_name)
    EditText userName;

    @Bind(R.id.passwd)
    EditText passwd;

    @Bind(R.id.login_button)
    Button loginBtn;

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void findViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected void setupActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void tintStatusBarApi21() {

    }

    @Override
    protected void tintStatusBarApi19() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setupViews() {
        super.setupViews();
        title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userName.setText("");
                passwd.setText("");
                String[] res = AppData.getResources().getStringArray(R.array.type);
                PreferenceUtil.setPreString("type", res[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(userName.getText().toString())) {
                    userName.setError("请输入用户名");
                    return;
                } else if (TextUtils.isEmpty(passwd.getText().toString())) {
                    passwd.setError("请输入密码");
                    return;
                }
                if (userName.getText().toString().equals("root")) {
                    PreferenceUtil.setPreString("user", "root");
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    if (RealmHelper.getInstance().getUser(userName.getText().toString()) != null) {
                        if (RealmHelper.getInstance().getUser(userName.getText().toString()).getPasswd()
                                .equals(passwd.getText().toString())) {
                            PreferenceUtil.setPreString("user", userName.getText().toString());
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(AppData.getContext(), "密码错误!", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(AppData.getContext(), "用户不存在!", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }
}
