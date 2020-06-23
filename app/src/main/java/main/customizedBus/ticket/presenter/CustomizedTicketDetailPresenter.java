package main.customizedBus.ticket.presenter;

import android.content.Context;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.customizedBus.home.bean.PublicResponseBean;
import main.customizedBus.ticket.bean.TicketBean;
import main.customizedBus.ticket.bean.TicketDetailBean;
import main.customizedBus.ticket.module.CustomizedTicketDetailContract;
import main.customizedBus.ticket.module.CustomizedTicketListContract;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.MainUtil;
import main.utils.utils.PublicData;
import main.utils.utils.RetrofitUtil;

public class CustomizedTicketDetailPresenter implements CustomizedTicketDetailContract.presenter {
    private Context context;
    private CustomizedTicketDetailContract.View view;

    public CustomizedTicketDetailPresenter(Context context, CustomizedTicketDetailContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void sendRequestGetTicketDetail(Map<String, Object> param) {
        RetrofitUtil.getInstance().initRetrofitMain().sendRequestGetTicketDetail(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<TicketDetailBean>(context, PublicData.netWorkingMsg) {
                    @Override
                    protected void onSuccees(TicketDetailBean buyTicketOrderBean) throws Exception {
                        view.requestOnSuccees(buyTicketOrderBean);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                       view.requestOnFailure(e,isNetWorkError);
                    }
                });
    }

    @Override
    public void sendRequestCheckTicket(Map<String, Object> param) {
        RetrofitUtil.getInstance().initRetrofitMain().sendRequestCheckTicket(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<PublicResponseBean>(context, PublicData.netWorkingMsg) {
                    @Override
                    protected void onSuccees(PublicResponseBean bean) throws Exception {
                        view.requestOnCheckTicketSuccees(bean);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.requestOnFailure(e,isNetWorkError);
                    }
                });
    }

    @Override
    public void sendRequestTicketRefundApply(Map<String, Object> param) {
        RetrofitUtil.getInstance().initRetrofitMain().sendRequestTicketRefundApply(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<PublicResponseBean>(context, PublicData.netWorkingMsg) {
                    @Override
                    protected void onSuccees(PublicResponseBean bean) throws Exception {
                        view.requestOnTicketRefundApplySuccees(bean);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.requestOnFailure(e,isNetWorkError);
                    }
                });
    }
}
