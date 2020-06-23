package main.customizedBus.line.presenter;

import android.content.Context;

import main.customizedBus.home.bean.PublicResponseBean;
import main.customizedBus.initiateCustomization.module.CustomizedContract;
import main.customizedBus.line.bean.CustomizedLineBean;
import main.customizedBus.line.module.CustomizedLinesContract;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.MainUtil;
import main.utils.utils.PublicData;
import main.utils.utils.RetrofitUtil;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CustomizedLinesPresenter implements CustomizedLinesContract.presenter {
    private Context context;
    private CustomizedLinesContract.View view;

    public CustomizedLinesPresenter(Context context, CustomizedLinesContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void sendRequestGetLines(Map<String, Object> param) {
        RetrofitUtil.getInstance().initRetrofitMain().sendRequestGetLines(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<CustomizedLineBean>(context, PublicData.netWorkingMsg ) {
                    @Override
                    protected void onSuccees(CustomizedLineBean customizedLineBean) throws Exception {
                        view.requestOnSuccees(customizedLineBean);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                       view.requestOnFailure(e,isNetWorkError);
                    }
                });
    }
}
