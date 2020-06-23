package main.Adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import main.utils.utils.BaseViewHolder;

public abstract class BaseRecyclerAdapternew<T> extends RecyclerView.Adapter<BaseViewHolder>{
        private int mLayoutId;
        private List<T> mData;
        private Context mContext;

        public BaseRecyclerAdapternew(Context mContext, int mLayoutId, List<T> mData) {
            this.mContext = mContext;
            this.mLayoutId = mLayoutId;
            this.mData = mData;
        }

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = BaseViewHolder.getRecyclerHolder(mContext, parent, mLayoutId);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
            convert(holder, mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        /**
         * 对外提供的方法
         */
        public abstract void convert(BaseViewHolder holder, T t);

}
