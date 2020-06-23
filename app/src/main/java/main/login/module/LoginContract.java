package main.login.module;


import main.login.bean.Login;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

/**
 * Created by Administrator on 2019/4/11.
 */

public interface LoginContract {
    interface View extends BaseEView<presenter> {
        //设置查询线路
        void setLogin(Login loginBean);
        void setLoginMessage(String message);
    }

    interface presenter extends BasePresenter {
        //线路回调
        void getLogin(String userName, String passWord, String type);
    }
}
