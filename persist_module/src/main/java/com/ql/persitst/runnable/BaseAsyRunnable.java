package com.ql.persitst.runnable;

import android.os.Handler;
import android.os.Looper;

import com.ql.persitst.callback.BaseAsyCallBack;

public abstract class BaseAsyRunnable implements  Runnable{
    protected  String name;
    protected BaseAsyCallBack mCallBack;

    public BaseAsyRunnable(String name, BaseAsyCallBack mCallBack) {
        this.name = name;
        this.mCallBack = mCallBack;
    }

    @Override
    public void run() {
        detailRun();
        //调用完成后进行线程切换
        if (Looper.myLooper() != Looper.getMainLooper()) {
            // If we finish marking off of the main thread, we need to
            // actually do it on the main thread to ensure correct ordering.
            Handler mainThread = new Handler(Looper.getMainLooper());
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    callBackAction(mCallBack);
                }
            });
            return;
        }

    }
   protected abstract void detailRun();
    protected abstract void callBackAction(BaseAsyCallBack mCallBack);

}
