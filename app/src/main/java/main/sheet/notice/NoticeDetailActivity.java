package main.sheet.notice;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.sheet.bean.Notice;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.views.Header;

public class NoticeDetailActivity extends BaseActivity {

    @BindView(R.id.header)
    Header header;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
//        DisplayMetrics dm = getResources().getDisplayMetrics();
////        int scale = dm.densityDpi;
////        if (scale == 240) { //设置自动适配
////            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
////        } else if (scale == 160) {
////            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
////        } else {
////            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
////        }



        Intent intent = getIntent();
        Notice.DataBean.NoticeBean bean = (Notice.DataBean.NoticeBean) intent.getSerializableExtra("bean");
        String title = bean.getTitle();
        String content = bean.getDetails();
        String time = bean.getReleaseTime();
        tvTitle.setText(title);
        //加载html
        webView.loadDataWithBaseURL(null,content,"text/html","UTF-8",null);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
        //不使用缓存，只从网络获取数据.
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webSettings.setTextZoom(260);
//        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date(time);
//        String str = sdf.format(date);
        tvTime.setText(time);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_notice_detail;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }
}
