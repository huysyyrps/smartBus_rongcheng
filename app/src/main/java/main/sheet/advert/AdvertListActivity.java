package main.sheet.advert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.refreshview.CustomRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.sheet.bean.AdvertDown;
import main.sheet.bean.AdvertTop;
import main.sheet.bean.Notice;
import main.sheet.module.AdvertContract;
import main.sheet.presenter.AdvertPresenter;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.utils.BaseRecyclerAdapter;
import main.utils.utils.BaseViewHolder;
import main.utils.views.Header;

public class AdvertListActivity extends BaseActivity implements AdvertContract.View {

    @BindView(R.id.header)
    Header header;
    @BindView(R.id.recyclerView)
    CustomRefreshView recyclerView;
    @BindView(R.id.liContent1)
    LinearLayout llNoContent;
    int page = 1;
    String tag = "";
    Intent intent;
    AdvertPresenter advertPresenter;
    BaseRecyclerAdapter mAdapter;
    List<AdvertDown.DataBean> advertDownList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.getRecyclerView().setLayoutManager(manager);

        mAdapter = new BaseRecyclerAdapter<AdvertDown.DataBean>(this, R.layout.adver_item_layout, advertDownList) {
            @Override
            public void convert(BaseViewHolder holder, final AdvertDown.DataBean noticeBean) {
                holder.setText(R.id.tv_title, noticeBean.getTitle());
                holder.setText(R.id.tv_content, noticeBean.getPicturesTexts());
                holder.setOnClickListener(R.id.noticeItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(AdvertListActivity.this, AdvertDetailActivity.class);
                        intent.putExtra("bean", noticeBean);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(mAdapter);

        advertPresenter = new AdvertPresenter(this, this);
        advertPresenter.getAdvertDown("1", "10", "1");

        setClient();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_advert_list;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }

    /**
     * 滑动监听
     */
    private void setClient() {
        recyclerView.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                advertDownList.clear();
                advertPresenter.getAdvertDown("1", "10", "1");
            }

            @Override
            public void onLoadMore() {
                page++;
                advertPresenter.getAdvertDown(String.valueOf(page), "10", "1");
            }
        });
    }

    @Override
    public void setAdvertTop(AdvertTop advertTop) {

    }

    @Override
    public void setAdvertDown(AdvertDown advertDown) {
        if (advertDown.getCode() == 0) {
            for (int i = 0; i < advertDown.getData().size(); i++) {
                advertDownList.add(advertDown.getData().get(i));
            }
        } else if (advertDown.getCode() == -1) {
            Toast.makeText(this, advertDown.getMsg(), Toast.LENGTH_SHORT).show();
        }
        if (advertDown.getData().size() == 0 && advertDownList.size() == 0) {
            if (recyclerView != null) {
                recyclerView.setVisibility(View.GONE);
                llNoContent.setVisibility(View.VISIBLE);
            }
        } else if (advertDown.getData().size() == 0 && advertDownList.size() != 0) {
            if (recyclerView != null) {
                recyclerView.complete();
                recyclerView.onNoMore();
            }
        }
        if (advertDown.getData().size() < 20) {
            if (recyclerView != null) {
                mAdapter.notifyDataSetChanged();
                recyclerView.complete();
                recyclerView.onNoMore();
            }
        } else {
            if (recyclerView != null) {
                mAdapter.notifyDataSetChanged();
                recyclerView.complete();
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setNotice(Notice notice) {

    }

    @Override
    public void setAdvertMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
