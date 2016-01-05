package com.example.pengfeixie.dac.event;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by pengfeixie on 16/1/5.
 */
public class BusProvider {

    private static final Bus instance = new Bus(ThreadEnforcer.MAIN);

    public static Bus getInstance() {
        return instance;
    }
}