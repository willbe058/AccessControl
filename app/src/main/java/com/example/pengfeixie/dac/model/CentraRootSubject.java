package com.example.pengfeixie.dac.model;

import com.example.pengfeixie.dac.dao.RealmHelper;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by pengfeixie on 16/1/4.
 */
public class CentraRootSubject extends RealmObject {

    @PrimaryKey
    private String name;

    private String passwd;

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
