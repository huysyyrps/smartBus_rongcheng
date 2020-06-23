 package main.smart.activity;

import java.util.List;

import com.readystatesoftware.viewbadger.BadgeView;

import main.smart.rcgj.R;
import main.smart.bus.bean.InterfaceBean;
import main.smart.common.util.ConstData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter2 extends BaseAdapter {

    private Context mContext;
    private List<InterfaceBean> mList;
    public static BadgeView mBadgeView;
	  
	public GridAdapter2(Context paramContext,List<InterfaceBean> interfaceList) {
		// TODO Auto-generated constructor stub
		this.mContext = paramContext;
	    this.mList = interfaceList;
	    
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
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
		InterfaceBean bean = mList.get(position);
		//Integer labelId =((Integer)mLabelMap.get(key)).intValue();
		griditem.mLabel.setText(bean.getTitle());
		//Integer iconId =(Integer)mImageMap.get(key);
		try {
	
			if (bean.getIconType().equals("0"))
			{
				//Bitmap bm = BitmapFactory.decodeResource(this.mContext.getResources(), bean.getDrawable());
				
				if (bean.getTitle().equals("����"))
				{
					griditem.mIcon.setBackgroundResource(R.drawable.grid_icon_bg2);
				}
				else
				{
					griditem.mIcon.setBackgroundResource(R.drawable.grid_icon_bg);
				}
				griditem.mIcon.setImageResource(bean.getDrawable());
//				Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),
//						bean.getDrawable());
				if (bean.getTitle().equals("����")&&ConstData.bUpdateNotice)//&&ConstData.bUpdateNotice
				{
					BadgeView badgeView = new BadgeView(mContext,griditem.mIcon);  
					badgeView.setText("new");  
					badgeView.show(); 
				    GridAdapter2.mBadgeView = badgeView;
					
				}
				Options opts = new Options();
				Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), bean.getDrawable(), opts);
			}
			else if (bean.getIconType().equals("1"))
			{
				griditem.mIcon.setBackgroundResource(R.drawable.grid_icon_bg);
				//griditem.mIcon.setBackgroundColor(Color.white);
				if (bean.getBmp()!= null)
				{
					griditem.mIcon.setBackgroundColor(Color.TRANSPARENT);
					//Bitmap bmp = MenuActivity.getHttpBitmap(bean.getIcon());
					griditem.mIcon.setImageBitmap(bean.getBmp());
					
				}
				
			
//				String path = bean.getPath();
//			
//				File file = new File(path);  
//				if (file.exists()) 
//				{  
//					Bitmap bm = BitmapFactory.decodeFile(path);  
//					griditem.mIcon.setImageBitmap(bm);
//				}
			}
	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;
//		return null;
	}
	
	class GridItem
	{
		ImageView mIcon;
	    TextView mLabel;
	}

}
