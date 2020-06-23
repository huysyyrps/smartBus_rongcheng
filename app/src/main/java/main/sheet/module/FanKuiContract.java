package main.sheet.module;


import main.sheet.bean.AdvertDown;
import main.sheet.bean.AdvertTop;
import main.sheet.bean.FanKui;
import main.sheet.bean.Notice;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

/**
 * Created by Administrator on 2019/4/11.
 */

public interface FanKuiContract {
    interface View extends BaseEView<presenter> {
        //获取反馈
        void setFanKui(FanKui fanKui);
        void setFanKuiMessage(String message);
    }

    interface presenter extends BasePresenter {
        //反馈回调
        void getFanKui(String id, String content);
    }
}
