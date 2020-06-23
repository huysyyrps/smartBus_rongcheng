package main.sheet.module;


import main.sheet.bean.AdvertDown;
import main.sheet.bean.AdvertTop;
import main.sheet.bean.Notice;
import main.sheet.bean.UpVersion;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

/**
 * Created by Administrator on 2019/4/11.
 */

public interface UpVersionContract {
    interface View extends BaseEView<presenter> {
        //获取版本号
        void setUpVersion(UpVersion upVersion);
        void setUpVersionMessage(String message);
    }

    interface presenter extends BasePresenter {
        //版本号回调
        void getUpVersion();
    }
}
