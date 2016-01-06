package com.example.pengfeixie.dac.view;

import com.example.pengfeixie.dac.model.CentralizedObject;
import com.example.pengfeixie.dac.model.Power;
import com.xpf.me.architect.view.IView;

import java.util.List;

/**
 * Created by pengfeixie on 16/1/6.
 */
public interface ObjectView extends IView {

    void setData(List<Power> objects);

    void setError(String error);
}
