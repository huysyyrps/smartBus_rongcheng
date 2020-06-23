package main.customizedBus.initiateCustomization.module;

import main.customizedBus.home.bean.PublicResponseBean;
import main.customizedBus.initiateCustomization.bean.CustomizedDemandListBean;
import main.login.module.LoginContract;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;
import main.utils.base.BaseView;

import java.util.HashMap;
import java.util.Map;

public interface CustomizedContract {
    interface View extends BaseEView<presenter>{
        void requestOnSuccees(PublicResponseBean publicResponseBean);//网络请求成功
        void requestOnFailure(Throwable e, boolean isNetWorkError);//网络请求失败
        void requestOnSucceesDemandList(CustomizedDemandListBean listBean);//网络请求成功
        void requestOnFailureDemandList(Throwable e, boolean isNetWorkError);//网络请求失败
    }
    interface presenter extends BasePresenter {
        //发起定制
        void sendRequestBusCustomizedSave(Map<String, Object> param);
        //发起定制记录
        void  sendRequestGetcustomizedDemandList(Map<String, Object> param);
    }
}
