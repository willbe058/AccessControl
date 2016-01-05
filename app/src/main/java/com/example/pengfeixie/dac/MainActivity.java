package com.example.pengfeixie.dac;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dd.realmbrowser.FuckActivity;
import com.example.pengfeixie.dac.base.BaseAppBarActivity;
import com.example.pengfeixie.dac.dao.RealmHelper;
import com.example.pengfeixie.dac.model.CentralizedSubject;
import com.example.pengfeixie.dac.ui.fragment.SubjectFragment;
import com.example.pengfeixie.dac.utils.PreferenceUtil;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends BaseAppBarActivity {

    @Bind(R.id.subjectName)
    TextView currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            currentUser.setText("超级管理员");
        } else {
            currentUser.setText(PreferenceUtil.getPreString("user", ""));
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new SubjectFragment()).commit();
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
