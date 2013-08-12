package com.example.servicedemo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;

public class TestActivity extends ListActivity implements OnClickListener{
	
	private Button testButton;
	private EditText TextView;
	private RadioButton cpu;
	private RadioButton memory;
	private RadioButton useRate;
	private RadioButton other;
	private RadioGroup navGroup;
	private static final String TAG = "navButton";
	private Vibrator vibrator;
	final long[] pattern = {0, 10}; //等待震动开始时间，保持震动的时间
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE); 
		testButton = (Button)findViewById(R.id.testButton);
		navGroup = (RadioGroup)findViewById(R.id.navGroup);
		cpu = (RadioButton)findViewById(R.id.cpu);
		memory = (RadioButton)findViewById(R.id.memory);
		useRate = (RadioButton)findViewById(R.id.useRate);
		other = (RadioButton)findViewById(R.id.other);	
		TextView = (EditText)findViewById(R.id.editContent);		
		testButton.setOnClickListener(this);
		  
		
		navGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {		
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == cpu.getId()){
					Log.v(TAG,"cpu");
					vibrator.vibrate(pattern, -1);
				}else if(checkedId == memory.getId()) {	
					vibrator.vibrate(pattern, -1);
					showMemoryList();					
					
				}else if(checkedId == useRate.getId()) {
					Log.v(TAG, "useRate");
					vibrator.vibrate(pattern, -1);
				}else if(checkedId == other.getId()) {
					Log.v(TAG, "other");
					vibrator.vibrate(pattern, -1);
				}else{
					return;
				}
			}		
		});	
		
	}
	
	public void onClick(View v) {
		Log.v("TEXT", TextView.getText().toString());
	}
	
	//return all running app
	public ArrayList<String> getAllRunningApp() {
		ArrayList<String> apps = new ArrayList<String>();
		ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
		
		for (RunningAppProcessInfo runningAppProcessInfo : hgf)
		{
		    apps.add(runningAppProcessInfo.processName);
		}
		return apps;
	}
	
	public ArrayList<String> getAllAppMemory() {
		ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
		MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
		activityManager.getMemoryInfo(memoryInfo);
		
		ArrayList<String> app_memory = new ArrayList<String>();
		
		List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();

		Map<Integer, String> pidMap = new TreeMap<Integer, String>();
		
		for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses)
		{
		    pidMap.put(runningAppProcessInfo.pid, runningAppProcessInfo.processName);
		}
		
		//Returns a set of the keys contained in this map
		Collection<Integer> keys = pidMap.keySet();
		for(int key : keys)
		{
		    int[] pids = new int[1];   //声明整型数组pids,分配1个int长度,new int(1)是初始化为1
		    pids[0] = key;
		    Log.v("key", "key"+pids[0]);

		    //Return information about the memory usage of one or more processes
		    android.os.Debug.MemoryInfo[] memoryInfoArray = activityManager.getProcessMemoryInfo(pids);
		    
		    for(android.os.Debug.MemoryInfo pidMemoryInfo: memoryInfoArray)
		    {
//		        Log.i(TAG, String.format("** MEMINFO in pid %d [%s] **\n",pids[0],pidMap.get(pids[0])));
//		        Log.i(TAG, " pidMemoryInfo.getTotalPrivateDirty(): " + pidMemoryInfo.getTotalPrivateDirty() + "\n"); //USS 进程独自占用的物理内存（不包含共享库占用的内存）
//		        Log.i(TAG, " pidMemoryInfo.getTotalPss(): " + pidMemoryInfo.getTotalPss() + "\n");  //PSS实际使用的物理内存（比例分配共享库占用的内存）
//		        Log.i(TAG, " pidMemoryInfo.getTotalSharedDirty(): " + pidMemoryInfo.getTotalSharedDirty() + "\n");  //RSS实际使用物理内存（包含共享库占用的内存）
		        app_memory.add("" + pidMap.get(pids[0]));
		        app_memory.add("USS" + pidMemoryInfo.getTotalPrivateDirty());
		        app_memory.add("PSS" + pidMemoryInfo.getTotalPss());
		        app_memory.add("RSS" + pidMemoryInfo.getTotalSharedDirty());
		    }
		}
		return app_memory;
	}
	
	public void showMemoryList() {
		ArrayList<String> app_info = getAllAppMemory();
		ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>(); 										
		for(int i=0; i<app_info.size(); ++i)
		{
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("appName", app_info.get(i));
			map.put("USS", app_info.get(i+1));
			map.put("PSS",app_info.get(i+2));
			map.put("RSS",app_info.get(i+3));
			i = i+3;
			list.add(map);
		}
		SimpleAdapter listAdapter = new SimpleAdapter(TestActivity.this, list, R.layout.app_list, new String[]{"appName","USS","PSS","RSS"}, new int[]{R.id.app_name,R.id.USS,R.id.PSS,R.id.RSS});
		setListAdapter(listAdapter);
	}
	//长按菜单响应的函数
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}
}
