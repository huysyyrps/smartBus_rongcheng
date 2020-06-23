package main.smart.bus.activity;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.umeng.common.Log;

import main.smart.rcgj.R;
import main.smart.bus.activity.adapter.AutoAdapter;
import main.smart.bus.bean.ZDXX;
import main.smart.bus.util.BusManager;
import main.smart.common.http.DataBase;
import main.smart.common.util.ConstData;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

//���˲�ѯ
public class BusStationFragment extends Fragment {
	private int MID;				//������ȡ��ѡ������
	private String Address = "";	//��˾��ַ
	private String Homeadd = "";	//��ͥ��ַ
	private String Curadd = "";	   //��ǰλ��
	private String txt = "";
	private String qstxt;
	private String jstxt;
	private Button btn;
	private View localView;
	private AutoCompleteTextView qszd;
	private AutoCompleteTextView jszd;
	private DataBase database;
	private Activity activity;
	private ListView cxls;
	private String[] hisstore;				// ��ʷ���ݵ�����Դ
	private Button start_btn;
	//private ImageButton end_btn;
	private String[] datalist = new String[1];
	private List<ZDXX> stalist;
	private String flag="0";
	private String iden="1";
	private BusManager mBusMan;

	//private String[] mData = new String[0];
	private AutoAdapter<String> mAdapter1;
	private AutoAdapter<String> mAdapter2;
	private boolean mbFinish = false;
	/**
	 * ���˽����ʼ��
	 *
	 * */
	public View onCreateView(LayoutInflater paramLayoutInflater,ViewGroup paramViewGroup, Bundle paramBundle) {
		localView = paramLayoutInflater.inflate(R.layout.bus_station_fragment,paramViewGroup, false);
		stalist=ConstData.list;
		android.util.Log.e(null, "������Ҫ�� "+stalist.size());

		for(int i=0;i<stalist.size();i++){
			android.util.Log.e(null, "����Ҫ��վ������"+stalist.get(i).getZdname());
		}
		mBusMan = BusManager.getInstance();
		btn = (Button) localView.findViewById(R.id.bus_line_Search_btn);

		btn.setOnClickListener(new BtnListener());
		cxls = (ListView)localView.findViewById(R.id.cxls);

		qszd = (AutoCompleteTextView) localView.findViewById(R.id.qszd);
		jszd = (AutoCompleteTextView) localView.findViewById(R.id.jszd);

		Context context = localView.getContext();
		activity = (Activity) context;
		datalist[0] = "";

		Log.e(null, "��ʲô�� ������Բеĳ�ŵ����"+datalist);
		mAdapter1 = new AutoAdapter<String>(activity,android.R.layout.simple_dropdown_item_1line, datalist);
		qszd.setAdapter(mAdapter1);
		mAdapter2 = new AutoAdapter<String>(activity,android.R.layout.simple_dropdown_item_1line, datalist);
		jszd.setAdapter(mAdapter2);

		start_btn = (Button) localView.findViewById(R.id.cur_add_btn);
		start_btn.setOnClickListener(new start_btn());
		//end_btn = (ImageButton) localView.findViewById(R.id.map_end_btn);
		//end_btn.setOnClickListener(new end_btn());
		qszd.addTextChangedListener(new QszdListener());
		jszd.addTextChangedListener(new JszdListener());
		cxls.setOnItemLongClickListener(new CxlsListener());
		cxls.setOnItemClickListener(new CxlsClickListener());


		database = new DataBase(activity, "AppData");
		Thread th =new Thread(getdata);
		Thread th1 = new Thread(LoadHistory);
		th.start();
		th1.start();
		return localView;
	}

	/**
	 * �յ�ļ���
	 * **/
	class start_btn implements OnClickListener {
		public void onClick(View v) {

			String str = qszd.getText().toString();
			if (str.equals(""))
			{
				Builder log = new AlertDialog.Builder(activity);
				log.setTitle("��ʾ");
				log.setPositiveButton("ȷ��", null);
				log.setMessage("��ʼվ�㲻��Ϊ�գ�");
				log.show();

			}
			else
			{
				Curadd = str;
				ConstData.curAddr2 = Curadd;
				hisstore[2] = "�ҵ�λ�� " + Curadd;
				initHistory(hisstore);
			}
		}
	}

//	/**
//	 * ���ļ���
//	 * **/
//	class start_btn implements OnClickListener {
//		public void onClick(View v) {
//			Intent it = new Intent();
//			Bundle bundle = new Bundle();
//			bundle.putString("qszd", qszd.getText().toString());
//			bundle.putString("jszd", jszd.getText().toString());
//			it.putExtras(bundle);
//			it.setClass(activity, QdMap.class);
//			startActivity(it);// ��ת��ҳ
//		}
//	}

	// ��ѯ��ʷ�ļ���
	class CxlsListener implements OnItemLongClickListener {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
			if (arg2 < 3) {
				cxls.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
					public void onCreateContextMenu(ContextMenu menu, View v,
													ContextMenuInfo menuInfo) {
					}
				});
			} else {
				cxls.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
					public void onCreateContextMenu(ContextMenu menu, View v,
													ContextMenuInfo menuInfo) {
						menu.setHeaderTitle("����");
						menu.add(0, 0, 0, "��Ϊ���");
						menu.add(0, 1, 0, "��Ϊ�յ�");
						menu.add(0, 2, 0, "��Ϊ�ؼ�");
						menu.add(0, 3, 0, "��Ϊ�ϰ�");
						menu.add(0, 4, 0, "�����ʷ");
					}
				});
			}
			return false;
		}
	}

	// ����¼�����Ӧ����
	class CxlsClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
			// TODO Auto-generated method stub
			Builder log = new AlertDialog.Builder(activity);
			log.setTitle("��ʾ");
			log.setPositiveButton("ȷ��", null);
			if (arg2 == 0) {
				if (Homeadd.equals("")) {
					log.setMessage("�ؼҵ�ַδ����");
					log.show();
					return;
				} else {
					if (Curadd.equals(""))
					{
						log.setMessage("�ҵ�λ��δ����");
						log.show();
					}
					else
					{
						Intent ient = new Intent();
						Bundle bundle = new Bundle();
						bundle.putString("qszd", Curadd);
						bundle.putString("jszd", Homeadd);
						ient.putExtras(bundle);
						ient.setClass(activity, QueryResultsAction.class);
						startActivity(ient);
					}
					//textzd.setText(Homeadd);
				}
//				if (Homeadd.equals("")) {
//					Intent it = new Intent();
//					Bundle bundle = new Bundle();
//					bundle.putString("qszd", qszd.getText().toString());
//					bundle.putString("jszd", jszd.getText().toString());
//					it.putExtras(bundle);
//					it.setClass(activity, QdMap.class);
//					startActivity(it);// ��ת��ҳ
//					return;
//				} else {
//					jszd.setText(Homeadd);
//				}
			}
			if (arg2 == 1) {
				if (Address.equals("")) {
					log.setMessage("�ϰ��ַδ����");
					log.show();
					return;
				} else {
					if (Curadd.equals(""))
					{
						log.setMessage("�ҵ�λ��δ����");
						log.show();
					}
					else
					{
						Intent ient = new Intent();
						Bundle bundle = new Bundle();
						bundle.putString("qszd", Curadd);
						bundle.putString("jszd", Address);
						ient.putExtras(bundle);
						ient.setClass(activity, QueryResultsAction.class);
						startActivity(ient);
					}
					//textzd.setText(Address);
				}
//				if (Address.equals("")) {
//					Intent it = new Intent();
//					Bundle bundle = new Bundle();
//					bundle.putString("qszd", qszd.getText().toString());
//					bundle.putString("jszd", jszd.getText().toString());
//					it.putExtras(bundle);
//					it.setClass(activity, ZdMap.class);
//					startActivity(it);// ��ת��ҳ
//					return;
//				} else {
//					jszd.setText(Address);
//				}
			}
			if (arg2 == 2){
				if (Curadd.equals(""))
				{
					log.setMessage("�������,������ҵ�λ�á���ť�����õ�ǰλ�ã�");
					log.show();
				}

//				Intent it = new Intent();
//				Bundle bundle = new Bundle();
//				bundle.putString("qszd", qszd.getText().toString());
//				bundle.putString("jszd", jszd.getText().toString());
//				it.putExtras(bundle);
//				it.setClass(activity, QdMap.class);
//				startActivity(it);// ��ת��ҳ
			}
//			if (arg2 == 3){
//				Intent it = new Intent();
//				Bundle bundle = new Bundle();
//				bundle.putString("qszd", qszd.getText().toString());
//				bundle.putString("jszd", jszd.getText().toString());
//				it.putExtras(bundle);
//				it.setClass(activity, ZdMap.class);
//				startActivity(it);// ��ת��ҳ
//			}
		}
	}

	// �����˵���Ӧ����
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		MID = (int) info.id;
		switch (item.getItemId()) {
			case 0:
				// ��Ϊ���
				String qtxt = hisstore[MID];
				qszd.setText(qtxt);
				Toast.makeText(activity, "��Ϊ���", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				// ��Ϊ�յ�
				String jtxt = hisstore[MID];
				jszd.setText(jtxt);
				Toast.makeText(activity, "��Ϊ�յ�", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				// ��Ϊ�ؼ�
				InsertZD(Homeadd,"22",hisstore[MID]);
				Homeadd = hisstore[MID];
				ConstData.homeAddr2 = Homeadd;
				hisstore[0] = "��Ҫ�ؼ� " + Homeadd;
				initHistory(hisstore);
				break;
			case 3:
				// ��Ϊ�ϰ�
				InsertZD(Address,"23",hisstore[MID]);
				Address = hisstore[MID];
				ConstData.workAddr2 = Address;
				hisstore[1] = "��Ҫ�ϰ� " + Address;
				initHistory(hisstore);
				break;
			case 4:
				// �����ʷ
				iden="2";
				Thread th=new Thread(LoadHistory);
				th.start();
				Toast.makeText(activity, "�����ʷ", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
		}
		return super.onContextItemSelected(item);
	}

	//��¼վ��
	public void InsertZD(String val,String flag,String va){
		synchronized(this){
			SQLiteDatabase db = database.getWritableDatabase();
			if (val.equals("")){
				ContentValues value = new ContentValues();
				value.put("zdname",	va);
				value.put("zdtype", flag);
				db.insert("LSJL", null, value);
			}else{
				ContentValues value = new ContentValues();
				value.put("zdname", va);
				db.update("LSJL", value,"zdtype = ?", new String[]{flag});
			}
			db.close();
		}
	}

	// ��ʼվ��ֵ����
	class QszdListener implements TextWatcher {
		@Override
		public void afterTextChanged(Editable s) {

		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,int after) {

		}
		@Override
		public void onTextChanged(CharSequence s, int start, int before,int count) {
			flag="1";
			if (!mbFinish)
			{
				return;
			}
//			else
//			{
//				mAdapter1.notifyDataSetChanged();
//			}
//			txt = qszd.getText().toString();
//			Thread th =new Thread(getdata);
//			th.start();
		}
	}

	// ����վ��ֵ����
	class JszdListener implements TextWatcher {
		public void afterTextChanged(Editable s) {
		}
		public void beforeTextChanged(CharSequence s, int start, int count,int after) {
		}
		public void onTextChanged(CharSequence s, int start, int before,int count) {
			flag="2";
			if (!mbFinish)
			{
				return;
			}
//			else
//			{
//				mAdapter2.notifyDataSetChanged();
//			}
//			txt = jszd.getText().toString();
//			Thread th =new Thread(getdata);
//			th.start();
		}
	}

	// ��ѯ��ť�����㻻����·��Ϣ
	class BtnListener implements OnClickListener {
		public void onClick(View v) {
			// TODO Auto-generated method stub
			qstxt = qszd.getText().toString();
			jstxt = jszd.getText().toString();
			Builder log = new AlertDialog.Builder(activity);
			log.setTitle("��ʾ");
			log.setPositiveButton("ȷ��", null);
			if (qstxt.equals("")) {
				log.setMessage("��ʼվ�㲻��Ϊ�գ�");
				log.show();
				return;
			}
			if (jstxt.equals("")) {
				log.setMessage("����վ�㲻��Ϊ�գ�");
				log.show();
				return;
			}
			if (qstxt.equals(jstxt)) {// ��ʼ����վ����ͬ����
				log.setMessage("�������Ϊͬһվ�㣡");
				log.show();
				return;
			}
//			addHistory();
			Thread th=new Thread(addHistory);
			th.start();
			Intent ient = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("qszd", qstxt);
			bundle.putString("jszd", jstxt);
			ient.putExtras(bundle);
			ient.setClass(activity, QueryResultsAction.class);
			startActivity(ient);//��ת��������ϸ��Ϣ


		}
	}



	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		iden="1";
		Thread th1=new Thread(LoadHistory);
		th1.start();
		super.onResume();
	}

	//���ӵ���ʷ��¼
	Runnable addHistory=new Runnable() {
		@Override
		public void run() {
			synchronized(this){
				SQLiteDatabase db = database.getWritableDatabase();
				ContentValues value1 = new ContentValues();
				value1.put("zdname", qstxt);
				value1.put("zdtype", "20");
				ContentValues value2 = new ContentValues();
				value2.put("zdname", jstxt);
				value2.put("zdtype", "20");
				db.insert("LSJL", null, value1);
				db.insert("LSJL", null, value2);
				db.close();
			}
		}
	};

//	void addHistory()
//	{
//		synchronized(this){
//			SQLiteDatabase db = database.getWritableDatabase();
//			ContentValues value1 = new ContentValues();
//			value1.put("zdname", qstxt);
//			value1.put("zdtype", "1");
//			ContentValues value2 = new ContentValues();
//			value2.put("zdname", jstxt);
//			value2.put("zdtype", "1");
//			db.insert("LSJL", null, value1);
//			db.insert("LSJL", null, value2);
//			db.close();
//		}
//	}

	//������ʷ��¼��Ϣ
	Runnable LoadHistory=new Runnable() {
		@Override
		public void run() {
			if (iden.equals("1")) {// ������ʷ����
				int i=3;
				int j=3;
				Set<String> set=new HashSet();
				SQLiteDatabase db = database.getReadableDatabase();
				//db.beginTransaction();
				Cursor curor = db.query(true, "LSJL",new String[] {"zdname,zdtype"}, null, null, null, null,null, null);
				while(curor.moveToNext()){
					String zd=curor.getString(curor.getColumnIndex("zdname"));
					String lx=curor.getString(curor.getColumnIndex("zdtype"));
					if (lx.equals("20")){
						i++;
						set.add(zd);
					}
				}
				//db.setTransactionSuccessful();
				//db.endTransaction();
				curor.close();
				db.close();
				Homeadd=ConstData.homeAddr2;
				Address=ConstData.workAddr2;
				Curadd = ConstData.curAddr2;
				hisstore = new String[set.size() + j];
				hisstore[0] = "��Ҫ�ؼ� " + Homeadd;
				hisstore[1] = "��Ҫ�ϰ� " + Address;
				hisstore[2] = "�ҵ�λ�� " + Curadd;
				//hisstore[3] = "�ҵ�Ŀ��";

				for (String str : set) {
					hisstore[j] = str;
					j++;
				}


			}
			if (iden.equals("2")) {// �����ʷ����
				synchronized(this){
					SQLiteDatabase db = database.getWritableDatabase();
					db.delete("LSJL", "zdtype like (?)",new String[]{"2%"});
					hisstore = new String[3];
					hisstore[0] = "��Ҫ�ؼ�";
					hisstore[1] = "��Ҫ�ϰ�";
					hisstore[2] = "�ҵ�λ��";
					//hisstore[3] = "�ҵ�Ŀ��";
					db.close();
					Homeadd="";
					ConstData.homeAddr2="";
					Address="";
					ConstData.workAddr2="";
					Curadd="";
					ConstData.curAddr2="";
				}
			}
			handler.sendEmptyMessage(3);
		}
	};



	//���ع���վ������
	Runnable getdata=new Runnable() {
		@Override
		public void run() {
			mbFinish=false;
			if(stalist!=null){
				int j=0;
				Set set=new HashSet();
				for(int i=0;i<stalist.size();i++){
					ZDXX zd=stalist.get(i);
					String name= zd.getZdname();
					if (name.indexOf(txt)!=-1){
						set.add(zd.getZdname());
					}
				}
				datalist=new String[set.size()];
				for( Iterator   it = set.iterator();  it.hasNext(); ) {
					datalist[j]=it.next().toString();
					j++;
				}
			}
			mbFinish=true;
			handler.sendEmptyMessage(4);
//				if("0".equals(flag)){
//					handler.sendEmptyMessage(0);
//				}
//				if("1".equals(flag)){
//					handler.sendEmptyMessage(1);
//				}
//				if("2".equals(flag)){
//					handler.sendEmptyMessage(2);
//				}
//			}
		}
	};



	//handler ������
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					initQszd(datalist);
					initJszd(datalist);
					break;
				case 1:
					initQszd(datalist);
					break;
				case 2:
					initJszd(datalist);
					break;
				case 3:
					initHistory(hisstore);
				case 4:
					mAdapter1 = new AutoAdapter<String>(activity,android.R.layout.simple_dropdown_item_1line, datalist);
					qszd.setAdapter(mAdapter1);
					mAdapter2 = new AutoAdapter<String>(activity,android.R.layout.simple_dropdown_item_1line, datalist);
					jszd.setAdapter(mAdapter2);

					break;
				default:
					break;
			}

		};
	};


	// ��ʼվ����������
	public void initQszd(String[] data) {
		AutoAdapter<String> adapter = new AutoAdapter<String>(activity,android.R.layout.simple_dropdown_item_1line, data);
		qszd.setAdapter(adapter);
	}

	// Ŀ��վ����������
	public void initJszd(String[] data) {
		AutoAdapter<String> adapter = new AutoAdapter<String>(activity,android.R.layout.simple_dropdown_item_1line, data);
		jszd.setAdapter(adapter);
	}

	//��ʷ��¼������Դ
	public void initHistory(String[] data) {
		AutoAdapter<String> adapter = new AutoAdapter<String>(activity,android.R.layout.simple_dropdown_item_1line, data);
		cxls.setAdapter(adapter);
	}


}
