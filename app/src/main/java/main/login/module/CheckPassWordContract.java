package main.login.module;


import main.login.bean.CheckPassWord;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

/**
 * Created by Administrator on 2019/4/11.
 */

public interface CheckPassWordContract {
    interface View extends BaseEView<presenter> {
        //设置查询线路
        void setCheckPassWord(CheckPassWord checkPassWord);
        void setCheckPassWordMessage(String message);
    }

    interface presenter extends BasePresenter {
        //线路回调
        void getCheckPassWord(String userName, String newPwd);
    }
}
