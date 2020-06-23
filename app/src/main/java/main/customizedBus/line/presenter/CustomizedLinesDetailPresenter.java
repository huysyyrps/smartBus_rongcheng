package main.customizedBus.line.presenter;

import android.content.Context;

import main.ApiAddress;
import main.customizedBus.line.bean.CustomizedLineBean;
import main.customizedBus.line.bean.CustomizedLineDetailBean;
import main.customizedBus.line.module.CustomizedLinesContract;
import main.customizedBus.line.module.CustomizedLinesDetailContract;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.MainUtil;
import main.utils.utils.PublicData;
import main.utils.utils.RetrofitUtil;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CustomizedLinesDetailPresenter implements CustomizedLinesDetailContract.presenter {
    private Context context;
    private CustomizedLinesDetailContract.View view;

    public CustomizedLinesDetailPresenter(Context context, CustomizedLinesDetailContract.View view) {
        this.context = context;
        this.view = view;
    }


    @Override
    public void sendRequestGetcustomizedLineDetail(int lineId) {
       // ApiAddress.LineDetial = "customized/line/view/page/mobile/" + String.valueOf(lineId);
        RetrofitUtil.getInstance().initRetrofitMain().sendRequestGetcustomizedLineDetail(lineId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<CustomizedLineDetailBean>(context, PublicData.netWorkingMsg) {
                    @Override
                    protected void onSuccees(CustomizedLineDetailBean customizedLineDetailBean) throws Exception {
                         view.requestOnSuccees(customizedLineDetailBean);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                          view.requestOnFailure(e,isNetWorkError);
                    }
                });
    }
}
