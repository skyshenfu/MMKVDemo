package com.zty.power.mkkvdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.ql.persitst.callback.AsyReadListCallback;
import com.ql.persitst.callback.AsyReadObjectCallback;
import com.ql.persitst.callback.AsyWriteCallback;
import com.ql.persitst.factory.PersistUtil;
import com.tencent.mmkv.MMKV;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PlatformFileBean platformFileBean1=new PlatformFileBean();
        platformFileBean1.setId("123");
        PlatformFileBean platformFileBean2=new PlatformFileBean();
        platformFileBean2.setId("456");
        List<PlatformFileBean> list=Arrays.asList(platformFileBean1,platformFileBean2);
        PersistUtil.getInstance().putAsyObject("ZTY", list, new AsyWriteCallback() {
            @Override
            public void writeFinished() {
                Log.e("TAG", Thread.currentThread().getName()+"写完成");
            }
        });
        Log.e("TAG", "中间点");

        PersistUtil.getInstance().getAsyList("ZTY", PlatformFileBean.class, new AsyReadListCallback<PlatformFileBean>() {
            @Override
            public void readListFinished(List<PlatformFileBean> Value) {
                Log.e("TAG", Thread.currentThread().getName()+"读完成");

            }
        });

    }
}
