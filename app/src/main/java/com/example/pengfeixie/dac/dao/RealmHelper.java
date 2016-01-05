package com.example.pengfeixie.dac.dao;

import com.example.pengfeixie.dac.model.CentralizedObject;
import com.example.pengfeixie.dac.model.CentralizedSubject;
import com.example.pengfeixie.dac.model.Power;
import com.example.pengfeixie.dac.model.Right;
import com.xpf.me.architect.app.AppData;

import java.security.PublicKey;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

/**
 * Created by pengfeixie on 16/1/4.
 */
public class RealmHelper {

    private static Realm realm;

    private static RealmHelper ourInstance = new RealmHelper();

    public static RealmHelper getInstance() {
        return ourInstance;
    }

    private RealmHelper() {
        RealmConfiguration configuration = new RealmConfiguration.Builder(AppData.getContext())
                .name("dac")
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(configuration);
        realm = Realm.getDefaultInstance();
    }

    private CentralizedSubject fromSubject;

    private CentralizedObject object;

    private CentralizedSubject toSubject;

    private RealmList<Right> rights;

    public Realm getRealm() {
        return realm;
    }

    public void addSubject(CentralizedSubject subject) {
        realm.beginTransaction();
        realm.copyToRealm(subject);
        realm.commitTransaction();
    }

    public void addObject(CentralizedObject object, CentralizedSubject subject, Power power) {
        realm.beginTransaction();
        CentralizedObject mObject = realm.copyToRealm(object);
        mObject.setCreator(subject);
        Power mPower = realm.copyToRealm(power);
        mPower.setsName(subject.getName());
        mPower.setoName(object.getName());
        mPower.setGrantor(subject);
        RealmList<Right> rights = new RealmList<>();
        for (int i = 0; i < 7; i++) {
            Right right = new Right();
            switch (i) {
                case 0:
                    right.setName("c");
                    break;
                case 1:
                    right.setName("r");
                    break;
                case 2:
                    right.setName("w");
                    break;
                case 3:
                    right.setName("d");
                    break;
                case 4:
                    right.setName("a");
                    break;
                case 5:
                    right.setName("e");
                    break;
                case 6:
                    right.setName("o");
                    break;
            }
            realm.copyToRealm(right);
            rights.add(right);
        }
        realm.commitTransaction();
    }

    public RealmHelper grant(CentralizedSubject subject) {
        this.fromSubject = subject;
        return this;
    }

    public RealmHelper object(CentralizedObject object) {
        this.object = object;
        return this;
    }

    public RealmHelper rights(RealmList<Right> rights) {
        this.rights = rights;
        return this;
    }

    public void to(CentralizedSubject subject) {
        this.toSubject = subject;
        Power power = new Power();
        power.setsName(toSubject.getName());
        power.setoName(object.getName());
        power.setRights(rights);
        power.setGrantor(fromSubject);
        realm.beginTransaction();
        realm.copyToRealm(power);
        realm.commitTransaction();
    }



}
