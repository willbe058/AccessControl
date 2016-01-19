package com.example.pengfeixie.dac.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by pengfeixie on 16/1/4.
 */
public class CentralizedSubject extends RealmObject implements Grantor {

    @PrimaryKey
    private String name;

    private String passwd;

    private String info;

    private RealmList<BlackToken> blackTokens;

    public RealmList<BlackToken> getBlackTokens() {
        return blackTokens;
    }

    public void setBlackTokens(RealmList<BlackToken> blackTokens) {
        this.blackTokens = blackTokens;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
