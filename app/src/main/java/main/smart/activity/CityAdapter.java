package main.smart.activity;

import java.util.List;

import main.smart.rcgj.R;
import main.smart.common.bean.SwitchCity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CityAdapter extends BaseAdapter {
	
	private List<SwitchCity> mList;
	private Context mContext;
	 public CityAdapter(Context pContext, List<SwitchCity> pList) {
		         this.mContext = pContext;
		         this.mList = pList;
		   }

	public CityAdapter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		
		for(int i=0;i<mList.size();i++){
			Log.e(null, "�����õ������г�������"+mList.get(position).getCityName());
		}
		 LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
		         convertView=_LayoutInflater.inflate(R.layout.city_select_item, null);
		         if(convertView!=null)
		         {
		             TextView _TextView1=(TextView)convertView.findViewById(R.id.select_city_name);
		             TextView _TextView2=(TextView)convertView.findViewById(R.id.select_city_ip);
		             _TextView1.setText(mList.get(position).getCityName());
		             //_TextView2.setText(mList.get(position).getIp());
		             _TextView2.setText("");
		         }
		         return convertView;

	}
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return super.getDropDownView(position, convertView, parent);
	}
    
}
