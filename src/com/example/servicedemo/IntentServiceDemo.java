package com.example.servicedemo;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.view.View.OnClickListener;

//IntentService继承自Service
public class IntentServiceDemo extends IntentService {
	private static final String TAG = "IntentServiceDemo";  
    private static final SimpleDateFormat SDF_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss.SSS");  
  
    //继承IntentService时，必须提供一个无参构造函数，且在该构造函数内，需要调用父类的构造函数
    public IntentServiceDemo() {  
        super(TAG);  
        Log.e(TAG, " ----> constructor");  
    }  
  
    @Override  
    public void onCreate() {  
        super.onCreate();  
  
        // 打印出该Service所在线程的ID  
        long id = Thread.currentThread().getId();  
        Log.e(TAG, " ----> service端:onCreate() in thread id: "+ id);  //主线程(ui)的id
    }  
  
    @Override  
    public void onDestroy() {  
        super.onDestroy();  
        Log.e(TAG, " ----> onDestroy()");                         //在所有的请求(Intent)都被执行完以后会自动停止服务
    }  
  
    @Override  
    public int onStartCommand(Intent intent, int flags, int startId) {  
    	Log.e(TAG, " ----> onStartCommand()");  
    	Log.e("flags", "flags" + flags);
    	Log.e("startId", "startId" + startId);
        // 记录发送此请求的时间  
        intent.putExtra("time", System.currentTimeMillis());  //生成一个默认的且与主线程互相独立的工作者线程来执行所有传送至 onStartCommand() 方法的Intetnt
        return super.onStartCommand(intent, flags, startId);  //提供了一个onStartCommand()方法的默认实现，它将Intent先传送至工作队列，
    }                                                         //然后从工作队列中每次取出一个传送至onHandleIntent()方法，同一时刻只传送一个Intent对象,在该方法中对Intent对相应的处理
  
    @Override  
    public void setIntentRedelivery(boolean enabled) {  
    	Log.e(TAG, " ----> setIntentRedelivery()");  
        super.setIntentRedelivery(enabled);  
    }  
  
    @Override  
    protected void onHandleIntent(Intent intent) {  
        // 打印出处理intent所用的线程的ID  
        long id = Thread.currentThread().getId();  
        Log.e(TAG," ----> onHandleIntent() in thread id: " + id);  
        long time = intent.getLongExtra("time", 0);  
        Date date = new Date(time);  
        try {  
            // 打印出每个请求对应的触发时间  
        	Log.e(TAG," ----> onHandleIntent(): 下载文件中..." + SDF_DATE_FORMAT.format(date));  
            Thread.sleep(3000);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
    }  
  
}
