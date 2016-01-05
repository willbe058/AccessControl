package com.example.pengfeixie.dac;

import com.example.pengfeixie.dac.dao.RealmHelper;
import com.example.pengfeixie.dac.model.CentralizedObject;
import com.example.pengfeixie.dac.model.CentralizedSubject;
import com.example.pengfeixie.dac.model.Right;

import io.realm.RealmList;

/**
 * Created by pengfeixie on 16/1/4.
 */
public class test {

    public static final void main(String[] args) {

        CentralizedSubject subject1 = new CentralizedSubject();
        subject1.setName("xpf");
        subject1.setPasswd("123");
        subject1.setInfo("");
        RealmHelper.getInstance().addSubject(subject1);

    }
}
