package main.customizedBus.home.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.refreshview.CustomRefreshView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.customizedBus.home.adapter.StandbyTicketFragmentAdapter;
import main.customizedBus.ticket.activity.TicketDetailActivity;
import main.customizedBus.ticket.bean.TicketBean;
import main.customizedBus.ticket.module.CustomizedTicketListContract;
import main.customizedBus.ticket.presenter.CustomizedTicketListPresenter;
import main.smart.rcgj.R;
import main.utils.utils.PublicData;


public class StandbyTicketFragment extends Fragment implements CustomizedTicketListContract.View  {
   private CustomRefreshView recyclerView;
   private StandbyTicketFragmentAdapter adapter;
    private CustomizedTicketListPresenter ticketListPresenter;
    public List<TicketBean.DataBean> dataList;
    public int page = 1;//�ڼ�ҳ����
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_standby_ticket,container,false);
        dataList = new ArrayList<>();
        initView(view);
        ticketListPresenter = new CustomizedTicketListPresenter(getActivity(),this);
        getTicketListFromNet();
        return view;

    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.id_recyclerView);
        recyclerView.getRecyclerView().setBackgroundColor(getResources().getColor(R.color.recyclerViewBackgroundColor));
        //���ò��ֹ�����
        LinearLayoutManager linearLayoutManager = new  LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
//        recyclerView.setLayoutManager(linearLayoutManager);
        //����adapter
        adapter = new StandbyTicketFragmentAdapter(getActivity(),null);
        recyclerView.setAdapter(adapter);
        //�������� ������� ����ʧ��
        recyclerView.setEmptyView("��������");
        adapter.setOnRecyclerViewClickListener(new StandbyTicketFragmentAdapter.OnRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                if (position<adapter.dataList.size()){
                    Intent intent = new Intent(getActivity(), TicketDetailActivity.class);
                    TicketBean.DataBean dataBean = adapter.dataList.get(position);
                    intent.putExtra("id",dataBean.getId());
                    intent.putExtra("mobileStatus",dataBean.getMobileStatus());
                    startActivity(intent);
                }
            }
        });
        //���÷ָ���
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        //������ˢ��
        recyclerView.setOnLoadListener(new CustomRefreshView.OnLoadListener() {
            @Override
            public void onRefresh() {
                //����ˢ�£������ˢ�º���߼�

                page=1;
                getTicketListFromNet();
                //�������ʱ�����ؿؼ�����ˢ�µ�״̬
                //   recyclerView.complete();
            }

            @Override
            public void onLoadMore() {
                //�������ظ��࣬�����������ݵ��߼�
                page+=1;
                getTicketListFromNet();
                //�������ʱ�����ؿؼ��������ص�״̬
                // recyclerView.complete();
            }
        });

    }

    /*****************************��������*******************************************************/
    private  void getTicketListFromNet(){
        Map<String,Object> param = new HashMap<>();
        param.put("Passenger", PublicData.userAccount);
        param.put("page", page);
        param.put("limit", PublicData.limit);
        param.put("mobileStatus", 8);//0�������У�1�������У�2���ѹ��ڣ�3������Ʊ��4���˿����ˣ�5���˿��У�6�����˿7���˿�ʧ��8:������
//       param.put("payStatus", PublicData.userAccount);
        ticketListPresenter.sendRequestGetTicketList(param);


    }
    @Override
    public void requestOnSuccees(TicketBean ticketBean) {
        recyclerView.complete();
        if (ticketBean != null&&ticketBean.getData()!=null){
            if ( page==1){
                dataList.clear();
            }
            dataList.addAll(ticketBean.getData());
            adapter.setDataList(dataList);
            if (page==1&&ticketBean.getData().size()==0){
                recyclerView.isEmptyViewShowing();

            }else  if (ticketBean.getData().size()<PublicData.limit){
                recyclerView.onNoMore();
            }
        }
        else {
            recyclerView.onError();
        }

    }

    @Override
    public void requestOnFailure(Throwable e, boolean isNetWorkError) {
        recyclerView.complete();
        recyclerView.setErrorView();
    }
}
