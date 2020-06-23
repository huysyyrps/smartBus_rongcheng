package main.sheet.module;


import main.login.bean.Register;
import main.utils.base.BaseDView;
import main.utils.base.BasePresenter;

/**
 * Created by Administrator on 2019/4/11.
 */

public interface RegisterContract {
    interface View extends BaseDView<presenter> {
        //注册
        void setRegister(Register bean);
        void setRegisterMessage(String s);
    }

    interface presenter extends BasePresenter {
        void getRegister(String account, String password);
    }
}
