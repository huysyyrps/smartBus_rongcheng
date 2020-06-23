package main.Charge;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;

public class RidecodeActivity extends BaseActivity {
    @BindView(R.id.iv)
    ImageView imgCode;
    @BindView(R.id.qrcodechong)
    LinearLayout qrcodechong;
    @BindView(R.id.qrcodeyu)
    LinearLayout qrcodeyu;
    @BindView(R.id.qrcodeji)
    LinearLayout qrcodeji;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        String str = "https://www.baidu.com";
        if (str.equals("FR11")||str.equals("FR12")||str.equals("FR13")||str.equals("FR15")) {
           // imgCode.setBackgroundResource(R.drawable.tuikuan);
        } else {
            final Bitmap qrCodeBitmap = createQRCodeBitmap(str);

            RidecodeActivity.this.runOnUiThread(new Runnable() {
                @SuppressLint("NewApi")
                @Override
                public void run() {

                    @SuppressWarnings("deprecation")
                    BitmapDrawable iconDrawable = new BitmapDrawable(qrCodeBitmap);
                    imgCode.setBackground(iconDrawable);
                }
            });
        }

    }
    @Override
    protected int provideContentViewId() {
        return R.layout.activity_orcode;
    }

    @Override
    protected boolean isHasHeader() {
        return false;
    }

    @Override
    protected void rightClient() {

    }

    @OnClick({R.id.iv,R.id.qrcodechong,R.id.qrcodeyu,R.id.qrcodeji})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv:
//                intent = new Intent(this, TransactionActivity.class);
//                startActivity(intent);
                break;
            case R.id.qrcodechong:
                intent = new Intent(this, ActivityCardCharge.class);
                startActivity(intent);
                break;
            case R.id.qrcodeyu:
                intent = new Intent(this, WalletActivity.class);
                startActivity(intent);
                break;
            case R.id.qrcodeji:
                intent = new Intent(this, TransactionActivity.class);
                startActivity(intent);
                break;
        }
    }


    private Bitmap createQRCodeBitmap(String url) {

        Hashtable<EncodeHintType, Object> qrParam = new Hashtable<EncodeHintType, Object>();

        qrParam.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        qrParam.put(EncodeHintType.CHARACTER_SET, "UTF-8");


        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 300, 300, qrParam);


            int w = bitMatrix.getWidth();
            int h = bitMatrix.getHeight();
            int[] data = new int[w * h];

            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (bitMatrix.get(x, y))
                        data[y * w + x] = 0xff000000;
                    else
                        data[y * w + x] = -1;
                }
            }


            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

            bitmap.setPixels(data, 0, w, 0, 0, w, h);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
