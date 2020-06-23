package main.other.module;


import main.sheet.bean.Swrllist;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

/**
 * Created by Administrator on 2019/4/11.
 */

public interface SwrlListContract {
    interface View extends BaseEView<presenter> {
        //获取失物认领
        void setSwrlList(Swrllist swrllist);
        void setSwrlListMessage(String message);
    }

    interface presenter extends BasePresenter {
        //失物认领回调
        void getSwrlList(String state);
    }
}
