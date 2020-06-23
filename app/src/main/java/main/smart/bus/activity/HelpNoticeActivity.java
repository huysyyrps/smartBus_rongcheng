package main.smart.bus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import main.smart.rcgj.R;
import main.smart.bus.activity.adapter.NoticeInfoAdapter;
import main.smart.bus.bean.NoticeInfo;
import main.smart.common.http.DBHandler;
import main.smart.common.util.ConstData;
import main.smart.common.util.PreferencesHelper;

public class HelpNoticeActivity extends Activity {

	// private ArrayList<String> mTitleList = null;
	// private HashMap<String, String> mHashMap = null;
	private List<String> mTitleList = null;
	private List<String> mDateList = null;
	private List<String> mContentList = null;
	private List<NoticeInfo> mNoticeList = null;
	private ListView mListView;
	private final int mDataLen = 1000;
	private int mMapSize = 0;
	private int mPos = 1;
	private String mDate = "";
	private DBHandler mdbHandler = null;
	private NoticeInfoAdapter mAdapter;
	private ProgressBar mProgeress;
	private boolean mbLastFlag = false;

	// string state = "0" 0��δ������1�ǽ�����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_notice_layout);
		mdbHandler = new DBHandler();
		// mHashMap = new HashMap<String,String>();
		mTitleList = new ArrayList<String>();
		mContentList = new ArrayList<String>();
		mDateList = new ArrayList<String>();
		mNoticeList = new ArrayList<NoticeInfo>();

		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mDate = format.format(date);

		mListView = (ListView) findViewById(R.id.help_notice_list);
		mListView.setOnItemClickListener(new ListViewClickListener());
		mAdapter = new NoticeInfoAdapter(HelpNoticeActivity.this, (ArrayList<String>) mTitleList,
				(ArrayList<String>) mDateList);
		mListView.setAdapter(mAdapter);

		mProgeress = (ProgressBar) findViewById(R.id.notice_Progress);
		mProgeress.setVisibility(View.VISIBLE);

		Thread thread = new Thread(GetData);
		thread.start();

	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message paramMessage) {
			super.handleMessage(paramMessage);
			String line = "";
			switch (paramMessage.what) {
			case 1:
				mProgeress.setVisibility(View.INVISIBLE);
				mAdapter.notifyDataSetChanged();
				break;
			default:
				return;
			}

		}
	};
	// ���ع���
	Runnable GetData = new Runnable() {
		public void run() {
			String url = ConstData.goURL + "/GetInfo/Notice";
			Log.e("noticeurl+", "========================"+url);
			ArrayList<NoticeInfo> nList = new ArrayList<NoticeInfo>();
			String result = mdbHandler.getNoticeInfo(url, mPos, mDataLen, mDate, nList);

			if (result == null) {
				if (mbLastFlag == false) {
//					mTitleList.add("������ظ�����Ϣ");
//					mDateList.add("");
					mbLastFlag = true;
				}

			} else {
				if (mbLastFlag) {
					mTitleList.remove(mTitleList.size() - 1);
					mDateList.remove(mDateList.size() - 1);
				}
				int size = nList.size();
				mMapSize += size;
				mPos += size;
				for (int i = 0; i < nList.size(); i++) {
					NoticeInfo info = nList.get(i);
					mTitleList.add(info.getTitle());
					mDateList.add(info.getDate());
				}
				if (result.equals("0")) {
					NoticeInfo info = nList.get(nList.size() - 1);
					mDate = info.getDate();
//					mTitleList.add("     ������ظ�����Ϣ");
//					mDateList.add("");
					mbLastFlag = true;
				}
				mNoticeList.addAll(nList);
			}

			mHandler.removeMessages(1);
			mHandler.sendEmptyMessageDelayed(1, 300L);
			if (ConstData.bUpdateNotice) {
				ConstData.bUpdateNotice = false;
				PreferencesHelper.getInstance().updateNoticeLastDate(ConstData.noticeDate);
			}
		};

	};

	// ����¼�����Ӧ����
	class ListViewClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			if (position == mMapSize) {
				mProgeress.setVisibility(View.VISIBLE);
				Thread thread = new Thread(GetData);
				thread.start();
			} else {
				// String strKey = mTilteList.get(position);
				// String content = mContentList.get(position);
				NoticeInfo info = mNoticeList.get(position);
				Intent inte = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("Title", info.getTitle());
				bundle.putString("Content", info.getInfo());
				Log.e(null, "������������"+info.getInfo());
				bundle.putString("Date", info.getDate());
				inte.putExtras(bundle);

				/*boolean bool = info.getInfo().matches("^((https|http|ftp|rtsp|mms)?://)"
						+ "+(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" + "(([0-9]{1,3}\\.){3}[0-9]{1,3}"
						+ "|" + "([0-9a-z_!~*'()-]+\\.)*" + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." + "[a-z]{2,6})"
						+ "(:[0-9]{1,10})?" + "((/?)|" + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$");*/
				// if(info.getInfo().contains("http")){
				String url=info.getInfo();
			//	boolean bool = info.getInfo().matches("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");
				
				
				String regex = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
				Pattern pattern = Pattern.compile(regex);
				if (pattern.matcher(url).matches()) {
					inte.setClass(HelpNoticeActivity.this, HelpWebActivity.class);
					inte.putExtra("URL", info.getInfo());
				} else {
					Log.e(null, "���������������������");
					inte.setClass(HelpNoticeActivity.this, NoticeInfoActivity.class);
				}
//				if (bool == true) {
//					
//				}else {
//					Log.e(null, "���������������������");
//					inte.setClass(HelpNoticeActivity.this, NoticeInfoActivity.class);
//				}
				startActivity(inte);
			}
		}
	};

	// ������Դ
	public void LoadData(List<String> list) {
		// AutoAdapter<String> adapter = new
		// AutoAdapter<String>(this,R.layout.list, list);
		// mListView.setAdapter(adapter);
		mAdapter = new NoticeInfoAdapter(HelpNoticeActivity.this, (ArrayList<String>) mTitleList,
				(ArrayList<String>) mDateList); 
		mListView.setAdapter(mAdapter);
	}

	// ���ذ�ťʱ��
	public void back(View paramView) {
		onBackPressed();
	}

}
