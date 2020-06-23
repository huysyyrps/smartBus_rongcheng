package main.smart.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.smart.rcgj.R;
import main.smart.bus.util.BusManager;
import main.smart.bus.view.CustomerSpinner;
import main.smart.common.bean.SwitchCity;
import main.smart.common.util.CityManager;
import main.smart.common.util.ConstData;
import main.smart.common.util.PreferencesHelper;
import main.utils.utils.SharePreferencesUtils;

/**
 * 切换城市 定位城市和手动选择的城市
 */
public class CitySwitchActivity extends Activity {
    private CityManager mCityMan;
    private BusManager mBusMan;
    private CustomerSpinner cityComb;
    private ArrayList<String> list;
    private ArrayList<String> letterToCity;
    private AlertDialog dialog;
    private List<SwitchCity> cityList;
    private String mDCityName = "";

    //private boolean mbFirst = true;
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        this.mCityMan = CityManager.getInstance();
        setContentView(R.layout.activity_city_switch);
        this.mBusMan = BusManager.getInstance();
        // 加载本地城市设置信息
//		String allEnCodes="A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";

        getCitiesInDB();
        // 定位城市
        TextView gpsCityView = (TextView) findViewById(R.id.current_city);
        gpsCityView.setText(ConstData.GPS_CITY);
        cityComb = (CustomerSpinner) findViewById(R.id.city_select_id);
        cityComb.setList(letterToCity);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letterToCity);
        cityComb.setAdapter(adapter);
        //监听
        cityComb.setOnItemSelectedListener(new CitySpinnerSelectedListener());
        //选中城市
        for (int i = 0; i < letterToCity.size(); i++) {
            if (mDCityName.equals(letterToCity.get(i))) {

                cityComb.setSelection(i);
                break;
            }
        }
        //首次打开默认选中的是cityCode=0
        SwitchCity selCity = new SwitchCity();
        selCity.setCityCode(0);

        for (int i = 0; i < cityList.size(); i++) {
            if ("荣成市".equals(cityList.get(i).getCityName())) {
                selCity = cityList.get(i);
            }
        }


        System.out.println("mCityMan.getSelectCityCode()=" + mCityMan.getSelectCityCode());
        System.out.println("selCity.getCityCode()=" + selCity.getCityCode());
        System.out.println("selCity=" + selCity.toString());

//        if ((mCityMan.getSelectCityCode()!=selCity.getCityCode())&&!(mCityMan.isSelected())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CitySwitchActivity.this);
            builder.setMessage("正在下载数据请稍后...");
            builder.setTitle("提示");
            builder.setCancelable(false);
            dialog = builder.show();
            dialog.show();

            PreferencesHelper mPreference = PreferencesHelper.getInstance();
            if (selCity.getCityHelp() != null) {
                //ConstData.help=selCity.getCityHelp();
                mPreference.updateCityHelp(ConstData.help);
            } else {
                mPreference.updateCityHelp(ConstData.help);
            }

            //清空查询线路历史记录
            mBusMan.clearBusLineHistory();
            //清空保存在本地的线路站点信息
            mBusMan.clearBusStationList();


            //封装数据
            ConstData.goURL = "http://" + selCity.getIp() + ":" + selCity.getGoServerPort();
            Log.e("goURL", ConstData.goURL);
            ConstData.URL = selCity.getUrl();
            URL url;
            try {
                url = new URL(ConstData.URL);
                ConstData.tmURL = "http://" + url.getHost() + ":" + url.getPort();
            } catch (MalformedURLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
if (selCity.getCenterX()!=null){
    ConstData.CENTER_X = Double.parseDouble(selCity.getCenterX());
    ConstData.CENTER_Y = Double.parseDouble(selCity.getCenterY());
}else{

    Toast.makeText(CitySwitchActivity.this,"获取不到城市",Toast.LENGTH_LONG).show();
}

            System.out.println("组装url:" + ConstData.goURL);
            //下载数据
            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    //下载数据
                    try {
                        mCityMan.LoadInterface(true);
                        ConstData.bLoadMenu = true;

                    } catch (MalformedURLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            });
            thread.start();

            mCityMan.getAllLine();
            mCityMan.setSelectedCity(selCity);
            mCityMan.updateCityServer(true, handler);

            //设定选中城市
            ConstData.SELECT_CITY = selCity.getCityName();
//        ConstData.SELECT_CITY = "三门峡市";
            CitySwitchActivity.this.setResult(10);
            // 取得活动的Preferences对象
            SharedPreferences uiState = getSharedPreferences("user", MODE_PRIVATE);
            // 取得编辑对象
            SharedPreferences.Editor editor = uiState.edit();
            //赋值
            editor.putString("selectCity", ConstData.SELECT_CITY);
            // 提交保存
            editor.commit();
            PreferencesHelper.getInstance().updateNoticeLastDate("");

//        }
        String versionName = getAppVersionName(this);
        SharePreferencesUtils sharePreferencesUtils = new SharePreferencesUtils();
        sharePreferencesUtils.setString(this,"versionName",versionName);
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
//            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }


    private void getCitiesInDB() {
        list = new ArrayList();
        cityList = mCityMan.getSwitchCityList();
        SwitchCity sc = new SwitchCity();
        sc.setCityName("");
        sc.setIp("");
        sc.setCityCode(0);
        cityList.add(0, sc);
        int defaultCityCode = mCityMan.getSelectCityCode();
        for (int i = 0; i < cityList.size(); i++) {
            Log.e(null, "手动选择的城市列表" + cityList.get(i).getCityName());
            if (defaultCityCode == cityList.get(i).getCityCode()) {
                mDCityName = cityList.get(i).getCityName();
            }
            if (cityList.get(i).getCityName().equals("安庆市")) {

            } else {
                list.add(cityList.get(i).getCityName());

            }
        }
        Hanyuu hanyu = new Hanyuu();
        String listAll = "";
        String[] allEnCode = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals("")) {
                listAll = listAll + list.get(i) + ",";
            }
        }
        String strList = hanyu.getStringPinYin(listAll);
        String[] pinyinArr = strList.split(",");
        String[] pininHeadArr = new String[pinyinArr.length + 1];
        pininHeadArr[0] = "";
        for (int i = 0, j = 0; i < pinyinArr.length; i++, j++) {
            String pinyinHead = pinyinArr[i].substring(0, 1).toUpperCase();
            pininHeadArr[j + 1] = pinyinHead;
        }
        letterToCity = new ArrayList<String>();
        String str = "";
        letterToCity.add(str);
        for (int i = 0; i < allEnCode.length; i++) {
            str = allEnCode[i];
            boolean isAddLetter = false;
            for (int j = 0; j < pininHeadArr.length; j++) {
                if (str.equals(pininHeadArr[j])) {
                    if (!isAddLetter) {
                        letterToCity.add(str);
                        isAddLetter = true;
                    }
                    letterToCity.add(list.get(j));
                }
            }
        }

    }

    //下拉列表监听
    class CitySpinnerSelectedListener implements OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long arg3) {
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    //handler 处理
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {

                case 100:
                    dialog.dismiss();
                    finish();
                    break;
                default:
                    break;
            }
        }

        ;
    };


    public void back(View paramView) {
        onBackPressed();

    }

    public void onBackPressed() {
        /*if (!(this.mCityMan.isSelected())) {
			new AlertDialog.Builder(this).setTitle(2131230793)
					.setMessage(2131230817).setPositiveButton(2131230788, null)
					.create().show();
			return;
		}*/
        super.onBackPressed();
    }
}

class Hanyuu {
    private HanyuPinyinOutputFormat format = null;
    private String[] pinyin;

    public Hanyuu() {
        format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    }

    //转换单个字符
    public String getCharacterPinYin(char c) {
        try {
            pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }

        // 如果c不是汉字，toHanyuPinyinStringArray会返回null
        if (pinyin == null) return null;

        // 只取一个发音，如果是多音字，仅取第一个发音
        return pinyin[0];
    }

    //转换一个字符串
    public String getStringPinYin(String str) {
        StringBuilder sb = new StringBuilder();
        String tempPinyin = null;
        for (int i = 0; i < str.length(); ++i) {
            tempPinyin = getCharacterPinYin(str.charAt(i));
            if (tempPinyin == null) {
                // 如果str.charAt(i)非汉字，则保持原样
                sb.append(str.charAt(i));
            } else {
                sb.append(tempPinyin);
            }
        }
        return sb.toString();
    }
}
