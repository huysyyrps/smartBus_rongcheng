package main.smart.bus.view;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import main.smart.rcgj.R;
import main.smart.bus.bean.BusBean;
import main.smart.bus.bean.StationBean;
import main.smart.bus.bean.StationRegion;
import main.smart.common.util.ConstData;

public class RunBusView extends View {

	private String lineCode;				//线路
	private int station;					//站点
	private int sxx;          				//上下行
	private int mLinkSize;    				//线条粗细
	private int mTextSize;   				//字体大小
	private int mTextColor;   				//字体颜色
	private int width;						//宽度
	private int height;						//高度
	private Bitmap mEndIcon;  				//终点图片
	private Bitmap mBeginIcon;				//始发图片
	private int mBusLineColor;				//线路颜色
	private Bitmap mStation;   				//即将到站
	private Bitmap mStationIcon;			//站点图片
	private int stationnum=0;				//站点数
	private GetComeTime myListener;
	private Activity activity;
	private List<StationRegion> stlist;		//站点坐标位置
	private List<BusBean> list=new ArrayList<BusBean>();
	private List<StationBean> Stations=new ArrayList<StationBean>();

	public RunBusView(Context context) {
		super(context);
	}

	//初始化信息
	public RunBusView (Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		activity=(Activity) paramContext;
		myListener=(GetComeTime)activity;
		Resources localResources = getResources();
		mTextColor = localResources.getColor(R.color.green);
		mBusLineColor = localResources.getColor(R.color.busline_graph_color);
		mEndIcon = BitmapFactory.decodeResource(getResources(),R.drawable.sketch_finish);
		mBeginIcon = BitmapFactory.decodeResource(getResources(),R.drawable.sketch_start);
		mLinkSize = localResources.getDimensionPixelSize(R.dimen.busline_graph_link_size);
		mTextSize = localResources.getDimensionPixelSize(R.dimen.busline_graph_node_text_size);
		mStationIcon = BitmapFactory.decodeResource(getResources(),R.drawable.staitonlist_station_noline);
		mStation = BitmapFactory.decodeResource(getResources(),R.drawable.staitonlist_station_noline_zd);
		width=mBeginIcon.getWidth()/2;//开始图标的宽度
		height=mBeginIcon.getHeight()*2;//开始图标的宽度
	}


	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		stlist=new ArrayList<StationRegion>();
		paintLinks(canvas);//画直线
		paintNodes(canvas);//画站点
	}

	/**
	 * 画直线
	 * @param paramCanvas
	 */
	public void paintLinks(Canvas paramCanvas) {
		paramCanvas.save();
		int linesize=2*height*(stationnum-1);
		Paint localPaint = new Paint(1);
		localPaint.setColor(this.mBusLineColor);
		localPaint.setStrokeWidth(this.mLinkSize);
		paramCanvas.translate(width+15, height/3);
		paramCanvas.drawLine(0, 0, 0, linesize, localPaint);
		paramCanvas.restore();
	}

	/**
	 * 画站点
	 * @param paramCanvas
	 */
	public void paintNodes(Canvas paramCanvas) {
		paramCanvas.save();
		float x=width/2;
		float y=height/2;
		Paint paint = new Paint(1);
		Paint localPaint = new Paint(1);
		paint.setTextSize(48);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(this.mTextColor);
		localPaint.setStyle(Paint.Style.FILL);
		localPaint.setTextSize(this.mTextSize);
		localPaint.setColor(this.mTextColor);
		paramCanvas.translate(0,0);
		if(sxx==0){
			for(int i=0;i<Stations.size();i++){
				StationBean bean=Stations.get(i);
				int sum=BusStation(i+1);
				String txt=bean.getStationName();
				if (i==0){
					paramCanvas.drawBitmap(mBeginIcon, x, y-40, null);
					paramCanvas.translate(2*width,y+height/2);
					paramCanvas.drawText(txt, 60, -60, paint);
					paramCanvas.translate(0,height/2);
					paramCanvas.drawText(sum+getResources().getString(R.string.now_station), 60, -40, localPaint);
					paramCanvas.translate(-2*width,-y-height);
					SumStationXY(x+width,y+height,x-width,y-height,(i+1),sxx);
				}else if (i==Stations.size()-1){
					y=y+height*2;
					paramCanvas.drawBitmap(mEndIcon,x, y-30, null);
					paramCanvas.translate(2*width,y+height/2);
					paramCanvas.drawText(txt, 60, -60, paint);
					paramCanvas.translate(0,height/2);
					paramCanvas.drawText(sum+getResources().getString(R.string.now_station), 60, -40, localPaint);
					SumStationXY(x+width,y+height,x-width,y-height,(i+1),sxx);
				}else{
					y=y+height*2;
					if (sum>0){
						paramCanvas.drawBitmap(mStation,x+15, y,null);
					}else{
						paramCanvas.drawBitmap(mStationIcon,x+15, y,null);
					}
					paramCanvas.translate(2*width,y+height/2);
					paramCanvas.drawText(txt, 60, -60, paint);
					paramCanvas.translate(0,height/2);
					paramCanvas.drawText(sum+getResources().getString(R.string.now_station), 60, -40, localPaint);
					paramCanvas.translate(-2*width,-y-height);
					SumStationXY(x+width,y+height,x-width,y-height,(i+1),sxx);
				}
			}
		}else{
			for(int i=Stations.size()-1;i>=0;i--){
				StationBean bean=Stations.get(i);
				int sum=BusStation(i+1);
				String txt=bean.getStationName();
				if (i==Stations.size()-1){
					paramCanvas.drawBitmap(mBeginIcon, x, y-30, null);
					paramCanvas.translate(2*width,y+height/2);
					paramCanvas.drawText(txt, 60, -60, paint);
					paramCanvas.translate(0,height/2);
					paramCanvas.drawText(sum+getResources().getString(R.string.now_station), 60, -40, localPaint);
					paramCanvas.translate(-2*width,-y-height);
					SumStationXY(x+width,y+height,x-width,y-height,(i+1),sxx);
				}else if (i==0){
					y=y+height*2;
					paramCanvas.drawBitmap(mEndIcon,x, y-40, null);
					paramCanvas.translate(2*width,y+height/2);
					paramCanvas.drawText(txt, 60, -60, paint);
					paramCanvas.translate(0,height/2);
					paramCanvas.drawText(sum+getResources().getString(R.string.now_station), 60, -40, localPaint);
					SumStationXY(x+width,y+height,x-width,y-height,(i+1),sxx);
				}else{
					y=y+height*2;
					if (sum>0){
						paramCanvas.drawBitmap(this.mStation,x+15, y,null);
					}else{
						paramCanvas.drawBitmap(this.mStationIcon,x+15, y,null);
					}
					paramCanvas.translate(2*width,y+height/2);
					paramCanvas.drawText(txt, 60, -60, paint);
					paramCanvas.translate(0,height/2);
					paramCanvas.drawText(sum+getResources().getString(R.string.now_station), 60, -40, localPaint);
					paramCanvas.translate(-2*width,-y-height);
					SumStationXY(x+width,y+height,x-width,y-height,(i+1),sxx);
				}
			}
		}
		paramCanvas.restore();
	}

	/**
	 * 刷新即将到站车辆
	 */
	public Integer BusStation(int zd){
		int sum=0;
		for(int i=0;i<list.size();i++){
			BusBean bus=list.get(i);
			if (sxx==0){
				if(bus.getLeftStation()+1==zd){
					sum=sum+1;
				}
			}else{
				if(bus.getLeftStation()-1==zd){
					sum=sum+1;
				}
			}
		}
		return sum;
	}

	/**
	 * 计算站点坐标范围
	 * @param station
	 * @param sxx
	 */
	public void  SumStationXY(float maxX,float maxY,float minX,float minY,int station,int sxx){
		StationRegion sr=new StationRegion();
		sr.setSxx(sxx);
		sr.setMaxX(maxX);
		sr.setMaxY(maxY);
		sr.setMinX(minX);
		sr.setMinY(minY);
		sr.setStation(station);
		stlist.add(sr);
	}

	/**
	 * 刷新数据重新画图
	 * @param mStations
	 */
	public void setData(List<StationBean> mStations, int sx ,String line){
		sxx=sx;
		lineCode=line;
		Stations=mStations;
		stationnum=mStations.size();
		setMeasuredDimension(getMeasuredWidth(), 2*height*(stationnum+2));
		invalidate();
	}

	/**
	 * 更新车辆数据
	 */
	public void updateBus(List<BusBean> data){
		list=data;
		stationnum=Stations.size();
		setMeasuredDimension(getMeasuredWidth(), 2*height*(stationnum+2));
		invalidate();
	}



	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredWidth(), 2*height*(stationnum+2));
	}


	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float x=event.getX();
		float y=event.getY();
		for(int i=0;i<stlist.size();i++){
			StationRegion sr=stlist.get(i);
			float maxX=sr.getMaxX();
			float minX=sr.getMinX();
			float maxY=sr.getMaxY();
			float minY=sr.getMinY();
			if(x>=minX&&x<=maxX&&y>=minY&&y<=maxY){
				station=sr.getStation();
				myListener.getTime(ConstData.goURL+"/SumComeTime", lineCode, sxx, station);
				break;
			}
		}
		return super.onTouchEvent(event);
	}

	public interface GetComeTime{
		public void getTime(String url,String line, int sxx, int zd);
	}


}
