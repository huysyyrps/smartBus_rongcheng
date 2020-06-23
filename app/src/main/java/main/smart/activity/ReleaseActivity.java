package main.smart.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.smart.rcgj.R;
import main.smart.bus.activity.BusActivity;
import main.smart.bus.bean.LineBean;
import main.smart.common.http.DBHandler;
import main.smart.common.util.CityManager;
import main.smart.common.util.ConstData;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author likaiyin
 */
public class ReleaseActivity extends Activity implements OnClickListener {

	@SuppressWarnings("unused")
	private ImageView _g_ctrl_close;
	private GridView gv;// ����GridView��ͼ
	private static OkHttpClient client;
	private ArrayList<Map<String, Object>> licity = new ArrayList<Map<String, Object>>();
	private CityManager mCityMan = CityManager.getInstance();
	int[] names;
	List<String> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.textdialog);
		_init();
		gv = (GridView) findViewById(R.id.gv);
		new Thread() {
			@Override
			public void run() {
				Log.e(null, "��ȡ����������ӿ�" + ConstData.tmURL + "/sdhyschedule/PhoneQueryAction!getDepRegion.shtml");
				client = new OkHttpClient();

				Request request = new Request.Builder()
						.url(ConstData.tmURL + "/sdhyschedule/PhoneQueryAction!getDepRegion.shtml")

						.build();

				client.newCall(request).enqueue(new Callback() {
					@Override
					public void onFailure(Call call, IOException e) {
						Log.e(null, "��������ʧ��" + e);
					}

					@Override
					public void onResponse(Call call, Response response) throws IOException {
						if (!response.isSuccessful())
							throw new IOException("Unexpected code " + response);

						String code = response.body().string();
						Log.e(null, "��ȡ����������ӿ�" + code);
						try {
							JSONObject json = new JSONObject(code);

							Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
							// ͨ��Gson ������̨������������
							Map<String, Object> map1 = gson.fromJson(code, new TypeToken<Map<String, Object>>() {
							}.getType());
							Log.e(null, "��ȡ����������ӿ�" + json);
							if (json != null) {
								licity = (ArrayList<Map<String, Object>>) map1.get("areas");

								DBHandler dbhandler = new DBHandler();
								mHandler.sendEmptyMessage(1);

							} else {
								Log.e(null, "��ȡ�����б�Ϊ��");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

			}
		}.start();

	}

	public void setLineAdapter() {
		List<LineBean> listLine = mCityMan.getLine();
		list = new ArrayList<String>();
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		int e = 0;
		int f = 0;
		int g = 0;
		int h = 0;
		int i = 0;
		int j = 0;
		if (listLine.size() < 1) {
			mCityMan.getLocalAllLine();
			listLine = mCityMan.getLine();
		}
		
		String city = ConstData.SELECT_CITY;
		Log.e(null, "==========================" + listLine.get(0).getDwbhs().toString().substring(0, 6));
		for (int n = 0; n < listLine.size(); n++) {

			if (("001001").equals(listLine.get(n).getDwbhs().toString().substring(0, 6))) {
				a = a + 1;

			} else if (("001012").equals(listLine.get(n).getDwbhs().toString().substring(0, 6))) {
				b = b + 1;
			} else if (("001021").equals(listLine.get(n).getDwbhs().toString().substring(0, 6))) {
				c = c + 1;
			} else if (("001024").equals(listLine.get(n).getDwbhs().toString().substring(0, 6))) {
				d = d + 1;
			} else if (("001026").equals(listLine.get(n).getDwbhs().toString().substring(0, 6))) {
				e = e + 1;
			} else if (("001029").equals(listLine.get(n).getDwbhs().toString().substring(0, 6))) {
				f = f + 1;
			} else if (("001033").equals(listLine.get(n).getDwbhs().toString().substring(0, 6))) {
				g = g + 1;
			} else if (("001047").equals(listLine.get(n).getDwbhs().toString().substring(0, 6))) {
				h = h + 1;
			} else if (("001049").equals(listLine.get(n).getDwbhs().toString().substring(0, 6))) {
				i = i + 1;
			} else if (("001050").equals(listLine.get(n).getDwbhs().toString().substring(0, 6))) {
				j = j + 1;
			}
			
			
		

			Log.e(null, "==========================" + list);
		}
		list.add(a+"");
		list.add(b+"");
		list.add(c+"");
		list.add(d+"");
		list.add(e+"");
		list.add(f+"");
		list.add(g+"");
		list.add(h+"");
		list.add(i+"");
		list.add(j+"");
	}

	// handler����
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

			case 1:
				setLineAdapter();
				gv.setAdapter(new MyAdapter(ReleaseActivity.this));// ͨ������������ʵ�������ڲ���
				// Ϊÿ����Ԫ��item����ӵ����¼�
				gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						TextView tv = (TextView) view.findViewById(R.id.tv);
						ConstData.danwei = licity.get(position).get("depId").toString();
						Intent in = new Intent(ReleaseActivity.this, BusActivity.class);
						startActivity(in);

					}
				});
				break;
			default:
				break;
			}
		};
	};

	/**
	 * ��ʼ��
	 */
	private void _init() {
		_g_ctrl_close = (ImageView) findViewById(R.id.img_close);

		_g_ctrl_close.setOnClickListener(this);

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.activity_frombottom, R.anim.activity_tobottom);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_close:
			onBackPressed();
			break;
		// case R.id.btnFindMe:
		// startActivity(new Intent(ReleaseActivity.this, BusActivity.class));
		// break;
		// case R.id.btnyutai:
		// startActivity(new Intent(ReleaseActivity.this, BusActivity.class));
		// break;
		// case R.id.btnweishan:
		// startActivity(new Intent(ReleaseActivity.this, BusActivity.class));
		// break;
		// case R.id.btnFindMe1:
		// startActivity(new Intent(ReleaseActivity.this, BusActivity.class));
		// break;
		default:
			break;
		}

	}

	// �Զ�����������ͨ���̳�BaseAdapter��
	class MyAdapter extends BaseAdapter {
		Context context;// ���������������õ�������
		// ����Ҫ���õ�ͼƬ�����ֱַ��װ������
		int[] images = { R.drawable.re_bus, R.drawable.yutai, R.drawable.weishan, R.drawable.re_xiang, R.drawable.area,
				R.drawable.area1, R.drawable.area2, R.drawable.area3, R.drawable.area5, R.drawable.area6 };

		// String[] names = {"����1", "����2", "����3", "����4", "����5", "����6", "����3",
		// "����4", "����5", "����5"};
		// ͨ�����췽����ʼ��������
		public MyAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			return licity.size();// imagesҲ����
		}

		@Override
		public Object getItem(int position) {
			return licity.get(position);// imagesҲ����
		}
		

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// ͨ�����������LayoutInflater�������Ԫ���ڵĲ���
			View v = LayoutInflater.from(context).inflate(R.layout.item_layout, null);
			// ʹ��findViewById�ֱ��ҵ���Ԫ���ڲ��ֵ�ͼƬ�Լ�����
			ImageView iv = (ImageView) v.findViewById(R.id.iv);
			TextView tv = (TextView) v.findViewById(R.id.tv);
			TextView tvxian = (TextView) v.findViewById(R.id.tvxianlu);
			// ����������Ԫ�����ò�����ͼƬ�Լ����ֵ�����
			iv.setImageResource(images[position]);
			tv.setText(licity.get(position).get("areaName").toString());
			tvxian.setText("������·"+list.get(position)+"��"

			);
			// ����ֵһ��Ϊ��Ԫ�����岼��v
			return v;
		}
	}

}
