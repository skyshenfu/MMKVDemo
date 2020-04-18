package com.ql.persitst.runnable;

import com.ql.persitst.callback.AsyReadStringCallback;
import com.ql.persitst.callback.AsyWriteCallback;
import com.ql.persitst.callback.BaseAsyCallBack;
import com.ql.persitst.factory.PersistUtil;

public class AsyReadStringRunnable extends BaseAsyRunnable {

    private String mResult;
    public AsyReadStringRunnable(String name,String fileName, AsyReadStringCallback mCallBack) {
        super(name, fileName,mCallBack);

    }

    @Override
    protected void detailRun() {
        mResult= PersistUtil.getInstance(fileName).getString(name);

    }

    @Override
    protected void callBackAction(BaseAsyCallBack mCallBack) {
        ((AsyReadStringCallback)mCallBack).readStringFinished(mResult);
    }
}
