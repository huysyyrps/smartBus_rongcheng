package main.sheet;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.login.AgreeActivity;
import main.login.CheckPassWordActivity;
import main.login.RegisterActivity;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.utils.MD5;
import main.utils.utils.NetworkTest;
import main.utils.views.Header;

public class AboutMyActivity extends BaseActivity {

    @BindView(R.id.header)
    Header header;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvStation)
    TextView tvStation;
    Intent intent;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            String versionName = pi.versionName;
            tvVersion.setText(versionName);
            tvPhone.setText("0631-7571234");
            tvStation.setText("http://www.joycart.com.cn/");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_about_my;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }

    @OnClick({R.id.tv1, R.id.tv2})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv1:
                intent = new Intent(this, AgreeActivity.class);
                intent.putExtra("tag","1");
                intent.putExtra("url","https://www.lbsmk.com:7443//IBAPP/xy/yhxy.html");
                startActivity(intent);
                break;
            case R.id.tv2:
                intent = new Intent(this,AgreeActivity.class);
                intent.putExtra("tag","2");
                intent.putExtra("url","https://www.lbsmk.com:7443/IBAPP/xy/yszc.html");
                startActivity(intent);
                break;
        }
    }
}
