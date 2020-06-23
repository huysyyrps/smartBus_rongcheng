package main.sheet.complaints;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.sheet.bean.ComplaintsList;
import main.sheet.bean.FanKui;
import main.sheet.module.FanKuiContract;
import main.sheet.presenter.FanKuiPresenter;
import main.smart.rcgj.R;
import main.utils.base.AlertDialogUtil;
import main.utils.base.BaseActivity;
import main.utils.views.Header;

public class ComplaintsDetailActivity extends BaseActivity implements FanKuiContract.View {

    @BindView(R.id.header)
    Header header;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tvBack)
    TextView tvBack;

    String id = "";
    AlertDialogUtil alertDialogUtil;
    FanKuiPresenter fanKuiPresenter;
    @BindView(R.id.tvPJ)
    TextView tvPJ;
    @BindView(R.id.ll)
    LinearLayout ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        alertDialogUtil = new AlertDialogUtil(this);
        Intent intent = getIntent();
        ComplaintsList.DataBean bean = (ComplaintsList.DataBean) intent.getSerializableExtra("bean");
        String title = bean.getTelephone();
        String content = bean.getContent();
        String time = bean.getComplaintTime();
        String feedback = bean.getProcessingResults();
        String result = bean.getFeedback()      ;
        id = bean.getId();
        tvTitle.setText(title);
        tvContent.setText(content);
        tvBack.setText(feedback);
        tvPJ.setText(result);
        String processingStatus = bean.getProcessingStatus();
        if (processingStatus.equals("0") || processingStatus.equals("2")) {
            header.setRightIv(false);
        }
        tvTime.setText(time);
        fanKuiPresenter = new FanKuiPresenter(this, this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_complaints_detail;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(LayoutInflater.from(this).inflate(R.layout.dialog_with_edittext, null));
        dialog.show();
        dialog.getWindow().setContentView(R.layout.dialog_with_edittext);
        EditText tv_content = (EditText) dialog.findViewById(R.id.etContent);
        TextView tv_yes = (TextView) dialog.findViewById(R.id.yes);
        TextView tv_no = (TextView) dialog.findViewById(R.id.no);
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String str = tv_content.getText().toString();
                if (str.equals("")) {
                    Toast.makeText(ComplaintsDetailActivity.this, "ÇëÌîÐ´ÄÚÈÝ", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    fanKuiPresenter.getFanKui(id, str);
                }
            }
        });
        tv_no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void setFanKui(FanKui fanKui) {
        if (fanKui.getCode() == 1) {
            Toast.makeText(this, getResources().getString(R.string.successs_up), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, fanKui.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setFanKuiMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
