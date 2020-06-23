package main.smart.activity;

import java.util.ArrayList;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import main.SharedPreferencesHelper;
import main.smart.bus.view.CustomerSpinner;
import main.smart.common.util.PreferencesHelper;
import main.smart.rcgj.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class SettingActivity extends Activity{

	private CustomerSpinner VideoType;
	private CustomerSpinner shockType;
	private CustomerSpinner reminderType;
	private CustomerSpinner busrefreshset;
	private PreferencesHelper mPreferenceMan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		this.mPreferenceMan = PreferencesHelper.getInstance();
		VideoType=(CustomerSpinner) findViewById(R.id.VideoType);
		shockType=(CustomerSpinner) findViewById(R.id.shockType);
		reminderType=(CustomerSpinner) findViewById(R.id.reminderType);
		busrefreshset=(CustomerSpinner) findViewById(R.id.busrefreshset);
		ArrayList<String> list1=new ArrayList();
		ArrayList<String> list2=new ArrayList();
		ArrayList<String> list3=new ArrayList();
		ArrayList<String> list4=new ArrayList();
		list1.add(getResources().getString(R.string.open));
		list1.add(getResources().getString(R.string.close));
		list2.add(getResources().getString(R.string.first_station));
		list2.add(getResources().getString(R.string.end_station));
		list3.add(getResources().getString(R.string.zhongdeng));
		list3.add(getResources().getString(R.string.kuai));
		list3.add(getResources().getString(R.string.man));
		list4.add(getResources().getString(R.string.open));
		list4.add(getResources().getString(R.string.close));
		ArrayAdapter adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1);
		shockType.setAdapter(adapter1);
		shockType.setList(list1);
		ArrayAdapter adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list2);
		reminderType.setAdapter(adapter2);
		reminderType.setList(list2);
		ArrayAdapter adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list3);
		busrefreshset.setAdapter(adapter3);
		busrefreshset.setList(list3);
		ArrayAdapter adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list4);
		VideoType.setAdapter(adapter4);
		VideoType.setList(list4);
		shockType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(mPreferenceMan.getShock()!=arg2){
					mPreferenceMan.updateShock(arg2);
					show();
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});


		reminderType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(mPreferenceMan.getReminder()!=arg2){
					mPreferenceMan.updateReminder(arg2);
					show();
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		busrefreshset.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (mPreferenceMan.getBusrefresh()!=arg2){
					mPreferenceMan.updatebusrefreshset(arg2);
					new SharedPreferencesHelper(SettingActivity.this,"login")
							.saveData(SettingActivity.this,"time",list3.get(arg2));
					show();
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});


		VideoType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(mPreferenceMan.getVideoType()!=arg2){
					mPreferenceMan.updateVideoType(arg2);
					show();
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		//读取当前选项
		shockType.setSelection(mPreferenceMan.getShock());
		VideoType.setSelection(mPreferenceMan.getVideoType());
		reminderType.setSelection(mPreferenceMan.getReminder());
		busrefreshset.setSelection(mPreferenceMan.getBusrefresh());
	}


	public void show(){
		Toast.makeText(SettingActivity.this, getResources().getString(R.string.save_success), Toast.LENGTH_LONG).show();
	}

	public void back(View paramView){
		onBackPressed();
	}



}
