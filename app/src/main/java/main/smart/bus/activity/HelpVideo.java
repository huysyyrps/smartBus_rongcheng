package main.smart.bus.activity;

import main.smart.rcgj.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;


public class HelpVideo extends Activity{

	private VideoView vv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.helpvideo);
//		vv=(VideoView)findViewById(R.id.helpvideoview);
//		String uri = "android.resource://" + this.getPackageName() + "/" + R.raw.help;
//		vv.setMediaController(new MediaController(this));   
//		vv.setVideoURI(Uri.parse(uri));
//		vv.start();
//		vv.requestFocus();
	}
	
	 
	public void back(View paramView){
		onBackPressed();
	}
	
}
