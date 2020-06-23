package main.login.presenter;

import android.content.Context;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.login.bean.CheckPassWord;
import main.login.module.CheckPassWordContract;
import main.smart.rcgj.R;
import main.utils.base.BaseObserverNoEntry1;
import main.utils.utils.RetrofitUtil;


/**
 * @author: Allen.
 * @date: 2018/7/25
 * @description:
 */

public class CheckPassWordPresenter implements CheckPassWordContract.presenter {

    private Context context;
    private CheckPassWordContract.View view;

    public CheckPassWordPresenter(Context context, CheckPassWordContract.View view) {
        this.context = context;
        this.view = view;
    }

    /**
     * 修改密码
     */
    @Override
    public void getCheckPassWord(String userName, String newPwd) {
        RetrofitUtil.getInstance().initRetrofitMain().getCheckPassWord(userName,newPwd).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry1<CheckPassWord>(context, context.getResources().getString(R.string.handler_data)) {
                    @Override
                    protected void onSuccees(CheckPassWord t) throws Exception {
                        view.setCheckPassWord(t);
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.setCheckPassWordMessage(""+ e.getMessage());
                    }
                });
    }
}
