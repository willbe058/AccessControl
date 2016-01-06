package com.example.pengfeixie.dac.model.usecase;

import com.example.pengfeixie.dac.dao.RealmHelper;
import com.example.pengfeixie.dac.model.Power;
import com.xpf.me.architect.model.IModel;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by pengfeixie on 16/1/6.
 */
public class ObjectUsecase implements IModel<List<Power>> {
    @Override
    public Observable<List<Power>> execute() {
        return null;
    }

    public Observable<List<Power>> getData(final String name) {
        return Observable.create(new Observable.OnSubscribe<List<Power>>() {
            @Override
            public void call(Subscriber<? super List<Power>> subscriber) {
                List<Power> powers = RealmHelper.getInstance().getPowers(name);
                subscriber.onNext(powers);
                subscriber.onCompleted();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread());
    }
}
