package com.zty.power;

import android.app.Application;

import com.ql.persitst.factory.PersistUtil;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PersistUtil.init(this,"hero");
    }
}
