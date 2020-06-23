package main.sheet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.login.LoginActivity;
import main.sheet.bean.Change;
import main.sheet.module.ChangeContract;
import main.sheet.presenter.ChangePresenter;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.utils.MD5;
import main.utils.utils.SharePreferencesUtils;
import main.utils.views.Header;

public class ChangeWordActivity extends BaseActivity implements ChangeContract.View {

    @BindView(R.id.header)
    Header header;
    @BindView(R.id.etOldPassWord)
    EditText etOldPassWord; //输入的是原密码
    @BindView(R.id.etPassWord)
    EditText etPassWord;//输入新密码
    @BindView(R.id.etPassWordAgain)
    EditText etPassWordAgain;//确认密码
    @BindView(R.id.btn)
    Button btn;
    String passWord1, passWord2;
    String userName,passWord;
    ChangePresenter changePresenter;
    SharePreferencesUtils sharePreferencesUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        sharePreferencesUtils = new SharePreferencesUtils();
        userName = sharePreferencesUtils.getString(this, "userName", "");
        passWord = sharePreferencesUtils.getString(this, "passWord", "");
        passWord1 = etPassWord.getText().toString();
        passWord2 = etPassWordAgain.getText().toString();
        changePresenter = new ChangePresenter(this,this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_change_word;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        if (etOldPassWord.getText().toString().equals("")){
            Toast.makeText(this, getResources().getString(R.string.write_password_old), Toast.LENGTH_SHORT).show();
            return;
        }else if (etPassWord.getText().toString().equals("")){
            Toast.makeText(this, getResources().getString(R.string.write_password_new), Toast.LENGTH_SHORT).show();
            return;
        }else if (etPassWordAgain.getText().toString().equals("")){
            Toast.makeText(this, getResources().getString(R.string.write_password_new), Toast.LENGTH_SHORT).show();
            return;
        }
        passWord1 = etPassWord.getText().toString();
        passWord2 = etPassWordAgain.getText().toString();
        if (!passWord1.equals(passWord2)){
            Toast.makeText(this,getResources().getString(R.string.write_password_newagain) , Toast.LENGTH_SHORT).show();
            return;
        }
        if(!passWord.equals(etOldPassWord.getText().toString())){
            Toast.makeText(this,getResources().getString(R.string.write_password_yuan) , Toast.LENGTH_SHORT).show();
            return;
        }
        passWord=etOldPassWord.getText().toString();
        passWord = new MD5().md5(passWord);
        passWord1 = new MD5().md5(passWord1);
        changePresenter.getChange(userName,passWord,passWord1);
    }

    @Override
    public void setChange(Change notice) {
        if (notice.getCode() == 3) {
            Toast.makeText(this, notice.getMsg(), Toast.LENGTH_SHORT).show();
        } else if (notice.getCode() == 1) {
            sharePreferencesUtils.setString(this, "passWord", etPassWord.getText().toString());
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void setChangeMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
