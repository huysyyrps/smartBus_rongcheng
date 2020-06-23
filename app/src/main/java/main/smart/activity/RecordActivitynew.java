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
    private EditText cardNoText;//����
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

                    //1��ʹ��Dialog������style
                    final Dialog dialog = new Dialog(RecordActivitynew.this, R.style.DialogTheme);
                    //2�����ò���
                    View viewdia = View.inflate(RecordActivitynew.this, R.layout.dialogbottom, null);
                    dialog.setContentView(viewdia);

                    Window window = dialog.getWindow();
                    //���õ���λ��
                    window.setGravity(Gravity.BOTTOM);
                    //���õ�������
//                window.setWindowAnimations(R.style.main_menu_animStyle);
                    //���öԻ����С
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
//                // title���⣬΢�š�QQ��QQ�ռ��ƽ̨ʹ��
//                oks.setTitle(getString(R.string.share));
//                // titleUrl QQ��QQ�ռ���ת����
//                oks.setTitleUrl("https://www.lbsmk.com:7443/IBAPP/downloadUrl/downloadApp.html");
//                // text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
//                oks.setText(getString(R.string.share));
//                // imagePath��ͼƬ�ı���·����ȷ��SDcard������ڴ���ͼƬ
//                oks.setImagePath("/sdcard/test.jpg");
//                // url��΢�š�Facebook��ƽ̨��ʹ��
////                oks.setUrl("http://sharesdk.cn");
//                // ��������GUI
//                oks.show(getActivity());
//
//                Wechat.ShareParams sp = new Wechat.ShareParams();
//                sp.setShareType(Platform.SHARE_WEBPAGE);//�ǳ���Ҫ��һ��Ҫ���÷�������
//                sp.setTitle(getString(R.string.share)); //�������
//                sp.setText(getString(R.string.share));   //�����ı�
//                sp.setUrl("https://www.lbsmk.com:7443/IBAPP/downloadUrl/downloadApp.html");   //���ѵ�����Ӻ󣬿��Կ������������
//                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
////                wechat.setPlatformActionListener(platformActionListener); // ���÷����¼��ص�
//                // ִ�з���
//                wechat.share(sp);
                                    }
            }
        });

    }
}
