package com.ql.persitst.callback;

import java.util.List;

public interface AsyReadListCallback<T> extends BaseAsyCallBack{
    void readListFinished(List<T> Value);
}
