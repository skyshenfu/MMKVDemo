package com.ql.persitst.runnable;

import com.ql.persitst.callback.AsyReadObjectCallback;
import com.ql.persitst.callback.AsyReadStringCallback;
import com.ql.persitst.callback.BaseAsyCallBack;
import com.ql.persitst.factory.PersistUtil;

import java.util.List;

public class AsyReadObjectRunnable<T> extends BaseAsyRunnable {

    private Class<?> clazz;
    private T mResult;

    public <T> AsyReadObjectRunnable(String name, Class<T> clazz, AsyReadObjectCallback asyReadObjectCallback) {
        super(name,asyReadObjectCallback);
        this.clazz=clazz;
    }

    @Override
    protected void detailRun() {
        mResult= (T) PersistUtil.getInstance().getObject(name,clazz);
    }

    @Override
    protected void callBackAction(BaseAsyCallBack mCallBack) {
        ((AsyReadObjectCallback)mCallBack).readObjectFinished(mResult);
    }
}
