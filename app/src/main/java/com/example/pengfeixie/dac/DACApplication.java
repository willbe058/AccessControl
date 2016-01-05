package com.example.pengfeixie.dac;

import com.dd.realmbrowser.FuckBrowser;
import com.dd.realmbrowser.RealmBrowser;
import com.example.pengfeixie.dac.model.CentraRootSubject;
import com.example.pengfeixie.dac.model.CentralizedObject;
import com.example.pengfeixie.dac.model.CentralizedSubject;
import com.example.pengfeixie.dac.model.Power;
import com.example.pengfeixie.dac.model.Right;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import com.xpf.me.architect.app.CommonApplication;

/**
 * Created by pengfeixie on 16/1/4.
 */
public class DACApplication extends CommonApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

    }
}
