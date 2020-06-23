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
//	private DisplayImageOptions options;//��������ͼƬ
	AnimateFirstDisplayListener lsn = new AnimateFirstDisplayListener();
	private List<ItemInfo> data;
	private Context context ;
	public CircleMenuAdapter(Context context, List<ItemInfo> itemInfos) {
		this.data = itemInfos;
		this.context = context;
//		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.touxiang) // ����ͼƬ�������ڼ���ʾ��ͼƬ
//				.showImageForEmptyUri(R.drawable.touxiang)// ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
//				.showImageOnFail(R.drawable.touxiang) // ����ͼƬ����/��������д���ʱ����ʾ��ͼƬ
//				.cacheOnDisk(true)
//				.considerExifParams(true)
//				.cacheInMemory(true)// �������ص�ͼƬ�Ƿ񻺴����ڴ���
//				.cacheOnDisc(true)// �������ص�ͼƬ�Ƿ񻺴���SD����
//				.displayer(new FadeInBitmapDisplayer(20))// �Ƿ�ͼƬ���غú���Ķ���ʱ��
//				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();// �������
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
