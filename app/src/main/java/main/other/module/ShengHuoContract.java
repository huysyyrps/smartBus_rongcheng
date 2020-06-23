package main.other.module;


import main.other.bean.BianMin;
import main.other.bean.ShengHuo;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

/**
 * Created by Administrator on 2019/4/11.
 */

public interface ShengHuoContract {
    interface View extends BaseEView<presenter> {
        void setShengHuo(ShengHuo swrllist);
        void setShengHuoMessage(String message);
    }

    interface presenter extends BasePresenter {
        void getShengHuo(String page, String limit);
    }
}
