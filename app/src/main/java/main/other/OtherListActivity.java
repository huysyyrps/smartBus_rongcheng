package main.other;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.sheet.bean.ItemBean;
import main.sheet.complaints.ComplaintsUpActivity;
import main.sheet.nxwd.NxwdListActivity;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.utils.BaseRecyclerAdapter;
import main.utils.utils.BaseViewHolder;
import main.utils.views.Header;

public class OtherListActivity extends BaseActivity {

    @BindView(R.id.header)
    Header header;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    Intent intent;
    BaseRecyclerAdapter mAdapter;
    List<ItemBean> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //Ìí¼ÓÄ£¿é
        addItem();
        setItemAdapter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_other_list;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }

    private void addItem() {
//        ItemBean bean1 = new ItemBean();
//        int drawableId1 = getResources().getIdentifier("ic_comlipe", "drawable", getPackageName());
//        bean1.setAddress(drawableId1);
//        bean1.setName(getResources().getString(R.string.comlipe));
//        itemList.add(bean1);

        ItemBean bean2 = new ItemBean();
        int drawableId2 = getResources().getIdentifier("ic_swrl", "drawable", this.getPackageName());
        bean2.setAddress(drawableId2);
        bean2.setName(getResources().getString(R.string.swrl));
        itemList.add(bean2);

        ItemBean bean3 = new ItemBean();
        int drawableId3 = getResources().getIdentifier("ic_bianmin", "drawable", this.getPackageName());
        bean3.setAddress(drawableId3);
        bean3.setName(getResources().getString(R.string.bianmin));
        itemList.add(bean3);

        ItemBean bean4 = new ItemBean();
        int drawableId4 = getResources().getIdentifier("ic_shenghuo", "drawable", this.getPackageName());
        bean4.setAddress(drawableId4);
        bean4.setName(getResources().getString(R.string.shenghuo));
        itemList.add(bean4);

        ItemBean bean5 = new ItemBean();
        int drawableId5 = getResources().getIdentifier("ic_nxwd", "drawable", this.getPackageName());
        bean5.setAddress(drawableId5);
        bean5.setName(getResources().getString(R.string.nxwd));
//        itemList.add(bean5);

    }

    private void setItemAdapter() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(manager);
        mAdapter = new BaseRecyclerAdapter<ItemBean>(this, R.layout.adapter_itembean, itemList) {
            @Override
            public void convert(BaseViewHolder holder, final ItemBean itemBean) {
                holder.setText(R.id.textView, itemBean.getName());
                holder.setImageResource(R.id.imageView, itemBean.getAddress());
                holder.setOnClickListener(R.id.linearLayout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (itemBean.getName().equals(getResources().getString(R.string.comlipe))) {
                            intent = new Intent(OtherListActivity.this, ComplaintsUpActivity.class);
                            startActivity(intent);
                        }
                        if (itemBean.getName().equals(getResources().getString(R.string.swrl))) {
                            intent = new Intent(OtherListActivity.this, SwrlListActivity.class);
                            startActivity(intent);
                        }
                        if (itemBean.getName().equals(getResources().getString(R.string.bianmin))) {
                            intent = new Intent(OtherListActivity.this, BianMinActivity.class);
                            startActivity(intent);
                        }
                        if (itemBean.getName().equals(getResources().getString(R.string.shenghuo))) {
                            intent = new Intent(OtherListActivity.this, ShengHuoActivity.class);
                            startActivity(intent);
                        }
                        if (itemBean.getName().equals(getResources().getString(R.string.nxwd))) {
                            intent = new Intent(OtherListActivity.this, NxwdListActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
