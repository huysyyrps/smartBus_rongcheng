package main.sheet.presenter;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.sheet.bean.Nxwd;
import main.sheet.module.SmkContract;
import main.smart.rcgj.R;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.RetrofitUtil;


/**
 * @author: Allen.
 * @date: 2018/7/25
 * @description:
 */

public class SmkPresenter implements SmkContract.presenter {

    private Context context;
    private SmkContract.View view;

    public SmkPresenter(Context context, SmkContract.View view) {
        this.context = context;
        this.view = view;
    }

    /**
     * 农信网点
     */
    @Override
    public void getSmk(String page, String limit) {
        RetrofitUtil.getInstance().initRetrofitMain().getSmk(page,limit).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<Nxwd>(context, context.getResources().getString(R.string.handler_data)) {
                    @Override
                    protected void onSuccees(Nxwd t) throws Exception {
                        view.setNxwd(t);
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.setNxwdMessage(""+ e.getMessage());
                    }
                });
    }
}
