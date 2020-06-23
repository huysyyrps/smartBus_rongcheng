package main.smart.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.smart.rcgj.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class GridAdapter extends BaseAdapter
{
 
  private Context mContext;
  private List<String> mItems;
//���ܲ˵�ͼƬ
	public static Map mImageMap = new HashMap() {

		
		{
			put("bus", R.drawable.icon_bus);
			put("bike", R.drawable.icon_bicycle);
			put("taxi", R.drawable.icon_taxi);
			put("subway", R.drawable.icon_metro);
			put("coach", R.drawable.icon_coach);
			put("train", R.drawable.icon_train);
			put("flight", R.drawable.icon_plane);
			put("traffic", R.drawable.icon_traffic);
			put("user", R.drawable.icon_user);
			put("set", R.drawable.icon_setting);
			put("navigation", R.drawable.icon_navigate);
			put("nearby", R.drawable.icon_nearby);
			put("barcode", R.drawable.icon_barcode);
			put("share", R.drawable.icon_share);
			put("help",R.drawable.icon_traffic);
			put("state",R.drawable.ic_bookmark);
			put("huoche",R.drawable.huohuo);
			
		}
};
//���ܲ˵�ͼƬ�ϵ�����
public static  Map mLabelMap = new HashMap() {

	
		{
			put("bus", R.string.bus);
			put("bike", R.string.bicycle);
			put("taxi", R.string.taxi);
			put("subway", R.string.metro);
			put("coach", R.string.coach);
			put("train", R.string.train);
			put("flight", R.string.plane);
			put("traffic", R.string.traffic);
			put("user", R.string.user);
			put("set", R.string.setting);
			put("navigation", R.string.navigation);
			put("nearby", R.string.nearby);
			put("barcode", R.string.barcode);
			put("share", R.string.share);
			put("help",R.string.help);
			put("state",R.string.state);
		}
};

  public GridAdapter(Context paramContext, List<String> paramList)
  {
    this.mContext = paramContext;
    this.mItems = paramList;
  
  }

  public int getCount()
  {
    return this.mItems.size();
  }

  public Object getItem(int paramInt)
  {
    return this.mItems.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }
/**
 * ÿһ��
 * */
  @Override
  public View getView(int paramInt, View view, ViewGroup viewGroup)
  {
		GridItem griditem;
		if (view == null)
		{
			view = LayoutInflater.from(mContext).inflate(R.layout.main_page_grid_item, null);
			griditem = new GridItem();
			griditem.mLabel = (TextView)view.findViewById(R.id.label);
			griditem.mIcon = (ImageView)view.findViewById(R.id.icon);
			view.setTag(griditem);
		} else
		{
			griditem = (GridItem)view.getTag();
		}
		String key =mItems.get(paramInt);
		Integer labelId =((Integer)mLabelMap.get(key)).intValue();
		griditem.mLabel.setText(labelId);
		Integer iconId =(Integer)mImageMap.get(key);
		try {
			griditem.mIcon.setImageResource(iconId);
	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
  }

  class GridItem
  {
    ImageView mIcon;
    TextView mLabel;
  }
}
