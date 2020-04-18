package com.ql.persitst.callback;

public interface AsyReadObjectCallback<T> extends BaseAsyCallBack{
    void readObjectFinished(T objectValue);
}
