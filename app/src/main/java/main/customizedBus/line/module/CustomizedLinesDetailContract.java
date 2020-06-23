package main.customizedBus.line.module;

import main.customizedBus.line.bean.CustomizedLineBean;
import main.customizedBus.line.bean.CustomizedLineDetailBean;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

import java.util.Map;

public interface CustomizedLinesDetailContract {
    interface View extends BaseEView<presenter>{
        void requestOnSuccees(CustomizedLineDetailBean lineBean);//网络请求成功
        void requestOnFailure(Throwable e, boolean isNetWorkError);//网络请求失败
    }
    interface presenter extends BasePresenter {
        //所有线路
        void sendRequestGetcustomizedLineDetail(int lineId);

    }
}
