package com.example.pengfeixie.dac.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by pengfeixie on 16/1/4.
 */
public class CentralizedObject extends RealmObject {

    @PrimaryKey
    private String name;

    private String info;

    private Grantor creator;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Grantor getCreator() {
        return creator;
    }

    public void setCreator(Grantor creator) {
        this.creator = creator;
    }
}
