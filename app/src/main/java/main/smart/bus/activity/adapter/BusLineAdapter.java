package main.smart.bus.activity.adapter;


import java.util.List;

import main.smart.rcgj.R;
import main.smart.bus.bean.LineBean;
import main.smart.bus.bean.StationBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BusLineAdapter extends BaseAdapter {

    List<LineBean> busLines;
    private Context context;

    public BusLineAdapter(Context paramContext, List<LineBean> paramList) {
        this.context = paramContext;
        this.busLines = paramList;
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
        LineBean busline;
        if (paramView != null) {
            handleview = (HandleView) paramView.getTag();
        } else {
            paramView = LayoutInflater.from(context).inflate(R.layout.bus_line_list_item, null);
            handleview = new HandleView();
            handleview.tvName = (TextView) paramView.findViewById(R.id.bus_line_name);
            handleview.tvDetail = (TextView) paramView.findViewById(R.id.bus_line_detail);
            paramView.setTag(handleview);
        }
        busline = (LineBean) busLines.get(paramInt);
        String sxx = busline.getLineId() == 0 ? context.getResources().getString(R.string.up_go) : context.getResources().getString(R.string.down_go) ;
        handleview.tvName.setText(busline.getLineName());
        //德州的需求
        String station = "";
        List<StationBean> list = busline.getStations();
        for (int i = 0; i < list.size(); i++) {
            if ((sxx.equals("下行") && i == 1) || (sxx.equals("上行") && i == list.size() - 2)) {
                station = list.get(i).getStationName();
            }
        }
       // handleview.tvDetail.setText(sxx + " " + busline.getBeginStation() + " --" + station + "-- " + busline.getEndStation());
        handleview.tvDetail.setText( " " + busline.getBeginStation() + " --" + station + "-- " + busline.getEndStation());

        //handleview.tvDetail.setText( sxx+" "+busline.getBeginStation()+" --- "+busline.getEndStation());
        return paramView;
    }

    class HandleView {
        TextView tvDetail;
        TextView tvName;
    }
}