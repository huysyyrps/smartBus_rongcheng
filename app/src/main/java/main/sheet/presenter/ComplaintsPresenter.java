package main.sheet.presenter;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import main.sheet.bean.Complaints;
import main.sheet.module.ComplaintsContract;
import main.smart.rcgj.R;
import main.utils.base.BaseObserverNoEntry;
import main.utils.utils.RetrofitUtil;


/**
 * @author: Allen.
 * @date: 2018/7/25
 * @description:
 */

public class ComplaintsPresenter implements ComplaintsContract.presenter {

    private Context context;
    private ComplaintsContract.View view;

    public ComplaintsPresenter(Context context, ComplaintsContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void getComplaints(String title, String content,String account) {
        RetrofitUtil.getInstance().initRetrofitMain().getComplaints(title,content,account).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserverNoEntry<Complaints>(context, context.getResources().getString(R.string.handler_data)) {
                    @Override
                    protected void onSuccees(Complaints t) throws Exception {
                        view.setComplaints(t);
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.setComplaintsMessage(""+ e.getMessage());
                    }
                });
    }
}
