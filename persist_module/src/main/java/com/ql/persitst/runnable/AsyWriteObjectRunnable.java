package com.ql.persitst.runnable;

import com.ql.persitst.callback.AsyWriteCallback;
import com.ql.persitst.callback.BaseAsyCallBack;
import com.ql.persitst.factory.PersistUtil;

public class AsyWriteObjectRunnable extends BaseAsyRunnable {
    private Object value;

    public AsyWriteObjectRunnable(String name, Object value, String fileName,AsyWriteCallback mCallBack) {
        super(name,fileName, mCallBack);
        this.value=value;
    }

    @Override
    protected void detailRun() {
        PersistUtil.getInstance(fileName).putObject(name,value);

    }

    @Override
    protected void callBackAction(BaseAsyCallBack mCallBack) {
        ((AsyWriteCallback)mCallBack).writeFinished();
    }
}
