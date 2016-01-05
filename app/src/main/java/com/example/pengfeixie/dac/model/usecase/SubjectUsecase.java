package com.example.pengfeixie.dac.model.usecase;

import com.example.pengfeixie.dac.dao.RealmHelper;
import com.example.pengfeixie.dac.model.CentralizedSubject;
import com.xpf.me.architect.model.IModel;

import java.util.List;

import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by pengfeixie on 16/1/5.
 */
public class SubjectUsecase implements IModel<List<CentralizedSubject>> {
    @Override
    public Observable<List<CentralizedSubject>> execute() {

        return Observable.create(new Observable.OnSubscribe<List<CentralizedSubject>>() {
            @Override
            public void call(Subscriber<? super List<CentralizedSubject>> subscriber) {
                List<CentralizedSubject> results = RealmHelper.getInstance().getAllUser();
                subscriber.onNext(results);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
