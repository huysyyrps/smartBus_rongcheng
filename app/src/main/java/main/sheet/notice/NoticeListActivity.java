package main.sheet.notice;


import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import butterknife.BindView;
import butterknife.ButterKnife;
import main.sheet.notice.fragment.FragmentNotice1;
import main.sheet.notice.fragment.FragmentNotice2;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.views.Header;

public class NoticeListActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.header)
    Header header;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;

    private FragmentNotice1 fragment01;
    private FragmentNotice2 fragment02;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        //Ĭ��ѡ�е�һ��
        RadioButton btn = (RadioButton) radioGroup.getChildAt(0);
        btn.setChecked(true);
        initFragment();
        radioGroup.setOnCheckedChangeListener(NoticeListActivity.this);
    }

    /**
     * ��ʼ����һ��ҳ��
     */
    private void initFragment() {
        //��ȡ������
        manager = getSupportFragmentManager();
        //ͨ����������ȡһ���¼�
        FragmentTransaction transaction = manager.beginTransaction();
        //��ӵ�һ��fragment��֡������
        fragment01 = new FragmentNotice1();
        transaction.add(R.id.frame_layout, fragment01);
        transaction.commit();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_notice_list;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb1:
                FragmentTransaction ft1 = manager.beginTransaction();
                hideAll(ft1);
                if (fragment01 == null) {
                    fragment01 = new FragmentNotice1();
                    ft1.add(R.id.frame_layout, fragment01);
                } else {
                    ft1.show(fragment01);
                }
                ft1.commit();
                break;
            case R.id.rb2:
                FragmentTransaction ft2 = manager.beginTransaction();
                hideAll(ft2);
                if (fragment02 == null) {
                    fragment02 = new FragmentNotice2();
                    ft2.add(R.id.frame_layout, fragment02);
                } else {
                    ft2.show(fragment02);
                }
                ft2.commit();
                break;
        }
    }

    /**
     * ��������fragment
     *
     * @param ft
     */
    private void hideAll(FragmentTransaction ft) {
        if (ft == null) {
            return;
        }
        if (fragment01 != null) {
            ft.hide(fragment01);
        }
        if (fragment02 != null) {
            ft.hide(fragment02);
        }
    }
}
