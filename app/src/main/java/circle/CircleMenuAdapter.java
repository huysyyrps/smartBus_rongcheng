package circle;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import main.smart.rcgj.R;


public class CircleMenuAdapter extends BaseAdapter {
//	private DisplayImageOptions options;//加载网络图片
	AnimateFirstDisplayListener lsn = new AnimateFirstDisplayListener();
	private List<ItemInfo> data;
	private Context context ;
	public CircleMenuAdapter(Context context, List<ItemInfo> itemInfos) {
		this.data = itemInfos;
		this.context = context;
//		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.touxiang) // 设置图片在下载期间显示的图片
//				.showImageForEmptyUri(R.drawable.touxiang)// 设置图片Uri为空或是错误的时候显示的图片
//				.showImageOnFail(R.drawable.touxiang) // 设置图片加载/解码过程中错误时候显示的图片
//				.cacheOnDisk(true)
//				.considerExifParams(true)
//				.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
//				.cacheOnDisc(true)// 设置下载的图片是否缓存在SD卡中
//				.displayer(new FadeInBitmapDisplayer(20))// 是否图片加载好后渐入的动画时间
//				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();// 构建完成
	}

	@Override
	public int getCount() {

		if (data == null || data.isEmpty()) {
			return 0;
		}
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(parent.getContext(), R.layout.item_circle_menu, null);
			holder = new ViewHolder();
			holder.iv = (ImageView) convertView.findViewById(R.id.iv_circle_menu_item);
			holder.tv = (TextView) convertView.findViewById(R.id.tv_circle_menu_item);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		ItemInfo item = data.get(position);
		if (item != null) {
		//	holder.iv.setImageResource(item.getImgId());
			
//			ImageLoader imageloader = ImageLoader.getInstance();
//			imageloader.init(ImageLoaderConfiguration.createDefault(context));
//			imageloader.displayImage(
//					item.getImgId().toString(), holder.iv, options,
//					lsn);
			holder.iv.setImageResource(item.getImgId());;
			holder.tv.setText(item.getText());
		}
		return convertView;
	}

	class ViewHolder {
		ImageView iv;
		TextView tv;
	}

}
