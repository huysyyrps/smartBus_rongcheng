package main.other.module;


import main.other.bean.BianMin;
import main.sheet.bean.Swrllist;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

/**
 * Created by Administrator on 2019/4/11.
 */

public interface BianMinContract {
    interface View extends BaseEView<presenter> {
        void setBianMin(BianMin swrllist);
        void setBianMinMessage(String message);
    }

    interface presenter extends BasePresenter {
        void getBianMin(String page, String limit);
    }
}
