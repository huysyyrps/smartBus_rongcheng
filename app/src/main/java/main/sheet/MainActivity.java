package main.sheet;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.login.LoginActivity;
import main.other.OtherListActivity;
import main.sheet.bean.UpVersion;
import main.sheet.fragment.Fragment01;
import main.sheet.fragment.Fragment02;
import main.sheet.fragment.Fragment03;
import main.sheet.module.UpVersionContract;
import main.sheet.presenter.UpVersionPresenter;
import main.smart.activity.CitySwitchActivity;
import main.smart.common.util.CityManager;
import main.smart.rcgj.R;
import main.utils.base.AlertDialogCallBack;
import main.utils.base.AlertDialogUtil;
import main.utils.base.BaseActivity;
import main.utils.utils.SharePreferencesUtils;
import permission.AnnPermission;
import permission.PermissionCallback;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, UpVersionContract.View {

    private static boolean isExit = false;
    AlertDialogUtil alertDialogUtil;
    //推出程序
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.rb3)
    RadioButton rb3;

    String downData = "";
    String downUrl = "";
    UpVersionPresenter upVersionPresenter;
    private CityManager mCityMan;
    private Fragment01 fragment01;
    private Fragment02 fragment02;
    private Fragment03 fragment03;
    private FragmentManager manager;
    SharePreferencesUtils sharePreferencesUtils;
    Intent intent = null;
    String userName;

    //推出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
           // exit();
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            alertDialogUtil.showDialog(getResources().getString(R.string.alert_logout), new AlertDialogCallBack() {

                @Override
                public int getData(int s) {
                    return 0;
                }

                @Override
                public void confirm() {

                    finish();

                }

                @Override
                public void cancel() {

                }
            });
            mHandler.sendEmptyMessageDelayed(0, 4000);
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        hideStatusNavigationBar();
        ButterKnife.bind(this);
        sharePreferencesUtils = new SharePreferencesUtils();
        userName = sharePreferencesUtils.getString(this, "userName", "");
        alertDialogUtil = new AlertDialogUtil(this);
        this.mCityMan = CityManager.getInstance();
        //默认选中第一个
        RadioButton btn = (RadioButton) radioGroup.getChildAt(0);
        btn.setChecked(true);

        AnnPermission.create(MainActivity.this)
                .animStyle(R.style.PermissionAnimFade)
                .checkMutiPermission(new PermissionCallback() {
                    @Override
                    public void onClose() {

                        showToast(getString(R.string.permission_on_close));
                    }

                    @Override
                    public void onFinish() {
                     //   showToast(getString(R.string.permission_completed));
                    }

                    @Override
                    public void onDeny(String permission, int position) {

                    }

                    @Override
                    public void onGuarantee(String permission, int position) {

                    }
                });
        initFragment();
        radioGroup.setOnCheckedChangeListener(MainActivity.this);
        // 未选择过城市，弹出提示
        Dialog();
        upVersionPresenter = new UpVersionPresenter(this, this);
        upVersionPresenter.getUpVersion();

    }

    // 未选择城市提示框
    public boolean Dialog() {
        String versionName = getAppVersionName(this);
        String oldVersionName = new SharePreferencesUtils().getString(this, "versionName", "");
        int CityCode = mCityMan.getSelectCityCode();
        if (CityCode == 0 || !mCityMan.isSelected() || !versionName.equals(oldVersionName)) {
            Intent intent = new Intent(this, CitySwitchActivity.class);
            startActivity(intent);
        }
        return true;
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

    /**
     * 初始化第一个页面
     */
    private void initFragment() {
        //获取管理器
        manager = getSupportFragmentManager();
        //通过管理器获取一个事件
        FragmentTransaction transaction = manager.beginTransaction();
        //添加第一个fragment到帧布局中
        fragment01 = new Fragment01();
        transaction.add(R.id.frame_layout, fragment01);
        transaction.commit();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isHasHeader() {
        return false;
    }

    @Override
    protected void rightClient() {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.rb1:
                FragmentTransaction ft1 = manager.beginTransaction();
                hideAll(ft1);
                if (fragment01 == null) {
                    fragment01 = new Fragment01();
                    ft1.add(R.id.frame_layout, fragment01);
                } else {
                    ft1.show(fragment01);
                }
                ft1.commit();
                break;
            case R.id.rb2:
                if (userName != null && !userName.equals("")) {
                FragmentTransaction ft2 = manager.beginTransaction();
                hideAll(ft2);
                if (fragment02 == null) {
                    fragment02 = new Fragment02();
                    ft2.add(R.id.frame_layout, fragment02);
                } else {
                    ft2.show(fragment02);
                }
                ft2.commit();
                } else {
                    intent = new Intent(this, LoginActivity.class);
                    intent.putExtra("tag", "inner");
                    startActivity(intent);
                }
                break;
            case R.id.rb3:
                FragmentTransaction ft3 = manager.beginTransaction();
                hideAll(ft3);
                if (fragment03 == null) {
                    fragment03 = new Fragment03();
                    ft3.add(R.id.frame_layout, fragment03);
                } else {
                    ft3.show(fragment03);
                }
                ft3.commit();
                break;
        }
    }

    /**
     * 隐藏所有fragment
     *
     * @param ft
     */
    private void hideAll(FragmentTransaction ft) {
        if (ft == null) {
            return;
        }
        if (fragment01 != null) {
            ft.hide(fragment01);
        }
        if (fragment02 != null) {
            ft.hide(fragment02);
        }
        if (fragment03 != null) {
            ft.hide(fragment03);
        }


    }

    private void hideStatusNavigationBar() {
        if (Build.VERSION.SDK_INT < 16) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN //hide statusBar
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; //hide navigationBar
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }
    }


    @Override
    public void setUpVersion(UpVersion bean) {
        String versionNo = bean.getData().get(0).getVnumber();
        Log.e("soso","*************"+versionNo);
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = MainActivity.this.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(MainActivity.this.getPackageName(), 0);
            versionName = pi.versionCode+"";

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("soso",Integer.parseInt(versionNo)+"versionName*************"+Integer.parseInt(versionName));
        if (Integer.parseInt(versionNo)>Integer.parseInt(versionName)) {

          //  if (!versionNo.equals(versionName) && Double.valueOf(versionNo) > Double.valueOf(versionName)) {
            downData = bean.getData().get(0).getUpdateinformation();
            downUrl = bean.getData().get(0).getDownloadlink();
            new AlertDialogUtil(MainActivity.this).showDialog(getResources().getString(R.string.want_updata) + "\n" + downData, new AlertDialogCallBack() {
                @Override
                public int getData(int s) {
                    return 0;
                }

                @Override
                public void confirm() {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri content_url = Uri.parse(downUrl);
                    intent.setData(content_url);
                    startActivity(intent);
                }

                @Override
                public void cancel() {
//                    finish();
                }
            });
        }
    }

    @Override
    public void setUpVersionMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
