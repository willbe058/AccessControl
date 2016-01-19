package com.example.pengfeixie.dac.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by pengfeixie on 16/1/19.
 */
public class BlackToken extends RealmObject {

    @PrimaryKey
    private String id;

    private CentralizedSubject owner;

    private CentralizedObject object;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CentralizedSubject getOwner() {
        return owner;
    }

    public void setOwner(CentralizedSubject owner) {
        this.owner = owner;
    }

    public CentralizedObject getObject() {
        return object;
    }

    public void setObject(CentralizedObject object) {
        this.object = object;
    }
}
