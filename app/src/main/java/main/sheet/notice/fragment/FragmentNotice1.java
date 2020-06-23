package main.sheet.notice.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.refreshview.CustomRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import main.sheet.bean.Notice;
import main.sheet.module.NoticeContract;
import main.sheet.notice.NoticeDetailActivity;
import main.sheet.presenter.NoticePresenter;
import main.smart.rcgj.R;
import main.utils.utils.BaseRecyclerAdapter;
import main.utils.utils.BaseViewHolder;

public class FragmentNotice1 extends Fragment implements NoticeContract.View {

    View view;
    Unbinder unbinder;

    @BindView(R.id.liContent1)
    LinearLayout llNoContent;
    @BindView(R.id.recyclerView)
    CustomRefreshView recyclerView;

    Intent intent;
    int page = 0;
    BaseRecyclerAdapter mAdapter;
    NoticePresenter noticePresenter;
    List<Notice.DataBean.NoticeBean> beanList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notice_list1, container, false);
        unbinder = ButterKnife.bind(this, view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.getRecyclerView().setLayoutManager(manager);

        mAdapter = new BaseRecyclerAdapter<Notice.DataBean.NoticeBean>(getActivity(), R.layout.notice_item_layout, beanList) {
            @Override
            public void convert(BaseViewHolder holder, final Notice.DataBean.NoticeBean noticeBean) {
                holder.setText(R.id.tv_title, "标题："+noticeBean.getTitle());
                holder.setText(R.id.tv_content, "发布时间："+noticeBean.getReleaseTime());
                holder.setOnClickListener(R.id.noticeItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(getActivity(), NoticeDetailActivity.class);
                        intent.putExtra("bean", noticeBean);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(mAdapter);

        noticePresenter = new NoticePresenter(getActivity(), this);
        noticePresenter.getNotice("1", "10", "");

        setClient();
        return view;
    }

    /**
     * 滑动监听
     */
    private void setClient() {
        recyclerView.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                beanList.clear();
                noticePresenter.getNotice("1", "10", "");
            }

            @Override
            public void onLoadMore() {
                page++;
                noticePresenter.getNotice(String.valueOf(page), "10", "");
            }
        });
    }

    @Override
    public void setNotice(Notice notice) {
        if (notice.getCode() == 1) {
            for (int i = 0; i < notice.getData().getNotice().size(); i++) {
                beanList.add(notice.getData().getNotice().get(i));
            }
        } else if (notice.getCode() == 3) {
            Toast.makeText(getActivity(), notice.getMsg(), Toast.LENGTH_SHORT).show();
        }
        if (notice.getData().getNotice().size() == 0 && beanList.size() == 0) {
            if (recyclerView != null) {
                recyclerView.setVisibility(View.GONE);
                llNoContent.setVisibility(View.VISIBLE);
            }
        } else if (notice.getData().getNotice().size() == 0 && beanList.size() != 0) {
            if (recyclerView != null) {
                recyclerView.complete();
                recyclerView.onNoMore();
            }
        }
        if (notice.getData().getNotice().size() < 20) {
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
    public void setNoticeMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
