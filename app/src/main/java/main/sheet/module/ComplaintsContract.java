package main.sheet.module;


import main.sheet.bean.AdvertDown;
import main.sheet.bean.AdvertTop;
import main.sheet.bean.Complaints;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

/**
 * Created by Administrator on 2019/4/11.
 */

public interface ComplaintsContract {
    interface View extends BaseEView<presenter> {
        //获取投诉
        void setComplaints(Complaints complaints);
        void setComplaintsMessage(String message);
    }

    interface presenter extends BasePresenter {
        //投诉回调
        void getComplaints(String title, String content,String account);
    }
}
