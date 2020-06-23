package main.other.presenter;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.other.bean.ShengHuo;
import main.other.module.ShengHuoContract;
import main.smart.rcgj.R;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.RetrofitUtil;


/**
 * @author: Allen.
 * @date: 2018/7/25
 * @description:
 */

public class ShengHuoPresenter implements ShengHuoContract.presenter {

    private Context context;
    private ShengHuoContract.View view;

    public ShengHuoPresenter(Context context, ShengHuoContract.View view) {
        this.context = context;
        this.view = view;
    }

    /**
     * 获取生活服务列表
     */

    @Override
    public void getShengHuo(String page, String limit) {
        RetrofitUtil.getInstance().initRetrofitMain().getShengHuo(page,limit).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<ShengHuo>(context, context.getResources().getString(R.string.handler_data)) {
                    @Override
                    protected void onSuccees(ShengHuo t) throws Exception {
                        view.setShengHuo(t);
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.setShengHuoMessage(""+ e.getMessage());
                    }
                });
    }
}
