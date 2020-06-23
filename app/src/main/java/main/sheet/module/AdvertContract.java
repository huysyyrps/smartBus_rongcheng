package main.sheet.module;


import main.login.bean.CheckPassWord;
import main.sheet.bean.AdvertDown;
import main.sheet.bean.AdvertTop;
import main.sheet.bean.Notice;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

/**
 * Created by Administrator on 2019/4/11.
 */

public interface AdvertContract {
    interface View extends BaseEView<presenter> {
        //获取广告
        void setAdvertTop(AdvertTop advertTop);
        void setAdvertDown(AdvertDown advertDown);
        void setNotice(Notice notice);
        void setAdvertMessage(String message);
    }

    interface presenter extends BasePresenter {
        //广告回调
        void getAdvertTop(String page, String limit, String avdId);
        void getAdvertDown(String page, String limit, String avdId);
        void getNotice(String page, String limit, String avdId);
    }
}
