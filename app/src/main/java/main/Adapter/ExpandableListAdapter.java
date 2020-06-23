package main.Adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import main.smart.bus.bean.FavorLineBean;
import main.smart.rcgj.R;


public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private List<List<FavorLineBean>> child_text_array;
	private Context context;

	private List<FavorLineBean> list;

	public ExpandableListAdapter(Context context,
                                 List<FavorLineBean> list, List<List<FavorLineBean>>  child_text_array) {
		this.context = context;
		this.list = list;
		this.child_text_array = child_text_array;
	}

	/**
	 * 获取一级标签总数
	 */
	@Override
	public int getGroupCount() {
		return list.size();
	}

	/**
	 * 获取一级标签下二级标签的总数
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
        return child_text_array.get(groupPosition).size();
	}

	/**
	 * 获取一级标签内容
	 */
	@Override
	public Object getGroup(int groupPosition) {
		return list.get(groupPosition).getStations().get(0).getStationName();
	}

	/**
	 * 获取一级标签下二级标签的内容
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		//return child_text_array[groupPosition][childPosition];
        return child_text_array.get(groupPosition).get(childPosition);
	}

	/**
	 * 获取一级标签的ID
	 */
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	/**
	 * 获取二级标签的ID
	 */
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	/**
	 * 指定位置相应的组视图
	 */
	@Override
	public boolean hasStableIds() {
		return true;
	}

	/**
	 * 对一级标签进行设置
	 */
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		convertView = (LinearLayout) LinearLayout.inflate(context,
				R.layout.item_group_layout, null);

		ImageView group_icon = (ImageView) convertView
				.findViewById(R.id.img_icon);
		TextView group_title = (TextView) convertView
				.findViewById(R.id.group_title);
		TextView julitext = (TextView) convertView
				.findViewById(R.id.julitext);

		if (isExpanded) {
			julitext.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.group_down, 0);
		} else {
			julitext.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.group_up, 0);
		}
//		group_icon.setImageResource(Integer.parseInt(list.get(groupPosition)
//				.get("img").toString()));
		group_title.setText(list.get(groupPosition).getStations().get(0).getStationName().toString());
		julitext.setText(Double.valueOf(list.get(groupPosition).getJuli()).intValue()+"m");
		return convertView;
	}

	/**
	 * 对一级标签下的二级标签进行设置
	 */
	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		convertView = (LinearLayout) RelativeLayout.inflate(context,
				R.layout.item_child_layout, null);
		TextView child_text = (TextView) convertView
				.findViewById(R.id.child_text);
        TextView bus_line_namefujin = (TextView) convertView
                .findViewById(R.id.bus_line_namefujin);

      //  child_text.setText(child_text_array[groupPosition][childPosition]);
		child_text.setText(child_text_array.get(groupPosition).get(childPosition).getEndStation());
        bus_line_namefujin.setText(child_text_array.get(groupPosition).get(childPosition).getLineName());
		return convertView;
	}

	/**
	 * 当选择子节点的时候，调用该方法
	 */
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
