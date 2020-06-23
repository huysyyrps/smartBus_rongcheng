package main.other;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.other.bean.BianMin;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.views.Header;

public class ShengHuoDetailActivity extends BaseActivity {

    @BindView(R.id.header)
    Header header;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvContent)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        header.setTvTitle(getResources().getString(R.string.shenghuo));
        Intent intent = getIntent();
        BianMin.DataBean bean = (BianMin.DataBean) intent.getSerializableExtra("bean");
//        String title = bean.getTitle();
//        String content = bean.getDetails();
//        long time = bean.getGmtCreate();
//        tvTitle.setText(title);
//        tvContent.setText(content);
//        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date(time);
//        String str = sdf.format(date);
//        tvTime.setText(str);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_advert_detail;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }
}
