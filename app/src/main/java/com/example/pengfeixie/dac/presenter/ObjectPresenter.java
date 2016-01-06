package com.example.pengfeixie.dac.presenter;

import com.example.pengfeixie.dac.model.Power;
import com.example.pengfeixie.dac.model.usecase.ObjectUsecase;
import com.example.pengfeixie.dac.view.ObjectView;
import com.xpf.me.architect.presenter.BasePresenter;

import java.util.List;

import rx.Subscriber;

/**
 * Created by pengfeixie on 16/1/6.
 */
public class ObjectPresenter extends BasePresenter<ObjectView> {

    private String name;

    public ObjectPresenter(String name) {
        this.name = name;
    }

    private ObjectUsecase usecase = new ObjectUsecase();

    public void loadData() {
        usecase.getData(name)
                .subscribe(new Subscriber<List<Power>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (getView() != null) {
                            getView().setError(e.getLocalizedMessage());

                        }
                    }

                    @Override
                    public void onNext(List<Power> powers) {
                        if (getView() != null) {
                            getView().setData(powers);
                        }
                    }
                });
    }
}
