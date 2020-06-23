package main.smart.bus.activity;


import main.smart.rcgj.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
/**
 * ����վ�㣺listView ������·����ϸ��Ϣ
 * **/
public class DetailsAction extends Activity {

	private TextView qs;
	private TextView js;
	private TextView hc;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bus_transfer_detail_station);
		qs=(TextView) findViewById(R.id.qdxx);
		js=(TextView) findViewById(R.id.zdxx);
		hc=(TextView) findViewById(R.id.hcxx);
		Intent intent = getIntent();
		String qszd = intent.getStringExtra("qd");
		String jszd = intent.getStringExtra("zd");
		String memo = intent.getStringExtra("memo");
		qs.setText(qszd);
		js.setText(jszd);
		hc.setText(memo);
	}

	//���ذ�ťʱ��
	public void back(View paramView){
	    onBackPressed();
	}
}
