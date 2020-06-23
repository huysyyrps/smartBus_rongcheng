package main.smart.huoche;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import main.smart.rcgj.R;

public class Pickadapter extends BaseAdapter {

	private String timesla;
	public List<Map<String, Object>> data;
	private Context context;


	@SuppressWarnings("deprecation")
	public Pickadapter(Context context, List<Map<String, Object>> data) {
		this.data = data;
		this.context = context;
		
	}

	@Override
	public int getCount() {
		if (data == null)
			return 0;
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("deprecation")
	
	@Override
	public View getView(int position, View view, ViewGroup group) {
		users u;
		if (view == null) {
			u = new users();
			// �õ�һ��LayoutInflater������Ϊ������Ҫ����������õ���ͼ
			// �������ӵ�view��
			view = LayoutInflater.from(context).inflate(R.layout.activity_datepick, null);
			u.checi = (TextView) view.findViewById(R.id.checi);
			u.starttime = (TextView) view.findViewById(R.id.starttime);
			u.startaction = (TextView) view.findViewById(R.id.startaction);
			u.lishitime = (TextView) view.findViewById(R.id.lishitime);
			u.endtime = (TextView) view.findViewById(R.id.endtime);
			u.endaction = (TextView) view.findViewById(R.id.endaction);
			u.money = (TextView) view.findViewById(R.id.money);
			u.pickno = (TextView) view.findViewById(R.id.pickno);
			u.yingzuo = (TextView) view.findViewById(R.id.yingzuo);
			u.pickend = (TextView) view.findViewById(R.id.pickend);
			u.pickruan = (TextView) view.findViewById(R.id.pickruan);
			
			u.tv_wu = (TextView) view.findViewById(R.id.tv_wu);
			u.tv_ying = (TextView) view.findViewById(R.id.tv_ying);
			u.tv_yw = (TextView) view.findViewById(R.id.tv_yw);
			u.tv_rw = (TextView) view.findViewById(R.id.tv_rw);
			view.setTag(u);
		} else {
			u = (users) view.getTag();

		}
u.checi.setText(data.get(position).get("trainno").toString());
		
		u.starttime.setText(data.get(position).get("starttime").toString());
		u.startaction.setText(data.get(position).get("station").toString());
		u.lishitime.setText(data.get(position).get("costtime").toString());
		u.endtime.setText(data.get(position).get("endtime").toString());
		u.endaction.setText(data.get(position).get("endstation").toString());
		u.money.setText(data.get(position).get("numyz").toString());
		
		if(data.get(position).get("type")==null ){
			u.pickno.setText(data.get(position).get("numwz").toString());
			u.yingzuo.setText(data.get(position).get("numyz").toString());
			u.pickend.setText(data.get(position).get("numyw").toString());
			u.pickruan.setText(data.get(position).get("numrw").toString());
		}else{
			if((data.get(position).get("type").toString()).equals("G") ){
				
				u.pickno.setText(data.get(position).get("numsw").toString());
				u.yingzuo.setText(data.get(position).get("numyd").toString());
				u.pickend.setText(data.get(position).get("numed").toString());
				
				u.pickruan.setVisibility(view.GONE);
				u.tv_wu.setText("����");
				u.tv_ying.setText("һ�ȣ�");
				u.tv_yw.setText("���ȣ�");
				u.tv_rw.setVisibility(view.GONE);
				
			}else if((data.get(position).get("type").toString()).equals("D")){
				u.pickno.setText(data.get(position).get("numrw").toString());
				u.yingzuo.setText(data.get(position).get("numyw").toString());
				u.pickend.setText(data.get(position).get("numed").toString());
				
				u.pickruan.setVisibility(view.GONE);
				u.tv_wu.setText("һ���ԣ�");
				u.tv_ying.setText("�����ԣ�");
				u.tv_yw.setText("���ȣ�");
				u.tv_rw.setVisibility(view.GONE);
				
			}else {
				u.pickno.setText(data.get(position).get("numwz").toString());
				u.yingzuo.setText(data.get(position).get("numyz").toString());
				u.pickend.setText(data.get(position).get("numyw").toString());
				u.pickruan.setText(data.get(position).get("numrw").toString());
			}
		}
		
		
		
		
		
		
			
		
		
		
		return view;
	}

	class users {
		
		TextView starttime;//��ʼʱ��
		TextView startaction;//ʼ��վ
		TextView lishitime;//��ʱʱ��
		TextView checi;//����
		TextView endtime;//����ʱ��
		TextView endaction;//�յ�վ
		TextView money;//Ʊ��
		TextView pickno;//����
		TextView yingzuo;//Ӳ��
		TextView pickend;//Ӳ��
		TextView pickruan;//����
		TextView tv_wu;
		TextView tv_ying;
		TextView tv_yw;
		TextView tv_rw;
		
	}



}
