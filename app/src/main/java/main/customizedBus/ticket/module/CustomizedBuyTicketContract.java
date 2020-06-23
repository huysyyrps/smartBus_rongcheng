package main.customizedBus.ticket.module;

import java.util.Map;

import main.customizedBus.home.bean.PublicResponseBean;
import main.customizedBus.line.bean.CustomizedLineBean;
import main.customizedBus.ticket.bean.BuyTicketOrderBean;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;
import retrofit2.http.QueryMap;

public interface CustomizedBuyTicketContract {
    interface View extends BaseEView<presenter>{
        void requestOnSuccees(BuyTicketOrderBean orderBean);//网络请求成功
      void requestOnFailure(Throwable e, boolean isNetWorkError);//网络请求失败
        void requestOnCancelOrderSuccees(PublicResponseBean responseBean);//取消订单
        void requestOnCancelOrderFailure(Throwable e, boolean isNetWorkError);//网络请求失败
    }
    interface presenter extends BasePresenter {
        //获取购票订单信息
        void sendRequestGetBuyTicketOrderInfo(Map<String, Object> param);
        //取消购票
        void  sendRequestCancelOrder(@QueryMap Map<String, Object> param);

    }
}
