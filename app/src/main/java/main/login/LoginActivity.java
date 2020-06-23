package main.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.login.bean.Login;
import main.login.module.LoginContract;
import main.login.presenter.LoginPresenter;
import main.sheet.MainActivity;
import main.smart.rcgj.R;
import main.utils.base.AlertDialogCallBack;
import main.utils.base.AlertDialogUtil;
import main.utils.base.BaseActivity;
import main.utils.utils.MD5;
import main.utils.utils.NetworkTest;
import main.utils.utils.SharePreferencesUtils;
import main.utils.views.Header;

public class LoginActivity extends BaseActivity implements LoginContract.View {
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
    @BindView(R.id.header)
    Header header;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etPassWord)
    EditText etPassWord;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tvForgrtPassword)
    TextView tvForgrtPassword;
    @BindView(R.id.tvRegister)
    TextView tvRegister;
    Intent intent;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.ivSeeNewPw)
    ImageView ivSeeNewPw;
    private boolean mbDisplayFlg = false;
    LoginPresenter loginPresenter;
    SharePreferencesUtils sharePreferencesUtils;

    String tag="";

    //推出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (tag.equals("inner")){
                finish();
            }else if (tag.equals("outter")){
                exit();
                finish();
            }else {
                finish();
            }
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
        ButterKnife.bind(this);
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");
        loginPresenter = new LoginPresenter(this, this);
        sharePreferencesUtils = new SharePreferencesUtils();
        String userNamenew = sharePreferencesUtils.getString(this, "userNamenew", "");
        String passWordnew = sharePreferencesUtils.getString(this, "passWordnew", "");
        etUserName.setText(userNamenew);
        etPassWord.setText(passWordnew);

        String userName = sharePreferencesUtils.getString(this, "userName", "");
        String passWord = sharePreferencesUtils.getString(this, "passWord", "");
        if (!userName.equals("") && !passWord.equals("")) {
            etUserName.setText(userName);
            etPassWord.setText(passWord);
        }
        alertDialogUtil = new AlertDialogUtil(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }

    @OnClick({R.id.btn_login, R.id.tvForgrtPassword, R.id.tvRegister, R.id.ivSeeNewPw,R.id.tv1, R.id.tv2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String mduserPassWord = new MD5().md5(etPassWord.getText().toString());
                if (etUserName.getText().toString().equals("") || etPassWord.getText().toString().equals("")) {
                    Toast.makeText(this, getResources().getString(R.string.no_name_password), Toast.LENGTH_SHORT).show();
                } else {
                    if (mduserPassWord.equals("")) {
                        Toast.makeText(this, getResources().getString(R.string.md_faile), Toast.LENGTH_SHORT).show();
                    } else {
                        if ((Boolean) new NetworkTest().goToNetWork(this)) {
                            loginPresenter.getLogin(etUserName.getText().toString(), mduserPassWord, "APP");
                        } else {
                            Toast.makeText(this, getResources().getString(R.string.umeng_socialize_network), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case R.id.tvForgrtPassword:
                intent = new Intent(this, CheckPassWordActivity.class);
                startActivity(intent);
                break;
            case R.id.tvRegister:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.ivSeeNewPw:
                if (!mbDisplayFlg) {
                    etPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mbDisplayFlg = !mbDisplayFlg;
                break;
            case R.id.tv1:
                intent = new Intent(this,AgreeActivity.class);
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

    @Override
    protected void onRestart() {
        super.onRestart();
        String userName = sharePreferencesUtils.getString(this, "userName", "");
        String passWord = sharePreferencesUtils.getString(this, "passWord", "");
        if (!userName.equals("") && !passWord.equals("")) {
            etUserName.setText(userName);
            etPassWord.setText(passWord);
        }
    }

    @Override
    public void setLogin(Login loginBean) {
        if (loginBean.getCode() == 3) {
            Toast.makeText(this, loginBean.getMsg(), Toast.LENGTH_SHORT).show();
        } else if (loginBean.getCode() == 1) {
            sharePreferencesUtils.setString(this, "userName", etUserName.getText().toString());
            sharePreferencesUtils.setString(this, "passWord", etPassWord.getText().toString());


            sharePreferencesUtils.setString(this, "userNamenew", etUserName.getText().toString());
            sharePreferencesUtils.setString(this, "passWordnew", etPassWord.getText().toString());
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void setLoginMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
