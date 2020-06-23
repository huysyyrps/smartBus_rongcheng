package main.sheet.complaints;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import main.sheet.bean.ComplaintsList;
import main.sheet.module.ComplaintsListContract;
import main.sheet.presenter.ComplaintsListPresenter;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.utils.BaseRecyclerAdapter;
import main.utils.utils.BaseViewHolder;
import main.utils.utils.SharePreferencesUtils;
import main.utils.utils.time_select.CustomDatePickerDay;
import main.utils.views.Header;

public class ComplaintsListActivity extends BaseActivity implements ComplaintsListContract.View {

    @BindView(R.id.header)
    Header header;
    @BindView(R.id.tvStartTime)
    TextView tvStartTime;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.tvEndTime)
    TextView tvEndTime;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.spinner)
    Spinner spinner;
    BaseRecyclerAdapter baseRecyclerAdapter;
    List<String> typeList = new ArrayList<>();
    List<ComplaintsList.DataBean> beanList = new ArrayList<>();
    ComplaintsListPresenter complaintsListPresenter;
    @BindView(R.id.llNoContent)
    LinearLayout llNoContent;
    @BindView(R.id.ll_nocontent)
    LinearLayout llNocontent;

    int page = 1;
    String type = "";
    String account = "";
    private CustomDatePickerDay customDatePicker1, customDatePicker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        initDatePicker();
        typeList.add("未处理");
        typeList.add("处理未反馈");
        typeList.add("已反馈");
        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        String s = spinner.getSelectedItem().toString();
        String startTime = tvStartTime.getText().toString();
        String endTime = tvEndTime.getText().toString();
        if (s.equals("未处理")) {
            type = "0";
        } else if (s.equals("处理未反馈")){
            type = "1";
        }else if (s.equals("已反馈")){
            type = "2";
        }
        account = new SharePreferencesUtils().getString(this,"userName","");
        complaintsListPresenter = new ComplaintsListPresenter(this, this);
        complaintsListPresenter.getComplaintsList("",account, "", "", "1", "1000");
    }

    /**
     * 选择时间
     */
    private void initDatePicker() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        //过去七天
        c.setTime(new Date());
        c.add(Calendar.DATE, -7);
        Date d = c.getTime();
        String day = format.format(d);
        tvStartTime.setText(day);
        System.out.println("过去七天：" + day);

        Calendar c1 = Calendar.getInstance();
        //过去七天
        c1.setTime(new Date());
        c1.add(Calendar.DATE, +1);
        Date d1 = c1.getTime();
        String day1 = format.format(d1);
        tvEndTime.setText(day1);
        sendHttpContent();

        customDatePicker1 = new CustomDatePickerDay(this, new CustomDatePickerDay.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvStartTime.setText(time.split(" ")[0]);
            }
        }, "2000-01-01 00:00", "2030-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

        customDatePicker2 = new CustomDatePickerDay(this, new CustomDatePickerDay.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tvEndTime.setText(time.split(" ")[0]);
            }
        }, "2000-01-01 00:00", "2030-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(false); // 显示时和分
        customDatePicker2.setIsLoop(false); // 允许循环滚动
    }

    private void sendHttpContent() {
        Date startTime = null, endTime = null;
        if (tvStartTime.getText().toString().isEmpty() || tvStartTime.getText().toString().isEmpty()) {
            Toast.makeText(this, "起止时间不能为空", Toast.LENGTH_SHORT).show();
        } else {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                startTime = sdf1.parse(tvStartTime.getText().toString());
                endTime = sdf1.parse(tvEndTime.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (startTime.getTime() > endTime.getTime()) {
            Toast.makeText(this, "请选择正确时间", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_complaints_list;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {
        beanList.clear();
        String s = spinner.getSelectedItem().toString();
        if (s.equals("未处理")) {
            type = "0";
        } else if (s.equals("处理未反馈")){
            type = "1";
        }else if (s.equals("已反馈")){
            type = "2";
        }
        String startTime = tvStartTime.getText().toString();
        String endTime = tvEndTime.getText().toString();
        complaintsListPresenter.getComplaintsList(type,account, startTime, endTime, "1", "15");
    }

    @OnClick({R.id.tvStartTime, R.id.tvEndTime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvStartTime:
                customDatePicker1.show(tvStartTime.getText().toString());
                break;
            case R.id.tvEndTime:
                customDatePicker2.show(tvEndTime.getText().toString());
                break;
        }
    }

    @Override
    public void setComplaintsList(ComplaintsList complaintsList) {
        beanList.clear();
        if (complaintsList.getCode() == 0) {
            for (int i = 0; i < complaintsList.getData().size(); i++) {
                beanList.add(complaintsList.getData().get(i));
            }
            if (beanList.size() == 0) {
                recyclerView.setVisibility(View.GONE);
                llNoContent.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                llNoContent.setVisibility(View.GONE);
                baseRecyclerAdapter = new BaseRecyclerAdapter<ComplaintsList.DataBean>(this, R.layout.tsjy_item_layout, beanList) {
                    @Override
                    public void convert(BaseViewHolder holder, final ComplaintsList.DataBean noticeBean) {
                        holder.setText(R.id.tvLine, getResources().getString(R.string.phone)+":"+noticeBean.getTelephone());
                        holder.setText(R.id.tvTime, getResources().getString(R.string.time)+":"+noticeBean.getComplaintTime());
                        holder.setText(R.id.tvContent, getResources().getString(R.string.content)+":"+noticeBean.getContent());
                        String type = noticeBean.getProcessingStatus();
                        if (type.equals("0")){
                            holder.setText2(R.id.tvType,R.id.tvType1,R.id.tvType2, "未处理","0");
                        }else if (type.equals("1")){
                            holder.setText2(R.id.tvType,R.id.tvType1,R.id.tvType2, "已处理","1");
                        }else {
                            holder.setText2(R.id.tvType,R.id.tvType1,R.id.tvType2, "已评价","2");
                        }
                        holder.setOnClickListener(R.id.noticeItem, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ComplaintsListActivity.this, ComplaintsDetailActivity.class);
                                intent.putExtra("bean", noticeBean);
                                startActivity(intent);
                            }
                        });
                    }
                };
                recyclerView.setAdapter(baseRecyclerAdapter);
                baseRecyclerAdapter.notifyDataSetChanged();
            }

        } else {
            Toast.makeText(this, complaintsList.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setComplaintsListMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String startTime = tvStartTime.getText().toString();
        String endTime = tvEndTime.getText().toString();
        complaintsListPresenter.getComplaintsList("",account, "", "", "1", "1000");
    }
}
