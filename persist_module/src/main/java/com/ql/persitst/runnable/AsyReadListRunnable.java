package com.ql.persitst.runnable;

import com.ql.persitst.callback.AsyReadListCallback;
import com.ql.persitst.callback.AsyReadObjectCallback;
import com.ql.persitst.callback.BaseAsyCallBack;
import com.ql.persitst.factory.PersistUtil;

import java.util.List;

public class AsyReadListRunnable<T> extends BaseAsyRunnable {

    private Class<?> clazz;
    private List<T> mResult;

    public <T> AsyReadListRunnable(String name, Class<T> clazz,String fileName, AsyReadListCallback asyReadListCallback) {
        super(name,fileName,asyReadListCallback);
        this.clazz=clazz;
    }
    @Override
    protected void detailRun() {
        mResult= (List<T>) PersistUtil.getInstance(fileName).getList(name,clazz);
    }

    @Override
    protected void callBackAction(BaseAsyCallBack mCallBack) {
        ((AsyReadListCallback)mCallBack).readListFinished(mResult);
    }
}
