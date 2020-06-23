package main.smart.activity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import main.smart.rcgj.R;
import main.smart.common.http.DBHandler;

public class WeatherActivity extends Activity {
	// ���ذ�ť
	 private ImageView weBackImg;
	// ��������
	private TextView datey;
	// ����
	private TextView date;
	// ����
	private TextView cityField;
	// ��������ͼ��
	private ImageView weather_icon01;
	// ��������
	private TextView currentWeather;
	// �¶ȷ�Χ
	private TextView currentWind;
	// ��������
	private TextView currentWindPower;
	// ���죬���죬�����
	private TextView today, today2, today3;
	// ��������ͼ��
	private ImageView weather_icon02;
	// ��������
	private TextView weather02;
	// ��������
	private TextView temp02;
	// ���շ���
	private TextView wind02;
	// ��������ͼ��
	private ImageView weather_icon03, weather_icon04;
	// ��������
	private TextView weather03,weather04;
	// ��������
	private TextView temp03,temp04;
	// �������
	private TextView wind03,wind04;
	// ����ָ��
	private TextView Dressing;
	// ϴ��ָ��
	private TextView xiche;
	// ��ðָ��
	private TextView ganmao;
	// pm2.5
	private TextView pm;
	private String weathertv, fanweitv, weather, datecan;
	private ArrayList<Map<String, Object>> li = new ArrayList<Map<String, Object>>();
	private Map<String, Object> weather_data ;
	private List desc = new ArrayList();
	private List desc2 = new ArrayList();
	private List desc3 = new ArrayList();
	
	private ArrayList<Map<String, Object>> zhishu = new ArrayList<Map<String, Object>>();
	private String urlweather = "";
	private String pmtex = "";
	private String city = "";
	private String weathercity = "";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		weathercity=getIntent().getStringExtra("city");
		initView();
		getweather();
	}

	/**
	 * �ؼ��ĳ�ʼ��
	 */
	private void initView() {
		 weBackImg = (ImageView) findViewById(R.id.scBackImg);
		 weBackImg.setOnClickListener(new OnClickListener() {
		
		 @Override
		 public void onClick(View v) {
		 WeatherActivity.this.finish();
		 }
		 });
		weather_icon01 = (ImageView) findViewById(R.id.weather_icon01);
		weather_icon02 = (ImageView) findViewById(R.id.weather_icon02);
		weather_icon03 = (ImageView) findViewById(R.id.weather_icon03);
		weather_icon04 = (ImageView) findViewById(R.id.weather_icon04);
		pm = (TextView) findViewById(R.id.pm);
		datey = (TextView) findViewById(R.id.date_y);
		date = (TextView) findViewById(R.id.date);
		today = (TextView) findViewById(R.id.today);
		today2 = (TextView) findViewById(R.id.today2);
		today3 = (TextView) findViewById(R.id.today3);
		cityField = (TextView) findViewById(R.id.cityField);
		// currentTemp = (TextView) findViewById(R.id.currentTemp);
		currentWeather = (TextView) findViewById(R.id.currentWeather);
		currentWind = (TextView) findViewById(R.id.currentWind);
		currentWindPower = (TextView) findViewById(R.id.currentWindPower);
		weather02 = (TextView) findViewById(R.id.weather02);
		temp02 = (TextView) findViewById(R.id.temp02);
		wind02 = (TextView) findViewById(R.id.wind02);
		weather03 = (TextView) findViewById(R.id.weather03);
		weather04 = (TextView) findViewById(R.id.weather04);
		temp03 = (TextView) findViewById(R.id.temp03);
		temp04 = (TextView) findViewById(R.id.temp04);
		wind03 = (TextView) findViewById(R.id.wind03);
		wind04 = (TextView) findViewById(R.id.wind04);
		Dressing = (TextView) findViewById(R.id.chuanyi);
		xiche = (TextView) findViewById(R.id.xiche);
		ganmao = (TextView) findViewById(R.id.ganmao);
		
	}


	/**
	 * ��ȡ����ֵ
	 * 
	 * @return
	 */
	private String getDate() {
		final Calendar c = Calendar.getInstance();
		// ����ʱ��
		c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		String mYear = String.valueOf(c.get(Calendar.YEAR));
		String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);
		String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
		if ("1".equals(mWay)) {
			mWay = "��";
		} else if ("2".equals(mWay)) {
			mWay = "һ";
		} else if ("3".equals(mWay)) {
			mWay = "��";
		} else if ("4".equals(mWay)) {
			mWay = "��";
		} else if ("5".equals(mWay)) {
			mWay = "��";
		} else if ("6".equals(mWay)) {
			mWay = "��";
		} else if ("7".equals(mWay)) {
			mWay = "��";
		}
		return mYear + "��" + mMonth + "��" + mDay + "��" + "(����" + mWay + ")";
	}

	/**
	 * ͨ��Url��������˵�ͼƬ
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getHttpBitmap(String url) {
		URL picUrl;
		Bitmap bitmap = null;
		try {
			picUrl = new URL(url);
			// ����������
			HttpURLConnection conn = (HttpURLConnection) picUrl.openConnection();
			// ��ȡ����
			InputStream is = conn.getInputStream();
			// ���ݽ���
			bitmap = BitmapFactory.decodeStream(is);
			// �ر�������
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * ��ȡ��������
	 */
	private void getweather() {
		// DBHandler.getweaather(action, city)

		new Thread() {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				
				
				
				String jsonStr = DBHandler.getweaather(weathercity);

				try {
					JSONObject json2 = new JSONObject(jsonStr);

					Gson gson2 = new GsonBuilder().enableComplexMapKeySerialization().create();
					// ͨ��Gson ������̨������������
					Map<String, Object> map12 = gson2.fromJson(jsonStr,
							new TypeToken<Map<String, Object>>() {
							}.getType());
					
					if (json2 != null) {

						li = (ArrayList<Map<String, Object>>) map12.get("data");
						weather_data = (Map<String, Object>) li.get(0);

						
						weathertv = weather_data.get("wea").toString();
						fanweitv = weather_data.get("tem2").toString() + "~"
								+ weather_data.get("tem1").toString();
						pmtex = weather_data.get("air").toString();
						city = map12.get("city").toString();
						
						desc= (ArrayList) li.get(1).get("win");
						desc2= (ArrayList) li.get(2).get("win");
						desc3= (ArrayList) li.get(3).get("win");
						zhishu=(ArrayList<Map<String, Object>>) weather_data.get("index");
						DBHandler dbhandler = new DBHandler();
						mHandler.sendEmptyMessage(4);

					} else {
						DBHandler dbhandler = new DBHandler();
						mHandler.sendEmptyMessage(5);
					}
				} catch (JSONException e) {
					DBHandler dbhandler = new DBHandler();
					mHandler.sendEmptyMessage(5);
				}


			}
		}.start();
	}

	// handler����
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {

			case 4:

				cityField.setText(city);
				currentWeather.setText(weathertv);
				datey.setText(getDate());
				date.setText("");
				currentWindPower.setText(weather_data.get("tem").toString());
				currentWind.setText(fanweitv);
				if (weathertv.contains("��")) {
					weather_icon01.setBackground(getDrawable(R.drawable.sunny));
				} else if (weathertv.contains("����")) {
					weather_icon01.setBackground(getDrawable(R.drawable.partly_cloudy));
				} else if (weathertv.contains("��")) {
					weather_icon01.setBackground(getDrawable(R.drawable.cloudy));
				} else if (weathertv.contains("����")) {
					weather_icon01.setBackground(getDrawable(R.drawable.shower));
				} else if (weathertv.contains("������")) {
					weather_icon01.setBackground(getDrawable(R.drawable.stormy_rain));
				} else if (weathertv.contains("���ѩ")) {
					weather_icon01.setBackground(getDrawable(R.drawable.snow_rain));
				} else if (weathertv.contains("С��")) {
					weather_icon01.setBackground(getDrawable(R.drawable.light_rain));
				} else if (weathertv.contains("����")) {
					weather_icon01.setBackground(getDrawable(R.drawable.moderate_rain));
				} else if (weathertv.contains("����")) {
					weather_icon01.setBackground(getDrawable(R.drawable.moderate_rain));
				} else if (weathertv.contains("����")) {
					weather_icon01.setBackground(getDrawable(R.drawable.moderate_rain));
				} else if (weathertv.contains("��ѩ")) {
					weather_icon01.setBackground(getDrawable(R.drawable.shower_snow));
				} else if (weathertv.contains("Сѩ")) {
					weather_icon01.setBackground(getDrawable(R.drawable.light_snow));
				} else if (weathertv.contains("��ѩ")) {
					weather_icon01.setBackground(getDrawable(R.drawable.moderate_snow));
				} else if (weathertv.contains("��ѩ")) {
					weather_icon01.setBackground(getDrawable(R.drawable.moderate_snow));
				} else if (weathertv.contains("��ѩ")) {
					weather_icon01.setBackground(getDrawable(R.drawable.moderate_snow));
				} else if (weathertv.contains("��")) {
					weather_icon01.setBackground(getDrawable(R.drawable.fog));
				} else if (weathertv.contains("��")) {
					weather_icon01.setBackground(getDrawable(R.drawable.haze));
				} else if (weathertv.contains("ɳ����")) {
					weather_icon01.setBackground(getDrawable(R.drawable.dust_storm));
				} else {
					weather_icon01.setBackground(getDrawable(R.drawable.unknown));
				}
				
				

				if (pmtex.indexOf(".") > 0) {
					// ������
					pmtex = pmtex.replaceAll("0+?$", "");// ȥ���������õ���

					pmtex = pmtex.replaceAll("[.]$", "");// ��С�������ȫ������ȥ��С����

				}
				

				today.setText(li.get(1).get("date").toString());
				today2.setText(li.get(2).get("date").toString());
				today3.setText(li.get(3).get("date").toString());
				
				weather02.setText(li.get(1).get("wea").toString());
				weather03.setText(li.get(2).get("wea").toString());
				weather04.setText(li.get(3).get("wea").toString());
				
				temp02.setText(li.get(1).get("tem2").toString()+"~"+li.get(1).get("tem1").toString());
				temp03.setText(li.get(2).get("tem2").toString()+"~"+li.get(1).get("tem1").toString());
				temp04.setText(li.get(3).get("tem2").toString()+"~"+li.get(1).get("tem1").toString());
				
				wind02.setText(desc.get(0).toString()+li.get(1).get("win_speed").toString());
				wind03.setText(desc2.get(0).toString()+li.get(2).get("win_speed").toString());
				wind04.setText(desc3.get(0).toString()+li.get(3).get("win_speed").toString());
				
				
				
				if ((li.get(1).get("wea").toString()).contains("��")) {
					weather_icon02.setBackground(getDrawable(R.drawable.sunny));
				} else if ((li.get(1).get("wea").toString()).contains("����")) {
					weather_icon02.setBackground(getDrawable(R.drawable.partly_cloudy));
				} else if ((li.get(1).get("wea").toString()).contains("��")) {
					weather_icon02.setBackground(getDrawable(R.drawable.cloudy));
				} else if ((li.get(1).get("wea").toString()).contains("����")) {
					weather_icon02.setBackground(getDrawable(R.drawable.shower));
				} else if ((li.get(1).get("wea").toString()).contains("������")) {
					weather_icon02.setBackground(getDrawable(R.drawable.stormy_rain));
				} else if ((li.get(1).get("wea").toString()).contains("���ѩ")) {
					weather_icon02.setBackground(getDrawable(R.drawable.snow_rain));
				} else if ((li.get(1).get("wea").toString()).contains("С��")) {
					weather_icon02.setBackground(getDrawable(R.drawable.light_rain));
				} else if ((li.get(1).get("wea").toString()).contains("����")) {
					weather_icon02.setBackground(getDrawable(R.drawable.moderate_rain));
				} else if ((li.get(1).get("wea").toString()).contains("����")) {
					weather_icon02.setBackground(getDrawable(R.drawable.moderate_rain));
				} else if ((li.get(1).get("wea").toString()).contains("����")) {
					weather_icon02.setBackground(getDrawable(R.drawable.moderate_rain));
				} else if ((li.get(1).get("wea").toString()).contains("��ѩ")) {
					weather_icon02.setBackground(getDrawable(R.drawable.shower_snow));
				} else if ((li.get(1).get("wea").toString()).contains("Сѩ")) {
					weather_icon02.setBackground(getDrawable(R.drawable.light_snow));
				} else if ((li.get(1).get("wea").toString()).contains("��ѩ")) {
					weather_icon02.setBackground(getDrawable(R.drawable.moderate_snow));
				} else if ((li.get(1).get("wea").toString()).contains("��ѩ")) {
					weather_icon02.setBackground(getDrawable(R.drawable.moderate_snow));
				} else if ((li.get(1).get("wea").toString()).contains("��ѩ")) {
					weather_icon02.setBackground(getDrawable(R.drawable.moderate_snow));
				} else if ((li.get(1).get("wea").toString()).contains("��")) {
					weather_icon02.setBackground(getDrawable(R.drawable.fog));
				} else if ((li.get(1).get("wea").toString()).contains("��")) {
					weather_icon02.setBackground(getDrawable(R.drawable.haze));
				} else if ((li.get(1).get("wea").toString()).contains("ɳ����")) {
					weather_icon02.setBackground(getDrawable(R.drawable.dust_storm));
				} else {
					weather_icon02.setBackground(getDrawable(R.drawable.unknown));
				}
				
				if ((li.get(2).get("wea").toString()).contains("��")) {
					weather_icon03.setBackground(getDrawable(R.drawable.sunny));
				} else if ((li.get(2).get("wea").toString()).contains("����")) {
					weather_icon03.setBackground(getDrawable(R.drawable.partly_cloudy));
				} else if ((li.get(2).get("wea").toString()).contains("��")) {
					weather_icon03.setBackground(getDrawable(R.drawable.cloudy));
				} else if ((li.get(2).get("wea").toString()).contains("����")) {
					weather_icon03.setBackground(getDrawable(R.drawable.shower));
				} else if ((li.get(2).get("wea").toString()).contains("������")) {
					weather_icon03.setBackground(getDrawable(R.drawable.stormy_rain));
				} else if ((li.get(2).get("wea").toString()).contains("���ѩ")) {
					weather_icon03.setBackground(getDrawable(R.drawable.snow_rain));
				} else if ((li.get(2).get("wea").toString()).contains("С��")) {
					weather_icon03.setBackground(getDrawable(R.drawable.light_rain));
				} else if ((li.get(2).get("wea").toString()).contains("����")) {
					weather_icon03.setBackground(getDrawable(R.drawable.moderate_rain));
				} else if ((li.get(2).get("wea").toString()).contains("����")) {
					weather_icon03.setBackground(getDrawable(R.drawable.moderate_rain));
				} else if ((li.get(2).get("wea").toString()).contains("����")) {
					weather_icon03.setBackground(getDrawable(R.drawable.moderate_rain));
				} else if ((li.get(2).get("wea").toString()).contains("��ѩ")) {
					weather_icon03.setBackground(getDrawable(R.drawable.shower_snow));
				} else if ((li.get(2).get("wea").toString()).contains("Сѩ")) {
					weather_icon03.setBackground(getDrawable(R.drawable.light_snow));
				} else if ((li.get(2).get("wea").toString()).contains("��ѩ")) {
					weather_icon03.setBackground(getDrawable(R.drawable.moderate_snow));
				} else if ((li.get(2).get("wea").toString()).contains("��ѩ")) {
					weather_icon03.setBackground(getDrawable(R.drawable.moderate_snow));
				} else if ((li.get(2).get("wea").toString()).contains("��ѩ")) {
					weather_icon03.setBackground(getDrawable(R.drawable.moderate_snow));
				} else if ((li.get(2).get("wea").toString()).contains("��")) {
					weather_icon03.setBackground(getDrawable(R.drawable.fog));
				} else if ((li.get(2).get("wea").toString()).contains("��")) {
					weather_icon03.setBackground(getDrawable(R.drawable.haze));
				} else if ((li.get(2).get("wea").toString()).contains("ɳ����")) {
					weather_icon03.setBackground(getDrawable(R.drawable.dust_storm));
				} else {
					weather_icon03.setBackground(getDrawable(R.drawable.unknown));
				}
				
				if ((li.get(3).get("wea").toString()).contains("��")) {
					weather_icon04.setBackground(getDrawable(R.drawable.sunny));
				} else if ((li.get(3).get("wea").toString()).contains("����")) {
					weather_icon04.setBackground(getDrawable(R.drawable.partly_cloudy));
				} else if ((li.get(3).get("wea").toString()).contains("��")) {
					weather_icon04.setBackground(getDrawable(R.drawable.cloudy));
				} else if ((li.get(3).get("wea").toString()).contains("����")) {
					weather_icon04.setBackground(getDrawable(R.drawable.shower));
				} else if ((li.get(3).get("wea").toString()).contains("������")) {
					weather_icon04.setBackground(getDrawable(R.drawable.stormy_rain));
				} else if ((li.get(3).get("wea").toString()).contains("���ѩ")) {
					weather_icon04.setBackground(getDrawable(R.drawable.snow_rain));
				} else if ((li.get(3).get("wea").toString()).contains("С��")) {
					weather_icon04.setBackground(getDrawable(R.drawable.light_rain));
				} else if ((li.get(3).get("wea").toString()).contains("����")) {
					weather_icon04.setBackground(getDrawable(R.drawable.moderate_rain));
				} else if ((li.get(3).get("wea").toString()).contains("����")) {
					weather_icon04.setBackground(getDrawable(R.drawable.moderate_rain));
				} else if ((li.get(3).get("wea").toString()).contains("����")) {
					weather_icon04.setBackground(getDrawable(R.drawable.moderate_rain));
				} else if ((li.get(3).get("wea").toString()).contains("��ѩ")) {
					weather_icon04.setBackground(getDrawable(R.drawable.shower_snow));
				} else if ((li.get(3).get("wea").toString()).contains("Сѩ")) {
					weather_icon04.setBackground(getDrawable(R.drawable.light_snow));
				} else if ((li.get(3).get("wea").toString()).contains("��ѩ")) {
					weather_icon04.setBackground(getDrawable(R.drawable.moderate_snow));
				} else if ((li.get(3).get("wea").toString()).contains("��ѩ")) {
					weather_icon04.setBackground(getDrawable(R.drawable.moderate_snow));
				} else if ((li.get(3).get("wea").toString()).contains("��ѩ")) {
					weather_icon04.setBackground(getDrawable(R.drawable.moderate_snow));
				} else if ((li.get(3).get("wea").toString()).contains("��")) {
					weather_icon04.setBackground(getDrawable(R.drawable.fog));
				} else if ((li.get(3).get("wea").toString()).contains("��")) {
					weather_icon04.setBackground(getDrawable(R.drawable.haze));
				} else if ((li.get(3).get("wea").toString()).contains("ɳ����")) {
					weather_icon04.setBackground(getDrawable(R.drawable.dust_storm));
				} else {
					weather_icon04.setBackground(getDrawable(R.drawable.unknown));
				}
				
//				Dressing .setText(desc.get(0).get("des").toString());
//				xiche .setText(desc.get(1).get("des").toString());
//				ganmao .setText(desc.get(2).get("des").toString());
				for (int i = 0; i < zhishu.size(); i++) {
			          if ((zhishu.get(i).get("title").toString()).equals("����ָ��")) {
			        	  Dressing .setText(zhishu.get(i).get("desc").toString());
			          } else if ((zhishu.get(i).get("title").toString()).equals("������Ⱦ��ɢָ��")) {
			            		ganmao .setText(zhishu.get(i).get("desc").toString());
			          } else if ((zhishu.get(i).get("title").toString()).equals("ϴ��ָ��") ) {
			        	  xiche .setText(zhishu.get(i).get("desc").toString());
			          }
			        }
				if (pmtex.equals("")) {

				} else {
					if (Integer.parseInt(pmtex) <= 50) {
						pm.setBackgroundColor(getResources().getColor(R.color.you));
						pm.setText(pmtex + "  ��");
					} else if (Integer.parseInt(pmtex) > 50 && Integer.parseInt(pmtex) <= 100) {
						pm.setBackgroundColor(getResources().getColor(R.color.liang));
						pm.setText(pmtex + "  ��");
					} else if (Integer.parseInt(pmtex) > 100 && Integer.parseInt(pmtex) <= 150) {
						pm.setBackgroundColor(getResources().getColor(R.color.qingdu));
						pm.setText(pmtex + "  �����Ⱦ");
					} else if (Integer.parseInt(pmtex) > 150 && Integer.parseInt(pmtex) <= 200) {
						pm.setBackgroundColor(getResources().getColor(R.color.zhongdu));
						pm.setText(pmtex + "  �ж���Ⱦ");
					} else if (Integer.parseInt(pmtex) > 200 && Integer.parseInt(pmtex) <= 300) {
						pm.setBackgroundColor(getResources().getColor(R.color.zongdu));
						pm.setText(pmtex + "  �ض���Ⱦ");
					} else {
						pm.setBackgroundColor(getResources().getColor(R.color.yanzhong));
						pm.setText(pmtex + "  ������Ⱦ");
					}
				}
//				OkHttpUtils.get().url(weather_data.get(1).get("dayPictureUrl").toString()).tag(this).build()
//						.connTimeOut(20000).readTimeOut(20000).writeTimeOut(20000).execute(new BitmapCallback() {
//							@Override
//							public void onError(Call call, Exception e, int id) {
//							}
//
//							@Override
//							public void onResponse(Bitmap bitmap, int id) {
//								weather_icon02.setImageBitmap(bitmap);
//							}
//						});
//
//				OkHttpUtils.get().url(weather_data.get(2).get("dayPictureUrl").toString()).tag(this).build()
//						.connTimeOut(20000).readTimeOut(20000).writeTimeOut(20000).execute(new BitmapCallback() {
//							@Override
//							public void onError(Call call, Exception e, int id) {
//							}
//
//							@Override
//							public void onResponse(Bitmap bitmap, int id) {
//								weather_icon03.setImageBitmap(bitmap);
//							}
//						});
//				OkHttpUtils.get().url(weather_data.get(3).get("dayPictureUrl").toString()).tag(this).build()
//						.connTimeOut(20000).readTimeOut(20000).writeTimeOut(20000).execute(new BitmapCallback() {
//							@Override
//							public void onError(Call call, Exception e, int id) {
//							}
//
//							@Override
//							public void onResponse(Bitmap bitmap, int id) {
//								weather_icon04.setImageBitmap(bitmap);
//							}
//						});
//
//				OkHttpUtils.get().url(urlweather).tag(this).build().connTimeOut(20000).readTimeOut(20000)
//						.writeTimeOut(20000).execute(new BitmapCallback() {
//							@Override
//							public void onError(Call call, Exception e, int id) {
//							}
//
//							@Override
//							public void onResponse(Bitmap bitmap, int id) {
//								weather_icon01.setImageBitmap(bitmap);
//							}
//						});
				// weathericon.setImageBitmap(bitmap);
				break;

			default:
				break;
			}
		};
	};
}
