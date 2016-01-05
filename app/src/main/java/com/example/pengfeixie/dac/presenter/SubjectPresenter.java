package com.example.pengfeixie.dac.presenter;

import com.example.pengfeixie.dac.model.CentralizedSubject;
import com.example.pengfeixie.dac.model.usecase.SubjectUsecase;
import com.example.pengfeixie.dac.view.SubjectView;
import com.xpf.me.architect.presenter.BasePresenter;

import java.util.List;

import rx.Subscriber;

/**
 * Created by pengfeixie on 16/1/5.
 */
public class SubjectPresenter extends BasePresenter<SubjectView> {

    private SubjectUsecase usecase = new SubjectUsecase();

    public void loadData() {
        usecase.execute()
                .subscribe(new Subscriber<List<CentralizedSubject>>() {
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
                    public void onNext(List<CentralizedSubject> centralizedSubjects) {
                        if (getView() != null) {
                            getView().setData(centralizedSubjects);
                        }
                    }
                });
    }
}
