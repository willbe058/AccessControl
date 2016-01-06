package com.example.pengfeixie.dac.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by pengfeixie on 16/1/4.
 */
public class Power extends RealmObject {

    @PrimaryKey
    private String id;

    private String sName;

    private String oName;

    private RealmList<Right> rights;

    private CentralizedSubject grantor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getoName() {
        return oName;
    }

    public void setoName(String oName) {
        this.oName = oName;
    }

    public RealmList<Right> getRights() {
        return rights;
    }

    public void setRights(RealmList<Right> rights) {
        this.rights = rights;
    }

    public CentralizedSubject getGrantor() {
        return grantor;
    }

    public void setGrantor(CentralizedSubject grantor) {
        this.grantor = grantor;
    }
}
