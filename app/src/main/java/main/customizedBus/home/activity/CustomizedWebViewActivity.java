package main.customizedBus.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;


import java.util.HashMap;
import java.util.Map;

import main.customizedBus.home.bean.CustomizedBusIntroduceBean;
import main.customizedBus.home.module.CustomizedBusIntroduceContract;
import main.customizedBus.home.presenter.CustomizedBusIntroducePresenter;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;

public class CustomizedWebViewActivity extends BaseActivity implements CustomizedBusIntroduceContract.View {
  private WebView webView;
  private String content;
  private int introduceType = 0;//1.�˳�ָ��2.���ƹ���3.��Ʊ��֪
  private CustomizedBusIntroducePresenter introducePresenter;
  private TextView testView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        introducePresenter = new CustomizedBusIntroducePresenter(this,this);
        Intent intent = getIntent();
        introduceType = intent.getExtras().getInt("introduceType");
        Map<String,Object> param = new HashMap<>();
      switch (introduceType){
          case 1:
              activityHeader.setTvTitle("�˳�ָ��");
              param.put("messageType",3003);
              break;
          case 2:
              activityHeader.setTvTitle("���ƹ���");
              param.put("messageType",3004);
              break;
          case 3:
              activityHeader.setTvTitle("��Ʊ��֪");
              param.put("messageType",3005);
              break;
      }



        introducePresenter.sendRequestGetCustomizedBusIntroduce(param);

    }

    @Override
    public void initView() {
        super.initView();
        webView = findViewById(R.id.id_web_view);
    }

    private void reloadView(){
        //����html
        String str =  Html.fromHtml(content).toString();
        webView.loadData(content, "text/html", "utf-8");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//����ʹ��js
        //��ʹ�û��棬ֻ�������ȡ����.
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //֧����Ļ����
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webSettings.setTextZoom(260);

    }
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_customized_web_view;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }

    @Override
    public void requestOnSuccees(CustomizedBusIntroduceBean introduceBean) {
        if (introduceBean.getCode()==1){
            if (introduceBean.getData().size()>0){
                CustomizedBusIntroduceBean.DataBean dataBean =  introduceBean.getData().get(0);
                content = dataBean.getDetails();
            }


            reloadView();
        }
        showMessage(introduceBean.getMsg());
    }

    @Override
    public void requestOnFailure(Throwable e, boolean isNetWorkError) {
        showToastNetFail();

    }
}
