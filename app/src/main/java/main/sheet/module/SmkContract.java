package main.sheet.module;


import main.sheet.bean.Nxwd;
import main.utils.base.BaseEView;
import main.utils.base.BasePresenter;

/**
 * Created by Administrator on 2019/4/11.
 */

public interface SmkContract {
    interface View extends BaseEView<presenter> {
        //获取市民卡
        void setNxwd(Nxwd nxwd);
        void setNxwdMessage(String message);
    }

    interface presenter extends BasePresenter {
        //市民卡回调
        void getSmk(String page, String limit);
    }
}
