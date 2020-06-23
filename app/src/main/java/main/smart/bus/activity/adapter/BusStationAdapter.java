package main.smart.bus.activity.adapter;

import java.util.List;

import main.smart.rcgj.R;
import main.smart.bus.bean.StationBean;
import main.smart.common.util.CityManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BusStationAdapter extends BaseAdapter {
    private List<StationBean> busStations;
    private Context context;
    private OnArrowClickListener onArrowClickListener;

    public BusStationAdapter(Context paramContext, List<StationBean> paramList) {
        this.context = paramContext;
        this.busStations = paramList;
    }

    public int getCount() {
        return this.busStations.size();
    }

    public Object getItem(int paramInt) {
        return this.busStations.get(paramInt);
    }

    public long getItemId(int paramInt) {
        return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        HandleView localHandleView;
        StationBean localBusStation;
        localBusStation = (StationBean) busStations.get(paramInt);

        if (paramView != null) {
            localHandleView = (HandleView) paramView.getTag();
        } else {
            paramView = LayoutInflater.from(this.context).inflate(R.layout.bus_station_list_item, null);
            localHandleView = new HandleView();
            localHandleView.tvName = ((TextView) paramView.findViewById(R.id.bus_station_name));
            paramView.setTag(localHandleView);
        }

        localHandleView.tvName = ((TextView) paramView.findViewById(R.id.bus_station_name));
        if (busStations.get(paramInt).getArea() == CityManager.getInstance().getSelectCityCode())
            localHandleView.tvName.setText(busStations.get(paramInt).getStationName());
        return paramView;
    }
    public void setOnArrowClickListener(OnArrowClickListener paramOnArrowClickListener) {
        this.onArrowClickListener = paramOnArrowClickListener;
    }

    class HandleView {
        TextView tvName;
    }

    public static abstract interface OnArrowClickListener {
        public abstract void onOnArrowClick(int paramInt);
    }
}
