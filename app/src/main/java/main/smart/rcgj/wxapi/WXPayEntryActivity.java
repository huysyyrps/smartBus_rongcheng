package main.smart.rcgj.wxapi;






import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import main.smart.common.util.Constants;
import main.smart.rcgj.R;
import main.utils.utils.SharePreferencesUtils;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	private IWXAPI api;
	private Button fanhui;
	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_result);

      SharePreferencesUtils sharePreferencesUtils = new SharePreferencesUtils();
      String  text =  sharePreferencesUtils.getString(this,"wxPayResult","");
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

		api.handleIntent(getIntent(), this);
		fanhui=findViewById(R.id.btn_fan);
		textView = findViewById(R.id.text_content);
		textView.setText(text);
		fanhui.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				WXPayEntryActivity.this.finish();
			}
		});
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {
		int code = resp.errCode;
		if (code == 0){
			Toast.makeText(WXPayEntryActivity.this,  R.string.chargesuc, Toast.LENGTH_SHORT).show();
			//显示充值成功的页面和需要的操作
		}

		if (code == -1){
			//错误
			Toast.makeText(WXPayEntryActivity.this, R.string.chargefail, Toast.LENGTH_SHORT).show();
			api.openWXApp();
			WXPayEntryActivity.this.finish();
		}

		if (code == -2){
			Toast.makeText(WXPayEntryActivity.this, R.string.chargefailqu, Toast.LENGTH_SHORT).show();
			WXPayEntryActivity.this.finish();
		}
	}

}