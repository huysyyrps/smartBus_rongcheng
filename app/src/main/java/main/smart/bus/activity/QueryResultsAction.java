package main.smart.bus.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.smart.bus.activity.adapter.ListViewAdapterM;
import main.smart.bus.activity.adapter.ListViewAdapterW;
import main.smart.bus.activity.adapter.AutoAdapter;
import main.smart.bus.bean.HCXX;
import main.smart.bus.bean.ZDXX;
import main.smart.common.util.ConstData;
import main.smart.rcgj.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
/**
 * 换乘的详细信息 列表
 *
 * **/
public class QueryResultsAction extends Activity{

	private String qszd;//起始站点名
	private String jszd;//终点站名字
	private int qsbh;//起始站号
	private int jsbh;//终点站号
	private TextView qs;
	private TextView js;
	private TextView ts;
	private ListView show;
	private List<HCXX> list;
	private ListViewAdapterM mAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.queryresult);
		//提取跳转前 存入的参数
		Intent intent = getIntent();
		qszd = intent.getStringExtra("qszd");
		jszd = intent.getStringExtra("jszd");
		//换乘线路list
		show=(ListView) findViewById(R.id.queryreshow);
		//起止 站点
		qs=(TextView) findViewById(R.id.startstation);
		js=(TextView) findViewById(R.id.endstation);
		ts=(TextView) findViewById(R.id.show_ts);
		qs.setText(qszd);
		js.setText(jszd);
		ts.setText("查询中...");
		show.setOnItemClickListener(new ShowListener());
		Thread th=new Thread(search);
		th.start();
	}
	//handler 处理函数
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:

					initAutoDataText(list);
					ts.setText("查询完成");
					break;
				default:
					break;
			}

		};
	};


	//listview的监听事件
	class ShowListener implements OnItemClickListener{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
			HCXX xx=list.get(arg2);
			Intent inte=new Intent();
			inte.setClass(QueryResultsAction.this, DetailsAction.class);
			Bundle bundle=new Bundle();
			bundle.putString("qd", xx.getQd());
			bundle.putString("zd", xx.getZd());
			bundle.putString("memo", xx.getMemo());
			inte.putExtras(bundle);
			startActivity(inte);// 跳转主页
		}
	}


	//站点查询
	Runnable search=new Runnable() {
		@Override
		public void run() {
			list=new ArrayList<HCXX>();
			List<ZDXX> data=ConstData.list;
			if (data!=null){
				//先判断直达车
				for(int i=0;i<data.size();i++){
					ZDXX xx1=data.get(i);
					int zd=xx1.getZd();
					int xl=xx1.getXl();
					String xlName = xx1.getXlname();
					String zdpc=xx1.getZdname();
					if (zdpc.equals(qszd)){
						for(int j=0;j<data.size();j++){
							ZDXX xx2=data.get(j);
							int zzd=xx2.getZd();
							if (xx2.getXl()==xl&&xx2.getZdname().equals(jszd)&&xx1.getSxx()==xx2.getSxx()){
								if ((xx1.getSxx()==0&&zzd>zd)||(xx1.getSxx()==1&&zd>zzd)){
									HCXX hcxx=new HCXX();
									hcxx.setQd(qszd);
									hcxx.setZd(jszd);
									if ( xlName == null || xlName.equals(""))
									{
										hcxx.setName(xl+getResources().getString(R.string.daolu));
										hcxx.setQzfx(getBusFX(xl,xx1.getSxx(),data));
										hcxx.setMemo(getResources().getString(R.string.ccz)+xl+getResources().getString(R.string.daolufangx)+hcxx.getQzfx()+getResources().getString(R.string.jing)+Math.abs(zzd-zd)+getResources().getString(R.string.dada));
									}
									else
									{
										hcxx.setName(xlName+getResources().getString(R.string.daolu));
										hcxx.setQzfx(getBusFX(xl,xx1.getSxx(),data));
										hcxx.setMemo(getResources().getString(R.string.ccz)+xlName+getResources().getString(R.string.daolufangx)+hcxx.getQzfx()+getResources().getString(R.string.jing)+Math.abs(zzd-zd)+getResources().getString(R.string.dada));
									}

									list.add(hcxx);
								}
							}
						}
					}
				}
				//判断换乘一次车辆
				if(list.size()==0){
					//筛选出经过结束站点的线路
					Set<Integer> set=new HashSet();
					for (int i=0;i<data.size();i++){
						ZDXX xx=data.get(i);
						if (xx.getZdname().equals(jszd)){
							set.add(xx.getXl());
						}
					}
					for(int i=0;i<data.size();i++){
						ZDXX xx1=data.get(i);
						String qdmc=xx1.getZdname();
						if (qdmc.equals(qszd)){
							int qd=xx1.getZd();
							int xl=xx1.getXl();
							int zd=xx1.getZd();
							String xlName = xx1.getXlname();
							for(int j=0;j<data.size();j++){
								ZDXX xx2=data.get(j);
								if(xx2.getXl()==xl&&xx2.getSxx()==xx1.getSxx()){
									int bh=xx2.getZd();
									//下行，查询后面的站点时候有合适的换乘//上行，查询后面的站点时候有合适的换乘
									if ((xx1.getSxx()==0&&bh>zd)||(xx1.getSxx()==1&&bh<zd)){
										int sumq=Math.abs(bh-zd);
										String fx=getBusFX(xl,xx1.getSxx(),data);
										if (xlName.equals("") || xlName == null)
										{
											getBusData(list,data,set,xx2.getZdname(),xl,fx,sumq);

										}
										else
										{
											getBusData(list,data,set,xx2.getZdname(),xl,xlName,fx,sumq);
										}
									}
								}
							}
						}
					}
					Set<String> lineSet=new HashSet();
					//换乘方式相同的保留乘坐站点最小的一次
					for(int i=0;i<list.size();i++){
						HCXX xx=(HCXX) list.get(i);
						lineSet.add(xx.getName());
					}
					List<HCXX> temp=new ArrayList();
					for(String str:lineSet) {
						int j=0;
						int count=0;
						for(int i=0;i<list.size();i++){
							HCXX xx=(HCXX) list.get(i);
							if (xx.getName().equals(str)){
								int sum=xx.getQzs()+xx.getJzs();
								if (count==0){
									j=i;
									count=sum;
								}
								if(count!=0&&sum<count){
									j=i;
									count=sum;
								}
							}
						}
						temp.add((HCXX) list.get(j));
					}
					list=temp;
				}
			}
			handler.sendEmptyMessage(1);
		}
	};


	//获取换乘详细信息
	public void  getBusData(List list,List<ZDXX> data,Set<Integer> set,String zdmc,int line,String fx,int sumq){
		for(int xl:set){
			int zd=-1;
			int zdz=-1;
			for(int m=0;m<data.size();m++){
				ZDXX xx=data.get(m);
				if(xx.getZdname().equals(zdmc)&&xx.getXl()==xl){
					zd=xx.getZd();
				}
				if(xx.getZdname().equals(jszd)&&xx.getXl()==xl){
					zdz=xx.getZd();
				}
			}
			if (zd!=-1&&zdz!=-1){//又换成信息
				String hcfx="";
				int sumh=Math.abs(zdz-zd);
				if (zd<zdz){//下行
					hcfx=getBusFX(xl,0,data);
				}else{//上行
					hcfx=getBusFX(xl,1,data);
				}
				HCXX hcxx=new HCXX();
				hcxx.setQd(qszd);
				hcxx.setZd(jszd);
				hcxx.setQzs(sumq);
				hcxx.setJzs(sumh);
				hcxx.setQzfx(fx);
				hcxx.setHcfx(hcfx);
				hcxx.setName(line+getResources().getString(R.string.daolu)+"----->"+xl+getResources().getString(R.string.daolu)+getResources().getString(R.string.dianjichak));
				hcxx.setMemo(getResources().getString(R.string.ccz)+line+getResources().getString(R.string.daolufangx)+fx+getResources().getString(R.string.gongjiaojj)+sumq+getResources().getString(R.string.zdd)+zdmc+getResources().getString(R.string.hc)+xl+getResources().getString(R.string.daolufangx)+hcfx+getResources().getString(R.string.gongjiaojj)+sumh+getResources().getString(R.string.zdd));


				list.add(hcxx);
			}
		}
	}

	//获取换乘详细信息
	public void  getBusData(List list,List<ZDXX> data,Set<Integer> set,String zdmc,int line,String lineName,String fx,int sumq){
		for(int xl:set){
			int zd=-1;
			int zdz=-1;
			String xlName = "";
			for(int m=0;m<data.size();m++){
				ZDXX xx=data.get(m);
				if(xx.getZdname().equals(zdmc)&&xx.getXl()==xl){
					zd=xx.getZd();
					xlName = xx.getXlname();
				}
				if(xx.getZdname().equals(jszd)&&xx.getXl()==xl){
					zdz=xx.getZd();
					xlName = xx.getXlname();
				}
			}
			if (zd!=-1&&zdz!=-1){//又换成信息
				String hcfx="";
				int sumh=Math.abs(zdz-zd);
				if (zd<zdz){//下行
					hcfx=getBusFX(xl,0,data);
				}else{//上行
					hcfx=getBusFX(xl,1,data);
				}
				HCXX hcxx=new HCXX();
				hcxx.setQd(qszd);
				hcxx.setZd(jszd);
				hcxx.setQzs(sumq);
				hcxx.setJzs(sumh);
				hcxx.setQzfx(fx);
				hcxx.setHcfx(hcfx);
				hcxx.setName(line+getResources().getString(R.string.daolu)+"----->"+xl+getResources().getString(R.string.daolu)+getResources().getString(R.string.dianjichak));
				hcxx.setMemo(getResources().getString(R.string.ccz)+line+getResources().getString(R.string.daolufangx)+fx+getResources().getString(R.string.gongjiaojj)+sumq+getResources().getString(R.string.zdd)+zdmc+getResources().getString(R.string.hc)+xl+getResources().getString(R.string.daolufangx)+hcfx+getResources().getString(R.string.gongjiaojj)+sumh+getResources().getString(R.string.zdd));

				list.add(hcxx);
			}
		}
	}

	//将数据库查询的返回数据 填充到控件
	public void initAutoDataText(List<HCXX> data) {
		String[] str=new String[data.size()];
//		for(int i=0;i<data.size();i++){
//			HCXX hcxx=data.get(i);
//			str[i]=""+hcxx.getName();
//		}
		if (data.size()==0){
			ts.setText(getResources().getString(R.string.wzdfa));
		}

		mAdapter = new ListViewAdapterM(this,  data);

		show.setAdapter(this.mAdapter);
//		AutoAdapter<String> adapter=new AutoAdapter<String>(this,R.layout.zdxq, str);
//		show.setAdapter(adapter);
	}

	//计算换乘车的方向
	public String getBusFX(int xl,int sxx,List<ZDXX> data){
		int zd=0;
		String value="";
		for(int i=0;i<data.size();i++){
			ZDXX zdxx=data.get(i);
			if (zdxx.getXl()==xl&&zdxx.getSxx()==sxx){
				if (sxx==0){
					if (zdxx.getZd()>zd){
						zd=zdxx.getZd();
						value=zdxx.getZdname();
					}
				}
				if(sxx==1){
					if (zdxx.getZd()==1){
						value=zdxx.getZdname();
					}
				}
			}
		}
		return value;
	}


	//返回按钮时间
	public void back(View paramView) {
		onBackPressed();
	}
}
