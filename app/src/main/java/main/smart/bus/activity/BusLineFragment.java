package main.smart.bus.activity;


import java.util.ArrayList;
import java.util.List;

import main.smart.rcgj.R;
import main.smart.bus.activity.adapter.BusLineAdapter;
import main.smart.bus.bean.LineBean;
import main.smart.bus.util.BusManager;
import main.smart.common.util.CityManager;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 选择功能菜单跳转到线路选择页面
         * 同时管理历史搜索记录
         */
public class BusLineFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private BusLineAdapter mAdapter;
    private ListView mBusLineHistoryView;
    private List<LineBean> mBusLineRecords = new ArrayList();
    private BusManager mBusMan;
    private CityManager mCityMan;
    private View mHistoryClearView;
    private EditText mSearchEdit;
    private TextView zuijin;

    /**
     * 初始化渲染页面
     **/
    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View localView = paramLayoutInflater.inflate(R.layout.bus_line_fragment, paramViewGroup, false);
        this.mBusMan = BusManager.getInstance();
        this.mCityMan = CityManager.getInstance();
        try {
            List<LineBean> list = this.mBusMan.getBusLineHistory();
            List<LineBean> lineList = new ArrayList<LineBean>();
            while (list.size() > 0) {
                LineBean be = list.remove(list.size() - 1);
                lineList.add(be);
            }
            this.mBusLineRecords.addAll(lineList);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        localView.findViewById(R.id.search_map_btn).setVisibility(View.GONE);
        this.mSearchEdit = ((EditText) localView.findViewById(R.id.search_edit));
        this.mSearchEdit.setHint(R.string.prompt_bus_line);
        this.mSearchEdit.setFocusable(false);
        this.mSearchEdit.setOnClickListener(this);
        this.mHistoryClearView = paramLayoutInflater.inflate(R.layout.search_record_clear, null);
        this.mBusLineHistoryView = ((ListView) localView.findViewById(R.id.bus_line_history_list));
        this.mBusLineHistoryView.addFooterView(this.mHistoryClearView);
        this.mAdapter = new BusLineAdapter(getActivity(), this.mBusLineRecords);

        this.mBusLineHistoryView.setAdapter(this.mAdapter);
        this.mBusLineHistoryView.setOnItemClickListener(this);
        if (this.mBusLineRecords.size() <= 0)
            hideHistoryClearView();
        return localView;
    }

    /**
     * 清空线路查询历史
     */
    private void clearBusLineHistory() {
        this.mBusMan.deletelinedata();
        this.mBusMan.clearBusLineHistory();
        this.mBusLineRecords.clear();
        this.mAdapter.notifyDataSetChanged();
        hideHistoryClearView();
    }

    /**
     * 隐藏线路查询历史下拉列表面板
     */
    private void hideHistoryClearView() {
        if (this.mBusLineHistoryView.getFooterViewsCount() <= 0)
            return;
        this.mBusLineHistoryView.removeFooterView(this.mHistoryClearView);
    }

    /**
     * 显示 线路查询历史下拉列表面板
     */
    private void showHistoryClearView() {
        if (this.mBusLineHistoryView.getFooterViewsCount() > 0)
            return;
        this.mBusLineHistoryView.addFooterView(this.mHistoryClearView);
    }

    /**
     * 更新 线路查询历史下拉列表
     */
    private void updateBusLineHistroy() {
        this.mBusLineRecords.clear();
        List<LineBean> list = this.mBusMan.getBusLineHistory();
        List<LineBean> lineList = new ArrayList<LineBean>();
        while (list.size() > 0) {
            LineBean be = list.remove(list.size() - 1);
            lineList.add(be);
        }
        this.mBusLineRecords.addAll(lineList);
        this.mAdapter.notifyDataSetChanged();
        showHistoryClearView();
    }

    // 监听 线路输入框点击事件
    public void onClick(View paramView) {
        startActivityForResult(new Intent(getActivity(), BusLineSearchActivity.class), 0);
    }

    /**
     * 选中查询线路，跳转到线路图
     */

    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
        if (paramInt >= this.mBusLineRecords.size()) {
            clearBusLineHistory();
            return;
        }
        LineBean localBusLine = (LineBean) this.mBusLineRecords.get(paramInt);
        this.mBusMan.saveBusLineToHistory(localBusLine);
        this.mBusMan.setSelectedLine(localBusLine);
        startActivityForResult(new Intent(getActivity(), BusLineDetailActivity.class), 1);
    }

    /**
     *
     * */
    public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        if ((paramInt2 != -1) && (paramInt1 <= 0))
            return;
        updateBusLineHistroy();
    }
}
