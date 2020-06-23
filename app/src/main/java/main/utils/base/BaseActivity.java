package main.utils.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import main.smart.rcgj.R;
import main.utils.views.Header;
import main.utils.views.StatusBarUtils;


/**
 *
 * activity�Ļ���
 */

public abstract  class BaseActivity extends AppCompatActivity implements BaseView,View.OnClickListener, Header.ClickLister {
    private AlertDialogUtil alertDialogUtil;
    protected Header activityHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // ���ú���
        setContentView(provideContentViewId());
        new StatusBarUtils().setWindowStatusBarColor(BaseActivity.this, R.color.color_bg_selected);
      //  Log.e("xxxxxx",  isHasHeader()+"");
        if(isHasHeader()){
            Header header=(Header)this.findViewById(R.id.header);
            header.setClickLister(this);
            activityHeader = header;
           // Log.e("xxxxxx",  header+"");
           // Log.e("xxxxxx",  this+"");

        }
        initView();
    }

    @Override
    public void initView() {
        initData();
    }

    @Override
    public void showLoading(String message) {
        ProgressDialogUtil.startLoad(this,message);

    }
    @Override
    public void onDestroyView() {
    }

    @Override
    public void hideLoading() {
        ProgressDialogUtil.stopLoad();

    }

    @Override
    public void showMessage(String message) {
        if(!TextUtils.isEmpty(message)){
            Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void toActivity(Context context, Class targetActivity) {
        Intent intent= new Intent(context,targetActivity.getClass());
        context.startActivity(intent);
    }

    @Override
    public void toActivity(Class targetActivity) {
        Intent intent= new Intent(this,targetActivity);
        this.startActivity(intent);
    }

    @Override
    public void toActivity(Class targetActivity, Object obj) {
        Intent intent=new Intent(this,targetActivity);
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",obj.getClass());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showTag(String message) {
        Log.e("Tag",message);
    }

    @Override
    public void showAlertDialg(String description, AlertDialogCallBack alertDialogCallBack) {
        alertDialogUtil= new AlertDialogUtil(this);
        alertDialogUtil.showDialog(description,alertDialogCallBack);
    }

    @Override
    public void showConfirmDialog(String description) {
        alertDialogUtil= new AlertDialogUtil(this);
        alertDialogUtil.showSmallDialog(description);
    }

    @Override
    public void onClick(View view) {

    }
    //�õ���ǰ����Ĳ����ļ�id(������ʵ��)
    protected abstract int provideContentViewId();
    /*
        �Ƿ���header(�������Ƿ����Զ���header)
        true  ��header
        false û��header
     */
    protected  abstract  boolean isHasHeader();

    /**
     * �Ҳ�����¼�
     * @return
     */
    protected  abstract  void rightClient();

    /**
     * headerͷ���ķ��ص���¼�
     */
    @Override
    public void LeftClickLister() {
        finish();
    }

    /**
     * header ͷ�����Ҳ����¼�
     */
    @Override
    public void rightClickLister() {
       // finish();
        rightClient();
    }

    @Override
    public void closeActivity() {
        finish();
    }

    @Override
    public void closeActivity(int resultCode) {
        setResult(resultCode);
        finish();
    }

    @Override
    public void toActivity(Class targetActivity, int state, String flag) {
        Intent intent=new Intent(this,targetActivity);
        intent.putExtra(flag,state);
        startActivity(intent);
    }

    @Override
    public void toActivity(Class targetActivity, String message, String flag) {
        Intent intent=new Intent(this,targetActivity);
        intent.putExtra(flag,message);
        startActivity(intent);
    }

    @Override
    public void toActivity(Class targetActivity, String message, String flag, int state, String flag2) {
        Intent intent=new Intent(this,targetActivity);
        intent.putExtra(flag,message);
        intent.putExtra(flag2,state);
        startActivity(intent);
    }

    @Override
    public void initData() {

    }
    public  void showToastNetFail(){
        showMessage("��������ʧ��");
    }
    public  void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();

    }
}
