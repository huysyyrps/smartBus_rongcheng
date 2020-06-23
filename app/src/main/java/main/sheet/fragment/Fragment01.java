package main.sheet.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import circle.CircleMenuAdapter;
import circle.ItemInfo;
import main.Charge.RidecodeActivity;
import main.customizedBus.home.activity.CustomizedBusHomeActivity;
import main.login.LoginActivity;
import main.model.CircleMenuStatus;
import main.other.OtherListActivity;
import main.sheet.advert.AdvertDetailActivity;
import main.sheet.bean.AdvertDown;
import main.sheet.bean.AdvertTop;
import main.sheet.bean.ItemBean;
import main.sheet.bean.Notice;
import main.sheet.complaints.ComplaintsUpActivity;
import main.sheet.module.AdvertContract;
import main.sheet.notice.NoticeDetailActivity;
import main.sheet.notice.NoticeListActivity;
import main.sheet.notice.NoticeListActivity1;
import main.sheet.presenter.AdvertPresenter;
import main.sheet.smk.SmkListActivity;
import main.smart.activity.OnlineActivity;
import main.smart.activity.RecordActivitynew;
import main.smart.bus.activity.BusActivity;
import main.smart.bus.activity.BusStationDetailActivity;
import main.smart.bus.activity.adapter.BusActivityNew;
import main.smart.bus.util.MarqueeView;
import main.smart.common.util.ConstData;
import main.smart.rcgj.R;
import main.utils.utils.BaseRecyclerAdapter;
import main.utils.utils.BaseViewHolder;
import main.utils.utils.GlideImageLoader;
import main.utils.utils.SharePreferencesUtils;
import view.CircleMenu;

/**
 * Created by Administrator on 2019/4/12.
 */

public class Fragment01 extends Fragment implements AdvertContract.View , MarqueeView.OnItemClickListener {

    @BindView(R.id.banner)
    Banner banner;
//    @BindView(R.id.maxRecyclerView)
//    RecyclerView maxRecyclerView;
//    @BindView(R.id.toolbar)
//    LinearLayout toolbar;
//    @BindView(R.id.ll)
//    SwipeRefreshLayout ll;
    @BindView(R.id.recyclerViewAdvert)
    RecyclerView recyclerViewAdvert;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
//    @BindView(R.id.scrollhua)
//    ScrollView scrollhua;
    ImageView havenewims;
    View view;
    Unbinder unbinder;
    Intent intent;
    BaseRecyclerAdapter mAdapter;
    BaseRecyclerAdapter mAdapter1;
    AdvertPresenter advertPresenter;
    SharePreferencesUtils sharePreferencesUtils;
    List<String> advertTopList = new ArrayList<>();
    List<AdvertDown.DataBean> advertDownList = new ArrayList<>();
    List<Notice.DataBean.NoticeBean> noticeList = new ArrayList<>();
//    List<ItemBean> itemList = new ArrayList<>();
    private ImageView ivCenter;
    private CircleMenu mCircleMenu;
    private ImageView ivCenterne;
    private CircleMenu mCircleMenune;
    private MarqueeView marqueeViewne;
    private MarqueeView marqueeView;
    private int[] mItemImgs = new int[7];
    private int[] mItemImgsne = new int[7];
    private RelativeLayout newmenu,oldmenu;
    private String[] itemName = new String[7];
    ObjectAnimator animRotate = null;
    ObjectAnimator animFling = null;

    ObjectAnimator animRotatene = null;
    ObjectAnimator animFlingne = null;
    private float startRotate;
    private float startFling;
    private float startRotatene;
    private float startFlingne;
    private boolean isGetData = false;
    private  CircleMenuAdapter circleMenuAdapternew;
    private  CircleMenuAdapter circleMenuAdapternewne;
    List<ItemInfo> data  = new ArrayList<>();
    List<ItemInfo> datane  = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment01, container, false);
        havenewims = view.findViewById(R.id.havenewims);
        marqueeView=view.findViewById(R.id.app_home_header_problem);
         mCircleMenu = (CircleMenu) view.findViewById(R.id.cm_main);
        ivCenter = (ImageView) view.findViewById(R.id.iv_center_main);
        newmenu=view.findViewById(R.id.newmenu);
        oldmenu=view.findViewById(R.id.oldmenu);
        mCircleMenune = (CircleMenu) view.findViewById(R.id.cm_mainne);
        ivCenterne = (ImageView) view.findViewById(R.id.iv_center_mainne);

        mItemImgs[0]= R.drawable.ic_search_line;
        mItemImgs[1]=R.drawable.ic_search;
        mItemImgs[2]= R.drawable.ic_bus_map;
        mItemImgs[3]= R.drawable.tonggaoshou;
        mItemImgs[4]= R.drawable.ic_flace_init;
        mItemImgs[5]= R.drawable.ic_other;
        mItemImgs[6]= R.drawable.jioyi;

        mItemImgsne[0]= R.drawable.ic_search_line;
        mItemImgsne[1]=R.drawable.ic_search;
        mItemImgsne[2]= R.drawable.ic_bus_map;
        mItemImgsne[3]= R.drawable.tonggaoshounew;
        mItemImgsne[4]= R.drawable.ic_flace_init;
        mItemImgsne[5]= R.drawable.ic_other;
        mItemImgsne[6]= R.drawable.jioyi;

//        circleMenuAdapternew= new CircleMenuAdapter(getActivity(),data) ;

//        ll.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                advertDownList.clear();
//                advertTopList.clear();
//                advertPresenter.getAdvertTop("0", "5", "");
////                advertPresenter.getAdvertDown("0", "2", "1");
//                advertPresenter.getNotice("1", "5", "");
//            }
//        });
        return view;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            //   这里可以做网络请求或者需要的数据刷新操作
            sharePreferencesUtils = new SharePreferencesUtils();
            Log.e("lolo","soussssssssssssssssssssss");
            itemName[0]= getResources().getString(R.string.search_line);
            itemName[1]=getResources().getString(R.string.jianyi);
            itemName[2]=getResources().getString(R.string.bus_map);
            itemName[3]= getResources().getString(R.string.bus_bulletin);
            itemName[4]=getResources().getString(R.string.flace_init);
            itemName[5]=getResources().getString(R.string.other);
            itemName[6]=getResources().getString(R.string.jiaoyi);
            unbinder = ButterKnife.bind(this, view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerViewAdvert.setLayoutManager(linearLayoutManager);
            advertPresenter = new AdvertPresenter(getActivity(), this);
            advertPresenter.getAdvertTop("0", "5", "");
//        advertPresenter.getAdvertDown("0", "2", "1");
            marqueeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("momomo", "onItemClick: ");
                    intent = new Intent(getActivity(), NoticeListActivity1.class);
                    // startActivity(intent);
                    startActivityForResult(intent,1);
                }
            });
            tvVersion.setText(getLocalVersion(getActivity())+"");
            ItemInfo item = null;
            ItemInfo itemne = null;
            for (int i = 0; i < itemName.length; i++) {
                item = new ItemInfo(mItemImgs[i], itemName[i]);
                itemne = new ItemInfo(mItemImgsne[i], itemName[i]);
                datane.add(itemne);
                data.add(item);
            }
            circleMenuAdapternew= new CircleMenuAdapter(getActivity(),data) ;
            mCircleMenu.setAdapter(circleMenuAdapternew);

            circleMenuAdapternewne= new CircleMenuAdapter(getActivity(),datane) ;
            mCircleMenune.setAdapter(circleMenuAdapternewne);

            if ((sharePreferencesUtils.getString(getActivity(), "ishavenew", "")).equals("true")||(sharePreferencesUtils.getString(getActivity(), "ishavenew", "")).equals("")){
                newmenu.setVisibility(View.VISIBLE);
                oldmenu.setVisibility(View.GONE);

                havenewims.setVisibility(View.VISIBLE);
                Log.e("lolo", "setItemAdapter:wwwwwwwwwwwtrue ");
            }else{
                newmenu.setVisibility(View.GONE);
                oldmenu.setVisibility(View.VISIBLE);
                havenewims.setVisibility(View.GONE);
                Log.e("lolo", "setItemAdapter:wwwwwwwwwwwfalse ");

            }




        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }


    private void setItemAdapter() {

        animRotate = ObjectAnimator.ofFloat(ivCenter, "rotation", startRotate, startRotate);
        animRotate.setDuration(1000).start();

        animRotatene = ObjectAnimator.ofFloat(ivCenterne, "rotation", startRotatene, startRotatene);
        animRotatene.setDuration(1000).start();

        if ((sharePreferencesUtils.getString(getActivity(), "ishavenew", "")).equals("true")||(sharePreferencesUtils.getString(getActivity(), "ishavenew", "")).equals("")){
            newmenu.setVisibility(View.VISIBLE);
            oldmenu.setVisibility(View.GONE);
            havenewims.setVisibility(View.VISIBLE);
            Log.e("lolo", "onresume:true ");
        }else{

            newmenu.setVisibility(View.GONE);
            oldmenu.setVisibility(View.VISIBLE);
            havenewims.setVisibility(View.GONE);
            Log.e("lolo", "onresume:false ");


        }
        ivCenter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//				Toast.makeText(CircleActivity.this, "圆盘中心", Toast.LENGTH_SHORT).show();

            }
        });
        mCircleMenu.setOnItemClickListener(new CircleMenu.OnMenuItemClickListener() {

            @Override
            public void onClick(View view, int position) {
                if (itemName[position].equals(getResources().getString(R.string.search_line))) {
                    intent = new Intent(getActivity(), BusActivity.class);
                    intent.putExtra("type", "line");
                    startActivity(intent);
                } else if (itemName[position].equals(getResources().getString(R.string.search_site))) {
                    intent = new Intent(getActivity(), BusActivity.class);
                    intent.putExtra("type", "station");
                    startActivity(intent);
                } else if (itemName[position].equals(getResources().getString(R.string.bus_map))) {
                    intent = new Intent(getActivity(), BusStationDetailActivity.class);
                    startActivity(intent);

                }else if (itemName[position].equals(getResources().getString(R.string.bus_bulletin))) {
                    intent = new Intent(getActivity(), NoticeListActivity1.class);
                    // startActivity(intent);
                    startActivityForResult(intent,1);
//                    intent = new Intent(getActivity(), NoticeListActivity1.class);
//                    startActivity(intent);
                } else if (itemName[position].equals(getResources().getString(R.string.busmode))) {
                    intent = new Intent(getActivity(), CustomizedBusHomeActivity.class);
                    startActivity(intent);
                } else if (itemName[position].equals(getResources().getString(R.string.comlipe))) {
//                    intent = new Intent(getActivity(), ComplaintsUpActivity.class);
//                    startActivity(intent);

                    String userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
                    if (userName!=null&&!userName.equals("")){

                        intent = new Intent(getActivity(), ComplaintsUpActivity.class);
                    }else {
                        intent = new Intent(getActivity(), LoginActivity.class);
                        intent.putExtra("tag", "inner");
                    }
                    startActivity(intent);
                } else if (itemName[position].equals(getResources().getString(R.string.flace_init))) {

                    String userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
                    if (userName!=null&&!userName.equals("")){
                        intent = new Intent(getActivity(), OnlineActivity.class);
                    }else {
                        intent = new Intent(getActivity(), LoginActivity.class);
                        intent.putExtra("tag", "inner");
                    }
                    startActivity(intent);

                } else if (itemName[position].equals(getResources().getString(R.string.other))) {
                    String userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
                    if (userName!=null&&!userName.equals("")){
                        intent = new Intent(getActivity(), OtherListActivity.class);
                    }else {
                        intent = new Intent(getActivity(), LoginActivity.class);
                        intent.putExtra("tag", "inner");
                    }
                    startActivity(intent);
                } else if (itemName[position].equals(getResources().getString(R.string.bus_code))) {
                    intent = new Intent(getActivity(), RidecodeActivity.class);
                    startActivity(intent);
                }else if (itemName[position].equals(getResources().getString(R.string.bus_bulletin))) {

                    Log.e("gogogo","`````````````````");
                    intent = new Intent(getActivity(), NoticeListActivity1.class);
                    // startActivity(intent);
                    startActivityForResult(intent,1);
                }else if (itemName[position].equals(getResources().getString(R.string.jiaoyi))) {
                    String userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
                    if (userName!=null&&!userName.equals("")){
                        intent = new Intent(getActivity(), RecordActivitynew.class);
                    }else {
                        intent = new Intent(getActivity(), LoginActivity.class);
                        intent.putExtra("tag", "inner");
                    }
                    startActivity(intent);

                }else if (itemName[position].equals(getResources().getString(R.string.other))) {
//                    intent = new Intent(getActivity(), CustomizedBusHomeActivity.class);
//                    startActivity(intent);
                }else if(itemName[position].equals(getResources().getString(R.string.jianyi))){
                    String userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
                    if (userName!=null&&!userName.equals("")){

                        intent = new Intent(getActivity(), ComplaintsUpActivity.class);
                    }else {
                        intent = new Intent(getActivity(), LoginActivity.class);
                        intent.putExtra("tag", "inner");
                    }
                    startActivity(intent);
                }

                    }


        });
        mCircleMenu.setFocusable(false);
        mCircleMenu.setOnStatusChangedListener(new CircleMenu.OnMenuStatusChangedListener() {

            @Override
            public void onStatusChanged(CircleMenuStatus status, double rotateAngle) {
                // TODO 可在此处定制各种动画
                mCircleMenu.setNestedScrollingEnabled(false);
                odAnimation(status, (float)rotateAngle);
            }

        });




        ivCenter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//				Toast.makeText(CircleActivity.this, "圆盘中心", Toast.LENGTH_SHORT).show();

            }
        });
        mCircleMenune.setOnItemClickListener(new CircleMenu.OnMenuItemClickListener() {

            @Override
            public void onClick(View view, int position) {
                if (itemName[position].equals(getResources().getString(R.string.search_line))) {
                    intent = new Intent(getActivity(), BusActivity.class);
                    intent.putExtra("type", "line");
                    startActivity(intent);
                } else if (itemName[position].equals(getResources().getString(R.string.search_site))) {
                    intent = new Intent(getActivity(), BusActivity.class);
                    intent.putExtra("type", "station");
                    startActivity(intent);
                } else if (itemName[position].equals(getResources().getString(R.string.bus_map))) {
                    intent = new Intent(getActivity(), BusStationDetailActivity.class);
                    startActivity(intent);

                }else if (itemName[position].equals(getResources().getString(R.string.bus_bulletin))) {
                    intent = new Intent(getActivity(), NoticeListActivity1.class);
                    // startActivity(intent);
                    startActivityForResult(intent,1);
//                    intent = new Intent(getActivity(), NoticeListActivity1.class);
//                    startActivity(intent);
                } else if (itemName[position].equals(getResources().getString(R.string.busmode))) {
                    intent = new Intent(getActivity(), CustomizedBusHomeActivity.class);
                    startActivity(intent);
                } else if (itemName[position].equals(getResources().getString(R.string.comlipe))) {
//                    intent = new Intent(getActivity(), ComplaintsUpActivity.class);
//                    startActivity(intent);

                    String userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
                    if (userName!=null&&!userName.equals("")){

                        intent = new Intent(getActivity(), ComplaintsUpActivity.class);
                    }else {
                        intent = new Intent(getActivity(), LoginActivity.class);
                        intent.putExtra("tag", "inner");
                    }
                    startActivity(intent);
                } else if (itemName[position].equals(getResources().getString(R.string.flace_init))) {

                    String userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
                    if (userName!=null&&!userName.equals("")){
                        intent = new Intent(getActivity(), OnlineActivity.class);
                    }else {
                        intent = new Intent(getActivity(), LoginActivity.class);
                        intent.putExtra("tag", "inner");
                    }
                    startActivity(intent);

                } else if (itemName[position].equals(getResources().getString(R.string.other))) {
                    String userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
                    if (userName!=null&&!userName.equals("")){
                        intent = new Intent(getActivity(), OtherListActivity.class);
                    }else {
                        intent = new Intent(getActivity(), LoginActivity.class);
                        intent.putExtra("tag", "inner");
                    }
                    startActivity(intent);
                } else if (itemName[position].equals(getResources().getString(R.string.bus_code))) {
                    intent = new Intent(getActivity(), RidecodeActivity.class);
                    startActivity(intent);
                }else if (itemName[position].equals(getResources().getString(R.string.bus_bulletin))) {

                    Log.e("gogogo","`````````````````");
                    intent = new Intent(getActivity(), NoticeListActivity1.class);
                    // startActivity(intent);
                    startActivityForResult(intent,1);
                }else if (itemName[position].equals(getResources().getString(R.string.jiaoyi))) {
                    String userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
                    if (userName!=null&&!userName.equals("")){
                        intent = new Intent(getActivity(), RecordActivitynew.class);
                    }else {
                        intent = new Intent(getActivity(), LoginActivity.class);
                        intent.putExtra("tag", "inner");
                    }
                    startActivity(intent);

                }else if (itemName[position].equals(getResources().getString(R.string.other))) {
//                    intent = new Intent(getActivity(), CustomizedBusHomeActivity.class);
//                    startActivity(intent);
                }else if(itemName[position].equals(getResources().getString(R.string.jianyi))){
                    String userName = sharePreferencesUtils.getString(getActivity(), "userName", "");
                    if (userName!=null&&!userName.equals("")){

                        intent = new Intent(getActivity(), ComplaintsUpActivity.class);
                    }else {
                        intent = new Intent(getActivity(), LoginActivity.class);
                        intent.putExtra("tag", "inner");
                    }
                    startActivity(intent);
                }

            }


        });
        mCircleMenune.setFocusable(false);
        mCircleMenune.setOnStatusChangedListener(new CircleMenu.OnMenuStatusChangedListener() {

            @Override
            public void onStatusChanged(CircleMenuStatus status, double rotateAngle) {
                // TODO 可在此处定制各种动画
                mCircleMenune.setNestedScrollingEnabled(false);
                odAnimation(status, (float)rotateAngle);
            }

        });





    }



    private void setBanner() {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(advertTopList);
        //设置轮播时间
        banner.setDelayTime(6000);
        banner.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        advertPresenter.getNotice("1", "1000", sharePreferencesUtils.getString(getActivity(), "appLatestTimeStampStr", ""));
        Log.e("lolo","11111111111"+sharePreferencesUtils.getString(getActivity(), "ishavenew", ""));
        setItemAdapter();

    }

    @Override
    public void setAdvertTop(AdvertTop advertTop) {
        advertTopList.clear();
        if (advertTop.getCode() == 0) {
            for (int i = 0; i < advertTop.getData().size(); i++) {
                advertTopList.add(advertTop.getData().get(i).getPicturesTexts());
            }
            setBanner();
        } else if (advertTop.getCode() == -1) {
            Toast.makeText(getActivity(), advertTop.getMsg(), Toast.LENGTH_SHORT).show();
        }
//        if (ll!=null){
//            ll.setRefreshing(false);
//        }
    }

    @Override
    public void setAdvertDown(AdvertDown advertDown) {
        advertDownList.clear();
        if (advertDown.getCode() == 0) {
            advertDownList.add(advertDown.getData().get(0));
//            for (int i = 0; i < advertDown.getData().size(); i++) {
//                advertDownList.add(advertDown.getData().get(i));
//            }
        } else if (advertDown.getCode() == -1) {
            Toast.makeText(getActivity(), advertDown.getMsg(), Toast.LENGTH_SHORT).show();
        }

        mAdapter1 = new BaseRecyclerAdapter<AdvertDown.DataBean>(getActivity(), R.layout.adver_item_layout, advertDownList) {
            @Override
            public void convert(BaseViewHolder holder, final AdvertDown.DataBean noticeBean) {
                holder.setText(R.id.tv_title, "\t" + noticeBean.getTitle());
                holder.setText1(R.id.tv_content, noticeBean.getPicturesTexts());
                holder.setOnClickListener(R.id.noticeItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(getActivity(), AdvertDetailActivity.class);
                        intent.putExtra("bean", noticeBean);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerViewAdvert.setAdapter(mAdapter1);
        mAdapter.notifyDataSetChanged();
//        if (ll!=null){
//            ll.setRefreshing(false);
//        }
    }

    @Override
    public void setNotice(Notice notice) {
        noticeList.clear();
        if (notice.getCode() == 1) {
            sharePreferencesUtils = new SharePreferencesUtils();
            sharePreferencesUtils.setString(getActivity(), "ishavenew", notice.getData().getIsNew().toString());
            ConstData.ishavenew=notice.getData().getIsNew().toString();
            Log.e("lolo",sharePreferencesUtils.getString(getActivity(), "appLatestTimeStampStr", "")+"^^^^^^^^^^^^4444^^^^^^^^^^"+notice.getData().getIsNew());
            ConstData.appLatestTimeStampStr=notice.getData().getLatestTimeStamp().toString();
            noticeList.add(notice.getData().getNotice().get(0));

            List<String> problems=new ArrayList<>();
            problems.clear();
            Log.e("gogo","###########"+noticeList.size());
//            for(int i=0;i<noticeList.size();i++){
//                Log.e("gogo",noticeList.get(i).getTitle());
//                problems.add(noticeList.get(i).getTitle());
//            }
            for (int i = 0; i < notice.getData().getNotice().size(); i++) {
                problems.add(notice.getData().getNotice().get(i).getTitle());
            }
            marqueeView.startWithList(problems);
//            for (int i = 0; i < notice.getData().getNotice().size(); i++) {
////                noticeList.add(notice.getData().getNotice().get(0));
////            }
        } else {
            Toast.makeText(getActivity(), notice.getMsg(), Toast.LENGTH_SHORT).show();
        }
        mAdapter1 = new BaseRecyclerAdapter<Notice.DataBean.NoticeBean>(getActivity(), R.layout.adver_item_layout, noticeList) {
            @Override
            public void convert(BaseViewHolder holder, final Notice.DataBean.NoticeBean noticeBean) {
                holder.setText(R.id.tv_title, noticeBean.getTitle());
                holder.setText(R.id.tv_content, noticeBean.getReleaseTime());
                holder.setOnClickListener(R.id.noticeItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(getActivity(), NoticeDetailActivity.class);
                        intent.putExtra("bean", noticeBean);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerViewAdvert.setAdapter(mAdapter1);
        mAdapter.notifyDataSetChanged();
//        if (ll!=null){
//            ll.setRefreshing(false);
//        }
    }
    public static String getLocalVersion(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }
    @Override
    public void setAdvertMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

//    @OnClick(R.id.toolbar)
//    public void onViewClicked() {
//        intent = new Intent(getActivity(), NoticeListActivity1.class);
//        startActivity(intent);
//    }
private void odAnimation(CircleMenuStatus status, float rotateAngle) {

    switch (status) {
        case IDLE:
            animRotate.cancel();
            animRotatene.cancel();
            break;
        case START_ROTATING:
           // scrollhua.setFocusable(false);
            break;
        case ROTATING:
            animRotate = ObjectAnimator.ofFloat(ivCenter, "rotation", startRotate, startRotate + rotateAngle);
            animRotate.setDuration(1000).start();
            startRotate += rotateAngle;

            animRotatene = ObjectAnimator.ofFloat(ivCenterne, "rotation", startRotatene, startRotatene + rotateAngle);
            animRotatene.setDuration(1000).start();
            startRotatene += rotateAngle;
            break;
        case STOP_ROTATING:
            break;
        case START_FLING:
            break;

        case FLING:
            animFling = ObjectAnimator.ofFloat(ivCenter, "rotation", startFling, startFling + rotateAngle);
            animFling.setDuration(200).start();
            startFling += rotateAngle;

            animFlingne = ObjectAnimator.ofFloat(ivCenterne, "rotation", startFlingne, startFlingne + rotateAngle);
            animFlingne.setDuration(200).start();
            startFlingne += rotateAngle;
            break;
        case STOP_FLING:

            break;

        default:
            break;
    }

}

    @Override
    public void onItemClick(int position, TextView textView) {
        Log.e("momomo", "onItemClick: ");
        intent = new Intent(getActivity(), NoticeListActivity1.class);
        startActivityForResult(intent,1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data1) {
        super.onActivityResult(requestCode, resultCode, data1);
        Log.e("lolo","wwwwwwwwwwwwwwwwwwww"+resultCode);

        switch (requestCode) {
            case 1: //返回的结果是来自于Activity B
                newmenu.setVisibility(View.GONE);
                oldmenu.setVisibility(View.VISIBLE);
                havenewims.setVisibility(View.GONE);


                break;
            case 0: //返回的结果是来自于Activity B
                newmenu.setVisibility(View.GONE);
                oldmenu.setVisibility(View.VISIBLE);
                havenewims.setVisibility(View.GONE);
                break;
            default:
                break;
        }

    }
}