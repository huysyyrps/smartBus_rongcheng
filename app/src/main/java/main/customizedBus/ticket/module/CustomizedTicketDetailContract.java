package main.customizedBus.ticket.module;

import java.util.Map;

import main.customizedBus.home.bean.PublicResponseBean;
import main.customizedBus.line.module.CustomizedLinesDetailContract;
import main.customizedBus.ticket.bean.TicketBean;
import main.customizedBus.ticket.bean.TicketDetailBean;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

public interface CustomizedTicketDetailContract {
    interface View extends CustomizedLinesDetailContract.View {
        void requestOnSuccees(TicketDetailBean ticketBean);//网络请求成功
       // void requestOnFailure(Throwable e, boolean isNetWorkError);//网络请求失败
        void requestOnCheckTicketSuccees(PublicResponseBean responseBean);//验票网络请求成功
        void requestOnCheckTicketFailure(Throwable e, boolean isNetWorkError);//验票网络请求失败
        void requestOnTicketRefundApplySuccees(PublicResponseBean responseBean);//退款网络请求成功
        void requestOnTicketRefundApplyFailure(Throwable e, boolean isNetWorkError);//退款网络请求失败
    }
    interface presenter extends BasePresenter {
        //获取购票订单信息
        void sendRequestGetTicketDetail(Map<String, Object> param);
        //获取购票订单信息
        void sendRequestCheckTicket(Map<String, Object> param);
        //退款申请
        void  sendRequestTicketRefundApply(Map<String, Object> param);

    }
}
