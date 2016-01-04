package com.example.pengfeixie.dac.dao;

import com.xpf.me.architect.app.AppData;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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

    public Realm getRealm() {
        return realm;
    }


}
