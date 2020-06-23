package main.other;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.other.bean.BianMin;
import main.other.module.BianMinContract;
import main.other.presenter.BianMinPresenter;
import main.smart.bus.activity.HelpWebActivity;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.utils.BaseRecyclerAdapter;
import main.utils.utils.BaseViewHolder;
import main.utils.views.Header;

public class BianMinActivity extends BaseActivity implements BianMinContract.View {

    @BindView(R.id.header)
    Header header;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    BianMinPresenter bianMinPresenter;
    BaseRecyclerAdapter baseRecyclerAdapter;
    List<BianMin.DataBean> beanList = new ArrayList<>();
    @BindView(R.id.llNoContent)
    LinearLayout llNoContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(manager);
        baseRecyclerAdapter = new BaseRecyclerAdapter<BianMin.DataBean>(this, R.layout.adapter_itembean, beanList) {
            @Override
            public void convert(BaseViewHolder holder, final BianMin.DataBean noticeBean) {
                holder.setText(R.id.textView, noticeBean.getServiceTitle());
                holder.setImageView(R.id.imageView, noticeBean.getServiceIcon());
                int type = noticeBean.getType();
                String url = noticeBean.getLinkAddress();
                holder.setOnClickListener(R.id.linearLayout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(BianMinActivity.this, BianMinDetailActivity.class);
//                        intent.putExtra("bean", noticeBean);
//                        startActivity(intent);
                        if (type ==1){
                            Intent intent = new Intent(BianMinActivity.this, HelpWebActivity.class);
                            intent.putExtra("URL", url);
//                            Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                            it.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                            startActivity(intent);
                        }else if (type == 2){
                            try{
                                Intent intent = getPackageManager().getLaunchIntentForPackage(url);
                                startActivity(intent);
                            }catch(Exception e){
                                Toast.makeText(BianMinActivity.this, "没有安装", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(baseRecyclerAdapter);
        bianMinPresenter = new BianMinPresenter(this, this);
        bianMinPresenter.getBianMin("2000", "1");
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_bian_min;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }


    @Override
    public void setBianMin(BianMin swrllist) {
        if (swrllist.getCode() == 0) {
            for (int i = 0; i < swrllist.getData().size(); i++) {
                beanList.add(swrllist.getData().get(i));
            }
        } else {
            Toast.makeText(this, swrllist.getMsg(), Toast.LENGTH_SHORT).show();
        }
        baseRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setBianMinMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
