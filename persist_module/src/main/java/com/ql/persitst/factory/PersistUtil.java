package com.ql.persitst.factory;



import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.collection.SimpleArrayMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.ql.persitst.callback.AsyReadListCallback;
import com.ql.persitst.callback.AsyReadObjectCallback;
import com.ql.persitst.callback.AsyWriteCallback;
import com.ql.persitst.config.PathConfig;
import com.ql.persitst.runnable.AsyReadListRunnable;
import com.ql.persitst.runnable.AsyReadObjectRunnable;
import com.ql.persitst.runnable.AsyWriteObjectRunnable;
import com.ql.persitst.runnable.AsyWriteStringRunnable;
import com.tencent.mmkv.BuildConfig;
import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVLogLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PersistUtil {
    private ExecutorService executorService;
    private MMKV mmkv;
    private String fileName;
    //保留Name与PersistUtil的映射
    private static SimpleArrayMap<String, PersistUtil> PERSIST_UTILS_MAP = new SimpleArrayMap<>();

    private PersistUtil(String fileName){
        mmkv=MMKV.mmkvWithID(fileName);
        this.fileName=fileName;
        executorService=  Executors.newSingleThreadExecutor();
    }

    //保存对象
    public void putObject(String name,Object value){
        mmkv.encode(name,toJsonString(value));
    }
    //异步保存字符串
    public  void  putAsyString(String name, String value, AsyWriteCallback asyWriteCallback){
        if (executorService !=null&&!executorService.isShutdown()){
            executorService.execute(new AsyWriteStringRunnable(name,value,fileName, asyWriteCallback));

        }
    }

    //异步保存字对象
    public  void  putAsyObject(String name, Object value, AsyWriteCallback asyWriteCallback){
        if (executorService !=null&&!executorService.isShutdown()){
            executorService.execute(new AsyWriteObjectRunnable(name,value,fileName, asyWriteCallback));
        }
    }

    //获取object
    public   <T>  void getAsyObject(String name, Class<T> clazz, AsyReadObjectCallback asyReadObjectCallback){
        if (executorService !=null&&!executorService.isShutdown()){
            executorService.execute(new AsyReadObjectRunnable(name,clazz,fileName, asyReadObjectCallback));
        }

    }

    //获取list
    public   <T>  void getAsyList(String name, Class<T> clazz, AsyReadListCallback asyReadListCallback){
        if (executorService !=null&&!executorService.isShutdown()){
            executorService.execute(new AsyReadListRunnable<>(name,clazz,fileName, asyReadListCallback));
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
    //读取int
    public int getInt(String name,int defaultValue){
        return mmkv.decodeInt(name, defaultValue);
    }

    public  void putFloat(String name,float value){
        mmkv.encode(name,value);
    }
    public float getFloat(String name,float defaultValue){
        return mmkv.decodeFloat(name, defaultValue);
    }
    public float getFloat(String name){
        return mmkv.decodeFloat(name, 0f);
    }

    public  void putLong(String name,long value){
        mmkv.encode(name,value);
    }
    public float getLong(String name,long defaultValue){
        return mmkv.decodeLong(name, defaultValue);
    }
    public long getLong(String name){
        return mmkv.decodeLong(name, 0L);
    }

    public  void putDouble(String name,double value){
        mmkv.encode(name,value);
    }
    public double getDouble(String name,double defaultValue){
        return mmkv.decodeDouble(name, defaultValue);
    }
    public double getDouble(String name){
        return mmkv.decodeDouble(name, 0D);
    }


    //读取boolean
    public boolean getBoolean(String name,boolean defaultValue){
       return mmkv.decodeBool(name, defaultValue);
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

    //使用默认文件名
    public  static PersistUtil getInstance(){
        return getInstance(null);
    }
    //使用自定义文件名
    public static PersistUtil getInstance(String fileName) {
        if (TextUtils.isEmpty(fileName)){
            fileName= PathConfig.DEFAULT_FILE_NAME;
        }
        PersistUtil persistUtil = PERSIST_UTILS_MAP.get(fileName);
        if (persistUtil == null) {
            persistUtil = new PersistUtil(fileName);
            PERSIST_UTILS_MAP.put(fileName, persistUtil);
        }
        return persistUtil;
    }

    public static void init(Application application){
        init(application,null);
    }

    /**
     *
     * @param application Application 一般为继承了Application的实例
     * @param pathName 不需要加"/"直接传个名字就行了 为/data/user/0/包名/files/下方的目录
     * 由于考虑到不同目标平台对于额外存储权限的不同，这里使用的是私有存储类似于SP
     */
    public static void init(Application application,String pathName){
        MMKVLogLevel logLevel = BuildConfig.DEBUG ? MMKVLogLevel.LevelDebug : MMKVLogLevel.LevelError;
        if (TextUtils.isEmpty(pathName)){
            MMKV.initialize(application.getFilesDir().getAbsolutePath()+"/"+PathConfig.DEFAULT_FILE_NAME,null,logLevel);
        }else {
            if (pathName.startsWith("/")){
                pathName= pathName.replaceFirst("/","");
            }
            MMKV.initialize(application.getFilesDir().getAbsolutePath()+"/"+pathName,null,logLevel);

        }
    }

}
