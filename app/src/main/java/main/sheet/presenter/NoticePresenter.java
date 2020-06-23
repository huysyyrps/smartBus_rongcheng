package main.sheet.presenter;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.sheet.bean.Notice;
import main.sheet.module.NoticeContract;
import main.smart.rcgj.R;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.RetrofitUtil;


/**
 * @author: Allen.
 * @date: 2018/7/25
 * @description:
 */

public class NoticePresenter implements NoticeContract.presenter {

    private Context context;
    private NoticeContract.View view;

    public NoticePresenter(Context context, NoticeContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getNotice(String page, String limit, String appLatestTimeStampStr) {
        RetrofitUtil.getInstance().initRetrofitMain().getNotice(page,limit,appLatestTimeStampStr).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<Notice>(context, context.getResources().getString(R.string.handler_data)) {
                    @Override
                    protected void onSuccees(Notice t) throws Exception {
                        view.setNotice(t);
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.setNoticeMessage(""+ e.getMessage());
                    }
                });
    }
}
