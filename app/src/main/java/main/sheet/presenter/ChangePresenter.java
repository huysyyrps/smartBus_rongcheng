package main.sheet.presenter;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.sheet.bean.Change;
import main.sheet.module.ChangeContract;
import main.smart.rcgj.R;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.RetrofitUtil;


/**
 * @author: Allen.
 * @date: 2018/7/25
 * @description:
 */

public class ChangePresenter implements ChangeContract.presenter {

    private Context context;
    private ChangeContract.View view;

    public ChangePresenter(Context context, ChangeContract.View view) {
        this.context = context;
        this.view = view;
    }

    /**
     * 修改密码
     */

    @Override
    public void getChange(String account, String password, String newPwd) {
        RetrofitUtil.getInstance().initRetrofitMain().getChange(account,password,newPwd).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<Change>(context, context.getResources().getString(R.string.handler_data)) {
                    @Override
                    protected void onSuccees(Change t) throws Exception {
                        view.setChange(t);
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.setChangeMessage(""+ e.getMessage());
                    }
                });
    }
}
