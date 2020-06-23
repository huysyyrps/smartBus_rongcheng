package main.sheet.presenter;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.sheet.bean.ComplaintsList;
import main.sheet.module.ComplaintsListContract;
import main.smart.rcgj.R;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.RetrofitUtil;


/**
 * @author: Allen.
 * @date: 2018/7/25
 * @description:
 */

public class ComplaintsListPresenter implements ComplaintsListContract.presenter {

    private Context context;
    private ComplaintsListContract.View view;

    public ComplaintsListPresenter(Context context, ComplaintsListContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getComplaintsList(String processingStatus,String account, String startComplaintDate, String endComplaintDate, String page, String limit) {
        RetrofitUtil.getInstance().initRetrofitMain().getComplaintsList(processingStatus,account,startComplaintDate,endComplaintDate
        ,page,limit).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<ComplaintsList>(context, context.getResources().getString(R.string.handler_data)) {
                    @Override
                    protected void onSuccees(ComplaintsList t) throws Exception {
                        view.setComplaintsList(t);
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.setComplaintsListMessage(""+ e.getMessage());
                    }
                });
    }
}
