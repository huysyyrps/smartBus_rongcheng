package main.customizedBus.ticket.presenter;

import android.content.Context;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.customizedBus.home.bean.PublicResponseBean;
import main.customizedBus.line.bean.CustomizedLineBean;
import main.customizedBus.line.module.CustomizedLinesContract;
import main.customizedBus.ticket.bean.BuyTicketOrderBean;
import main.customizedBus.ticket.module.CustomizedBuyTicketContract;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.MainUtil;
import main.utils.utils.PublicData;
import main.utils.utils.RetrofitUtil;

public class CustomizedBuyTicketPresenter implements CustomizedBuyTicketContract.presenter {
    private Context context;
    private CustomizedBuyTicketContract.View view;

    public CustomizedBuyTicketPresenter(Context context, CustomizedBuyTicketContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void sendRequestGetBuyTicketOrderInfo(Map<String, Object> param) {
        RetrofitUtil.getInstance().initRetrofitMain().sendRequestGetBuyTicketOrderInfo(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<BuyTicketOrderBean>(context, PublicData.netWorkingMsg) {
                    @Override
                    protected void onSuccees(BuyTicketOrderBean buyTicketOrderBean) throws Exception {
                        view.requestOnSuccees(buyTicketOrderBean);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                       view.requestOnFailure(e,isNetWorkError);
                    }
                });
    }

    @Override
    public void sendRequestCancelOrder(Map<String, Object> param) {
        RetrofitUtil.getInstance().initRetrofitMain().sendRequestCancelOrder(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<PublicResponseBean>(context, PublicData.netWorkingMsg) {
                    @Override
                    protected void onSuccees(PublicResponseBean responseBean) throws Exception {
                        view. requestOnCancelOrderSuccees(responseBean);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.requestOnCancelOrderFailure(e,isNetWorkError);
                    }
                });
    }
}
