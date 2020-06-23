package main.other;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.other.adapter.MySpinnerAdapter;
import main.other.module.SwrlListContract;
import main.other.presenter.SwrlListPresenter;
import main.sheet.bean.Swrllist;
import main.smart.rcgj.R;
import main.utils.utils.BaseRecyclerAdapter;
import main.utils.utils.BaseViewHolder;

public class SwrlListActivity extends AppCompatActivity implements SwrlListContract.View {

    @BindView(R.id.tv_tittle)
    TextView tvTittle;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.llNoContent)
    LinearLayout llNoContent;
    @BindView(R.id.ll_nocontent)
    LinearLayout llNocontent;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    String state = "";
    SwrlListPresenter swrlListPresenter;
    BaseRecyclerAdapter baseRecyclerAdapter;
    List<String> typeList = new ArrayList<>();
    List<Swrllist.DataBean> beanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swrl_list);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        tvTittle.setText(getResources().getString(R.string.swrl));
        typeList.add("已发布");
        typeList.add("已认领");
        MySpinnerAdapter adapter = new MySpinnerAdapter(this,typeList);
        spinner.setAdapter(adapter);
        swrlListPresenter = new SwrlListPresenter(this, this);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 0) {
//                    state = "2";
//                } else if (position == 1) {
//                    state = "3";
//                }
//                swrlListPresenter.getSwrlList(state);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        swrlListPresenter.getSwrlList("2");
    }

    @OnClick(R.id.iv_left)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void setSwrlList(Swrllist swrllist) {
        if (swrllist.getCode() == 0) {
            beanList.clear();
            for (int i = 0; i < swrllist.getData().size(); i++) {
                beanList.add(swrllist.getData().get(i));
            }
            if (beanList.size() == 0) {
                recyclerView.setVisibility(View.GONE);
                llNoContent.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                llNoContent.setVisibility(View.GONE);
            }
            baseRecyclerAdapter = new BaseRecyclerAdapter<Swrllist.DataBean>(this, R.layout.swrllist_item_layout, beanList) {
                @Override
                public void convert(BaseViewHolder holder, final Swrllist.DataBean noticeBean) {
                    holder.setText(R.id.tvLine, getResources().getString(R.string.line)+":"+noticeBean.getBusRoutes());
                    holder.setText(R.id.tvContent, getResources().getString(R.string.title1)+":"+noticeBean.getTitle());
                    holder.setText(R.id.tvTime, getResources().getString(R.string.time1)+":"+noticeBean.getOccurrenceTime());
                    holder.setOnClickListener(R.id.noticeItem, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        Intent intent = new Intent(SwrlListActivity.this, SwrlDetailActivity.class);
                        intent.putExtra("bean", noticeBean);
                        startActivity(intent);
                        }
                    });
                }
            };
            recyclerView.setAdapter(baseRecyclerAdapter);
            baseRecyclerAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, swrllist.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setSwrlListMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
