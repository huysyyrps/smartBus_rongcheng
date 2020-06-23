package main.other.presenter;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.other.bean.BianMin;
import main.other.module.BianMinContract;
import main.smart.rcgj.R;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.RetrofitUtil;


/**
 * @author: Allen.
 * @date: 2018/7/25
 * @description:
 */

public class BianMinPresenter implements BianMinContract.presenter {

    private Context context;
    private BianMinContract.View view;

    public BianMinPresenter(Context context, BianMinContract.View view) {
        this.context = context;
        this.view = view;
    }

    /**
     * 获取便民服务列表
     */

    @Override
    public void getBianMin(String page, String limit) {
        RetrofitUtil.getInstance().initRetrofitMain().getBianMin(page,limit).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<BianMin>(context, context.getResources().getString(R.string.handler_data)) {
                    @Override
                    protected void onSuccees(BianMin t) throws Exception {
                        view.setBianMin(t);
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.setBianMinMessage(""+ e.getMessage());
                    }
                });

    }
}
