package main.login.presenter;

import android.content.Context;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.login.bean.Register;
import main.login.module.RegisterContract;
import main.smart.rcgj.R;
import main.utils.base.BaseObserverNoEntry1;
import main.utils.utils.RetrofitUtil;


/**
 * @date: 2018/7/25
 * @description:
 */

public class RegisterPresenter implements RegisterContract.presenter {

    private Context context;
    private RegisterContract.View view;

    public RegisterPresenter(Context context, RegisterContract.View view) {
        this.context = context;
        this.view = view;
    }

    /**
     * 注册
     */
    @Override
    public void getRegister(String account, String password) {
        RetrofitUtil.getInstance().initRetrofitMain().getLineStation(account,password).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry1<Register>(context, context.getResources().getString(R.string.handler_data)) {
                    @Override
                    protected void onSuccees(Register t) throws Exception {
                        view.setRegister(t);
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.setRegisterMessage("reason:" + e.getMessage());
                    }
                });
    }
}
