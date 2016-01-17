package com.example.pengfeixie.dac.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pengfeixie.dac.model.Power;

import java.util.List;

import de.codecrafters.tableview.TableDataAdapter;

/**
 * Created by pengfeixie on 16/1/6.
 */
public class ObjectAdapter extends TableDataAdapter<Power> {

    private static final int TEXT_SIZE = 10;

    public ObjectAdapter(Context context, List<Power> data) {
        super(context, data);
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        Power power = getRowData(rowIndex);
        View renderView = null;
        switch (columnIndex) {
            case 0:
                renderView = renderString(power.getoName());
                break;
            case 1:
                if (power.getRights().get(0).isHas()) {
                    renderView = renderString("有");
                } else {
                    renderView = renderString("没有");
                }
                break;
            case 2:
                if (power.getRights().get(1).isHas()) {
                    renderView = renderString("有");
                } else {
                    renderView = renderString("没有");
                }
                break;
            case 3:
                if (power.getRights().get(2).isHas()) {
                    renderView = renderString("有");
                } else {
                    renderView = renderString("没有");
                }
                break;
            case 4:
                if (power.getRights().get(3).isHas()) {
                    renderView = renderString("有");

                } else {
                    renderView = renderString("没有");

                }
                break;
            case 5:
                if (power.getRights().get(4).isHas()) {
                    renderView = renderString("有");

                } else {
                    renderView = renderString("没有");

                }
                break;
            case 6:
                if (power.getRights().get(5).isHas()) {
                    renderView = renderString("有");
                } else {
                    renderView = renderString("没有");
                }
                break;
            case 7:
                if (power.getRights().get(6).isHas()) {
                    renderView = renderString("有");
                } else {
                    renderView = renderString("没有");
                }
                break;
            case 8:
                renderView = renderString(power.getGrantor().getName());
                break;

        }
        return renderView;
    }


    private View renderString(String value) {
        TextView textView = new TextView(getContext());
        textView.setText(value);
        textView.setPadding(20, 10, 20, 10);
        textView.setTextSize(TEXT_SIZE);
        return textView;
    }
}
