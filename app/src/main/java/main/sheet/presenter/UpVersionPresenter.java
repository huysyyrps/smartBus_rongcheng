package main.sheet.presenter;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.sheet.bean.UpVersion;
import main.sheet.module.UpVersionContract;
import main.smart.rcgj.R;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.RetrofitUtil;


/**
 * @author: Allen.
 * @date: 2018/7/25
 * @description:
 */

public class UpVersionPresenter implements UpVersionContract.presenter {

    private Context context;
    private UpVersionContract.View view;

    public UpVersionPresenter(Context context, UpVersionContract.View view) {
        this.context = context;
        this.view = view;
    }

    /**
     * 获取版本号
     */
    @Override
    public void getUpVersion() {
        RetrofitUtil.getInstance().initRetrofitSetSession().getVersion().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<UpVersion>(context, context.getResources().getString(R.string.handler_data)) {
                    @Override
                    protected void onSuccees(UpVersion t) throws Exception {
                        view.setUpVersion(t);
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                   //     view.setUpVersionMessage(""+ e.getMessage());
                    }
                });
    }
}
