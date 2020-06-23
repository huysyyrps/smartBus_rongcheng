package main.customizedBus.line.module;

import main.customizedBus.line.bean.CustomizedLineBean;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

import java.util.Map;

public interface CustomizedSelectLinesContract {
    interface View extends BaseEView<presenter>{
        void requestOnSuccees(CustomizedLineBean lineBean);//网络请求成功
        void requestOnFailure(Throwable e, boolean isNetWorkError);//网络请求失败
    }
    interface presenter extends BasePresenter {

        //起始地点目的地地点查询线路
        void sendRequestGetLinesWithStartEndAddress(Map<String, Object> param);
    }
}
