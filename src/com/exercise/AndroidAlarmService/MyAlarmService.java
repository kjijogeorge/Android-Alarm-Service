package com.exercise.AndroidAlarmService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyAlarmService extends Service {

	final File logFile = new File(Environment.getExternalStorageDirectory(),"Power.log");
    
	@Override
    public void onCreate() {
	Toast.makeText(this, "MyAlarmService.onCreate()", Toast.LENGTH_LONG).show();
	
    }
	
	@Override
	public IBinder onBind(Intent intent) {
	 // TODO Auto-generated method stub
	 Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG).show();
	 return null;
	}
	
	@Override
	public void onDestroy() {
	 super.onDestroy();
	 Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		 super.onStart(intent, startId);
		 //Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_LONG).show();
		 
		 BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
			
			 int scale = -1;
			 int level = -1;
			 int voltage = -1;
			 int temp = -1;
			 @Override
			 public void onReceive(Context context, Intent intent) {
				 level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
				 scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
				 temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
				 voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
				 String sendData = ( new Date().toString() + ": Battery level is " + level + "/" + scale + ", temp is " + temp + ", voltage is " + voltage);
				 Log.e("BatteryManager", "level is " + level + "/" + scale + ", temp is " + temp + ", voltage is " + voltage);
	
				 BufferedWriter writer;
				 try {
					 writer = new BufferedWriter(new FileWriter(logFile, true));
					 writer.append(sendData);
					 writer.newLine();
					 writer.close();
				 } catch (IOException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
				 }
			 }
		 };
		 IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		 registerReceiver(batteryReceiver, filter);

	}
	
	@Override
	public boolean onUnbind(Intent intent) {
	 // TODO Auto-generated method stub
	 Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();
	 return super.onUnbind(intent);
	}
	
}