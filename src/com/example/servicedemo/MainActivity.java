package com.example.servicedemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{  
     
    private MyService  mMyService;  
    private TextView mTextView;  
    private Button startServiceButton;  
    private Button stopServiceButton;  
    private Button bindServiceButton;  
    private Button unbindServiceButton;  
    private Button startAnotherActivity;
    private Context mContext;  
    private Button btnInternal1,btnInternal2,btnSystem;
    
    
  //自定义发送的广播内容
    static final String INTENAL_ACTION_1 = "com.example.servicedemo.Internal_1";  
    static final String INTENAL_ACTION_2 = "com.example.servicedemo.Internal_2";  
    static final String INTENAL_ACTION_3 = "com.example.servicedemo.Internal_3"; 
    static final String LOG = "mainActivity"; 
      
    //这里需要用到ServiceConnection在Context.bindService和context.unBindService()里用到  
    private ServiceConnection mServiceConnection = new ServiceConnection() { 
    	
        //当我bindService时，让TextView显示MyService里getSystemTime()方法的返回值   
        public void onServiceConnected(ComponentName name, IBinder service) {  
            // TODO Auto-generated method stub  
            mMyService = ((MyService.MyBinder)service).getService();  
            mTextView.setText("I am frome Service :" + mMyService.getSystemTime());  
        }  
          
        public void onServiceDisconnected(ComponentName name) {  
            // TODO Auto-generated method stub  
              
        }  
    };  
    
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        Log.e(LOG,"onCreate");
        setContentView(R.layout.main);    
        setupViews();  
        
        
    
    }  
    @Override
    public void onStart() {
    	super.onStart();
    	Log.e(LOG,"onStart");
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	Log.e(LOG,"onResume");
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	Log.e(LOG,"onPause");
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    	Log.e(LOG,"onStop");
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	Log.e(LOG,"onDestroy");
    }
    //sdCard大小 
//    public long[] getSDCardMemory() {  
//        long[] sdCardInfo=new long[2];  
//        String state = Environment.getExternalStorageState();  
//        if (Environment.MEDIA_MOUNTED.equals(state)) {  
//            File sdcardDir = Environment.getExternalStorageDirectory();  
//            StatFs sf = new StatFs(sdcardDir.getPath());  
//            long bSize = sf.getBlockSize();  
//            long bCount = sf.getBlockCount();  
//            long availBlocks = sf.getAvailableBlocks();  
//  
//            sdCardInfo[0] = bSize * bCount;//总大小  
//            sdCardInfo[1] = bSize * availBlocks;//可用大小  
//        }  
//        return sdCardInfo;  
//    }  
      
    
    
    public void setupViews(){  
      
        mContext = MainActivity.this;   //代表当前activity
        mTextView = (TextView)findViewById(R.id.text);  
          
          
        
        
        //通过id找到button  
        startServiceButton = (Button)findViewById(R.id.startservice);  
        stopServiceButton = (Button)findViewById(R.id.stopservice);  
        bindServiceButton = (Button)findViewById(R.id.bindservice);  
        unbindServiceButton = (Button)findViewById(R.id.unbindservice);  
        startAnotherActivity = (Button)findViewById(R.id.startAnotherActivity); 
        
        //广播button
        btnInternal1 = (Button)findViewById(R.id.Button01);
        btnInternal2 = (Button)findViewById(R.id.Button02);
        btnSystem = (Button)findViewById(R.id.Button03);
        
        //设置事件监听
        startServiceButton.setOnClickListener(this);  
        stopServiceButton.setOnClickListener(this);  
        bindServiceButton.setOnClickListener(this);  
        unbindServiceButton.setOnClickListener(this); 
        startAnotherActivity.setOnClickListener(this);
        
        btnInternal1.setOnClickListener(this);
        btnInternal2.setOnClickListener(this);
        btnSystem.setOnClickListener(this);
        
        
      //实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter();
        
      //动态注册广播消息  
        registerReceiver(bcrIntenal1, intentFilter); 
        
      //生成广播处理  
        //clsReceiver2 = new clsReceiver2();  
        //实例化过滤器并设置要过滤的广播  
//        IntentFilter smsintentFilter = new IntentFilter();  
//        smsintentFilter.addAction(SMS_ACTION);  
//          
//        //注册广播  
//        registerReceiver(smsBroadCastReceiver, intentFilter);
        
    }  
    
    //service与activity通信
    public void onClick(View v) {  
        // TODO Auto-generated method stub  
        if(v == startServiceButton){  
            Intent i  = new Intent();  
            i.setClass(MainActivity.this, MyService.class);  //运用.calss的方式获取Class实例
            i.putExtra("key", "diliver params");
            mContext.startService(i);   //实现跳转
        }else if(v == stopServiceButton){  
            Intent i  = new Intent();  
            i.setClass(MainActivity.this, MyService.class);  
            mContext.stopService(i);  
        }else if(v == bindServiceButton){  
            Intent i  = new Intent();  
            i.setClass(MainActivity.this, MyService.class);   
            mContext.bindService(i, mServiceConnection, BIND_AUTO_CREATE);  //mServiceConnection对象，作为回调函数， Context.BIND_AUTO_CREATE表示的是自动绑定
        }else if(v == unbindServiceButton){  
            mContext.unbindService(mServiceConnection);  
        }else if(v == startAnotherActivity) {
        	Intent i = new Intent("android.intent.action.IntentServiceActivity");
        	//i.setAction("com.example.servicedemo.IntentServiceDemoActivity");
        	startActivity(i);       //根据action name来启动另外一个activitys
        }else if(v == btnInternal1){                        //给动态注册的BroadcastReceiver发送数据
        	Intent intent = new Intent(INTENAL_ACTION_1);
        	sendBroadcast(intent);                         //发送广播
        }else if(v == btnInternal2){                        //给静态注册的BroadcastReceiver发送数据
        	Intent intent = new Intent(INTENAL_ACTION_2);
        	sendBroadcast(intent);
        }else if(v == btnSystem){                           //动态注册 接收2组信息的BroadcastReceiver
        	IntentFilter filter = new IntentFilter();
        	filter.addAction(Intent.ACTION_BATTERY_CHANGED); //系统电量检测信息
        	filter.addAction(INTENAL_ACTION_3);//第三组自定义消息  
            registerReceiver(batInfoReceiver, filter);  //注册广播 
              
            Intent intent = new Intent(INTENAL_ACTION_3);  
            intent.putExtra("Name", "hellogv");  
            intent.putExtra("Blog", "http://blog.csdn.net/hellogv");  
            sendBroadcast(intent);       //传递过去 
        }  
    }  
      
    /* 
     * 接收动态注册广播的BroadcastReceiver(广播接收器) 
     */  
    private BroadcastReceiver bcrIntenal1 = new BroadcastReceiver() {  
          
        public void onReceive(Context context, Intent intent) {  
            String action = intent.getAction();  
            Toast.makeText(context, "动态:"+action, 1000).show();  
        }  
    };  
    
    private BroadcastReceiver batInfoReceiver = new BroadcastReceiver() {  
        
        public void onReceive(Context context, Intent intent) {  
            String action = intent.getAction();  
            //如果捕捉到的action是ACTION_BATTERY_CHANGED  
            if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {  
                //当未知Intent包含的内容，则需要通过以下方法来列举  
                Bundle b=intent.getExtras();  
                Object[] lstName=b.keySet().toArray();  
  
                for(int i=0;i<lstName.length;i++)  
                {  
                    String keyName=lstName[i].toString();  
                    Log.e(keyName,String.valueOf(b.get(keyName)));  
                }  
            }  
            //如果捕捉到的action是INTENAL_ACTION_3  
            if (INTENAL_ACTION_3.equals(action)) {  
                //当未知Intent包含的内容，则需要通过以下方法来列举  
                Bundle b=intent.getExtras();  
                Object[] lstName=b.keySet().toArray();  
  
                for(int i=0;i<lstName.length;i++)  
                {  
                    String keyName=lstName[i].toString();  
                    Log.e(keyName,b.getString(keyName));  
                }  
            }  
        }  
    };  
      
}  