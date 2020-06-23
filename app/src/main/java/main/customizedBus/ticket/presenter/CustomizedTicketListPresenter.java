package main.customizedBus.ticket.presenter;

import android.content.Context;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.customizedBus.ticket.bean.BuyTicketOrderBean;
import main.customizedBus.ticket.bean.TicketBean;
import main.customizedBus.ticket.module.CustomizedBuyTicketContract;
import main.customizedBus.ticket.module.CustomizedTicketListContract;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.MainUtil;
import main.utils.utils.PublicData;
import main.utils.utils.RetrofitUtil;

public class CustomizedTicketListPresenter implements CustomizedTicketListContract.presenter {
    private Context context;
    private CustomizedTicketListContract.View view;

    public CustomizedTicketListPresenter(Context context, CustomizedTicketListContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void sendRequestGetTicketList(Map<String, Object> param) {
        RetrofitUtil.getInstance().initRetrofitMain().sendRequestGetTicketList(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<TicketBean>(context, PublicData.netWorkingMsg) {
                    @Override
                    protected void onSuccees(TicketBean buyTicketOrderBean) throws Exception {
                        view.requestOnSuccees(buyTicketOrderBean);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                       view.requestOnFailure(e,isNetWorkError);
                    }
                });
    }
}
