package main.smart.common.activity;

import main.smart.rcgj.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class SettingActivity extends Activity {

	public SettingActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
	}
	  public void back(View paramView)
	  {
	    onBackPressed();
	  }
}
