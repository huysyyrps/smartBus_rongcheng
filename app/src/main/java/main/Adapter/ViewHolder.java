package main.Adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;

    public ViewHolder(Context context, View itemview, ViewGroup parent) {
        super(itemview);
        mConvertView = itemview;
        mViews = new SparseArray<View>();
    }

    public static ViewHolder get(Context context, ViewGroup parent, int layoutid) {
        View itemview = LayoutInflater.from(context).inflate(layoutid, parent, false);
        ViewHolder holder = new ViewHolder(context, itemview, parent);
        return holder;
    }

    public <T extends View> T getView(int viewid) {
        View view = mViews.get(viewid);
        if (view == null) {
            view = mConvertView.findViewById(viewid);
            mViews.put(viewid, view);
        }
        return (T) view;
    }
}

