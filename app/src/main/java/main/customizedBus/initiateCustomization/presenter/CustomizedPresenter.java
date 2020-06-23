package main.customizedBus.initiateCustomization.presenter;

import android.content.Context;

import main.customizedBus.home.bean.PublicResponseBean;
import main.customizedBus.initiateCustomization.bean.CustomizedDemandListBean;
import main.customizedBus.initiateCustomization.module.CustomizedContract;
import main.login.module.LoginContract;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.MainUtil;
import main.utils.utils.PublicData;
import main.utils.utils.RetrofitUtil;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CustomizedPresenter implements CustomizedContract.presenter {
    private Context context;
    private CustomizedContract.View view;

    public CustomizedPresenter(Context context, CustomizedContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void sendRequestBusCustomizedSave(Map<String, Object> param) {
        RetrofitUtil.getInstance().initRetrofitMain().sendRequestBusCustomizedSave(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<PublicResponseBean>(context, PublicData.netWorkingMsg) {
            @Override
            protected void onSuccees(PublicResponseBean publicResponseBean) throws Exception {
                       view.requestOnSuccees(publicResponseBean);
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                view.requestOnFailure(e,isNetWorkError);
            }
        });
    }

    @Override
    public void sendRequestGetcustomizedDemandList(Map<String, Object> param) {
        RetrofitUtil.getInstance().initRetrofitMain(). sendRequestGetcustomizedDemandList(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<CustomizedDemandListBean>(context,PublicData.netWorkingMsg) {
                    @Override
                    protected void onSuccees(CustomizedDemandListBean customizedDemandListBean) throws Exception {
                            view.requestOnSucceesDemandList(customizedDemandListBean);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {

                    }
                });
    }
}
