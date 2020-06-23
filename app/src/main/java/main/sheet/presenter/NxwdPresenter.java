package main.sheet.presenter;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.sheet.bean.Nxwd;
import main.sheet.module.NxwdContract;
import main.smart.rcgj.R;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.RetrofitUtil;


/**
 * @author: Allen.
 * @date: 2018/7/25
 * @description:
 */

public class NxwdPresenter implements NxwdContract.presenter {

    private Context context;
    private NxwdContract.View view;

    public NxwdPresenter(Context context, NxwdContract.View view) {
        this.context = context;
        this.view = view;
    }

    /**
     * 农信网点
     */
    @Override
    public void getNxwd(String page, String limit) {
        RetrofitUtil.getInstance().initRetrofitMain().getNxwd(page,limit).subscribeOn(Schedulers.io())
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
