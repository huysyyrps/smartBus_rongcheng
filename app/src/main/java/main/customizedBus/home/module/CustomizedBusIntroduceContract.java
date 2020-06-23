package main.customizedBus.home.module;

import java.util.Map;

import main.customizedBus.home.bean.CustomizedBusIntroduceBean;
import main.customizedBus.home.bean.PublicResponseBean;
import main.customizedBus.initiateCustomization.bean.CustomizedDemandListBean;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

public interface CustomizedBusIntroduceContract {
    interface View extends BaseEView<presenter>{
        void requestOnSuccees(CustomizedBusIntroduceBean introduceBean);//网络请求成功
        void requestOnFailure(Throwable e, boolean isNetWorkError);//网络请求失败
    }
    interface presenter extends BasePresenter {
        //发起定制
        void sendRequestGetCustomizedBusIntroduce(Map<String, Object> param);
    }
}
