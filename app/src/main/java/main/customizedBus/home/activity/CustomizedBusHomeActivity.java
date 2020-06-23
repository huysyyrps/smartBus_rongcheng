package main.customizedBus.home.activity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import main.customizedBus.home.fragment.CustomizerBusHomeFragment;
import main.customizedBus.home.fragment.StandbyTicketFragment;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;
import main.utils.utils.PublicData;
import main.utils.utils.SharePreferencesUtils;
import main.utils.views.Header;

public class CustomizedBusHomeActivity extends BaseActivity {

   private FrameLayout frameLayout;
   private RadioGroup radioGroup;
   private RadioButton rBtnLeft;
   private RadioButton rBtnRight;
   private Header header;
   //fragment
    private CustomizerBusHomeFragment customizerBusHomeFragment;
    private StandbyTicketFragment standbyTicketFragment;
    //Fragment管理器以及事务
   private FragmentManager fragmentManager;
   private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        String userAccount = new SharePreferencesUtils().getString(this,"userName","");
        PublicData.userAccount = userAccount;


        initListence();
    }
//初始化视图监听
    private void initListence() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.id_rbtleft:

                        switchFragment (i);

                        break;
                    case R.id.id_rbtright:
                        switchFragment (i);
                        break;
                }
            }
        });
    }
//切换Fragment
    private void switchFragment(int i) {
        /**切换fragment时需要重新获取否则崩溃*/
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        switch (i){
            case R.id.id_rbtleft:
                if (standbyTicketFragment != null){
                    transaction.hide(standbyTicketFragment);
                }
               if (customizerBusHomeFragment == null){
                   customizerBusHomeFragment = new CustomizerBusHomeFragment();
                   transaction.add(R.id.id_framelayout, customizerBusHomeFragment);
               }else {
                   transaction.show(customizerBusHomeFragment);
               }

                break;
            case R.id.id_rbtright:
                if (customizerBusHomeFragment != null){
                    transaction.hide(customizerBusHomeFragment);
                }
                if (standbyTicketFragment == null){
                    standbyTicketFragment = new StandbyTicketFragment();
                    transaction.add(R.id.id_framelayout,  standbyTicketFragment);
                }else {
                    transaction.show(standbyTicketFragment);
                }

                break;
        }

        transaction.commit();
    }

    //初始化视图
   public void initView() {

        //
        frameLayout = findViewById(R.id.id_framelayout);
        radioGroup = findViewById(R.id.id_radiogroup);
        rBtnLeft = findViewById(R.id.id_rbtleft);
        rBtnRight = findViewById(R.id.id_rbtright);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        customizerBusHomeFragment = new CustomizerBusHomeFragment();
        transaction.add(R.id.id_framelayout, customizerBusHomeFragment).commit();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_customized_bus_home;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }
}
