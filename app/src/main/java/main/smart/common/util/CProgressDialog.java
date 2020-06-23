package main.smart.common.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import main.smart.rcgj.R;


public class CProgressDialog {
    private Context mContext;
    private Dialog mDialog;
    private TextView text;

    public CProgressDialog(Context context) {
        mContext = context;
    }

    public Dialog loadDialog() {
        mDialog = new Dialog(mContext, R.style.dialog);
        LayoutInflater in = LayoutInflater.from(mContext);
        View viewDialog = in.inflate(R.layout.progress_dialog, null);
        viewDialog.setBackgroundColor(0x7f000000);
        text = (TextView) viewDialog.findViewById(R.id.prodialog_text);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(viewDialog);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        return mDialog;
    }

    public void removeDialog() {
        mDialog.dismiss();
    }

    public void setText(String str) {
        text.setVisibility(View.VISIBLE);
        text.setText(str);
    }
}
