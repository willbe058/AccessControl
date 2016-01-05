package com.example.pengfeixie.dac.view;

import com.example.pengfeixie.dac.model.CentralizedSubject;
import com.xpf.me.architect.view.IView;

import java.util.List;

/**
 * Created by pengfeixie on 16/1/5.
 */
public interface SubjectView extends IView{

    void setData(List<CentralizedSubject> subjects);

    void setError(String error);
}
