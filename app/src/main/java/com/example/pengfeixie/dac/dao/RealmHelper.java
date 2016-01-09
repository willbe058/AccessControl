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

    private boolean[] has;

    public Realm getRealm() {
        return realm;
    }

    public void addSubject(CentralizedSubject subject) {
        realm.beginTransaction();
        realm.copyToRealm(subject);
        realm.commitTransaction();
    }

    public void addObject(CentralizedObject object, Power power) {
        realm.beginTransaction();
        realm.copyToRealm(object);
        Power mPower = realm.copyToRealm(power);
        mPower.setoName(object.getName());
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
            right.setHas(true);
//            realm.copyToRealmOrUpdate(right);
            mPower.getRights().add(right);
        }
        realm.copyToRealmOrUpdate(mPower);
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

    public RealmHelper rights(RealmList<Right> rights, boolean[] has) {
        this.rights = rights;
        this.has = has;
        return this;
    }

    public void to(CentralizedSubject subject) {
        this.toSubject = subject;
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
            right.setHas(has[i]);
        }
        Power power = new Power();
        power.setsName(toSubject.getName());
        power.setoName(object.getName());
        power.setRights(rights);
        power.setGrantor(fromSubject);
        realm.beginTransaction();
        realm.copyToRealm(power);
        realm.commitTransaction();
    }

    public CentralizedSubject getUser(String name) {
        return realm.where(CentralizedSubject.class).equalTo("name", name).findFirst();
    }

    public List<CentralizedSubject> getAllUser() {
        return realm.where(CentralizedSubject.class).findAll();
    }

    public List<CentralizedObject> getOwnedObjects(String subjectName) {
        return realm.where(CentralizedObject.class).equalTo("creator.name", subjectName).findAll();
    }

    public List<Power> getPowers(String subjectName) {
        return realm.where(Power.class).equalTo("sName", subjectName).findAll();
    }

    public CentralizedObject getObject(String objName) {
        return realm.where(CentralizedObject.class).equalTo("name", objName).findFirst();
    }

    public void deletePower(String id) {
        realm.beginTransaction();
        realm.where(Power.class).equalTo("id", id).findAll().remove(0);
        realm.commitTransaction();
    }

    public void deleteObject(String name) {
        realm.beginTransaction();
        realm.where(CentralizedObject.class).equalTo("name", name).findAll().remove(0);
        realm.commitTransaction();
    }

//    public List<CentralizedObject> getControledObjects(String name) {
//        this.getOwnedObjects(name)
//    }
}
