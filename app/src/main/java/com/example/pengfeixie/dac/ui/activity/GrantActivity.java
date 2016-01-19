package com.example.pengfeixie.dac.ui.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.pengfeixie.dac.R;
import com.example.pengfeixie.dac.base.BaseAppBarActivity;
import com.example.pengfeixie.dac.dao.RealmHelper;
import com.example.pengfeixie.dac.model.CentralizedObject;
import com.example.pengfeixie.dac.model.CentralizedSubject;
import com.example.pengfeixie.dac.model.Power;
import com.example.pengfeixie.dac.model.usecase.ObjectUsecase;
import com.example.pengfeixie.dac.model.usecase.SubjectUsecase;
import com.example.pengfeixie.dac.ui.adapter.SelfObjectAdapter;
import com.example.pengfeixie.dac.ui.adapter.SubjectsAdapter;
import com.example.pengfeixie.dac.utils.PreferenceUtil;
import com.github.clans.fab.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;

/**
 * Created by pengfeixie on 16/1/17.
 */
public class GrantActivity extends BaseAppBarActivity {

    @Bind(R.id.object_list)
    RecyclerView objectList;

    @Bind(R.id.subject_list)
    RecyclerView subjectList;

    @Bind(R.id.fab_grant)
    FloatingActionButton grantFab;

    @Bind(R.id.fab_revoke)
    FloatingActionButton revokeFab;

    private SelfObjectAdapter objectAdapter;

    private SubjectsAdapter subjectsAdapter;

    private CentralizedObject object;

    private CentralizedSubject mSubject = RealmHelper.getInstance().getUser(PreferenceUtil.getPreString("user", ""));

    private int[] has = new int[]{RealmHelper.NO, RealmHelper.NO, RealmHelper.NO, RealmHelper.NO
            , RealmHelper.NO, RealmHelper.NO, RealmHelper.NO};

    private CentralizedSubject toSubject;

    @Override
    protected void setupContentView() {
        setContentView(R.layout.activity_grant);
    }

    @Override
    protected void setupActionBar() {
        super.setupActionBar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(mSubject.getName() + "授权管理");
    }

    @Override
    protected void setupViews() {
        super.setupViews();
        objectAdapter = new SelfObjectAdapter(this);
        subjectsAdapter = new SubjectsAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        objectList.setHasFixedSize(true);
        objectList.setLayoutManager(layoutManager);
        objectList.setAdapter(objectAdapter);

        GridLayoutManager subLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        subjectList.setHasFixedSize(true);
        subjectList.setLayoutManager(subLayoutManager);
        subjectList.setAdapter(subjectsAdapter);

        new ObjectUsecase().getData(mSubject.getName())
                .subscribe(new Subscriber<List<Power>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(GrantActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<Power> powers) {
                        objectAdapter.addAll(powers);
                    }
                });
        new SubjectUsecase().execute()
                .subscribe(new Subscriber<List<CentralizedSubject>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(GrantActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(List<CentralizedSubject> centralizedSubjects) {
                        subjectsAdapter.addAll(centralizedSubjects);
                    }
                });

        objectAdapter.setLongClickListener(new SelfObjectAdapter.OnObjectLongClickListener() {
            @Override
            public void longClick(final View view, Power power) {
                object = RealmHelper.getInstance().getObject(power.getoName());
                //dialog multi choice
                //检查主体有没有对客体的某个权限
                view.setSelected(true);
                new MaterialDialog.Builder(view.getContext())
                        .title(power.getoName())
                        .items(R.array.grant)
                        .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < which.length; i++) {
                                    if (i > 0) sb.append("\n");
                                    sb.append("您选择了" + text[i] + "权限");
                                    has[which[i]] = RealmHelper.YES;
                                }
                                Toast.makeText(view.getContext(), sb.toString(), Toast.LENGTH_SHORT).show();
                                Log.i("has", Arrays.toString(has));
                                return true;
                            }
                        })
                        .alwaysCallMultiChoiceCallback()
                        .autoDismiss(false)
                        .positiveText("选择")
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        subjectsAdapter.setOnSubjectClickListener(new SubjectsAdapter.OnSubjectClickListener() {
            @Override
            public void clickSubject(View view, CentralizedSubject subject) {
                // TODO: 16/1/17
                toSubject = subject;
            }
        });

        grantFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RealmHelper.getInstance().grant(mSubject).rights(has).object(object).to(toSubject);
            }
        });

        revokeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GrantActivity.this, RevokeActivity.class));
            }
        });
    }
}
