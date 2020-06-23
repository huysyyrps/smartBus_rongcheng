package main.smart.bus.view;

import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep.TransitRouteStepType;

import main.smart.rcgj.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BaiduRouteView extends View{
	private String qd;
	private String zd;
	private int sum=2;
//	private String[] strText;
	private int mColWidth;//���
	private int mTextColor;//������ɫ
	private int mBusLineColor;// ��·��ɫ
	private Bitmap mEndIcon;//�յ�ͼƬ
	private Bitmap mBeginIcon;//ʼ��ͼƬ
	private int mLinkSize;//������ϸ
	private int mTextSize;//�����С
	private Bitmap walkwayIcon;//����ͼƬ
	private Bitmap buswayIcon;//����ͼƬ

	private TransitRouteLine mTrLine = null;
	//private TransitRouteResult mRes = null;
	
	public BaiduRouteView(Context context) {
		super(context);
	}
	//��ʼ����Ϣ
	public BaiduRouteView (Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		Resources localResources = getResources();
		mTextColor = localResources.getColor(R.color.black_text);
		mBusLineColor = localResources.getColor(R.color.bg_gray);
		mEndIcon = BitmapFactory.decodeResource(getResources(),R.drawable.sketch_finish);
		mBeginIcon = BitmapFactory.decodeResource(getResources(),R.drawable.sketch_start);
		buswayIcon=BitmapFactory.decodeResource(getResources(), R.drawable.bustransfer_busway);
		walkwayIcon=BitmapFactory.decodeResource(getResources(), R.drawable.bustransfer_walkway);
		mLinkSize = localResources.getDimensionPixelSize(R.dimen.busline_graph_link_size);
		mTextSize = localResources.getDimensionPixelSize(R.dimen.busline_graph_node_text_size);
	
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if(mTrLine == null)
			return;
		paintLinks(canvas);//ֱ��
		paintNodes(canvas);//��ͼ
	}
	
	
	/**
	 * ��ֱ��
	 * @param paramCanvas
	 */
	public void paintLinks(Canvas paramCanvas) {
		paramCanvas.save();
		// ���廭�� ��ϸ����ɫ
		Paint localPaint = new Paint(1);
		int width=mBeginIcon.getWidth();//��ʼͼ��Ŀ��
		int height=mBeginIcon.getHeight();//��ʼͼ��Ŀ��
		int linesize=mColWidth*(sum-1);//�߳�
		paramCanvas.translate(width, height);
		localPaint.setColor(this.mBusLineColor);
		localPaint.setStrokeWidth(this.mLinkSize);	
		paramCanvas.drawLine(0, 0, 0, linesize, localPaint);//��һ��ֱ��
		paramCanvas.restore();
	}
	
	//��ͼ��
	public void paintNodes(Canvas paramCanvas) {
		paramCanvas.save();
		Paint paint = new Paint(1);
		Paint localPaint = new Paint(1);
		int width=mBeginIcon.getWidth();//��ʼͼ��Ŀ��
		int height=mBeginIcon.getHeight();//��ʼͼ��Ŀ��
		int wid=this.buswayIcon.getWidth();
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(32);
		paint.setColor(this.mTextColor);
		localPaint.setStyle(Paint.Style.FILL);
		localPaint.setTextSize(this.mTextSize);
		localPaint.setColor(this.mTextColor);
		paramCanvas.translate(0,0);
		for(int i=0;i<sum;i++){
			if (i==0){
				paramCanvas.drawBitmap(this.mBeginIcon, width*2/3, 10, null);
				paramCanvas.translate(width*3/2,height/3*2);
				paramCanvas.drawText(qd, 0, 0, paint);
				paramCanvas.translate(-width*3/2,mColWidth-height/3*2);
			}else if(i==sum-1){
				paramCanvas.drawBitmap(this.mEndIcon, width*2/3, 20, null);
				paramCanvas.translate(width*3/2,height/3*2);
				paramCanvas.drawText(zd, 0, 0, paint);
			}else{
//				int size=strText[i-1].length();
//				int cou= strText[i-1].indexOf("����");
//				if (cou>=0){//����
				TransitStep trStep = mTrLine.getAllStep().get(i-1);
				if(trStep.getStepType() == TransitRouteStepType.WAKLING)
				{
					paramCanvas.drawBitmap(this.walkwayIcon,width-wid/2, 0,null);
				}else{//������
					paramCanvas.drawBitmap(this.buswayIcon,width-wid/2, 0,null);
				}
				String strText = trStep.getInstructions();
				if (strText.length()>16){
					paramCanvas.translate(width*3/2,height/2);
					paramCanvas.drawText(strText.substring(0, 16), 0, 0, paint);
					paramCanvas.translate(0,height/2);
					paramCanvas.drawText(strText.substring(16, strText.length()), 0, 0, paint);
					paramCanvas.translate(-width*3/2,mColWidth-height/3*2-height/2);
				}else{
					paramCanvas.translate(width*3/2,height/2);
					paramCanvas.drawText(strText, 0, 0, paint);
					paramCanvas.translate(-width*3/2,mColWidth-height/2);
				}
			}
		}
	}
	
	
	//�����������»�ͼ
//	public void setData(String qd1,String zd1,String data){
//		qd=qd1;
//		zd=zd1;
//		//strText=null;
//		//strText= data.split(";");
//		
//		sum=strText.length+2;
//		setMeasuredDimension(getMeasuredWidth(), mColWidth*(sum+2));
//		invalidate();
//	}

	//�����������»�ͼ
	public void setData(TransitRouteLine trLine){
		mTrLine = trLine;
		qd = trLine.getStarting().getTitle();
		zd = trLine.getTerminal().getTitle();
		//strText=null;
		//strText= data.split(";");
		
		sum = mTrLine.getAllStep().size()+2;
		setMeasuredDimension(getMeasuredWidth(), mColWidth*(sum+2));
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mColWidth = getMeasuredWidth() / 5;
		setMeasuredDimension(getMeasuredWidth(), mColWidth*10);
	}

	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
	}
	
	
	
	
}
