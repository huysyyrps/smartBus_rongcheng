package main.smart.bus.activity;


import main.smart.rcgj.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NoticeInfoActivity extends Activity {

	private EditText mTitleEdit;
	private EditText mContentEdit;
	private TextView mDateText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_info_layout);
		Intent intent = getIntent();
		Bundle bunde = intent.getExtras(); 
		
		String title= bunde.getString("Title");
		String content = bunde.getString("Content");
		String date = bunde.getString("Date");
		
		mTitleEdit = (EditText) findViewById(R.id.notice_title_edit);
		mContentEdit = (EditText) findViewById(R.id.notice_content_edit);
	    mDateText = (TextView) findViewById(R.id.notice_date_textView);
	    
		mTitleEdit.setText(title);
		mContentEdit.setText(content);
		mDateText.setText(date);
		
	}
	
	//���ذ�ťʱ��
	public void back(View paramView){
		onBackPressed();
	}

}
