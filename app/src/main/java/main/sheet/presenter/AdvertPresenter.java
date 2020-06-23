package main.sheet.presenter;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.sheet.bean.AdvertDown;
import main.sheet.bean.AdvertTop;
import main.sheet.bean.Notice;
import main.sheet.module.AdvertContract;
import main.smart.rcgj.R;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.RetrofitUtil;


/**
 * @author: Allen.
 * @date: 2018/7/25
 * @description:
 */

public class AdvertPresenter implements AdvertContract.presenter {

    private Context context;
    private AdvertContract.View view;

    public AdvertPresenter(Context context, AdvertContract.View view) {
        this.context = context;
        this.view = view;
    }

    /**
     * 获取广告
     */
    @Override
    public void getAdvertTop(String page, String limit, String avdId) {
        RetrofitUtil.getInstance().initRetrofitNoSession().getAdvertTop(page,limit,avdId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<AdvertTop>(context, context.getResources().getString(R.string.handler_data)) {
                    @Override
                    protected void onSuccees(AdvertTop t) throws Exception {
                        view.setAdvertTop(t);
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.setAdvertMessage(""+ e.getMessage());
                    }
                });
    }

    @Override
    public void getAdvertDown(String page, String limit, String avdId) {
        RetrofitUtil.getInstance().initRetrofitMain().getAdvertDown(page,limit,avdId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<AdvertDown>(context, context.getResources().getString(R.string.handler_data)) {
                    @Override
                    protected void onSuccees(AdvertDown t) throws Exception {
                        view.setAdvertDown(t);
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.setAdvertMessage(""+ e.getMessage());
                    }
                });
    }

    @Override
    public void getNotice(String page, String limit, String avdId) {
        RetrofitUtil.getInstance().initRetrofitNoSession().getNotice(page,limit,avdId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<Notice>(context, context.getResources().getString(R.string.handler_data)) {
                    @Override
                    protected void onSuccees(Notice t) throws Exception {
                        view.setNotice(t);
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.setAdvertMessage(""+ e.getMessage());
                    }
                });
    }
}
