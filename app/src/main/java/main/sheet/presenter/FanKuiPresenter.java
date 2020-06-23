package main.sheet.presenter;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.sheet.bean.FanKui;
import main.sheet.module.FanKuiContract;
import main.smart.rcgj.R;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.RetrofitUtil;


/**
 * @author: Allen.
 * @date: 2018/7/25
 * @description:
 */

public class FanKuiPresenter implements FanKuiContract.presenter {

    private Context context;
    private FanKuiContract.View view;

    public FanKuiPresenter(Context context, FanKuiContract.View view) {
        this.context = context;
        this.view = view;
    }

    /**
     * 反馈
     */

    @Override
    public void getFanKui(String id, String content) {
        RetrofitUtil.getInstance().initRetrofitMain().getComplaintsFK(id,content).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<FanKui>(context, context.getResources().getString(R.string.handler_data)) {
                    @Override
                    protected void onSuccees(FanKui t) throws Exception {
                        view.setFanKui(t);
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.setFanKuiMessage(""+ e.getMessage());
                    }
                });
    }
}
