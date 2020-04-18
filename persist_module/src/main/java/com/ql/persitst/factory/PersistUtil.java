package com.ql.persitst.factory;



import android.app.Application;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ql.persitst.callback.AsyReadListCallback;
import com.ql.persitst.callback.AsyReadObjectCallback;
import com.ql.persitst.callback.AsyWriteCallback;
import com.ql.persitst.runnable.AsyReadListRunnable;
import com.ql.persitst.runnable.AsyReadObjectRunnable;
import com.ql.persitst.runnable.AsyWriteObjectRunnable;
import com.ql.persitst.runnable.AsyWriteStringRunnable;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PersistUtil {
    private  MMKV mmkv;
    private ExecutorService executorService;
    private static class  PersistInstanceHolder{
        private   static PersistUtil INSTANCE=new PersistUtil();
    }
    private PersistUtil(){
        mmkv=MMKV.defaultMMKV();
        executorService=  Executors.newSingleThreadExecutor();
    }

    //保存对象
    public void putObject(String name,Object value){
        mmkv.encode(name,toJsonString(value));
    }
    //异步保存字符串
    public  void  putAsyString(String name, String value, AsyWriteCallback asyWriteCallback){
        if (executorService !=null&&!executorService.isShutdown()){
            executorService.execute(new AsyWriteStringRunnable(name,value, asyWriteCallback));

        }
    }

    //异步保存字对象
    public  void  putAsyObject(String name, Object value, AsyWriteCallback asyWriteCallback){
        if (executorService !=null&&!executorService.isShutdown()){
            executorService.execute(new AsyWriteObjectRunnable(name,value, asyWriteCallback));
        }
    }

    //获取object
    public   <T>  void getAsyObject(String name, Class<T> clazz, AsyReadObjectCallback asyReadObjectCallback){
        if (executorService !=null&&!executorService.isShutdown()){
            executorService.execute(new AsyReadObjectRunnable(name,clazz, asyReadObjectCallback));
        }

    }

    //获取list
    public   <T>  void getAsyList(String name, Class<T> clazz, AsyReadListCallback asyReadListCallback){
        if (executorService !=null&&!executorService.isShutdown()){
            executorService.execute(new AsyReadListRunnable<>(name,clazz, asyReadListCallback));
        }

    }

    //保存字符串
    public  void putString(String name,String value){
        mmkv.encode(name,value);
    }
    //保存boolean
    public  void putBoolean(String name,boolean value){
        mmkv.encode(name,value);
    }
    //保存int
    public  void putInt(String name,int value){
        //编码的时候
        mmkv.encode(name,value);
    }


    //读取boolean
    public boolean getBoolean(String name,boolean defaultValue){
       return mmkv.decodeBool(name, defaultValue);
    }
    //读取int
    public int getInt(String name,int defaultValue){
        return mmkv.decodeInt(name, defaultValue);
    }
    //读取String
    public String getString(String name,@NonNull String defaultValue){
        return mmkv.decodeString(name, defaultValue);
    }

    //读取boolean
    public boolean getBoolean(String name){
        return mmkv.decodeBool(name, false);
    }
    //读取int
    public int getInt(String name){
        return mmkv.decodeInt(name, 0);
    }
    //读取int
    public String getString(String name){
        return mmkv.decodeString(name, "");
    }


    //获取object
    public   <T> T getObject(String name,Class<T> clazz){
        return stringToBean(mmkv.decodeString(name),clazz);

    }

    //获取List
    public  <T>  List<T> getList(String name,Class<T> clazz){
        //最坏的情况也只是返回一个空的List 不会产生null
        return stringToList(mmkv.decodeString(name),clazz);
    }

    //字符串转javaBean
    private  <T> T stringToBean(String jsonResult, Class<T> clz) {
        Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(jsonResult, clz);
        } catch (Exception e) {
            e.printStackTrace();
            return t;
        }
        return t;
    }
    //字符串转javaList
    private  <T> List<T> stringToList(String jsonResult, Class<T> clz) {
        List<T> list = new ArrayList<>();
        try {
            JsonParser parser = new JsonParser();
            Gson gson = new Gson();
            JsonArray jsonarray = parser.parse(jsonResult).getAsJsonArray();
            for (JsonElement element : jsonarray) {
                list.add(gson.fromJson(element, clz));
            }
        }catch (Exception e){
            list.clear();
            return list;
        }
        return list;
    }
    //实现了序列化的的java对象或list转成String
    private  String toJsonString(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public  static PersistUtil getInstance(){
        return PersistUtil.PersistInstanceHolder.INSTANCE;
    }

    public static void init(Application application){
        MMKV.initialize(application);
    }

}
