package main.customizedBus.line.presenter;

import android.content.Context;

import main.customizedBus.line.bean.CustomizedLineBean;
import main.customizedBus.line.module.CustomizedLinesContract;
import main.customizedBus.line.module.CustomizedSelectLinesContract;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.MainUtil;
import main.utils.utils.PublicData;
import main.utils.utils.RetrofitUtil;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CustomizedSelectLinesPresenter implements CustomizedSelectLinesContract.presenter {
    private Context context;
    private CustomizedSelectLinesContract.View view;

    public CustomizedSelectLinesPresenter(Context context,  CustomizedSelectLinesContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void sendRequestGetLinesWithStartEndAddress(Map<String, Object> param) {
        RetrofitUtil.getInstance().initRetrofitMain().sendRequestGetLinesWithStartEndAddress(param).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<CustomizedLineBean>(context, PublicData.netWorkingMsg) {
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
