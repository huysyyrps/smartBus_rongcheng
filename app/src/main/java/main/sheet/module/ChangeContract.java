package main.sheet.module;


import main.sheet.bean.AdvertDown;
import main.sheet.bean.AdvertTop;
import main.sheet.bean.Change;
import main.sheet.bean.Notice;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

/**
 * Created by Administrator on 2019/4/11.
 */

public interface ChangeContract {
    interface View extends BaseEView<presenter> {
        //修改密码
        void setChange(Change notice);
        void setChangeMessage(String message);
    }

    interface presenter extends BasePresenter {
        //修改密码回调
        void getChange(String account, String password, String newPwd);
    }
}
