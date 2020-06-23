package main.smart.bus.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep.TransitRouteStepType;
import com.baidu.mapapi.search.route.TransitRouteResult;

import main.smart.bus.activity.adapter.AutoAdapter;
import main.smart.common.util.ConstData;
import main.smart.rcgj.R;

//百度换结果显示页面
public class BaiduRouteResultActivity extends Activity{
    private static final String TAG = "BaiduRouteResultActivity";
    private TextView qs;
    private TextView js;
    private String qszd;//起始站点名
    private String jszd;//终点站名字
    private ListView show;
    //private List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queryresult);
        TransitRouteResult res = ConstData.res;
        //res.getRouteLines().get(0).getAllStep().get(1).getDuration();
        int size = res.getRouteLines().size();
        qszd = res.getRouteLines().get(0).getStarting().getTitle();
        jszd = res.getRouteLines().get(0).getTerminal().getTitle();
        String[] str=new String[size];
        //list=new ArrayList();
        for (int i=0;i<size;i++){
            String txt="";
            String title="";

            TransitRouteLine routePlan = res.getRouteLines().get(i);
            for(int j=0;j<routePlan.getAllStep().size();j++){
                TransitStep trStep = routePlan.getAllStep().get(j);

                //String s = trStep.getStepType().toString();

//				if(trStep.getStepType() == TransitRouteStepType.WAKLING)
//				{
                //txt = trStep.getInstructions();
                //txt=txt+"步行约"+trStep.get+"米,到达"+mkOnPoiInfo.name+";";
//				}
                //Log.d(TAG,s);
//				PoiInfo onPoiInfo = mkLine.getGetOnStop();
//				PoiInfo offPoiInfo = mkLine.getGetOffStop();
                //线路找是否需要步行
//				int dis= getRouteDis(j,routePlan);
//				if(dis!=0){
//					txt=txt+"步行约"+dis+"米,到达"+mkOnPoiInfo.name+";";
//				}
//				txt=txt+"乘坐"+mkLine.getTitle()+"经"+mkLine.getNumViaStops()+"站,到达"+mkOffPoiInfo.name+";";
//				if(j==routePlan.getNumLines()-1){
//					dis= getRouteDis(j+1,routePlan);
//					if(dis!=0){
//						txt=txt+"步行约"+dis+"米,到达"+jszd;
//					}
//				}
                if(trStep.getStepType() == TransitRouteStepType.BUSLINE)
                {
                    if (title.equals("")){
                        title=trStep.getVehicleInfo().getTitle();
                    }else {
                        title=title+"--->"+trStep.getVehicleInfo().getTitle();
                    }
                }

            }
            str[i]=(i+1)+". "+title;
            //list.add(txt);
        }
        //换乘线路list
        show=(ListView) findViewById(R.id.queryreshow);
        show.setOnItemClickListener(new ShowListener());
        //起止 站点
        qs=(TextView) findViewById(R.id.startstation);
        js=(TextView) findViewById(R.id.endstation);
        qs.setText(qszd);
        js.setText(jszd);
        initAutoDataText(str);
    }


    //listview的监听事件
    class ShowListener implements OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            //String str=list.get(arg2);
            Intent inte=new Intent();
            inte.setClass(BaiduRouteResultActivity.this, BaiduRouteDetailsActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("qd", qszd);
            bundle.putString("zd", jszd);
            //bundle.putString("memo", str);
            bundle.putInt("index",arg2);
            inte.putExtras(bundle);
            startActivity(inte);// 跳转主页
        }
    }



//	//步行信息的计算
//	public Integer getRouteDis(int i,TransitRoutePlan routePlan){
//		int dis=0;
//		for (int j=0;j<routePlan.getNumRoute();j++){
//			MKRoute route=routePlan.
//			if(route.getIndex()+1==i){
//				dis=route.getDistance();
//			}
//		}
//		return dis;
//	}

    //将数据库查询的返回数据 填充到控件
    public void initAutoDataText(String[]  data) {
        AutoAdapter<String> adapter=new AutoAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, data);
        show.setAdapter(adapter);
    }
    public void back(View paramView){
        onBackPressed();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
