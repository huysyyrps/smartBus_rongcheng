package main.smart.huoche;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import main.smart.rcgj.R;


public class DatapickActivity extends Activity {
	private ListView alllist;
	private String beginstation,endstation,datepick;
	private Thread OrderThread;
	private ArrayList<Map<String, Object>> li = new ArrayList<Map<String, Object>>();
	private Pickadapter Adapter;
	private String be;
	private String en;
	private ImageView huochexq_back;
	private ProgressDialog progress_dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datapick);
		alllist = (ListView) findViewById(R.id.jialllist);
		huochexq_back= (ImageView) findViewById(R.id.huochexq_back);
		Intent intent = DatapickActivity.this.getIntent();
		beginstation=intent.getStringExtra("beginstation");
		endstation=intent.getStringExtra("endstation");
		datepick=intent.getStringExtra("datepick");
		huochexq_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DatapickActivity.this.finish();
				
			}
		});
		progress_dialog = new ProgressDialog(DatapickActivity.this);
		progress_dialog.setMessage("������...");
		progress_dialog.show();
		new Thread() {
			@Override
			public void run() {
				Message message = handler.obtainMessage();
				
				try {
					be = URLEncoder.encode(beginstation, "utf-8");
					en=URLEncoder.encode(endstation, "utf-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("appkey", "db0da73a73abd23c");
				map.put("start", beginstation);
				map.put("end",endstation );
				map.put("date", datepick);
				Log.e(null, "2222222222222" +map);
				String jsonStr = DBHandler.getRecord(map);
				try {
					JSONObject json = new JSONObject(jsonStr);

					Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
					// ͨ��Gson ������̨������������
					Map<String, Object> mapdate = gson.fromJson(jsonStr, new TypeToken<Map<String, Object>>() {
					}.getType());
					
					Log.e(null, "###############" + jsonStr);
					if (json != null && json.getString("msg") != null) {
						String success = json.getString("msg");
						if (success.equals("ok")) {
							Map<String, Object> mappik=(Map<String, Object>) mapdate.get("result");
							li = (ArrayList<Map<String, Object>>) mappik.get("list");
							message.what = 1;
							handler.sendMessage(message);
							
							Log.e(null, "2222222222222" + li.size());
							Log.e(null, "1111111111" + li.get(0));
						} else {
							message.what = 3;
							handler.sendMessage(message);
							String msg = json.getString("msg");

						}
					} else {
						message.what = 2;
						handler.sendMessage(message);
					}
				} catch (JSONException e) {
					message.what = 2;
					handler.sendMessage(message);
				}

			}
		}.start();
	}
	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			if(msg.what==1)
			{
				progress_dialog.hide();
				Log.e(null, "����ģ���" + li);
				Adapter = new Pickadapter(DatapickActivity.this, li);
				alllist.setAdapter(Adapter);
			}else if(msg.what==2){
				progress_dialog.hide();
				Toast.makeText(DatapickActivity.this, "��ѯ���ʧ��", 3).show();
			}else if(msg.what==3){
				progress_dialog.hide();
				Toast.makeText(DatapickActivity.this, "���޸�վ����Ϣ", 3).show();
			}
		}
	};
	
}
