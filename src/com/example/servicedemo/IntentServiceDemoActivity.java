//package com.example.servicedemo;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//
//public class IntentServiceDemoActivity extends Activity implements OnClickListener{
//	private Button startSer1;  
//    private Button stopSer1;  
//    private Button startSer2;  
//    private Button stopSer2;  
//    private String LOG = "intentServiceDemoActivity"; 
//	@Override  
//    public void onCreate(Bundle savedInstanceState) {  
//        super.onCreate(savedInstanceState);  
//        setContentView(R.layout.intentservice_activity);  
//  
//          
//  
////        log = (TextView) findViewById(R.id.log);  
////  
////        logView = (ScrollView) findViewById(R.id.logView);  
//        setView();
//        
//    }  
//	
//	public void setView() {
//		startSer1 = (Button) findViewById(R.id.startSer1);  
//        stopSer1 = (Button) findViewById(R.id.stopSer1);  
//  
//        startSer2 = (Button) findViewById(R.id.startSer2);  
//        stopSer2 = (Button) findViewById(R.id.stopSer2);
//
//        
//        startSer1.setOnClickListener(this);  
//        stopSer1.setOnClickListener(this);  
//  
//        startSer2.setOnClickListener(this);  
//        stopSer2.setOnClickListener(this);  
//  
//
//        
//        // 打印出主线程的ID  
//        long id = Thread.currentThread().getId();  
//        Log.e("theadId", " ----> onCreate() in thread id: " + id);  
//	}
//	
////    public void onClick2(View v) {
////    	if(v == startSer1) {
////    		Intent intent = new Intent(IntentServiceDemoActivity.this, IntentServiceDemo.class);  
////            startService(intent);
////    	}else if(v == stopSer1) {
////    		Intent intent = new Intent(IntentServiceDemoActivity.this, IntentServiceDemo.class);  
////            stopService(intent);
////    	}else if(v == startSer2) {
////    		
////    	}else{
////    		
////    	}
////    }	
//	@Override
//    public void onStart() {
//    	super.onStart();
//    	Log.e(LOG,"onStart");
//    }
//	@Override
//    public void onResume() {
//    	super.onResume();
//    	Log.e(LOG,"onResume");
//    }
//    
//    @Override
//    public void onPause() {
//    	super.onPause();
//    	Log.e(LOG,"onPause");
//    }
//    
//    @Override
//    public void onStop() {
//    	super.onStop();
//    	Log.e(LOG,"onStop");
//    }
//    
//    @Override
//    public void onDestroy() {
//    	super.onDestroy();
//    	Log.e(LOG,"onDestroy");
//    }
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		if(v == startSer1) {
//			Log.e("startTest","onclik test");
//			Intent i = new Intent();
//			i.setClass(IntentServiceDemoActivity.this, IntentServiceDemo.class);  //运用.calss的方式获取Class实例
//            i.setData(Uri.parse("http://commonsware.com/Android/excerpt.pdf"));
//			IntentServiceDemoActivity.this.startService(i);   
//    	}else if(v == stopSer1) {
//    		Log.e("stopTest","onclik test");
//			Intent i = new Intent();
//			i.setClass(IntentServiceDemoActivity.this, IntentServiceDemo.class);  //运用.calss的方式获取Class实例
//            
//			IntentServiceDemoActivity.this.stopService(i);
//    	}else if(v == startSer2) {
//    		
//    	}else{
//    		
//    	}
//	}
//	private Handle handle = new Handler() {
//		public void handleMessage(message msg) {
//			super.handleMessage(msg);
//			
//		}
//	}
//}
