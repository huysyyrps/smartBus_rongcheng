package main.sheet.nxwd;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.sheet.bean.Nxwd;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.views.Header;

import static main.utils.base.Constant.TAG_ONE;

public class NxwdDetailActivity extends BaseActivity {

    @BindView(R.id.header)
    Header header;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvDetail)
    TextView tvDetail;
    private BaiduMap mBaiduMap;

    Nxwd.DataBean bean;
    String tag = "no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        header.setTvTitle(getResources().getString(R.string.nxwd));
        Intent intent = getIntent();
        bean = (Nxwd.DataBean) intent.getSerializableExtra("bean");
        String title = bean.getNetworkName();
        String content = bean.getDetails();
        String address = bean.getSiteAddress();
        String phone = bean.getContactNumber();
        String detail = bean.getDetails();
        tvTitle.setText(title);
        tvAddress.setText("��ַ��" +address);
        tvPhone.setText("�绰��" + phone);
        tvDetail.setText("���飺"+detail);
        //����html
        webView.loadData(Html.fromHtml(content).toString(), "text/html", "UTF-8");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//����ʹ��js
        //��ʹ�û��棬ֻ�������ȡ����.
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //֧����Ļ����
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        webSettings.setTextZoom(260);

        //��ȡ��ͼ�ؼ�����
        mBaiduMap = mapView.getMap();
        //��ͨ��ͼ
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, TAG_ONE);
        } else {
            //��ʼ��λ
            initMap();
        }

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_nxwd_detail;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }

    private void initMap() {
        mBaiduMap = mapView.getMap();
        LatLng ll = new LatLng(Double.valueOf(bean.getLatitude()), Double.valueOf(bean.getLongitude()));
        MapStatus.Builder builder = new MapStatus.Builder();
        if (tag.equals("no")) {
            builder.target(ll).zoom(19);
        } else if (tag.equals("yes")) {
            builder.target(ll);
        }
        //����Markerͼ��
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka1);
        //����MarkerOption�������ڵ�ͼ�����Marker
        OverlayOptions option = new MarkerOptions()
                .position(ll)
                .icon(bitmap);
        //�ڵ�ͼ�����Marker������ʾ
        mBaiduMap.addOverlay(option);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Override
    protected void onDestroy() {
        mBaiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        super.onDestroy();
    }
}
