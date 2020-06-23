package main.login.presenter;

import android.content.Context;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.login.bean.Login;
import main.login.module.LoginContract;
import main.smart.rcgj.R;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.RetrofitUtil;


/**
 * @author: Allen.
 * @date: 2018/7/25
 * @description:
 */

public class LoginPresenter implements LoginContract.presenter {

    private Context context;
    private LoginContract.View view;

    public LoginPresenter(Context context, LoginContract.View view) {
        this.context = context;
        this.view = view;
    }

    /**
     * 登录
     */
    @Override
    public void getLogin(String userName, String passWord, String type) {
        RetrofitUtil.getInstance().initRetrofitGetSession().getLogin(userName,passWord,type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<Login>(context, context.getResources().getString(R.string.handler_data)) {
                    @Override
                    protected void onSuccees(Login t) throws Exception {
                        view.setLogin(t);
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.setLoginMessage(""+ e.getMessage());
                    }
                });
    }
}
