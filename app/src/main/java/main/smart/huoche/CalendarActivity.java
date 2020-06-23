package main.smart.huoche;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import main.smart.rcgj.R;
import main.smart.calender.MyCalendar;
import main.smart.calender.MyCalendar.OnDaySelectListener;

public class CalendarActivity extends Activity implements OnDaySelectListener {
	private LinearLayout ll;// ��������
	private MyCalendar c1;
	private SimpleDateFormat simpleDateFormat, sd1, sd2;
	private String nowday;
	private String lastdate = "";
	long nd = 1000 * 24L * 60L * 60L;// һ��ĺ�����
	private ImageView city_back;// ���ذ�ť

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		ll = (LinearLayout) findViewById(R.id.ll);
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		nowday = simpleDateFormat.format(new Date());
		sd1 = new SimpleDateFormat("yyyy");
		sd2 = new SimpleDateFormat("dd");
		city_back = (ImageView) findViewById(R.id.city_back);
		city_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		List<String> listDate = getDateList();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		for (int i = 0; i < listDate.size(); i++) {
			c1 = new MyCalendar(CalendarActivity.this);
			c1.setLayoutParams(params);
			Date date = null;
			try {
				date = simpleDateFormat.parse(listDate.get(i));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			c1.setTheDay(date);
			c1.setOnDaySelectListener(this);
			ll.addView(c1);
		}

	}

	// ���ݵ�ǰ���ڣ�����������£�����ǰday����1�ţ�Ϊ��������90�죬����Ҫ�����4���£�
	public List<String> getDateList() {
		List<String> list = new ArrayList<String>();
		Date date = new Date();
		@SuppressWarnings("deprecation")
		int nowMon = date.getMonth() + 1;

		String yyyy = sd1.format(date);
		String dd = sd2.format(date);
		 if (nowMon == 11) {
			list.add(yyyy + "-11-" + dd);
			list.add(yyyy + "-12-" + dd);
			
		} else if (nowMon == 12) {
			list.add(yyyy + "-12-" + dd);
			list.add((Integer.parseInt(yyyy) + 1) + "-01-" + dd);
			
		} else {
			list.add(yyyy + "-" + getMon(nowMon) + "-" + dd);
			list.add(yyyy + "-" + getMon((nowMon + 1)) + "-" + dd);
			
		}

		return list;
	}

	public String getMon(int mon) {
		String month = "";
		if (mon < 10) {
			month = "0" + mon;
		} else {
			month = "" + mon;
		}
		return month;
	}

	@Override
	public void onDaySelectListener(View view, String date) {
		// ����������С�ڵ�ǰ���ڣ�����������-��ǰ���ڳ���6���£����ܵ��
		try {
			if (simpleDateFormat.parse(date).getTime() < simpleDateFormat.parse(nowday).getTime()) {
				return;
			}
			long dayxc = (simpleDateFormat.parse(date).getTime() - simpleDateFormat.parse(nowday).getTime()) / nd;
			if (dayxc > 30) {
				return;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String dateDay = date.split("-")[2];
		if (Integer.parseInt(dateDay) < 10) {
			dateDay = date.split("-")[2].replace("0", "");
		}
		TextView textDayView = (TextView) view.findViewById(R.id.tv_calendar_day);
		int index1 = lastdate.indexOf(date);
		
		Date d;
		try {
			d = simpleDateFormat.parse(lastdate);

		} catch (ParseException e1) {

			e1.printStackTrace();
		}

		if (index1 != -1) {
			// ����
			textDayView.setTextColor(Color.BLACK);
			lastdate = lastdate.replace(date + ",", "");

		}else {
			textDayView.setBackgroundResource(R.drawable.rili2);
			lastdate = lastdate + date;
			Log.e(null, "-----------" + lastdate);
			Intent intent = this.getIntent();
			Bundle bundle = intent.getExtras();
			bundle.putString("godate", lastdate);//���Ҫ���ظ�ҳ��1������
			intent.putExtras(bundle);
			CalendarActivity.this.setResult(Activity.RESULT_OK, intent);//����ҳ��1
			CalendarActivity.this.finish();
			
		}

	}
}
