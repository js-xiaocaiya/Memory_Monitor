package com.example.servicedemo;

import android.app.Service;  
import android.content.Intent;  
import android.os.Binder;  
import android.os.IBinder;  
import android.text.format.Time;  
import android.util.Log;  

public class MyService extends Service {  
    //定义个一个Tag标签  
    private static final String TAG = "MyService";
    
    //service的生命周期
    @Override  
    public void onCreate() {  
        Log.e(TAG, "start onCreate~~~");  
        super.onCreate();  
    }  
      
    @Override  
    public void onStart(Intent intent, int startId) {  
    	String s1 = intent.getStringExtra("key");  //接受参数
        Log.e(TAG, "start onStart~~~" + s1);  
        super.onStart(intent, startId);   
    }  
      
    @Override  
    public void onDestroy() {  
        Log.e(TAG, "start onDestroy~~~");  
        super.onDestroy();  
    }  
      
    @Override  
    public IBinder onBind(Intent intent) {  
        Log.e(TAG, "start IBinder~~~");  
        return mBinder;       //返回了一个实现了 IBinder 接口的对象，这个对象将用于绑定Service 的 Activity 与 Local Service 通信
    } 
    
    @Override  
    public boolean onUnbind(Intent intent) {  
        Log.e(TAG, "start onUnbind~~~");  
        return super.onUnbind(intent);  
    }  
      
    //在service端提供公用方法供组件调用
    public String getSystemTime(){  
          
        Time t = new Time();  
        t.setToNow();  
        return t.toString();  
    }  
    
    //直接继承 Binder 而不是 IBinder,因为 Binder 实现了 IBinder 接口，这样我们可以少做很多工作。
    public class MyBinder extends Binder{  
    	public MyService getService()  
        {  
            return MyService.this;  //获取 Service 实例
        }  
    }  
  //声明类MyBinder的实例以供在onbind中返回
    private MyBinder mBinder = new MyBinder();  
    
     
}  
