package main.smart.bus.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.baidu.speechsynthesizer.SpeechSynthesizer;
import com.baidu.speechsynthesizer.SpeechSynthesizerListener;
import com.baidu.speechsynthesizer.publicutility.SpeechError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.Cosy;
import main.SharedPreferencesHelper;
import main.smart.bus.bean.BusBean;
import main.smart.bus.bean.LineBean;
import main.smart.bus.bean.StationBean;
import main.smart.bus.bean.StationRegion;
import main.smart.common.SmartBusApp;
import main.smart.common.util.CharUtil;
import main.smart.common.util.ConstData;
import main.smart.common.util.PreferencesHelper;
import main.smart.rcgj.R;

/**
 * 画线路图：站点，线，车，文字
 **/
public class BusLineGraphView extends View implements SpeechSynthesizerListener {
    static final String TAG = "LINE-VIEW";
    private Bitmap mBeginIcon;// 开始站图标
    private List<BusBean> mBusData;
    private List<Cosy.DataBean> beanList1;
    private Bitmap mBusIcon;        //车标下行(标准)
    private Bitmap mBusIcon1;        //车标下行(带箭头)
    private Bitmap mBusIcon2;        //车标下行(带箭头)
    private Bitmap mBusIcon3;        //车标下行(带箭头)
    private Bitmap mBusIconsx;        //车标上行(标准)
    private Bitmap mBusIconsx1;        //车标上行(带箭头)
    private Bitmap mBusIconsx2;        //车标上行(带箭头)
    private Bitmap mBusIconsx3;        //车标上行(带箭头)
    private LineBean mBusLine;        //线路对象
    private int mBusLineColor;        //线路颜色
    private int mColWidth;            //站点在线路上横向距离
    private int mRowHeight;            //直线图 两行的距离
    private Bitmap mComingBg;        //即将到站
    private Bitmap mEndIcon;        //终点站图标
    private Bitmap mGetOffIcon;        //下车图标
    private Bitmap mGetOnIcon;        //上车图标
    private int mGetOffStationIdx;    //下车站点
    private int mGetOnStationIdx;    //上车站点
    private int mLinkSize;            //画线
    private int mTextColor;            //画线染色
    private int mTextSize;            //字体大小
    private Bitmap mStationIcon;    //站点标
    private Bitmap mStationIcon1;    //站点标
    private int iden;            //是否选择了我要做
    private boolean onIden;            //上车报警
    private boolean offIden;        //下车报警
    private int isFlag = 0;            //语音提示
    private int isshock = 0;            //是否震动
    private int sumleftStation = 1;    //提前站数
    private int isVideo = 0;
    private String mGetOffStationName = "";
    private String mGetOnStationName = "";
    private PreferencesHelper mPreferenceMan;
    //	private SpeechSynthesizer speechSynthesizer;
    //站点点击 监听
    private OnBusStationClickListener mOnStationClickListener;
    List<Cosy.DataBean> beanList = new ArrayList<Cosy.DataBean>();
    /**
     * 指定license路径，需要保证该路径的可读写权限
     */
    private static final String LICENCE_FILE_NAME = Environment.getExternalStorageDirectory() + "/tts/baidu_tts_licence.dat";
    //记录每个站点的位置坐标
    private List<StationRegion> stlist = null;

    public BusLineGraphView(Context paramContext) {
        // super(paramContext);
        this(paramContext, null);

    }

    public LineBean getBusLine() {
        return this.mBusLine;
    }

    public void setBusLine(LineBean paramBusLine) {
        this.mBusLine = paramBusLine;
        this.updateBuses();
        invalidate();
    }

    public BusLineGraphView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);

        this.mGetOnStationIdx = -1;
        this.mGetOffStationIdx = -1;
        iden = -1;
        onIden = false;
        offIden = false;

        ConstData.onBus = 0;
        ConstData.upBus = 0;
        ConstData.BusCode = "";

        mGetOffStationName = "";
        mGetOnStationName = "";

        Resources localResources = getResources();
        this.mPreferenceMan = PreferencesHelper.getInstance();//数据管理
        this.isshock = mPreferenceMan.getShock();
        this.isVideo = mPreferenceMan.getVideoType();
        this.sumleftStation = mPreferenceMan.getReminder() + 1;
        this.mRowHeight = localResources.getDimensionPixelSize(R.dimen.busline_graph_row_height);
        this.mLinkSize = localResources.getDimensionPixelSize(R.dimen.busline_graph_link_size);
        this.mTextSize = localResources.getDimensionPixelSize(R.dimen.busline_graph_node_text_size);
        this.mTextColor = localResources.getColor(R.color.black_text);
        this.mBusLineColor = localResources.getColor(R.color.busline_graph_color);
        this.mBusIcon = BitmapFactory.decodeResource(getResources(), R.drawable.sketch_busicon);
        this.mBusIcon1 = BitmapFactory.decodeResource(getResources(), R.drawable.sketch_busicon_1);
        this.mBusIcon2 = BitmapFactory.decodeResource(getResources(), R.drawable.sketch_busicon_2);
        this.mBusIcon3 = BitmapFactory.decodeResource(getResources(), R.drawable.sketch_busicon_3);
        this.mBusIconsx = BitmapFactory.decodeResource(getResources(), R.drawable.sketch_busicon_red);
        this.mBusIconsx1 = BitmapFactory.decodeResource(getResources(), R.drawable.sketch_busicon_green_1);
        this.mBusIconsx2 = BitmapFactory.decodeResource(getResources(), R.drawable.sketch_busicon_green_2);
        this.mBusIconsx3 = BitmapFactory.decodeResource(getResources(), R.drawable.sketch_busicon_green_3);
        // 灰色圆圈站
        this.mStationIcon = BitmapFactory.decodeResource(getResources(), R.drawable.staitonlist_station_noline);
        // 灰色圆圈站
        this.mStationIcon1 = BitmapFactory.decodeResource(getResources(), R.drawable.staitonlist_station_noline1);
        // 即将有车到达的站
        this.mComingBg = BitmapFactory.decodeResource(getResources(), R.drawable.staitonlist_station_coming_solid);
        // 始发站
        this.mBeginIcon = BitmapFactory.decodeResource(getResources(), R.drawable.sketch_start);
        // 终点站
        this.mEndIcon = BitmapFactory.decodeResource(getResources(), R.drawable.sketch_finish);
        // 上
        this.mGetOnIcon = BitmapFactory.decodeResource(getResources(), R.drawable.stationlist_on);
        // 下
        this.mGetOffIcon = BitmapFactory.decodeResource(getResources(), R.drawable.stationlist_off);
//        if (!new File(LICENCE_FILE_NAME).getParentFile().exists()) {
//            new File(LICENCE_FILE_NAME).getParentFile().mkdirs();
//        }
//        // 复制license到指定路径
//        InputStream licenseInputStream = getResources().openRawResource(R.raw.temp_license_2015_07_03);
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(LICENCE_FILE_NAME);
//            byte[] buffer = new byte[1024];
//            int size = 0;
//            while ((size = licenseInputStream.read(buffer, 0, 1024)) >= 0) {
//                SpeechLogger.logD("size written: " + size);
//                fos.write(buffer, 0, size);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            try {
//                licenseInputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//		Activity activity= (Activity) paramContext;
//		speechSynthesizer =SpeechSynthesizer.newInstance(SpeechSynthesizer.SYNTHESIZER_AUTO, paramContext, "holder", this);
//        // 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
//        speechSynthesizer.setApiKey("1MbRG6l3TSfRSEFGLbXZdR3e", "W1oqj46bnTIRkQn6WnXzPZeF3LU3Ef1h");
//        // 请替换为语音开发者平台上注册应用得到的App ID (离线授权)
//        speechSynthesizer.setAppId("6513532");
//        // 设置授权文件路径
//        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, LICENCE_FILE_NAME);
//        // TTS所需的资源文件，可以放在任意可读目录，可以任意改名
//        String ttsTextModelFilePath =SmartBusApp.getInstance().getApplicationContext().getApplicationInfo().dataDir + "/lib/libbd_etts_text.dat.so";
//        String ttsSpeechModelFilePath =SmartBusApp.getInstance().getApplicationContext().getApplicationInfo().dataDir + "/lib/libbd_etts_speech_female.dat.so";
//        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, ttsTextModelFilePath);
//        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, ttsSpeechModelFilePath);
//        DataInfoUtils.verifyDataFile(ttsTextModelFilePath);
//        DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_DATE);
//        DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_SPEAKER);
//        DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_GENDER);
//        DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_CATEGORY);
//        DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_LANGUAGE);
//        speechSynthesizer.initEngine();
//        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

//    private void baiduSpeech(Context paramContext) {
//        // 部分版本不需要BDSpeechDecoder_V1
//        try {
//            System.loadLibrary("gnustl_shared");
//            System.loadLibrary("BDSpeechDecoder_V1");
//            System.loadLibrary("bd_etts");
//            System.loadLibrary("bds");
//        } catch (UnsatisfiedLinkError e) {
//            SpeechLogger.logD("load BDSpeechDecoder_V1 failed, ignore");
//        }
//
//        if (!new File(LICENCE_FILE_NAME).getParentFile().exists()) {
//            new File(LICENCE_FILE_NAME).getParentFile().mkdirs();
//        }
//        // 复制license到指定路径
//        InputStream licenseInputStream = getResources().openRawResource(R.raw.temp_license_2015_07_03);
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(LICENCE_FILE_NAME);
//            byte[] buffer = new byte[1024];
//            int size = 0;
//            while ((size = licenseInputStream.read(buffer, 0, 1024)) >= 0) {
//                SpeechLogger.logD("size written: " + size);
//                fos.write(buffer, 0, size);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (fos != null) {
//                try {
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            try {
//                licenseInputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        Activity activity = (Activity) paramContext;
//        speechSynthesizer = SpeechSynthesizer.newInstance(SpeechSynthesizer.SYNTHESIZER_AUTO, paramContext, "holder", this);
//        // 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
//        speechSynthesizer.setApiKey(CitySetting.bdKey, CitySetting.bdSecret);
//        // 请替换为语音开发者平台上注册应用得到的App ID (离线授权)
//        speechSynthesizer.setAppId(CitySetting.bdAppId);
//        // 设置授权文件路径
//        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, LICENCE_FILE_NAME);
//        // TTS所需的资源文件，可以放在任意可读目录，可以任意改名
//        String ttsTextModelFilePath = SmartBusApp.getInstance().getApplicationContext().getApplicationInfo().dataDir + "/lib/libbd_etts_text.dat.so";
//        String ttsSpeechModelFilePath = SmartBusApp.getInstance().getApplicationContext().getApplicationInfo().dataDir + "/lib/libbd_etts_speech_female.dat.so";
//        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, ttsTextModelFilePath);
//        speechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, ttsSpeechModelFilePath);
//        DataInfoUtils.verifyDataFile(ttsTextModelFilePath);
//        DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_DATE);
//        DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_SPEAKER);
//        DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_GENDER);
//        DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_CATEGORY);
//        DataInfoUtils.getDataFileParam(ttsTextModelFilePath, DataInfoUtils.TTS_DATA_PARAM_LANGUAGE);
//        speechSynthesizer.initEngine();
//        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
//    }

    //执行Ondraw开始画图了
    protected void onDraw(Canvas paramCanvas) {
        super.onDraw(paramCanvas);
        stlist = new ArrayList<StationRegion>();
        paintGraph(paramCanvas);//画线
        paintBuses(paramCanvas);//花车

    }

    // 画线
    public void paintGraph(Canvas paramCanvas) {
        if (this.mBusLine != null) {
            paintLinks(paramCanvas);//画线
            paintNodes(paramCanvas);//画站
        }
    }

    //开始画线了
    public void paintLinks(Canvas paramCanvas) {
        Log.d(TAG, "线路图画线");
        int stationNum = this.mBusLine.getStations().size();
        paramCanvas.save();
        //定义画笔 粗细，颜色
        Paint localPaint = new Paint(1);
        localPaint.setStrokeWidth(this.mLinkSize);
        localPaint.setColor(this.mBusLineColor);
        //站点总数
        stationNum = stationNum == 0 ? 31 : stationNum;
        //一共多少行站点
        int rowNum = (-1 + stationNum + 5) / 5;
        //原点分别在x轴和y轴偏移多远的距离，然后以偏移后的位置作为坐标原点。
        //也就是说原来在（100,100）,然后translate（1，1）新的坐标原点在（101,101）而不是（1,1）
        paramCanvas.translate(this.mColWidth, this.mRowHeight);
        int currRow = 0;
        do {
            if (currRow >= rowNum) {
                paramCanvas.restore();
                return;
            }
            int l = -1 + Math.min(stationNum - currRow * 5, 5);
            int i1;
            if (currRow % 2 != 0) {// 奇数行
                i1 = l * -mColWidth;
            } else {
                i1 = l * mColWidth;
            }
            paramCanvas.drawLine(0.0F, 0.0F, i1, 0, localPaint);
            paramCanvas.translate(i1, 0);
            if (currRow < rowNum - 1) {
                int i3 = this.mRowHeight;// 画折线
                paramCanvas.drawLine(0.0F, 0.0F, 0, i3, localPaint);
                paramCanvas.translate(0, i3);
            }
            ++currRow;
        } while (true);
    }

    //线路图上画站点
    public void paintNodes(Canvas paramCanvas) {
        Log.d(TAG, "线路图画站点");
        int j = 0;
        int k = 1;
        int l = 0;
        float x = this.mColWidth;
        float y = this.mRowHeight;
        int flag = 1;// 站点排列方向 1正向 2 逆向
        paramCanvas.save();
        int stationNum = this.mBusLine.getStations().size();
        int sxx = this.mBusLine.getLineId();//上下行 0：下行 1：上行
        // 定义画笔
        Paint localPaint = new Paint(1);
        localPaint.setStyle(Paint.Style.FILL);
        localPaint.setTextSize(this.mTextSize);
        localPaint.setColor(this.mTextColor);
        j = (int) (this.mColWidth * Math.acos(0.5235987755982988D));
        paramCanvas.translate(this.mColWidth, this.mRowHeight);
        if (sxx == 0) {
            while (k <= stationNum) {
                // -------同行---------------
                if (k == mGetOnStationIdx) {
                    mStationIcon = this.mGetOnIcon;
                    mGetOnStationName = mBusLine.getStations().get(k - 1).getStationName();
                }
                if (k == mGetOffStationIdx) {
                    mStationIcon = this.mGetOffIcon;
                    mGetOffStationName = mBusLine.getStations().get(k - 1).getStationName();
                }
                if (k % 5 != 0) {
                    if (k / 5 % 2 != 0) {// 奇数行，倒排
                        flag = -1;
                        int i2 = -this.mColWidth;
                        if (k % 5 != 1) {
                            paramCanvas.translate(i2, l);
                            x = x + i2;
                        }
                        SumStationXY(x, y, k, sxx, mStationIcon);
                        float f9 = -this.mStationIcon.getWidth() / 2;
                        float f10 = -this.mStationIcon.getHeight() / 2;
                        paramCanvas.drawBitmap(this.mStationIcon, f9, f10, null);
                    } else {// 偶数行，正排
                        flag = 1;
                        if (k != 1) {
                            int i2 = this.mColWidth;
                            // x轴 左右 ；y轴上下
                            if (k % 5 != 1) {
                                paramCanvas.translate(i2, l);
                                x = x + i2;
                            }
                            SumStationXY(x, y, k, sxx, mStationIcon);
                            float f1 = -this.mStationIcon.getWidth() / 2;
                            float f2 = -this.mStationIcon.getHeight() / 2;
                            paramCanvas.drawBitmap(this.mStationIcon, f1, f2, null);
                        }
                    }
                } else {// ---------换行处理-----------
                    String str = ((StationBean) this.mBusLine.getStations().get(k - 1)).getStationName();//文字
                    Path localPath = new Path();
                    localPath.moveTo(0.0F, -this.mRowHeight / 4);
                    localPath.lineTo(this.mColWidth, (int) (-(this.mColWidth * Math.tan(0.5235987755982988D))));
                    if (k != stationNum) {
                        int i2 = flag * this.mColWidth;
                        // x轴 左右 ；y轴上下
                        paramCanvas.translate(i2, l);
                        float f1 = -this.mStationIcon.getWidth() / 2;
                        float f2 = -this.mStationIcon.getHeight() / 2;
                        paramCanvas.drawBitmap(this.mStationIcon, f1, f2, null);
                        paintTextOnPath(paramCanvas, j, str, localPath, localPaint);
                        paramCanvas.translate(0, mRowHeight);
                        x = x + i2;
                        SumStationXY(x, y, k, sxx, mStationIcon);
                    } else {//最后一个
                        int i2 = flag * this.mColWidth;
                        // x轴 左右 ；y轴上下
                        paramCanvas.translate(i2, l);
                        x = x + i2;
                        SumStationXY(x, y, k, sxx, mStationIcon);
                    }
                    y = y + mRowHeight;
                }
                if (k % 5 != 0 || (k % 5 == 0 && k == stationNum)) {
                    // -----------站点文字------------
                    String str = ((StationBean) this.mBusLine.getStations().get(k - 1)).getStationName();
                    Path localPath = new Path();
                    localPath.moveTo(0.0F, -this.mRowHeight / 4);
                    localPath.lineTo(this.mColWidth, (int) (-(this.mColWidth * Math.tan(0.5235987755982988D))));
                    paintTextOnPath(paramCanvas, j, str, localPath, localPaint);
                }

                if (k == 1) {// 始发站
                    SumStationXY(x, y, k, sxx, mBeginIcon);
                    float f5 = -this.mBeginIcon.getWidth() / 2;
                    float f6 = -this.mBeginIcon.getHeight() / 2;
                    paramCanvas.drawBitmap(this.mBeginIcon, f5, f6, null);

                } else if (k == stationNum) {// 终点站
                    float f3 = -this.mEndIcon.getWidth() / 2;
                    float f4 = -this.mEndIcon.getHeight() / 2;
                    paramCanvas.drawBitmap(this.mEndIcon, f3, f4, null);
                }
//                if (mBusLine.getStations().get(k - 1).getHclx().equals("2")){
//                    this.mStationIcon = BitmapFactory.decodeResource(getResources(), R.drawable.staitonlist_station_noline1);
//                }else {
                    this.mStationIcon = BitmapFactory.decodeResource(getResources(), R.drawable.staitonlist_station_noline);
               // }
                ++k;
//                this.mStationIcon = BitmapFactory.decodeResource(getResources(), R.drawable.staitonlist_station_noline);
            }
        } else if (sxx == 1) {//上行
            k = stationNum;
            while (k > 0) {
                if (k == mGetOnStationIdx) {
                    mStationIcon = this.mGetOnIcon;
                    mGetOnStationName = mBusLine.getStations().get(k - 1).getStationName();
                }
                if (k == mGetOffStationIdx) {
                    mStationIcon = this.mGetOffIcon;
                    mGetOffStationName = mBusLine.getStations().get(k - 1).getStationName();
                }
                // -------同行---------------
                if ((stationNum - k) % 5 != 4) {
                    if ((stationNum - k) / 5 % 2 != 0) {// 奇数行，倒排
                        flag = -1;
                        int i2 = -this.mColWidth;
                        if ((stationNum - k) % 5 != 0) {
                            paramCanvas.translate(i2, l);
                            x = x + i2;
                        }
                        SumStationXY(x, y, k, sxx, mStationIcon);
                        float f9 = -this.mStationIcon.getWidth() / 2;
                        float f10 = -this.mStationIcon.getHeight() / 2;
                        paramCanvas.drawBitmap(this.mStationIcon, f9, f10, null);
                    } else {// 偶数行，正排
                        flag = 1;
                        int i2 = this.mColWidth;
                        if ((stationNum - k) % 5 != 0) {
                            paramCanvas.translate(i2, l);
                            x = x + i2;
                        }
                        SumStationXY(x, y, k, sxx, mStationIcon);
                        if (k != 1) {// x轴 左右 ；y轴上下
                            float f1 = -this.mStationIcon.getWidth() / 2;
                            float f2 = -this.mStationIcon.getHeight() / 2;
                            paramCanvas.drawBitmap(this.mStationIcon, f1, f2, null);
                        }
                    }
                } else {// ---------换行处理-----------
                    String str = ((StationBean) this.mBusLine.getStations().get(k - 1)).getStationName();
                    Path localPath = new Path();
                    localPath.moveTo(0.0F, -this.mRowHeight / 4);
                    localPath.lineTo(this.mColWidth, (int) (-(this.mColWidth * Math.tan(0.5235987755982988D))));
                    if (k != 1) {
                        int i2 = flag * this.mColWidth;
                        // x轴 左右 ；y轴上下
                        paramCanvas.translate(i2, l);
                        float f1 = -this.mStationIcon.getWidth() / 2;
                        float f2 = -this.mStationIcon.getHeight() / 2;
                        paramCanvas.drawBitmap(this.mStationIcon, f1, f2, null);
                        x = x + i2;
                        SumStationXY(x, y, k, sxx, mStationIcon);
                        // -----------站点文字------------
                        paintTextOnPath(paramCanvas, j, str, localPath, localPaint);
                        paramCanvas.translate(0, mRowHeight);
                    } else {//最后一个
                        int i2 = flag * this.mColWidth;
                        // x轴 左右 ；y轴上下
                        paramCanvas.translate(i2, l);
                        x = x + i2;
                        SumStationXY(x, y, k, sxx, mStationIcon);
                        paintTextOnPath(paramCanvas, j, str, localPath, localPaint);
                    }
                    y = y + mRowHeight;
                }
                if ((stationNum - k) % 5 != 4 || k == stationNum) {
                    // -----------站点文字------------
                    String str = ((StationBean) this.mBusLine.getStations().get(k - 1)).getStationName();
                    Path localPath = new Path();
                    localPath.moveTo(0.0F, -this.mRowHeight / 4);
                    localPath.lineTo(this.mColWidth, (int) (-(this.mColWidth * Math.tan(0.5235987755982988D))));
                    paintTextOnPath(paramCanvas, j, str, localPath, localPaint);
                }
                if (k == stationNum) {// 始发站
                    SumStationXY(x, y, k, sxx, mBeginIcon);
                    float f5 = -this.mBeginIcon.getWidth() / 2;
                    float f6 = -this.mBeginIcon.getHeight() / 2;
                    paramCanvas.drawBitmap(this.mBeginIcon, f5, f6, null);
                } else if (k == 1) {// 终点站
                    float f3 = -this.mEndIcon.getWidth() / 2;
                    float f4 = -this.mEndIcon.getHeight() / 2;
                    paramCanvas.drawBitmap(this.mEndIcon, f3, f4, null);
                }
//                if (mBusLine.getStations().get(k - 1).getHclx().equals("2")){
//                    this.mStationIcon = BitmapFactory.decodeResource(getResources(), R.drawable.staitonlist_station_noline1);
//                }else {
                    this.mStationIcon = BitmapFactory.decodeResource(getResources(), R.drawable.staitonlist_station_noline);
             //   }
                --k;
//                this.mStationIcon = BitmapFactory.decodeResource(getResources(), R.drawable.staitonlist_station_noline);
            }
        }
        paramCanvas.restore();
    }

    //画车
    private void paintBuses(Canvas canvas) {
        canvas.save();
        Log.d(TAG, "线路图画车");
        int stationNum = this.mBusLine.getStations().size();//站点总数
        if (mBusData != null && mBusData.size() > 0) {
            SharedPreferencesHelper spHelper = new SharedPreferencesHelper(SmartBusApp.getInstance(), "login");
            String dataString = spHelper.getData(SmartBusApp.getInstance(), "Cosy", "");
            try {
                if (!dataString.equals("")) {
                    JSONObject jsonObject = new JSONObject(dataString);
                    String typeString = jsonObject.getString("success");
                    beanList.clear();
                    if (typeString.equals(getResources().getString(R.string.success))) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Cosy.DataBean bean = new Cosy.DataBean();
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            bean.setBusCode(jsonObject2.getString("busCode"));
                            bean.setPopulation(jsonObject2.getString("population"));
                            beanList.add(bean);
                        }

                    }
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Paint paint = new Paint(1);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(mTextSize);
            paint.setColor(mTextColor);
            Iterator iterator = mBusData.iterator();
            //-----1.判断上下行----
            int sxx = this.mBusLine.getLineId();//上下行 0：下行 1：上行
            if (sxx == 0) {
                while (iterator.hasNext()) {
                    BusBean businfo = (BusBean) iterator.next();
                    String busCode = businfo.getBusCode();
//					if (iden){
//						String ch=ConstData.BusCode;
//						if (!busCode.equals(ch)){
//							continue;
//						}
//					}
                    if (businfo.getSxx().equals("0")) {
                        float leftPos = businfo.getLeftStation();
                        float f1 = Float.parseFloat(businfo.getLeftDistance());
                        int i = (int) Math.floor(leftPos);
                        int j = ((i - 1) % 5) * mColWidth + mColWidth;//宽度
                        int k = ((i - 1) / 5) * mRowHeight + mRowHeight;//高度
                        float width;
                        float height;
                        if (i % 5 != 0) {
                            //两站之间
                            if (i % 5 != 0)
                                j = (int) ((float) j + f1 * (float) mColWidth);
                            else
                                k = (int) ((float) k + f1 * (float) mRowHeight);
                            if (((i - 1) / 5) % 2 != 0)
                                j = getMeasuredWidth() - j;
                        } else {//转行的边界站点
                            if (((i - 1) / 5) % 2 != 0)
                                j = getMeasuredWidth() - j;
                            k = (int) ((float) k + f1 * (float) mRowHeight);
                        }
                        int bus = selectbus(leftPos, 0, stationNum);
                        if (bus == 1) {
                            width = j - mBusIcon1.getWidth() / 2;
                            height = k - mBusIcon1.getHeight();
                            canvas.drawBitmap(mBusIcon1, width, height, null);
                        } else if (bus == 2) {
                            width = j - mBusIcon2.getWidth() / 2;
                            height = k - mBusIcon2.getHeight();
                            canvas.drawBitmap(mBusIcon2, width, height, null);
                        } else {
                            width = j - mBusIcon3.getWidth() / 2;
                            height = k - mBusIcon3.getHeight();
                            canvas.drawBitmap(mBusIcon3, width, height, null);
                        }
                        String s = businfo.getBusNum();
                        String s1 = businfo.getBusCode();
                        List<String> stringList = new ArrayList<String>();
                        for (int l = 0; l < beanList.size(); l++) {
                            stringList.add(beanList.get(l).getBusCode());
                        }
                        if (stringList.contains(s1)) {
                            for (int m = 0; m < stringList.size(); m++) {
                                if (stringList.get(m).equals(s1)) {

                                    if (Integer.valueOf(beanList.get(m).getPopulation())<10) {

                                        Paint paint1 = new Paint(2);
                                        paint1.setStyle(Paint.Style.FILL);
                                        paint1.setTextSize(mTextSize);

                                        Paint paint2 = new Paint(3);
                                        paint2.setStyle(Paint.Style.FILL);
                                        paint2.setTextSize(mTextSize);

                                        paint1.setColor(getResources().getColor(R.color.green));
                                        paint2.setColor(getResources().getColor(R.color.green));
                                        Rect rect = new Rect();
                                        paint1.getTextBounds(s, 0, s.length(), rect);
                                        int l = j - rect.width() / 2;
                                        int i1 = k + rect.height();
                                        canvas.drawText(s, l, i1, paint1);

                                        Rect rect1 = new Rect();
                                        paint2.getTextBounds(getResources().getString(R.string.shushi), 0, getResources().getString(R.string.shushi).length(), rect1);
                                        int l1 = j - rect1.width() / 2;
                                        int i11 = k + rect1.height();
                                        canvas.drawText(getResources().getString(R.string.shushi), l1, i11 + 25, paint2);
                                    } else if (10<Integer.valueOf(beanList.get(m).getPopulation())&& Integer.valueOf(beanList.get(m).getPopulation())<20){
                                        Paint paint1 = new Paint(2);
                                        paint1.setStyle(Paint.Style.FILL);
                                        paint1.setTextSize(mTextSize);

                                        Paint paint2 = new Paint(3);
                                        paint2.setStyle(Paint.Style.FILL);
                                        paint2.setTextSize(mTextSize);

                                        paint1.setColor(getResources().getColor(R.color.orange_text));
                                        paint2.setColor(getResources().getColor(R.color.orange_text));
                                        Rect rect = new Rect();
                                        paint1.getTextBounds(s, 0, s.length(), rect);
                                        int l = j - rect.width() / 2;
                                        int i1 = k + rect.height();
                                        canvas.drawText(s, l, i1, paint1);

                                        Rect rect1 = new Rect();
                                        paint2.getTextBounds(getResources().getString(R.string.yiban), 0, getResources().getString(R.string.yiban).length(), rect1);
                                        int l1 = j - rect1.width() / 2;
                                        int i11 = k + rect1.height();
                                        canvas.drawText(getResources().getString(R.string.yiban), l1, i11 + 25, paint2);
                                    } else if (Integer.valueOf(beanList.get(m).getPopulation())>20) {

                                        Paint paint1 = new Paint(2);
                                        paint1.setStyle(Paint.Style.FILL);
                                        paint1.setTextSize(mTextSize);

                                        Paint paint2 = new Paint(3);
                                        paint2.setStyle(Paint.Style.FILL);
                                        paint2.setTextSize(mTextSize);

                                        paint1.setColor(getResources().getColor(R.color.red));
                                        paint2.setColor(getResources().getColor(R.color.red));
                                        Rect rect = new Rect();
                                        paint1.getTextBounds(s, 0, s.length(), rect);
                                        int l = j - rect.width() / 2;
                                        int i1 = k + rect.height();
                                        canvas.drawText(s, l, i1, paint1);

                                        Rect rect1 = new Rect();
                                        paint2.getTextBounds(getResources().getString(R.string.yongji), 0, getResources().getString(R.string.yongji).length(), rect1);
                                        int l1 = j - rect1.width() / 2;
                                        int i11 = k + rect1.height();
                                        canvas.drawText(getResources().getString(R.string.yongji), l1, i11 + 25, paint2);
                                    }

                                }
                            }
                        } else {
                            Rect rect = new Rect();
                            paint.getTextBounds(s, 0, s.length(), rect);
                            int l = j - rect.width() / 2;
                            int i1 = k + rect.height();
                            canvas.drawText(s, l, i1, paint);
                        }

                    }
                }
            } else {//----------------------上行  序号从大到小
                while (iterator.hasNext()) {
                    BusBean businfo = (BusBean) iterator.next();
                    String busCode = businfo.getBusCode();
//					if (iden){
//						String ch=ConstData.BusCode;
//						if (!busCode.equals(ch)){
//							continue;
//						}
//					}
                    if (businfo.getSxx().equals("1")) {
                        float leftPos = businfo.getLeftStation();
                        float f1 = Float.parseFloat(businfo.getLeftDistance());
                        int i = (int) Math.floor(leftPos);
                        int j = ((stationNum - i) % 5) * mColWidth + mColWidth;//宽度
                        int k = ((stationNum - i) / 5) * mRowHeight + mRowHeight;//高度
                        float width;
                        float height;
                        if ((stationNum - i) % 5 != 4) {
                            j = (int) ((float) j + f1 * (float) mColWidth);
                            if ((((stationNum - i)) / 5) % 2 != 0)
                                j = getMeasuredWidth() - j;
                        } else {//转行的边界站点
                            if ((((stationNum - i)) / 5) % 2 != 0)
                                j = getMeasuredWidth() - j;
                            k = (int) ((float) k + f1 * (float) mRowHeight);
                        }
                        int bus = selectbus(leftPos, 1, stationNum);
                        if (bus == 1) {
                            width = j - mBusIconsx1.getWidth() / 2;
                            height = k - mBusIconsx1.getHeight();
                            canvas.drawBitmap(mBusIconsx1, width, height, null);
                        } else if (bus == 2) {
                            width = j - mBusIconsx2.getWidth() / 2;
                            height = k - mBusIconsx2.getHeight();
                            canvas.drawBitmap(mBusIconsx2, width, height, null);
                        } else {
                            width = j - mBusIconsx3.getWidth() / 2;
                            height = k - mBusIconsx3.getHeight();
                            canvas.drawBitmap(mBusIconsx3, width, height, null);
                        }
                        String s = businfo.getBusNum();
                        String s1 = businfo.getBusCode();
//						for (int m = 0; m <beanList1.size(); m++) {
//							if (s.equals(beanList1.get(m).getCH())) {
////								Rect rect = new Rect();
////								paint.getTextBounds(s, 0, s.length(), rect);
////								int l = j - rect.width() / 2;
////								int i1 = k + rect.height();
////								canvas.drawText(s, l, i1, paint);
//							}
//						}
//						Rect rect = new Rect();
//						paint.getTextBounds(s, 0, s.length(), rect);
//						int l = j - rect.width() / 2;
//						int i1 = k + rect.height();
//						canvas.drawText(s, l, i1, paint);
                        List<String> stringList = new ArrayList<String>();
                        for (int l = 0; l < beanList.size(); l++) {
                            stringList.add(beanList.get(l).getBusCode());
                        }
                        if (stringList.contains(s1)) {
                            for (int m = 0; m < stringList.size(); m++) {
                                if (stringList.get(m).equals(s1)) {

                                    if (Integer.valueOf(beanList.get(m).getPopulation())<10) {

                                        Paint paint1 = new Paint(2);
                                        paint1.setStyle(Paint.Style.FILL);
                                        paint1.setTextSize(mTextSize);

                                        Paint paint2 = new Paint(3);
                                        paint2.setStyle(Paint.Style.FILL);
                                        paint2.setTextSize(mTextSize);

                                        paint1.setColor(getResources().getColor(R.color.green));
                                        paint2.setColor(getResources().getColor(R.color.green));
                                        Rect rect = new Rect();
                                        paint1.getTextBounds(s, 0, s.length(), rect);
                                        int l = j - rect.width() / 2;
                                        int i1 = k + rect.height();
                                        canvas.drawText(s, l, i1, paint1);

                                        Rect rect1 = new Rect();
                                        paint2.getTextBounds(getResources().getString(R.string.shushi), 0, getResources().getString(R.string.shushi).length(), rect1);
                                        int l1 = j - rect1.width() / 2;
                                        int i11 = k + rect1.height();
                                        canvas.drawText(getResources().getString(R.string.shushi), l1, i11 + 25, paint2);
                                    } else if (10<Integer.valueOf(beanList.get(m).getPopulation())&& Integer.valueOf(beanList.get(m).getPopulation())<20) {
                                        Paint paint1 = new Paint(2);
                                        paint1.setStyle(Paint.Style.FILL);
                                        paint1.setTextSize(mTextSize);

                                        Paint paint2 = new Paint(3);
                                        paint2.setStyle(Paint.Style.FILL);
                                        paint2.setTextSize(mTextSize);

                                        paint1.setColor(getResources().getColor(R.color.orange_text));
                                        paint2.setColor(getResources().getColor(R.color.orange_text));
                                        Rect rect = new Rect();
                                        paint1.getTextBounds(s, 0, s.length(), rect);
                                        int l = j - rect.width() / 2;
                                        int i1 = k + rect.height();
                                        canvas.drawText(s, l, i1, paint1);

                                        Rect rect1 = new Rect();
                                        paint2.getTextBounds(getResources().getString(R.string.yiban), 0, getResources().getString(R.string.yiban).length(), rect1);
                                        int l1 = j - rect1.width() / 2;
                                        int i11 = k + rect1.height();
                                        canvas.drawText(getResources().getString(R.string.yiban), l1, i11 + 25, paint2);
                                    } else if (Integer.valueOf(beanList.get(m).getPopulation())>20) {

                                        Paint paint1 = new Paint(2);
                                        paint1.setStyle(Paint.Style.FILL);
                                        paint1.setTextSize(mTextSize);

                                        Paint paint2 = new Paint(3);
                                        paint2.setStyle(Paint.Style.FILL);
                                        paint2.setTextSize(mTextSize);

                                        paint1.setColor(getResources().getColor(R.color.red));
                                        paint2.setColor(getResources().getColor(R.color.red));
                                        Rect rect = new Rect();
                                        paint1.getTextBounds(s, 0, s.length(), rect);
                                        int l = j - rect.width() / 2;
                                        int i1 = k + rect.height();
                                        canvas.drawText(s, l, i1, paint1);

                                        Rect rect1 = new Rect();
                                        paint2.getTextBounds(getResources().getString(R.string.yongji), 0, getResources().getString(R.string.yongji).length(), rect1);
                                        int l1 = j - rect1.width() / 2;
                                        int i11 = k + rect1.height();
                                        canvas.drawText(getResources().getString(R.string.yongji), l1, i11 + 25, paint2);
                                    }

                                }
                            }
                        } else {
                            Rect rect = new Rect();
                            paint.getTextBounds(s, 0, s.length(), rect);
                            int l = j - rect.width() / 2;
                            int i1 = k + rect.height();
                            canvas.drawText(s, l, i1, paint);
                        }
                    }
                }
            }
        }
        canvas.restore();
        Log.d(TAG, "线路图画车完成");
    }

    //选择车辆图标
    public int selectbus(float pos, int sxx, int stationNum) {
        if (sxx == 0) {
            String str = (long) pos + "";
            if (str.length() == 1) {
                str = "0" + str;
            }
            if (pos % 5 == 0) {
                return 2;
            } else {
                String val = str.substring(1, 2);
                if (val.equals("1") || val.equals("2") || val.equals("3") || val.equals("4")) {
                    return 1;
                } else {
                    return 3;
                }
            }
        } else {
            int k = 1;
            int station = stationNum;
            for (int i = 1; i <= stationNum; i++) {
                if (station == pos) {
                    k = i;
                    break;
                }
                station--;
            }
            String str = k + "";
            if (str.length() == 1) {
                str = "0" + str;
            }
            if (k % 5 == 0) {
                return 2;
            } else {
                String val = str.substring(1, 2);
                if (val.equals("1") || val.equals("2") || val.equals("3") || val.equals("4")) {
                    return 1;
                } else {
                    return 3;
                }
            }
        }
    }

    //线路图上 文字提示
    public void paintTextOnPath(Canvas paramCanvas, int paramInt, String stationName, Path paramPath, Paint paramPaint) {
        ArrayList list = CharUtil.staticLayout(paramInt, stationName, paramPaint);
        if ((list != null) && (list.size() == 1)) {
            paramCanvas.drawTextOnPath((String) list.get(0), paramPath, 0.0F, 5.5F, paramPaint);
            return;
        }
        for (int i = 0; i < list.size(); ++i) {
            paramCanvas.drawTextOnPath((String) list.get(i), paramPath, 0.0F, 28.5F * (i + 1) - (16 * list.size()), paramPaint);
        }
    }

    protected void onMeasure(int paramInt1, int paramInt2) {
        super.onMeasure(paramInt1, paramInt2);
        this.mColWidth = (getMeasuredWidth() / 6);
        setMeasuredDimension(getMeasuredWidth(), this.mRowHeight * (4 + (-1 + 5 + this.mBusLine.getStations().size()) / 5));
    }

    public void setBusLineAndOnOff(LineBean paramBusLine, int paramInt1, int paramInt2) {

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

    public void setOnBusStationClickListener(OnBusStationClickListener paramOnBusStationClickListener) {
        this.mOnStationClickListener = paramOnBusStationClickListener;
    }

    public void updateBuses(List<BusBean> paramList) {
        this.mBusData = paramList;
//		this.beanList1 = beanList;
//		,List<Cosy.DataBean> beanList
        invalidate();
    }

    public void updateBuses1(List<BusBean> paramList,List<Cosy.DataBean> beanList) {
        this.mBusData = paramList;
        this.beanList1 = beanList;
        invalidate();
    }

    public static abstract interface OnBusStationClickListener {
        public abstract void onBusStationClick(View paramView, int paramInt1, int paramInt2, int paramInt3);
    }

    //通知已经上车
//	public void setOnBus(boolean iden2) {
//		// TODO Auto-generated method stub
//		iden=true;
//		invalidate();
//	}

    //点击事件的判断
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub

        float x = event.getX();
        float y = event.getY();
        if (stlist == null) {
            return super.onTouchEvent(event);
        }


        //没选择我要做车，只能选择上车点不能选择下车
        for (int i = 0; i < stlist.size(); i++) {
            StationRegion sr = stlist.get(i);
            int sxx = sr.getSxx();
            int zd = sr.getStation();
            float maxX = sr.getMaxX();
            float minX = sr.getMinX();
            float maxY = sr.getMaxY();
            float minY = sr.getMinY();
            if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
                if (iden == 0) {
                    //选择了我要上车后，可以选择下车点

                    if (zd == mGetOffStationIdx) {
                        offIden = false;
                        mGetOffStationIdx = 0;
                    } else {
                        if (sxx == 0) {
                            if (zd > mGetOnStationIdx) {
                                offIden = false;
                                mGetOffStationIdx = zd;
                                ConstData.upBus = mGetOffStationIdx;
                                iden = 1;
                            }
                        } else {
                            if (zd < mGetOnStationIdx) {
                                offIden = false;
                                mGetOffStationIdx = zd;
                                ConstData.upBus = mGetOffStationIdx;
                                iden = 1;
                            }
                        }
                    }


                } else {

                    mGetOnStationIdx = -1;
                    mGetOffStationIdx = -1;
                    if (zd == mGetOnStationIdx) {
                        onIden = false;
                        mGetOnStationIdx = 0;
                    } else {
                        onIden = false;
                        mGetOnStationIdx = zd;
                        ConstData.onBus = mGetOnStationIdx;
                    }
                    iden = 0;
                }
                break;
            }
        }


        invalidate();
        return super.onTouchEvent(event);
    }

    //计算每个站点的坐标
    public void SumStationXY(float x, float y, int station, int sxx, Bitmap Icon) {
        StationRegion sr = new StationRegion();
        sr.setX(x);
        sr.setY(y);
        sr.setSxx(sxx);
        float maxX = x + Icon.getWidth() / 2;
        float minX = x - Icon.getWidth() / 2;
        float maxY = y + Icon.getHeight() / 2;
        float minY = y - Icon.getHeight() / 2;
        sr.setMaxX(maxX);
        sr.setMaxY(maxY);
        sr.setMinX(minX);
        sr.setMinY(minY);
        sr.setStation(station);
        stlist.add(sr);
    }

    //判断是否进行提醒
    private void updateBuses(){

        if(mBusData!=null){
            int sxx =this.mBusLine.getLineId();//上下行
            Iterator<BusBean> iterator = mBusData.iterator();
            //没选择上车只报上车提醒
            if (!offIden){
                if(!onIden){
                    while (iterator.hasNext()) {
                        BusBean businfo = iterator.next();
                        String busSxx=businfo.getSxx();				//上下行
                        float leftPos = businfo.getLeftStation();	//经过站点
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
            //选择了上车 上下站都提醒
            //先进行上车提醒
            if(!onIden){
                while (iterator.hasNext()) {
                    BusBean businfo = iterator.next();
                    String busSxx=businfo.getSxx();				//上下行
                    String busCode=businfo.getBusCode();		//车号
                    float leftPos = businfo.getLeftStation();	//经过站点
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
            //在处理下车提醒
            if(!offIden){
                while (iterator.hasNext()) {
                    BusBean businfo = iterator.next();
                    String busSxx=businfo.getSxx();				//上下行
                    String busCode=businfo.getBusCode();		//车号
                    float leftPos = businfo.getLeftStation();	//经过站点
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

    //播放提示音及震动提示
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            for(int i=0;i<3;i++){
                handler.sendEmptyMessage(1);
                Vibrator vibrator = (Vibrator) SmartBusApp.getInstance().getApplicationContext().getSystemService(SmartBusApp.getInstance().getApplicationContext().VIBRATOR_SERVICE);
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
            String str="距离";
            //语音播报
            if (isFlag==1){
                str=str+mGetOnStationName+"还有"+sumleftStation+"站";
            }
            if (isFlag==2){
                str=str+mGetOffStationName+"还有"+sumleftStation+"站";
            }
            if (!str.equals("距离")){
//				if(isVideo==0){
//					speechSynthesizer.speak(str);
//				}
            }
            isFlag=0;
        }
    };

    //文字提示
    private Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(SmartBusApp.getInstance().getApplicationContext(), getResources().getString(R.string.one_station), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        };
    };


    @Override
    public void onStartWorking(SpeechSynthesizer arg0) {
        Log.e(TAG, "请稍等");
    }

    @Override
    public void onSpeechStart(SpeechSynthesizer synthesizer) {
        Log.e(TAG, "开始");
    }

    @Override
    public void onSpeechResume(SpeechSynthesizer synthesizer) {
        Log.e(TAG, "继续");
    }

    @Override
    public void onSpeechProgressChanged(SpeechSynthesizer synthesizer, int progress) {

    }

    @Override
    public void onSpeechPause(SpeechSynthesizer synthesizer) {
        Log.e(TAG, "已暂停");
    }

    @Override
    public void onSpeechFinish(SpeechSynthesizer synthesizer) {
        Log.e(TAG, "已停止");
    }

    @Override
    public void onNewDataArrive(SpeechSynthesizer synthesizer, byte[] audioData, boolean isLastData) {
        Log.e(TAG, "新音频数据");
    }

    @Override
    public void onError(SpeechSynthesizer synthesizer, SpeechError error) {
        Log.e(TAG, "发生错误"+error);
    }

    @Override
    public void onCancel(SpeechSynthesizer synthesizer) {
        Log.e(TAG, "已取消");
    }

    @Override
    public void onBufferProgressChanged(SpeechSynthesizer synthesizer, int progress) {

    }

    @Override
    public void onSynthesizeFinish(SpeechSynthesizer arg0) {
        // TODO Auto-generated method stub
        Log.e(TAG, "合成已完成");
    }

}
