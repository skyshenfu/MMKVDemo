package com.zty.power;

import android.app.Application;

import com.tencent.mmkv.MMKV;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MMKV.initialize(this);
    }
}
