package main.sheet.complaints;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.sheet.bean.Complaints;
import main.sheet.module.ComplaintsContract;
import main.sheet.presenter.ComplaintsPresenter;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.utils.SharePreferencesUtils;
import main.utils.views.ForbidEmojiEditText;
import main.utils.views.Header;

public class ComplaintsUpActivity extends BaseActivity implements ComplaintsContract.View {

    @BindView(R.id.header)
    Header header;
    @BindView(R.id.etPhone)
    TextView etPhone;
    @BindView(R.id.etContent)
    ForbidEmojiEditText etContent;
    @BindView(R.id.btnUp)
    Button btnUp;

    String account = "";
    SharePreferencesUtils sharePreferencesUtils;
    ComplaintsPresenter complaintsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        sharePreferencesUtils = new SharePreferencesUtils();
        String userName = sharePreferencesUtils.getString(this, "userName", "");
        etPhone.setText(userName);
        account = new SharePreferencesUtils().getString(this,"userName","");
        complaintsPresenter = new ComplaintsPresenter(this,this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_complaints_up;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {
        Intent intent = new Intent(this,ComplaintsListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnUp)
    public void onViewClicked() {
        if (etPhone.getText().toString().equals("")){
            Toast.makeText(this, getResources().getString(R.string.prompt_phone), Toast.LENGTH_SHORT).show();
        }else {
            if (isMobileNO(etPhone.getText().toString())){
                if (etContent.getText().toString().equals("")){
                    Toast.makeText(this, getResources().getString(R.string.content_write), Toast.LENGTH_SHORT).show();
                }else {
                    complaintsPresenter.getComplaints(etPhone.getText().toString(),etContent.getText().toString(),account);
                }
            }else {
                Toast.makeText(this, getResources().getString(R.string.prompt_relaer_phone), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void setComplaints(Complaints complaints) {
        if (complaints.getCode() == 1){
            Toast.makeText(this, getResources().getString(R.string.successs_up), Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(this, complaints.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setComplaintsMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * ��֤�ֻ���ʽ
     */
    public static boolean isMobileNO(String mobiles) {
        /*
         * �ƶ���134��135��136��137��138��139��150��151��157(TD)��158��159��187��188
         * ��ͨ��130��131��132��152��155��156��185��186 ���ţ�133��153��180��189����1349��ͨ��
         * �ܽ��������ǵ�һλ�ض�Ϊ1���ڶ�λ�ض�Ϊ3��5��8������λ�õĿ���Ϊ0-9
         */
        String telRegex = "[1][3456789]\\d{9}";// "[1]"�����1λΪ����1��"[358]"����ڶ�λ����Ϊ3��5��8�е�һ����"\\d{9}"��������ǿ�����0��9�����֣���9λ��
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }
}
