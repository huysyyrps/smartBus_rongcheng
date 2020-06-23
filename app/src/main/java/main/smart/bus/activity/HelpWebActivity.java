package main.smart.bus.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import main.smart.rcgj.R;

public class HelpWebActivity extends Activity {
	private WebView mWebView;
	private ProgressBar mProgress;

	private String mURL;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_web_view);


		Button btnBack = (Button) findViewById(R.id.btnBack);
		TextView tvHearder = (TextView) findViewById(R.id.hearder);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		Intent intent = getIntent();
		Bundle bunde = intent.getExtras();
		String hearder = bunde.getString("hearder");
		tvHearder.setText(hearder);
		mURL = bunde.getString("URL");

		mWebView = (WebView) findViewById(R.id.help_webview);
		//WebView加载web资源
		mProgress = ((ProgressBar)findViewById(R.id.helpWebProgress));

		WebSettings webSettings = mWebView.getSettings();

		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//

		webSettings.setJavaScriptEnabled(true);

		//支持屏幕缩放
		webSettings.setSupportZoom(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setDisplayZoomControls(false);
		//
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
		webSettings.setTextZoom(260);


		//"http://www.baidu.com"
		mWebView.loadUrl(mURL);
		//覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
		mWebView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				// TODO Auto-generated method stub
				//返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器

				view.loadUrl(url);

				return true;
			}

		});


		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				if (newProgress == 100) {
					// 网页加载完成
					mProgress.setVisibility(View.GONE);
				} else {
					// 加载中
					mProgress.setProgress(newProgress);

				}

			}
		});

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
				//表示按返回键时的操作
				mWebView.goBack();   //后退

				//mWebView.goForward();//前进
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
