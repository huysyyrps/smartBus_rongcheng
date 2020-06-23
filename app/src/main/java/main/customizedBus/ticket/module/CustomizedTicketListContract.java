package main.customizedBus.ticket.module;

import java.util.Map;

import main.customizedBus.ticket.bean.BuyTicketOrderBean;
import main.customizedBus.ticket.bean.TicketBean;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

public interface CustomizedTicketListContract {
    interface View extends BaseEView<presenter>{
        void requestOnSuccees(TicketBean ticketBean);//网络请求成功
        void requestOnFailure(Throwable e, boolean isNetWorkError);//网络请求失败
    }
    interface presenter extends BasePresenter {
        //获取购票订单信息
        void sendRequestGetTicketList(Map<String, Object> param);

    }
}
