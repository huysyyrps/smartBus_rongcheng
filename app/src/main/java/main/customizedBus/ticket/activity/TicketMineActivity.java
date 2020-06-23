package main.customizedBus.ticket.activity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import main.customizedBus.ticket.fragment.TicketAllFragment;
import main.customizedBus.ticket.fragment.TicketCompleteFragment;
import main.customizedBus.ticket.fragment.TicketRetundFragment;
import main.smart.rcgj.R;
import main.utils.base.BaseActivity;

public class TicketMineActivity extends BaseActivity {
    private FrameLayout frameLayout;
    private RadioGroup radioGroup;
    private RadioButton ticketAllButton;
    private RadioButton ticketCompleteButton;
    private RadioButton ticketRefundButton;
   //Fragment
    private TicketAllFragment ticketAllFragment;
    private TicketCompleteFragment ticketCompleteFragment;
    private TicketRetundFragment ticketRetundFragment;
    //Fragment管理器以及事务
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView() {
        super.initView();
        frameLayout = findViewById(R.id.id_framelayout);
        radioGroup = findViewById(R.id.id_radiogroup);
        ticketAllButton =  findViewById(R.id.id_rbtall);
        ticketCompleteButton =  findViewById(R.id.id_rbtcomplete);
        ticketRefundButton =  findViewById(R.id.id_rbtrefund);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        ticketAllFragment = new TicketAllFragment();
        transaction.add(R.id.id_framelayout, ticketAllFragment).commit();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switchFragment (i);
            }
        });
    }

    //切换Fragment
    private void switchFragment(int i) {
        /**切换fragment时需要重新获取否则崩溃*/

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        hideFragment();

        switch (i){
            case R.id.id_rbtall:

                if (ticketAllFragment == null){
                    ticketAllFragment = new TicketAllFragment();
                    transaction.add(R.id.id_framelayout,  ticketAllFragment);
                }else {
                    transaction.show( ticketAllFragment);
                }

                break;
            case R.id.id_rbtcomplete:

                if (ticketCompleteFragment== null){
                    ticketCompleteFragment = new TicketCompleteFragment();
                    transaction.add(R.id.id_framelayout,  ticketCompleteFragment);
                }else {
                    transaction.show(ticketCompleteFragment);
                }

                break;
            case R.id.id_rbtrefund:

                if (ticketRetundFragment== null){
                    ticketRetundFragment = new TicketRetundFragment();
                    transaction.add(R.id.id_framelayout,  ticketRetundFragment);
                }else {
                    transaction.show(ticketRetundFragment);
                }

                break;
        }

        transaction.commit();
    }

   private void hideFragment(){
       if (ticketAllFragment != null){
           transaction.hide(ticketAllFragment);
       }
       if (ticketCompleteFragment != null){
           transaction.hide(ticketCompleteFragment);
       }
       if (ticketRetundFragment != null){
           transaction.hide(ticketRetundFragment);
       }
   }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_ticket_mine;
    }

    @Override
    protected boolean isHasHeader() {
        return true;
    }

    @Override
    protected void rightClient() {

    }
}
