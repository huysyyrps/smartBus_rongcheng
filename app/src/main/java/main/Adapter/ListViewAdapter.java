package main.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;



import java.util.List;
import java.util.Map;

import main.smart.rcgj.R;


public class ListViewAdapter extends BaseAdapter {
	private List<Map<String, Object>> list;
	private Context context;
	private int selectID;
	private OnMyCheckChangedListener mCheckChange;


	public ListViewAdapter(Context context, List<Map<String, Object>> list) {
		this.context = context;
		this.list = list;
	}


	public int getCount() {
		return list.size();
	}


	public Object getItem(int position) {
		return getItem(position);
	}


	public long getItemId(int position) {
		return position;
	}


	public void setSelectID(int position) {
		selectID = position;
	}


	@SuppressLint("InflateParams")
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewCache viewCache;


		if (convertView == null) {
			viewCache = new ViewCache();
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
			viewCache.linearLayout = (LinearLayout) convertView.findViewById(R.id.item_layout);
			viewCache.itemID = (ImageView) convertView.findViewById(R.id.id);
			viewCache.itemName = (TextView) convertView.findViewById(R.id.name);
			viewCache.radioBtn = (RadioButton) convertView.findViewById(R.id.radioBtn);

			convertView.setTag(viewCache);
		} else {
			viewCache = (ViewCache) convertView.getTag();
		}
		viewCache.itemID.setImageResource(Integer.parseInt(list.get(position).get("ID").toString()));
	//	viewCache.itemID.setText(list.get(position).get("ID"));
		viewCache.itemName.setText(list.get(position).get("Name").toString());

		if (selectID == position) {
			viewCache.radioBtn.setChecked(true);
		} else {
		//	viewCache.linearLayout.setBackgroundColor(0);
			viewCache.radioBtn.setChecked(false);
		}


		viewCache.radioBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				selectID = position;
				

				if (mCheckChange != null)
					mCheckChange.setSelectID(selectID);
			}
		});

		return convertView;
	}


	public void setOncheckChanged(OnMyCheckChangedListener l) {
		mCheckChange = l;
	}


	public interface OnMyCheckChangedListener {
		void setSelectID(int selectID);
	}

	class ViewCache {
		LinearLayout linearLayout;
		TextView  itemName;
		ImageView itemID;
		RadioButton radioBtn;
	}
}
