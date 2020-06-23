package main.sheet.notice;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.refreshview.CustomRefreshView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.sheet.bean.Notice;
import main.sheet.module.NoticeContract;
import main.sheet.presenter.NoticePresenter;
import main.smart.common.util.ConstData;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.utils.BaseRecyclerAdapter;
import main.utils.utils.BaseViewHolder;
import main.utils.utils.SharePreferencesUtils;
import main.utils.views.Header;

public class NoticeListActivity1 extends BaseActivity implements NoticeContract.View {

    @BindView(R.id.header)
    Header header;
    @BindView(R.id.llNoContent)
    LinearLayout llNoContent;
    @BindView(R.id.liContent1)
    LinearLayout llNocontent;
    @BindView(R.id.recyclerView)
    CustomRefreshView recyclerView;

    Intent intent;
    int page = 0;
    BaseRecyclerAdapter mAdapter;
    NoticePresenter noticePresenter;
    List<Notice.DataBean.NoticeBean> beanList = new ArrayList<>();
    SharePreferencesUtils sharePreferencesUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.getRecyclerView().setLayoutManager(manager);
        sharePreferencesUtils = new SharePreferencesUtils();
        mAdapter = new BaseRecyclerAdapter<Notice.DataBean.NoticeBean>(this, R.layout.notice_item_layout, beanList) {
            @Override
            public void convert(BaseViewHolder holder, final Notice.DataBean.NoticeBean noticeBean) {
                holder.setText(R.id.tv_title, noticeBean.getTitle());
                holder.setText(R.id.tv_content, noticeBean.getReleaseTime());
                String imgSrcn = "";
                imgSrcn = GetHtmlImageSrcList(noticeBean.getDetails());
                String text = GetHtmlText(noticeBean.getDetails());

                Log.e("lolo",noticeBean.getTitle()+"$$$$$$$$$$$$$$$$$"+noticeBean.getDetails());

//                String s = noticeBean.getDetails().replaceAll("<p>", "");
//                s = s.replaceAll("</p>", "");
                if (text.equals("")){
                    holder.setGoneText(R.id.tvDetail);
                }else {
                    holder.setVisitionText(R.id.tvDetail);
                    holder.setText(R.id.tvDetail,text);
                }
                if (imgSrcn.equals("")){

                    holder.setGoneimg(R.id.imageView);
                }else{
                    holder.setVisition(R.id.imageView);
                    holder.setImageView(R.id.imageView,imgSrcn);
                }
                holder.setOnClickListener(R.id.noticeItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.e("lolo",noticeBean.getDetails());
                        intent = new Intent(NoticeListActivity1.this, NoticeDetailActivity.class);
                        intent.putExtra("bean", noticeBean);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(mAdapter);
        noticePresenter = new NoticePresenter(this, this);
        noticePresenter.getNotice("1", "1000", ConstData.appLatestTimeStampStr);
        setClient();
    }

    /**
     * 获取HTML文件里面的IMG标签的SRC地址
     *
     * @param htmlText 带html格式的文本
     */
    public static String GetHtmlImageSrcList(String htmlText) {
        String imgSrc = "";
        Matcher m = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(htmlText);
        while (m.find()) {
            imgSrc=m.group(1);
        }
        return imgSrc;
    }

    /**
     * 去掉所有的HTML,获取其中的文本信息
     *
     * @param htmlText
     * @return
     */
    public static String GetHtmlText(String htmlText) {
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlText);
        htmlText = m_html.replaceAll(""); // 过滤HTML标签
        return htmlText;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_notice_list1;
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
                beanList.clear();
                noticePresenter.getNotice("1", "1000", ConstData.appLatestTimeStampStr);
            }

            @Override
            public void onLoadMore() {
                page++;
                noticePresenter.getNotice(String.valueOf(page), "1000", ConstData.appLatestTimeStampStr);
            }
        });
    }

    @Override
    public  void setNotice(Notice notice) {
        if (notice.getCode() == 1) {

            ConstData.ishavenew=notice.getData().getIsNew().toString();
            ConstData.appLatestTimeStampStr=notice.getData().getLatestTimeStamp().toString();

            sharePreferencesUtils = new SharePreferencesUtils();
            sharePreferencesUtils.setString(NoticeListActivity1.this, "ishavenew", notice.getData().getIsNew().toString());
            sharePreferencesUtils.setString(NoticeListActivity1.this, "appLatestTimeStampStr", notice.getData().getLatestTimeStamp().toString());
            for (int i = 0; i < notice.getData().getNotice().size(); i++) {
                beanList.add(notice.getData().getNotice().get(i));

                Log.e("lolo",notice.getData().getNotice().get(i).getDetails());
            }
        } else {
            Toast.makeText(this, notice.getMsg(), Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK ) {


                Intent intent = new Intent();
                intent.putExtra("respond", "Hello,Alice!I'm Bob.");
                // 设置返回码和返回携带的数据
                setResult(1, intent);
                // RESULT_OK就是一个默认值，=-1，它说OK就OK吧
                finish();

                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
