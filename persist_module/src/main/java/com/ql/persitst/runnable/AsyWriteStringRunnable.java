package com.ql.persitst.runnable;

import com.ql.persitst.callback.AsyWriteCallback;
import com.ql.persitst.callback.BaseAsyCallBack;
import com.ql.persitst.factory.PersistUtil;

public class AsyWriteStringRunnable extends BaseAsyRunnable {
    private String value;

    public AsyWriteStringRunnable(String name, String value,String fileName,AsyWriteCallback mCallBack) {
        super(name,fileName, mCallBack);
        this.value=value;
    }

    @Override
    protected void detailRun() {
        PersistUtil.getInstance(fileName).putString(name,value);

    }

    @Override
    protected void callBackAction(BaseAsyCallBack mCallBack) {
        ((AsyWriteCallback)mCallBack).writeFinished();
    }
}
