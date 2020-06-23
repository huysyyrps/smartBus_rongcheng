package main.sheet.module;


import main.sheet.bean.Complaints;
import main.sheet.bean.ComplaintsList;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

/**
 * Created by Administrator on 2019/4/11.
 */

public interface ComplaintsListContract {
    interface View extends BaseEView<presenter> {
        //获取投诉列表
        void setComplaintsList(ComplaintsList complaintsList);
        void setComplaintsListMessage(String message);
    }

    interface presenter extends BasePresenter {
        //投诉回调
        void getComplaintsList(String processingStatus,String account , String startComplaintDate,String endComplaintDate
                , String page,String limit);
    }
}
