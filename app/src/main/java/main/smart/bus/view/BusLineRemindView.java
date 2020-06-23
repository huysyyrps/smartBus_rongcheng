package main.smart.bus.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.smart.rcgj.R;
import main.smart.bus.bean.BusBean;
import main.smart.bus.bean.LineBean;
import main.smart.bus.bean.StationBean;
import main.smart.bus.bean.StationRegion;
import main.smart.common.SmartBusApp;
import main.smart.common.bean.CitySetting;
import main.smart.common.util.CharUtil;
import main.smart.common.util.ConstData;
import main.smart.common.util.PreferencesHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.speechsynthesizer.SpeechSynthesizerListener;
import com.baidu.speechsynthesizer.publicutility.DataInfoUtils;
import com.baidu.speechsynthesizer.publicutility.SpeechError;
import com.baidu.speechsynthesizer.publicutility.SpeechLogger;

@SuppressLint("NewApi")
public class BusLineRemindView extends View implements SpeechSynthesizerListener {

	
	private Bitmap mBeginIcon;// ��ʼվͼ��
	private List<BusBean> mBusData;
	private Bitmap mBusIcon;		//��������(��׼)
	private Bitmap mBusIcon1;		//��������(����ͷ)
	private Bitmap mBusIcon2;		//��������(����ͷ)
	private Bitmap mBusIcon3;		//��������(����ͷ)
	private Bitmap mBusIconsx;		//��������(��׼)
	private Bitmap mBusIconsx1;		//��������(����ͷ)
	private Bitmap mBusIconsx2;		//��������(����ͷ)
	private Bitmap mBusIconsx3;		//��������(����ͷ)
	private LineBean mBusLine;		//��·����
	private int mBusLineColor;		//��·��ɫ
	private int mColWidth;			//վ������·�Ϻ������
	private int mRowHeight;			//ֱ��ͼ ���еľ���
	private Bitmap mComingBg;		//������վ
	private Bitmap mEndIcon;		//�յ�վͼ��
	private Bitmap mGetOffIcon;		//�³�ͼ��
	private Bitmap mGetOnIcon;		//�ϳ�ͼ��
	private int mGetOffStationIdx;	//�³�վ��
	private int mGetOnStationIdx;	//�ϳ�վ��
	private int mLinkSize;			//����
	private int mTextColor;			//����Ⱦɫ
	private int mTextSize;			//�����С
	private Bitmap mStationIcon;	//վ���	
	private boolean iden;			//�Ƿ�ѡ������Ҫ��
	private boolean onIden;			//�ϳ�����
	private boolean offIden;		//�³�����	
	private int isFlag=0;			//������ʾ
	private int isshock=0;			//�Ƿ���
	private int sumleftStation=1;	//��ǰվ��
	private int isVideo=0;
	private String mGetOffStationName="";
	private String mGetOnStationName="";
	private PreferencesHelper mPreferenceMan;
	private SpeechSynthesizer speechSynthesizer;
	 private static final String LICENCE_FILE_NAME = Environment.getExternalStorageDirectory()+ "/tts/baidu_tts_licence.dat";
		//��¼ÿ��վ���λ������
	//��¼ÿ��վ���λ������
	private List<StationRegion> stlist=null;
		
	
	
	public BusLineRemindView(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		// TODO Auto-generated constructor stub
	}

	public BusLineRemindView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public BusLineRemindView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		mGetOnStationIdx = ConstData.onBus;
		mGetOffStationIdx = -1;
		
		iden=true;
		onIden=false;
		offIden=false;
		
		if (ConstData.upBus > 0)
		{
			
			mGetOffStationIdx = ConstData.upBus;
		}
		
		
		mGetOffStationName="";
		mGetOnStationName="";
		
		initIcon(); 	
		baiduSpeech(context);
	
	}

	public BusLineRemindView(Context context) {
		//super(context);
		this(context, null);
		
	}

	private void initIcon()
	{
		Resources localResources = getResources();
		this.mPreferenceMan = PreferencesHelper.getInstance();//���ݹ���
		this.isshock=mPreferenceMan.getShock();
		this.isVideo=mPreferenceMan.getVideoType();
		this.sumleftStation=mPreferenceMan.getReminder()+1;
		this.mRowHeight = localResources.getDimensionPixelSize(R.dimen.busline_graph_row_height);
		this.mLinkSize = localResources.getDimensionPixelSize(R.dimen.busline_graph_link_size);
		this.mTextSize = localResources.getDimensionPixelSize(R.dimen.busline_graph_node_text_size);
		this.mTextColor = localResources.getColor(R.color.black_text);
		this.mBusLineColor = localResources.getColor(R.color.busline_graph_color);
		this.mBusIcon = BitmapFactory.decodeResource(getResources(),R.drawable.sketch_busicon);
		this.mBusIcon1=BitmapFactory.decodeResource(getResources(),R.drawable.sketch_busicon_1);
		this.mBusIcon2=BitmapFactory.decodeResource(getResources(),R.drawable.sketch_busicon_2);
		this.mBusIcon3=BitmapFactory.decodeResource(getResources(),R.drawable.sketch_busicon_3);
		this.mBusIconsx=BitmapFactory.decodeResource(getResources(), R.drawable.sketch_busicon_red);
		this.mBusIconsx1=BitmapFactory.decodeResource(getResources(), R.drawable.sketch_busicon_green_1);
		this.mBusIconsx2=BitmapFactory.decodeResource(getResources(), R.drawable.sketch_busicon_green_2);
		this.mBusIconsx3=BitmapFactory.decodeResource(getResources(), R.drawable.sketch_busicon_green_3);
		// ��ɫԲȦվ
		this.mStationIcon = BitmapFactory.decodeResource(getResources(),R.drawable.staitonlist_station_noline);
		// �����г������վ
		this.mComingBg = BitmapFactory.decodeResource(getResources(),R.drawable.staitonlist_station_coming_solid);
		// ʼ��վ
		this.mBeginIcon = BitmapFactory.decodeResource(getResources(),R.drawable.sketch_start);
		// �յ�վ
		this.mEndIcon = BitmapFactory.decodeResource(getResources(),R.drawable.sketch_finish);
		// ��
		this.mGetOnIcon = BitmapFactory.decodeResource(getResources(),R.drawable.stationlist_on);
		// ��
		this.mGetOffIcon = BitmapFactory.decodeResource(getResources(),R.drawable.stationlist_off);
	
	}
	
	private void baiduSpeech(Context context)
	{
		// ���ְ汾����ҪBDSpeechDecoder_V1
        try {
        	System.loadLibrary("gnustl_shared");
            System.loadLibrary("BDSpeechDecoder_V1");
            System.loadLibrary("bd_etts");
            System.loadLibrary("bds");
        } catch (UnsatisfiedLinkError e) {
            SpeechLogger.logD("load BDSpeechDecoder_V1 failed, ignore");
        }
        
        if (!new File(LICENCE_FILE_NAME).getParentFile().exists()) {
            new File(LICENCE_FILE_NAME).getParentFile().mkdirs();
        }
        // ����license��ָ��·��
        InputStream licenseInputStream = getResources().openRawResource(R.raw.temp_license_2015_07_03);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(LICENCE_FILE_NAME);
            byte[] buffer = new byte[1024];
            int size = 0;
            while ((size = licenseInputStream.read(buffer, 0, 1024)) >= 0) {
                SpeechLogger.logD("size written: " + size);
                fos.write(buffer, 0, size);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                licenseInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
		Activity activity= (Activity) context;
		speechSynthesizer = SpeechSynthesizer.newInstance(SpeechSynthesizer.SYNTHESIZER_AUTO, context, "holder", this);
        // ���滻Ϊ����������ƽ̨ע��Ӧ�õõ���apikey��secretkey (������Ȩ)
        speechSynthesizer.setApiKey(CitySetting.bdKey, CitySetting.bdSecret);
        // ���滻Ϊ����������ƽ̨��ע��Ӧ�õõ���App ID (������Ȩ)
        speechSynthesizer.setAppId(CitySetting.bdAppId);
        // ������Ȩ�ļ�·��
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, LICENCE_FILE_NAME);
        // TTS�������Դ�ļ������Է�������ɶ�Ŀ¼�������������
        String ttsTextModelFilePath =SmartBusApp.getInstance().getApplicationContext().getApplicationInfo().dataDir + "/lib/libbd_etts_text.dat.so";
        String ttsSpeechModelFilePath =SmartBusApp.getInstance().getApplicationContext().getApplicationInfo().dataDir + "/lib/libbd_etts_speech_female.dat.so";
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, ttsTextModelFilePath);
        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, ttsSpeechModelFilePath);
        DataInfoUtils.verifyDataFile(ttsTextModelFilePath);
        DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_DATE);
        DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_SPEAKER);
        DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_GENDER);
        DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_CATEGORY);
        DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_LANGUAGE);
        speechSynthesizer.initEngine();
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		//super.onDraw(canvas);
		stlist=new ArrayList<StationRegion>();
		Log.e("BusLineRemind","�����ˣ�������");
		paintGraph(canvas);//����
		paintBuses(canvas);//����
	}
	

	@Override
	public void onBufferProgressChanged(SpeechSynthesizer arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancel(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(SpeechSynthesizer arg0, SpeechError arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNewDataArrive(SpeechSynthesizer arg0, byte[] arg1,
			boolean arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeechFinish(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeechPause(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeechProgressChanged(SpeechSynthesizer arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeechResume(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpeechStart(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartWorking(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSynthesizeFinish(SpeechSynthesizer arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public LineBean getBusLine() {
		return this.mBusLine;
	}

	public void setBusLine(LineBean paramBusLine) {
		this.mBusLine = paramBusLine;
		this.updateBuses();
		invalidate();
	}
	
	public void setBusLineAndOnOff(LineBean paramBusLine, int paramInt1,int paramInt2) {
		this.mBusLine = paramBusLine;
		//this.mGetOnStationIdx = paramInt1;
		//this.mGetOffStationIdx = paramInt2;
		invalidate();
	}

	public void setGetOnOffStations(int paramInt1, int paramInt2) {
		//this.mGetOnStationIdx = paramInt1;
		//this.mGetOffStationIdx = paramInt2;
		invalidate();
	}

	//�ж��Ƿ��������
	private void updateBuses(){
		if(mBusData!=null){
			int sxx =this.mBusLine.getLineId();//������
			Iterator<BusBean> iterator = mBusData.iterator();
			//ûѡ���ϳ�ֻ���ϳ�����
			if (!iden){
				if(!onIden){
					while (iterator.hasNext()) {
						BusBean businfo = iterator.next();
						String busSxx=businfo.getSxx();				//������
						float leftPos = businfo.getLeftStation();	//����վ��
						if (sxx==0){
							if(busSxx.equals("0")){
								if(leftPos+sumleftStation==mGetOnStationIdx&&mGetOnStationIdx!=0){
									isFlag=1;
									onIden=true;
									Thread th=new Thread(runnable);
									th.start();
								}
							}
						}else{
							if(busSxx.equals("1")){
								if(leftPos-sumleftStation==mGetOnStationIdx&&mGetOnStationIdx!=0){
									isFlag=1;
									onIden=true;
									Thread th=new Thread(runnable);
									th.start();
								}
							}
						}
					}
				}
				return;
			}
			//ѡ�����ϳ� ����վ������
			//�Ƚ����ϳ�����
			if(!onIden){
				while (iterator.hasNext()) {
					BusBean businfo = iterator.next();
					String busSxx=businfo.getSxx();				//������
					String busCode=businfo.getBusCode();		//����
					float leftPos = businfo.getLeftStation();	//����վ��
					if (sxx==0){
						if(busSxx.equals("0")&&busCode.equals(ConstData.BusCode)){
							if(leftPos+sumleftStation==mGetOnStationIdx&&mGetOnStationIdx!=0){
								isFlag=1;
								onIden=true;
								Thread th=new Thread(runnable);
								th.start();
							}
						}
					}else{
						if(busSxx.equals("1")&&busCode.equals(ConstData.BusCode)){
							if(leftPos-sumleftStation==mGetOnStationIdx&&mGetOnStationIdx!=0){
								isFlag=1;
								onIden=true;
								Thread th=new Thread(runnable);
								th.start();
							}
						}
					}
				}	
			}
			//�ڴ����³�����
			if(!offIden){
				while (iterator.hasNext()) {
					BusBean businfo = iterator.next();
					String busSxx=businfo.getSxx();				//������
					String busCode=businfo.getBusCode();		//����
					float leftPos = businfo.getLeftStation();	//����վ��
					if (sxx==0){
						if(busSxx.equals("0")&&busCode.equals(ConstData.BusCode)){
							if(leftPos+sumleftStation==mGetOffStationIdx&&mGetOffStationIdx!=0){
								isFlag=2;
								offIden=true;
								Thread th=new Thread(runnable);
								th.start();
							}
						}
					}else{
						if(busSxx.equals("1")&&busCode.equals(ConstData.BusCode)){
							if(leftPos-sumleftStation==mGetOffStationIdx&&mGetOffStationIdx!=0){
								isFlag=2;
								offIden=true;
								Thread th=new Thread(runnable);
								th.start();
							}
						}
					}
				}
			}
		}

	}
	
	//������ʾ��������ʾ
	Runnable runnable=new Runnable() {
		@Override
		public void run() {
			for(int i=0;i<3;i++){
				handler.sendEmptyMessage(1);
				Vibrator vibrator = (Vibrator)SmartBusApp.getInstance().getApplicationContext().getSystemService(SmartBusApp.getInstance().getApplicationContext().VIBRATOR_SERVICE); 
				Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);  
				Ringtone r = RingtoneManager.getRingtone(SmartBusApp.getInstance().getApplicationContext(), notification);  
				r.play(); 
				if(isshock==0){
					vibrator.vibrate(2000);
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String str="����";
			//��������
			if (isFlag==1){
				str=str+mGetOnStationName+"����"+sumleftStation+"վ";
			} 
			if (isFlag==2){
				str=str+mGetOffStationName+"����"+sumleftStation+"վ";
			}
			if (!str.equals("����")){
				if(isVideo==0){
					speechSynthesizer.speak(str);
				}
			}
			isFlag=0;
		}
	};
		
	//������ʾ
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
			//	Toast.makeText(SmartBusApp.getInstance().getApplicationContext(), "��������վ�㻹��1վ��ע�����³�", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};
	
	// ����
	public void paintGraph(Canvas paramCanvas) {
		if (this.mBusLine != null){
			paintLinks(paramCanvas);//����
			paintNodes(paramCanvas);//��վ
		}
	}
	
	//��ʼ������
	public void paintLinks(Canvas paramCanvas) {
		
		int stationNum = this.mBusLine.getStations().size();
		paramCanvas.save();
		//���廭�� ��ϸ����ɫ
		Paint localPaint = new Paint(1);
		localPaint.setStrokeWidth(this.mLinkSize);
		localPaint.setColor(this.mBusLineColor);
		//վ������
		stationNum = stationNum == 0 ? 31 : stationNum;
		//һ��������վ��
		int rowNum = (-1 + stationNum + 5) / 5;
		//ԭ��ֱ���x���y��ƫ�ƶ�Զ�ľ��룬Ȼ����ƫ�ƺ��λ����Ϊ����ԭ�㡣
		//Ҳ����˵ԭ���ڣ�100,100��,Ȼ��translate��1��1���µ�����ԭ���ڣ�101,101�������ǣ�1,1��
		paramCanvas.translate(this.mColWidth, this.mRowHeight);
		int currRow = 0;
		do {
			if (currRow >= rowNum) {
				paramCanvas.restore();
				return;
			}
			int l = -1 + Math.min(stationNum - currRow * 5, 5);
			int i1;
			if (currRow % 2 != 0) {// ������
				i1 = l * -mColWidth;
			} else {
				i1 = l * mColWidth;
			}
			paramCanvas.drawLine(0.0F, 0.0F, i1, 0, localPaint);
			paramCanvas.translate(i1, 0);
			if (currRow < rowNum - 1) {
				int i3 = this.mRowHeight;// ������
				paramCanvas.drawLine(0.0F, 0.0F, 0, i3, localPaint);
				paramCanvas.translate(0, i3);
			}
			++currRow;
		} while (true);
	}

	//����ÿ��վ�������
	public void  SumStationXY(float x,float y,int station,int sxx,Bitmap Icon){
		StationRegion sr=new StationRegion();
		sr.setX(x);
		sr.setY(y);
		sr.setSxx(sxx);
		float maxX=x+Icon.getWidth()/2;
		float minX=x-Icon.getWidth()/2;
		float maxY=y+Icon.getHeight()/2;
		float minY=y-Icon.getHeight()/2;
		sr.setMaxX(maxX);
		sr.setMaxY(maxY);
		sr.setMinX(minX);
		sr.setMinY(minY);
		sr.setStation(station);
		stlist.add(sr);
	}
	
	//��·ͼ�ϻ�վ��
	public void paintNodes(Canvas paramCanvas) {
		
		int j = 0;
		int k = 1;
		int l = 0;
		float x=this.mColWidth;
		float y=this.mRowHeight;
		int flag = 1;// վ�����з��� 1���� 2 ����
		paramCanvas.save();
		int stationNum = this.mBusLine.getStations().size();
		int sxx =this.mBusLine.getLineId();//������ 0������ 1������ 
		// ���廭��
		Paint localPaint = new Paint(1);
		localPaint.setStyle(Paint.Style.FILL);
		localPaint.setTextSize(this.mTextSize);
		localPaint.setColor(this.mTextColor);
		j = (int)(this.mColWidth * Math.acos(0.5235987755982988D));
		paramCanvas.translate(this.mColWidth, this.mRowHeight);
		if(sxx==0){
			while (k <= stationNum) {
				// -------ͬ��---------------
				if (k==mGetOnStationIdx){
					mStationIcon=this.mGetOnIcon;
					mGetOnStationName=mBusLine.getStations().get(k-1).getStationName();
				}
				if (k==mGetOffStationIdx) {
					mStationIcon=this.mGetOffIcon;
					mGetOffStationName=mBusLine.getStations().get(k-1).getStationName();
				}
				if ( k % 5 != 0) {
					if (k / 5 % 2 != 0){// �����У�����
						flag = -1;
						int i2 = -this.mColWidth;
						if (k % 5 != 1) {
							paramCanvas.translate(i2, l);
							x=x+i2;
						}
						SumStationXY(x,y,k,sxx,mStationIcon);
						float f9 = -this.mStationIcon.getWidth() / 2;
						float f10 = -this.mStationIcon.getHeight() / 2;
						paramCanvas.drawBitmap(this.mStationIcon, f9, f10, null);
					} else {// ż���У�����
						flag = 1;
						if (k != 1) {
							int i2 = this.mColWidth;
							// x�� ���� ��y������
							if (k % 5 != 1) {
								paramCanvas.translate(i2, l);
								x=x+i2;
							}
							SumStationXY(x,y,k,sxx,mStationIcon);
							float f1 = -this.mStationIcon.getWidth() / 2;
							float f2 = -this.mStationIcon.getHeight() / 2;
							paramCanvas.drawBitmap(this.mStationIcon, f1, f2, null);
						}
					}
				} else {// ---------���д���-----------
					String str = ((StationBean) this.mBusLine.getStations().get(k - 1)).getStationName();//����
					Path localPath = new Path();
					localPath.moveTo(0.0F, -this.mRowHeight / 4);
					localPath.lineTo(this.mColWidth,(int) (-(this.mColWidth * Math.tan(0.5235987755982988D))));
					if (k != stationNum) {
						int i2 = flag * this.mColWidth;
						// x�� ���� ��y������
						paramCanvas.translate(i2, l);
						float f1 = -this.mStationIcon.getWidth() / 2;
						float f2 = -this.mStationIcon.getHeight() / 2;
						paramCanvas.drawBitmap(this.mStationIcon, f1, f2, null);
						paintTextOnPath(paramCanvas, j, str, localPath, localPaint);
						paramCanvas.translate(0, mRowHeight);
						x=x+i2;
						SumStationXY(x,y,k,sxx,mStationIcon);
					}else{//���һ��
						int i2 = flag * this.mColWidth;
						// x�� ���� ��y������
						paramCanvas.translate(i2, l);
						x=x+i2;
						SumStationXY(x,y,k,sxx,mStationIcon);
					}
					y=y+mRowHeight;
				}
				if(k%5!=0||(k%5==0&&k==stationNum)){
					// -----------վ������------------
					String str = ((StationBean) this.mBusLine.getStations().get(k - 1)).getStationName();
					Path localPath = new Path();
					localPath.moveTo(0.0F, -this.mRowHeight / 4);
					localPath.lineTo(this.mColWidth,(int) (-(this.mColWidth * Math.tan(0.5235987755982988D))));
//					Log.e("StationName",String.valueOf(this.mRowHeight));
//					Log.e("StationName2",String.valueOf(this.mColWidth) );
					paintTextOnPath(paramCanvas, j, str, localPath, localPaint);
				}
				
				if (k == 1) {// ʼ��վ
					SumStationXY(x,y,k,sxx,mBeginIcon);
					float f5 = -this.mBeginIcon.getWidth() / 2;
					float f6 = -this.mBeginIcon.getHeight() / 2;
					paramCanvas.drawBitmap(this.mBeginIcon, f5, f6, null);

				} else if(k == stationNum) {// �յ�վ
					float f3 = -this.mEndIcon.getWidth() / 2;
					float f4 = -this.mEndIcon.getHeight() / 2;
					paramCanvas.drawBitmap(this.mEndIcon, f3, f4, null);
				}
				++k;
				this.mStationIcon = BitmapFactory.decodeResource(getResources(),R.drawable.staitonlist_station_noline);
			}
		}else if(sxx==1){//����
			k =stationNum;
			while (k >0) {
				if (k==mGetOnStationIdx){
					mStationIcon=this.mGetOnIcon;
					mGetOnStationName=mBusLine.getStations().get(k-1).getStationName();
				}
				if (k==mGetOffStationIdx) {
					mStationIcon=this.mGetOffIcon;
					mGetOffStationName=mBusLine.getStations().get(k-1).getStationName();
				}
				// -------ͬ��---------------
				if ((stationNum-k) % 5 != 4) {
					if ((stationNum-k) / 5 % 2 != 0){// �����У�����
						flag = -1;
						int i2 = -this.mColWidth;
						if ((stationNum-k) % 5 != 0) {
							paramCanvas.translate(i2, l);
							x=x+i2;
						}
						SumStationXY(x,y,k,sxx,mStationIcon);
						float f9 = -this.mStationIcon.getWidth() / 2;
						float f10 = -this.mStationIcon.getHeight() / 2;
						paramCanvas.drawBitmap(this.mStationIcon, f9, f10, null);
					} else {// ż���У�����
						flag = 1;
						int i2 = this.mColWidth;
						if ((stationNum-k) % 5 != 0) {
							paramCanvas.translate(i2, l);
							x=x+i2;
						}
						SumStationXY(x,y,k,sxx,mStationIcon);
						if (k != 1) {// x�� ���� ��y������
							float f1 = -this.mStationIcon.getWidth() / 2;
							float f2 = -this.mStationIcon.getHeight() / 2;
							paramCanvas.drawBitmap(this.mStationIcon, f1, f2, null);
						} 
					}
				} else {// ---------���д���-----------
					String str = ((StationBean) this.mBusLine.getStations().get(k - 1)).getStationName();
					Path localPath = new Path();
					localPath.moveTo(0.0F, -this.mRowHeight / 4);
					localPath.lineTo(this.mColWidth,(int) (-(this.mColWidth * Math.tan(0.5235987755982988D))));
					if (k != 1) {
						int i2 = flag * this.mColWidth;
						// x�� ���� ��y������
						paramCanvas.translate(i2, l);
						float f1 = -this.mStationIcon.getWidth() / 2;
						float f2 = -this.mStationIcon.getHeight() / 2;
						paramCanvas.drawBitmap(this.mStationIcon, f1, f2, null);
						x=x+i2;
						SumStationXY(x,y,k,sxx,mStationIcon);
						// -----------վ������------------
						paintTextOnPath(paramCanvas, j, str, localPath, localPaint);
						paramCanvas.translate(0, mRowHeight);
					}else{//���һ��
						int i2 = flag * this.mColWidth;
						// x�� ���� ��y������
						paramCanvas.translate(i2, l);
						x=x+i2;
						SumStationXY(x,y,k,sxx,mStationIcon);
						paintTextOnPath(paramCanvas, j, str, localPath, localPaint);
					}
					y=y+mRowHeight;
				}
				if((stationNum-k)%5!=4||k==stationNum){
					// -----------վ������------------
					String str = ((StationBean) this.mBusLine.getStations().get(k - 1)).getStationName();
					Path localPath = new Path();
					localPath.moveTo(0.0F, -this.mRowHeight / 4);
					localPath.lineTo(this.mColWidth,(int) (-(this.mColWidth * Math.tan(0.5235987755982988D))));
					paintTextOnPath(paramCanvas, j, str, localPath, localPaint);
				}
				if (k == stationNum) {// ʼ��վ
					SumStationXY(x,y,k,sxx,mBeginIcon);
					float f5 = -this.mBeginIcon.getWidth() / 2;
					float f6 = -this.mBeginIcon.getHeight() / 2;
					paramCanvas.drawBitmap(this.mBeginIcon, f5, f6, null);
				} else if (k == 1) {// �յ�վ
					float f3 = -this.mEndIcon.getWidth() / 2;
					float f4 = -this.mEndIcon.getHeight() / 2;
					paramCanvas.drawBitmap(this.mEndIcon, f3, f4, null);
				}
				--k;
				this.mStationIcon = BitmapFactory.decodeResource(getResources(),R.drawable.staitonlist_station_noline);
			}
		}
		paramCanvas.restore();
	}
	
	//���� 
	private void paintBuses(Canvas canvas){
		canvas.save();
		int stationNum=this.mBusLine.getStations().size();//վ������
		if (mBusData != null && mBusData.size() > 0){
			Paint paint = new Paint(1);
			paint.setStyle(android.graphics.Paint.Style.FILL);
			paint.setTextSize(mTextSize);
			paint.setColor(mTextColor);
			Iterator iterator = mBusData.iterator();
			//-----1.�ж�������----
			int sxx =this.mBusLine.getLineId();//������ 0������ 1������ 
			if(sxx==0){
				while (iterator.hasNext()) {
					BusBean businfo = (BusBean)iterator.next();
					String busCode=businfo.getBusCode();
					if (iden){
						String ch=ConstData.BusCode;
						if (!busCode.equals(ch)){
							continue;
						}
					}
					if(businfo.getSxx().equals("0")){
						float leftPos = businfo.getLeftStation();
						float f1 =Float.parseFloat(businfo.getLeftDistance());
						int i = (int)Math.floor(leftPos);
						int j =  ((i-1)%5)* mColWidth + mColWidth;//���
						int k = ((i-1) / 5) * mRowHeight + mRowHeight;//�߶�
						float width;
						float height;
						if (i  % 5 != 0){
							//��վ֮��
							if (i  % 5 != 0)
								j = (int)((float)j + f1 * (float)mColWidth);
							else
								k = (int)((float)k + f1 * (float)mRowHeight);
							if (((i-1) / 5) % 2 != 0)
								j = getMeasuredWidth() - j;
						}else{//ת�еı߽�վ��
							if (((i-1) / 5) % 2 != 0)
								j = getMeasuredWidth() - j;
							k = (int)((float)k + f1 * (float)mRowHeight);
						} 
						int bus=selectbus(leftPos,0,stationNum);
						if (bus==1){
							width = j - mBusIcon1.getWidth() / 2;
							height = k - mBusIcon1.getHeight();
							canvas.drawBitmap(mBusIcon1, width, height, null);
						}else if(bus==2){
							width = j - mBusIcon2.getWidth() / 2;
							height = k - mBusIcon2.getHeight();
							canvas.drawBitmap(mBusIcon2, width, height, null);
						}else{
							width = j - mBusIcon3.getWidth() / 2;
							height = k - mBusIcon3.getHeight();
							canvas.drawBitmap(mBusIcon3, width, height, null);
						}
						String s =businfo.getBusCode();
						Rect rect = new Rect();
						paint.getTextBounds(s, 0, s.length(), rect);
						int l = j - rect.width() / 2;
						int i1 = k + rect.height();
						canvas.drawText(s, l, i1, paint);
					}
				}	
			}else{//----------------------����  ��ŴӴ�С
				while (iterator.hasNext()) {
					BusBean businfo = (BusBean)iterator.next();
					String busCode=businfo.getBusCode();
					if (iden){
						String ch=ConstData.BusCode;
						if (!busCode.equals(ch)){
							continue;
						}
					}
					if(businfo.getSxx().equals("1")){
						float leftPos = businfo.getLeftStation();
						float f1 =Float.parseFloat(businfo.getLeftDistance());
						int i = (int)Math.floor(leftPos);
						int j =  ((stationNum-i)%5)* mColWidth + mColWidth;//���
						int k = ((stationNum-i) / 5) * mRowHeight + mRowHeight;//�߶�
						float width;
						float height;
						if ((stationNum-i)  % 5 != 4){
							j = (int)((float)j + f1 * (float)mColWidth);
							if ((((stationNum-i)) / 5) % 2 != 0)
								j = getMeasuredWidth() - j;
						}else{//ת�еı߽�վ��
							if ((((stationNum-i)) / 5) % 2 != 0)
								j = getMeasuredWidth() - j;
							k = (int)((float)k + f1 * (float)mRowHeight);
						} 
						int bus=selectbus(leftPos,1,stationNum);	
						if (bus==1){
							width = j - mBusIconsx1.getWidth() / 2;
							height = k - mBusIconsx1.getHeight();
							canvas.drawBitmap(mBusIconsx1, width, height, null);
						}else if(bus==2){
							width = j - mBusIconsx2.getWidth() / 2;
							height = k - mBusIconsx2.getHeight();
							canvas.drawBitmap(mBusIconsx2, width, height, null);
						}else{
							width = j - mBusIconsx3.getWidth() / 2;
							height = k - mBusIconsx3.getHeight();
							canvas.drawBitmap(mBusIconsx3, width, height, null);
						}
						String s =businfo.getBusCode();
						Rect rect = new Rect();
						paint.getTextBounds(s, 0, s.length(), rect);
						int l = j - rect.width() / 2;
						int i1 = k + rect.height();
						canvas.drawText(s, l, i1, paint); 
					}
				}
			}
		}
		canvas.restore();
	}
	//ѡ����ͼ��
	public int selectbus(float pos,int sxx,int stationNum){
		if (sxx==0){
			String str=(long)pos+"";
			if (str.length()==1){
				str="0"+str;
			}
			if (pos%5==0){
				return 2;
			}else{
				String val=str.substring(1, 2);
				if (val.equals("1")||val.equals("2")||val.equals("3")||val.equals("4")){
					return 1;
				}else{
					return 3;
				}	
			}
		}else{
			int k=1;
			int station=stationNum;
			for(int i=1;i<=stationNum;i++){
				if (station==pos){
					k=i;
					break;
				}
				station--;
			}
			String str=k+"";
			if (str.length()==1){
				str="0"+str;
			}
			if (k%5==0){
				return 2;
			}else{
				String val=str.substring(1, 2);
				if (val.equals("1")||val.equals("2")||val.equals("3")||val.equals("4")){
					return 1;
				}else{
					return 3;
				}	
			}
		}
	}

	//��·ͼ�� ������ʾ
	public void paintTextOnPath(Canvas paramCanvas, int paramInt,String stationName, Path paramPath, Paint paramPaint) {
		ArrayList list = CharUtil.staticLayout(paramInt, stationName,paramPaint);
		if ((list != null) && (list.size() == 1)) {
			paramCanvas.drawTextOnPath((String) list.get(0), paramPath, 0.0F,5.5F, paramPaint);
			return;
		}
		for (int i = 0;i < list.size(); ++i) {
			paramCanvas.drawTextOnPath((String) list.get(i), paramPath, 0.0F,28.5F * (i + 1) - (16 * list.size()), paramPaint);
		}
	}
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.mColWidth = (getMeasuredWidth() / 6);
		setMeasuredDimension(getMeasuredWidth(), this.mRowHeight* (4 + (-1 + 5 + this.mBusLine.getStations().size()) / 5));
	}

	//֪ͨ�Ѿ��ϳ�
	public void setOnBus(boolean iden2) {
		// TODO Auto-generated method stub
		iden=true;
		invalidate();
	}

	public void updateBuses(List<BusBean> paramList) {
		this.mBusData = paramList;
		//invalidate();
	}
	
}
