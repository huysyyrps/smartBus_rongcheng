package main.utils.utils;

import android.content.Context;
import android.text.Html;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import main.smart.common.SmartBusApp;
import main.utils.views.RichTextView;


/**
 * Created by Administrator on 2019/4/12.
 * 通用adapter的viewholder
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews; //用来存储控件
    private View mConvertView;
    private Context mContext;

    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }

    /**
     * 提供一个获取ViewHolder的方法
     */
    public static BaseViewHolder getRecyclerHolder(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        BaseViewHolder viewHolder = new BaseViewHolder(context, itemView);
        return viewHolder;
    }

    /**
     * 获取控件
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 给TextView设置setText方法
     */
    public BaseViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public BaseViewHolder setText1(int viewId, String text) {
        RichTextView tv = getView(viewId);
        tv.setText(Html.fromHtml(text));
        return this;
    }

    /**
     * 给TextView设置setText方法
     */
    public BaseViewHolder setText2(int viewId,int viewId1,int viewId2, String text,String type) {
        TextView tv1 = getView(viewId);
        TextView tv2 = getView(viewId1);
        TextView tv3 = getView(viewId2);
        if (type.equals("0")){
            tv1.setText(text);
            tv1.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
        }

        if (type.equals("1")){
            tv2.setText(text);
            tv2.setVisibility(View.VISIBLE);
            tv1.setVisibility(View.GONE);
            tv3.setVisibility(View.GONE);
        }

        if (type.equals("2")){
            tv3.setText(text);
            tv3.setVisibility(View.VISIBLE);
            tv2.setVisibility(View.GONE);
            tv1.setVisibility(View.GONE);
        }
        return this;
    }

    public BaseViewHolder setText3(int viewId, String text) {
        WebView tv = getView(viewId);
        tv.loadDataWithBaseURL(null,text,"text/html","UTF-8",null);
        return this;
    }

    /**
     * 给ImageView设置setImageResource方法
     */
    public BaseViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    /**
     * 给Radionbutton设置隐藏控件方法
     */
    public BaseViewHolder setGone(int viewId) {
        RadioButton rb = getView(viewId);
        rb.setVisibility(View.GONE);
        return this;
    }

    /**
     * 给Radionbutton设置隐藏控件方法
     */
    public BaseViewHolder setGoneText(int viewId) {
        TextView rb = getView(viewId);
        rb.setVisibility(View.GONE);
        return this;
    }

    public BaseViewHolder setVisitionText(int viewId) {
        TextView rb = getView(viewId);
        rb.setVisibility(View.VISIBLE);
        return this;
    }
    /**
     * 设置显示控件方法
     */
    public BaseViewHolder setVisition(int viewId) {
        ImageView iv = getView(viewId);
        iv.setVisibility(View.VISIBLE);
        return this;
    }

    public BaseViewHolder setGoneimg(int viewId) {
        ImageView iv = getView(viewId);
        iv.setVisibility(View.GONE);
        return this;
    }
    /**
     * 给Radionbutton设置cgeck方法
     */
    public BaseViewHolder setCheck(int viewId) {
        RadioButton rb = getView(viewId);
        rb.setChecked(true);
        return this;
    }

    /**
     * 添加点击事件
     */
    public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public BaseViewHolder setImageView(int viewId, String url) {
        ImageView imageView = getView(viewId);
        Glide.with(SmartBusApp.getInstance()).load(url).into(imageView);
        return this;
    }
}
