package main.other;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.sheet.bean.Swrllist;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.views.Header;

public class SwrlDetailActivity extends BaseActivity {

    @BindView(R.id.header)
    Header header;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvLine)
    TextView tvLine;
    @BindView(R.id.tvContent)
    WebView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        header.setRightTv(false);
        Intent intent = getIntent();
        Swrllist.DataBean  bean = (Swrllist.DataBean) intent.getSerializableExtra("bean");
        tvTitle.setText(bean.getTitle());
        tvTime.setText(bean.getOccurrenceTime());
        tvLine.setText(bean.getBusRoutes());
        //加载html
        tvContent.loadData(Html.fromHtml(bean.getDetailedDescription()).toString(), "text/html", "UTF-8");
        WebSettings webSettings = tvContent.getSettings();
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
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_swrl_detail;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {
    }
}
