package main.sheet.nxwd;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.sheet.bean.Nxwd;
import main.sheet.module.NxwdContract;
import main.sheet.presenter.NxwdPresenter;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.utils.BaseRecyclerAdapter;
import main.utils.utils.BaseViewHolder;
import main.utils.views.Header;

import static main.utils.base.Constant.TAG_ONE;

public class NxwdListActivity extends BaseActivity implements NxwdContract.View {

    @BindView(R.id.header)
    Header header;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    NxwdPresenter  nxwdPresenter;
    BaseRecyclerAdapter mAdapter;
    private Boolean isFirst = true;
    private static final double EARTH_RADIUS = 6378137.0;
    private LatLng mCurPoint = null; //��ǰ��γ��
    private LocationClient mLocClient;//�ٶȶ�λ
    List<Nxwd.DataBean> beanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        nxwdPresenter = new NxwdPresenter(this,this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, TAG_ONE);
        } else {
            //��ʼ��λ
            getLatAndLod();
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_nxwd_list;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case TAG_ONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //��ʼ��λ
                    getLatAndLod();
                } else {
                    Toast.makeText(NxwdListActivity.this, "Ȩ�ޱ��ܾ������ֶ�����", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * ��ʼ��λ
     */
    private void getLatAndLod() {
        // ��λ��ʼ��
        mLocClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // ���ֻ�GPS
        option.setAddrType("all");// ���صĶ�λ���������ַ��Ϣ
        option.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
        option.setScanSpan(500);// ���÷���λ����ļ��ʱ��Ϊ5000ms ���������С��1000ms����ֻ��λһ��
        mLocClient.setLocOption(option);
        MyLocationListenner myLocationListener = new MyLocationListenner();
        mLocClient.registerLocationListener(myLocationListener);
        mLocClient.start();
    }

    /**
     * �Զ���λ��ǰλ��
     */
    public class MyLocationListenner implements BDLocationListener {
        public void onReceiveLocation(BDLocation result) {
            if (isFirst) {
                isFirst = false;
                double latitude = result.getLatitude();    //��ȡγ����Ϣ
                double longitude = result.getLongitude(); //��ȡ������Ϣ
                Log.e("latitude", String.valueOf(latitude));
                Log.e("longitude", String.valueOf(longitude));
                if (result == null) {

                    Toast.makeText(NxwdListActivity.this, "��λ��ǰλ��ʧ��", Toast.LENGTH_LONG).show();
                    return;
                }

                if (result.getLatitude() > 0 && result.getLongitude() > 0) {
                    //						mCurPoint = new LatLng((int) (result.getLatitude() * 1e6),(int) (result.getLongitude() * 1e6));
                    mCurPoint = new LatLng(latitude, longitude);
//                    mCurPoint = new LatLng(34.559225, 110.890688);
                    Log.e("mCurPoint", mCurPoint.toString());
                    nxwdPresenter.getNxwd("1","50");
                } else {
                    Toast.makeText(NxwdListActivity.this, "��λ��ǰλ��ʧ��", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }

    @Override
    public void setNxwd(Nxwd nxwd) {
        if (nxwd.getCode() == 0) {
            for (int i = 0; i < nxwd.getData().size(); i++) {
//                double distance = gps2m(mCurPoint.latitude, mCurPoint.longitude
//                        ,Double.valueOf(nxwd.getData().get(i).getLatitude())
//                        ,Double.valueOf(nxwd.getData().get(i).getLongitude()));
//                if (distance<2000){
//                    beanList.add(nxwd.getData().get(i));
//                }
                beanList.add(nxwd.getData().get(i));
            }
        } else if (nxwd.getCode() == -1) {
            Toast.makeText(this, nxwd.getMsg(), Toast.LENGTH_SHORT).show();
        }
        mAdapter = new BaseRecyclerAdapter<Nxwd.DataBean>(this, R.layout.adapter_nxwd_item, beanList) {
            @Override
            public void convert(BaseViewHolder holder, final Nxwd.DataBean nxwd) {
                holder.setText(R.id.tvTitle, "��������"+nxwd.getNetworkName());
                holder.setText(R.id.tvPhone, "��ϵ�绰��"+nxwd.getContactNumber());
                holder.setText(R.id.tvAddress, "��ַ��"+nxwd.getSiteAddress());
                holder.setOnClickListener(R.id.ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(NxwdListActivity.this, NxwdDetailActivity.class);
                        intent.putExtra("bean", nxwd);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setNxwdMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
}
