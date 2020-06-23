package main.smart.bus.activity.adapter;

import java.util.ArrayList;
import java.util.List;

import main.smart.rcgj.R;
import main.smart.bus.bean.FavorLineBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FavorBusLineAdapter extends BaseAdapter {
    private List<FavorLineBean> busLines;

    public List<FavorLineBean> getBusLines() {
        return busLines;
    }

    public void setBusLines(List<FavorLineBean> busLines) {
        this.busLines = busLines;
    }

    private List<Boolean> checkList;

    public List<Boolean> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<Boolean> mChecked) {
        this.checkList = mChecked;
    }

    private Context context;
    private boolean isEditCheck = false;

    ///private HandleView view;
    public boolean isEditCheck() {
        return isEditCheck;
    }

    public void setEditCheck(boolean isCheck) {
        this.isEditCheck = isCheck;
    }

    public void allYesCheckList() {
        checkList.clear();
        for (int i = 0; i < busLines.size(); i++) {
            checkList.add(true);
        }

    }

    public void resetCheckList() {
        checkList.clear();
        for (int i = 0; i < busLines.size(); i++) {
            checkList.add(false);
        }

    }

    public FavorBusLineAdapter(Context paramContext, List<FavorLineBean> paramList) {
        this.context = paramContext;
        this.busLines = paramList;
        checkList = new ArrayList<Boolean>();
        for (int i = 0; i < busLines.size(); i++) {
            checkList.add(false);
        }
        isEditCheck = false;
    }

    public int getCount() {
        return this.busLines.size();
    }

    public Object getItem(int paramInt) {
        return this.busLines.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        HandleView handleview;
        FavorLineBean busline;
        if (paramView != null) {
            handleview = (HandleView) paramView.getTag();
        } else {
            paramView = LayoutInflater.from(context).inflate(R.layout.bus_favor_list_item, null);
            handleview = new HandleView();
            handleview.tvName = (TextView) paramView.findViewById(R.id.bus_line_name);
            handleview.tvDetail = (TextView) paramView.findViewById(R.id.bus_line_detail);
            paramView.setTag(handleview);
        }

        busline = (FavorLineBean) busLines.get(paramInt);
        int sxx = busline.getLineSxx();
        if (sxx == 0){
            handleview.tvName.setText(busline.getFavorName()+"---"+context.getResources().getString(R.string.up_go));
        }else if (sxx == 1){
            handleview.tvName.setText(busline.getFavorName()+"---"+context.getResources().getString(R.string.down_go));
        }
        final int p = paramInt;
        handleview.tvDetail.setText(busline.getLineName() + " " + busline.getBeginStation() + "-- " + busline.getEndStation());
        return paramView;
    }

    class HandleView {
        TextView tvDetail;
        TextView tvName;
    }

}
