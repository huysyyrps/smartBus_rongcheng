package main.other.presenter;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.other.module.SwrlListContract;
import main.sheet.bean.Swrllist;
import main.smart.rcgj.R;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.RetrofitUtil;


/**
 * @author: Allen.
 * @date: 2018/7/25
 * @description:
 */

public class SwrlListPresenter implements SwrlListContract.presenter {

    private Context context;
    private SwrlListContract.View view;

    public SwrlListPresenter(Context context, SwrlListContract.View view) {
        this.context = context;
        this.view = view;
    }

    /**
     * 获取失物认领列表
     */

    @Override
    public void getSwrlList(String state) {
        RetrofitUtil.getInstance().initRetrofitMain().getSwrlList(state).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<Swrllist>(context, context.getResources().getString(R.string.handler_data)) {
                    @Override
                    protected void onSuccees(Swrllist t) throws Exception {
                        view.setSwrlList(t);
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.setSwrlListMessage(""+ e.getMessage());
                    }
                });
    }
}
