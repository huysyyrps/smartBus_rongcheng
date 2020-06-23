package main.smart.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import main.Charge.ConsumptionActivity;
import main.Charge.TransactionActivity;
import main.smart.rcgj.R;


public class RecordActivitynew extends Activity {
    private EditText cardNoText;//卡号
    private String cardNo;
    private Button btn_logincha;
    private RelativeLayout alljob_black3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordnew);
       // StatusBarUtil.setStatusBarMode(this, true, R.color.color_bg_selectednew);

        cardNoText=findViewById(R.id.textCardNo2cha);
        btn_logincha=findViewById(R.id.btn_logincha);
        alljob_black3=findViewById(R.id.alljob_black3);
        alljob_black3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordActivitynew.this.finish();
            }
        });
        btn_logincha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((cardNoText.getText().toString()).equals("")){
                    Toast.makeText(RecordActivitynew.this, R.string.chargexuanka, Toast.LENGTH_SHORT).show();

                }else{
                    cardNo=cardNoText.getText().toString();
                    if(cardNo.length()<9){
                        if((9-(cardNo.length()))==3){
                            cardNo="000"+cardNo;
                        }else if((9-(cardNo.length()))==2){
                            cardNo="00"+cardNo;
                        }else if((9-(cardNo.length()))==1){
                            cardNo="0"+cardNo;
                        }

                    }else if(cardNo.length()==19){
                        cardNo="0"+cardNo;
                    }
//

                    //1、使用Dialog、设置style
                    final Dialog dialog = new Dialog(RecordActivitynew.this, R.style.DialogTheme);
                    //2、设置布局
                    View viewdia = View.inflate(RecordActivitynew.this, R.layout.dialogbottom, null);
                    dialog.setContentView(viewdia);

                    Window window = dialog.getWindow();
                    //设置弹出位置
                    window.setGravity(Gravity.BOTTOM);
                    //设置弹出动画
//                window.setWindowAnimations(R.style.main_menu_animStyle);
                    //设置对话框大小
                    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.show();

                    dialog.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("cardNo",cardNo);
                        intent.setClass(RecordActivitynew.this, TransactionActivitynew.class);
                        startActivity(intent);
                        }
                    });

                    dialog.findViewById(R.id.tv_take_pic).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            intent.putExtra("cardNo",cardNo);
                            intent.setClass(RecordActivitynew.this, ConsumptionActivity.class);
                            startActivity(intent);
                        }
                    });

                    dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });


//                OnekeyShare oks = new OnekeyShare();
//                // title标题，微信、QQ和QQ空间等平台使用
//                oks.setTitle(getString(R.string.share));
//                // titleUrl QQ和QQ空间跳转链接
//                oks.setTitleUrl("https://www.lbsmk.com:7443/IBAPP/downloadUrl/downloadApp.html");
//                // text是分享文本，所有平台都需要这个字段
//                oks.setText(getString(R.string.share));
//                // imagePath是图片的本地路径，确保SDcard下面存在此张图片
//                oks.setImagePath("/sdcard/test.jpg");
//                // url在微信、Facebook等平台中使用
////                oks.setUrl("http://sharesdk.cn");
//                // 启动分享GUI
//                oks.show(getActivity());
//
//                Wechat.ShareParams sp = new Wechat.ShareParams();
//                sp.setShareType(Platform.SHARE_WEBPAGE);//非常重要：一定要设置分享属性
//                sp.setTitle(getString(R.string.share)); //分享标题
//                sp.setText(getString(R.string.share));   //分享文本
//                sp.setUrl("https://www.lbsmk.com:7443/IBAPP/downloadUrl/downloadApp.html");   //网友点进链接后，可以看到分享的详情
//                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
////                wechat.setPlatformActionListener(platformActionListener); // 设置分享事件回调
//                // 执行分享
//                wechat.share(sp);
                                    }
            }
        });

    }
}
