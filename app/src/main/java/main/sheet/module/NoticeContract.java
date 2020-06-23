package main.sheet.module;


import main.sheet.bean.AdvertDown;
import main.sheet.bean.AdvertTop;
import main.sheet.bean.Notice;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

/**
 * Created by Administrator on 2019/4/11.
 */

public interface NoticeContract {
    interface View extends BaseEView<presenter> {
        //获取通知公告
        void setNotice(Notice notice);
        void setNoticeMessage(String message);
    }

    interface presenter extends BasePresenter {
        //广告回调
        void getNotice(String page, String limit, String avdId);
    }
}
