package main.sheet.module;


import main.sheet.bean.AdvertDown;
import main.sheet.bean.AdvertTop;
import main.sheet.bean.Nxwd;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

/**
 * Created by Administrator on 2019/4/11.
 */

public interface NxwdContract {
    interface View extends BaseEView<presenter> {
        //获取农信网点
        void setNxwd(Nxwd nxwd);
        void setNxwdMessage(String message);
    }

    interface presenter extends BasePresenter {
        //农信网点回调
        void getNxwd(String page, String limit);
    }
}
