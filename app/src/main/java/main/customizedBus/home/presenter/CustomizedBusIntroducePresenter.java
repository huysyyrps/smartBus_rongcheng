package main.customizedBus.home.presenter;

import android.content.Context;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.customizedBus.home.bean.CustomizedBusIntroduceBean;
import main.customizedBus.home.bean.PublicResponseBean;
import main.customizedBus.home.module.CustomizedBusIntroduceContract;
import main.customizedBus.initiateCustomization.bean.CustomizedDemandListBean;
import main.customizedBus.initiateCustomization.module.CustomizedContract;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.PublicData;
import main.utils.utils.RetrofitUtil;

public class CustomizedBusIntroducePresenter implements CustomizedBusIntroduceContract.presenter {
    private Context context;
    private CustomizedBusIntroduceContract.View view;

    public CustomizedBusIntroducePresenter(Context context, CustomizedBusIntroduceContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void sendRequestGetCustomizedBusIntroduce(Map<String, Object> param) {
        RetrofitUtil.getInstance().initRetrofitMain().sendRequestGetCustomizedBusIntroduce(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<CustomizedBusIntroduceBean>(context, PublicData.netWorkingMsg) {
            @Override
            protected void onSuccees(CustomizedBusIntroduceBean introduceBean) throws Exception {
                       view.requestOnSuccees(introduceBean);
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                view.requestOnFailure(e,isNetWorkError);
            }
        });
    }

}
