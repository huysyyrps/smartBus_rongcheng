// Generated code from Butter Knife. Do not modify!
package main.customizedBus.ticket.activity;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;

public class TicketInfoSelectActivity_ViewBinding implements Unbinder {
  private TicketInfoSelectActivity target;

  private View view7f0900e8;

  private View view7f0900f3;

  private View view7f0900fe;

  private View view7f090105;

  private View view7f090106;

  private View view7f090107;

  private View view7f090108;

  private View view7f090109;

  private View view7f09010a;

  private View view7f0900e9;

  private View view7f0900ea;

  private View view7f0900eb;

  private View view7f0900ec;

  private View view7f0900ed;

  private View view7f0900ee;

  private View view7f0900ef;

  private View view7f0900f0;

  private View view7f0900f1;

  private View view7f0900f2;

  private View view7f0900f4;

  private View view7f0900f5;

  private View view7f0900f6;

  private View view7f0900f7;

  private View view7f0900f8;

  private View view7f0900f9;

  private View view7f0900fa;

  private View view7f0900fb;

  private View view7f0900fc;

  private View view7f0900fd;

  private View view7f0900ff;

  private View view7f090100;

  private View view7f090101;

  private View view7f090102;

  private View view7f090103;

  private View view7f090104;

  private View view7f0901be;

  private View view7f0901cb;

  private View view7f0901d4;

  private View view7f0901f8;

  private View view7f0901e3;

  private View view7f0901db;

  @UiThread
  public TicketInfoSelectActivity_ViewBinding(TicketInfoSelectActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TicketInfoSelectActivity_ViewBinding(final TicketInfoSelectActivity target, View source) {
    this.target = target;

    View view;
    target.yyyyMMTV = Utils.findRequiredViewAsType(source, R.id.id_yyyymm_tv, "field 'yyyyMMTV'", TextView.class);
    target.shiftsRadioGroup = Utils.findRequiredViewAsType(source, R.id.id_shifts_radiogroup, "field 'shiftsRadioGroup'", RadioGroup.class);
    target.onbusStationTV = Utils.findRequiredViewAsType(source, R.id.id_onbus_station_tv, "field 'onbusStationTV'", TextView.class);
    target.onbusTimeTV = Utils.findRequiredViewAsType(source, R.id.id_onbus_time_tv, "field 'onbusTimeTV'", TextView.class);
    target.offbusStationTV = Utils.findRequiredViewAsType(source, R.id.id_offbus_station_tv, "field 'offbusStationTV'", TextView.class);
    target.offbusTimeTV = Utils.findRequiredViewAsType(source, R.id.id_offbus_time_tv, "field 'offbusTimeTV'", TextView.class);
    target.peopleNumStationTV = Utils.findRequiredViewAsType(source, R.id.id_people_num_tv, "field 'peopleNumStationTV'", TextView.class);
    target.lineNameTV = Utils.findRequiredViewAsType(source, R.id.id_line_name_tv, "field 'lineNameTV'", TextView.class);
    target.ticketPriceTV = Utils.findRequiredViewAsType(source, R.id.id_ticket_price_tv, "field 'ticketPriceTV'", TextView.class);
    target.ticketNumTV = Utils.findRequiredViewAsType(source, R.id.id_tick_num_tv, "field 'ticketNumTV'", TextView.class);
    target.ticketTotalPriceTV = Utils.findRequiredViewAsType(source, R.id.id_tick_total_price_tv, "field 'ticketTotalPriceTV'", TextView.class);
    view = Utils.findRequiredView(source, R.id.checkbox_1, "method 'OnCheckedChangeListener'");
    view7f0900e8 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_2, "method 'OnCheckedChangeListener'");
    view7f0900f3 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_3, "method 'OnCheckedChangeListener'");
    view7f0900fe = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_4, "method 'OnCheckedChangeListener'");
    view7f090105 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_5, "method 'OnCheckedChangeListener'");
    view7f090106 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_6, "method 'OnCheckedChangeListener'");
    view7f090107 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_7, "method 'OnCheckedChangeListener'");
    view7f090108 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_8, "method 'OnCheckedChangeListener'");
    view7f090109 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_9, "method 'OnCheckedChangeListener'");
    view7f09010a = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_10, "method 'OnCheckedChangeListener'");
    view7f0900e9 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_11, "method 'OnCheckedChangeListener'");
    view7f0900ea = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_12, "method 'OnCheckedChangeListener'");
    view7f0900eb = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_13, "method 'OnCheckedChangeListener'");
    view7f0900ec = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_14, "method 'OnCheckedChangeListener'");
    view7f0900ed = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_15, "method 'OnCheckedChangeListener'");
    view7f0900ee = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_16, "method 'OnCheckedChangeListener'");
    view7f0900ef = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_17, "method 'OnCheckedChangeListener'");
    view7f0900f0 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_18, "method 'OnCheckedChangeListener'");
    view7f0900f1 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_19, "method 'OnCheckedChangeListener'");
    view7f0900f2 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_20, "method 'OnCheckedChangeListener'");
    view7f0900f4 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_21, "method 'OnCheckedChangeListener'");
    view7f0900f5 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_22, "method 'OnCheckedChangeListener'");
    view7f0900f6 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_23, "method 'OnCheckedChangeListener'");
    view7f0900f7 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_24, "method 'OnCheckedChangeListener'");
    view7f0900f8 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_25, "method 'OnCheckedChangeListener'");
    view7f0900f9 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_26, "method 'OnCheckedChangeListener'");
    view7f0900fa = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_27, "method 'OnCheckedChangeListener'");
    view7f0900fb = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_28, "method 'OnCheckedChangeListener'");
    view7f0900fc = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_29, "method 'OnCheckedChangeListener'");
    view7f0900fd = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_30, "method 'OnCheckedChangeListener'");
    view7f0900ff = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_31, "method 'OnCheckedChangeListener'");
    view7f090100 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_32, "method 'OnCheckedChangeListener'");
    view7f090101 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_33, "method 'OnCheckedChangeListener'");
    view7f090102 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_34, "method 'OnCheckedChangeListener'");
    view7f090103 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.checkbox_35, "method 'OnCheckedChangeListener'");
    view7f090104 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.OnCheckedChangeListener(Utils.castParam(p0, "onCheckedChanged", 0, "OnCheckedChangeListener", 0, CheckBox.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.id_confirm_btn, "method 'onViewClicked'");
    view7f0901be = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.id_lastmonth_click_imgv, "method 'onViewClicked'");
    view7f0901cb = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.id_nexttmonth_click_imgv, "method 'onViewClicked'");
    view7f0901d4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.id_select_people_num_view, "method 'onViewClicked'");
    view7f0901f8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.id_onbus_station_view, "method 'onViewClicked'");
    view7f0901e3 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.id_offbus_station_view, "method 'onViewClicked'");
    view7f0901db = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.dateCheckBoxes = Utils.listFilteringNull(
        Utils.findRequiredViewAsType(source, R.id.checkbox_1, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_2, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_3, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_4, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_5, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_6, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_7, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_8, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_9, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_10, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_11, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_12, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_13, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_14, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_15, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_16, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_17, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_18, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_19, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_20, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_21, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_22, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_23, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_24, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_25, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_26, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_27, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_28, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_29, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_30, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_31, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_32, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_33, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_34, "field 'dateCheckBoxes'", CheckBox.class), 
        Utils.findRequiredViewAsType(source, R.id.checkbox_35, "field 'dateCheckBoxes'", CheckBox.class));
  }

  @Override
  @CallSuper
  public void unbind() {
    TicketInfoSelectActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.yyyyMMTV = null;
    target.shiftsRadioGroup = null;
    target.onbusStationTV = null;
    target.onbusTimeTV = null;
    target.offbusStationTV = null;
    target.offbusTimeTV = null;
    target.peopleNumStationTV = null;
    target.lineNameTV = null;
    target.ticketPriceTV = null;
    target.ticketNumTV = null;
    target.ticketTotalPriceTV = null;
    target.dateCheckBoxes = null;

    ((CompoundButton) view7f0900e8).setOnCheckedChangeListener(null);
    view7f0900e8 = null;
    ((CompoundButton) view7f0900f3).setOnCheckedChangeListener(null);
    view7f0900f3 = null;
    ((CompoundButton) view7f0900fe).setOnCheckedChangeListener(null);
    view7f0900fe = null;
    ((CompoundButton) view7f090105).setOnCheckedChangeListener(null);
    view7f090105 = null;
    ((CompoundButton) view7f090106).setOnCheckedChangeListener(null);
    view7f090106 = null;
    ((CompoundButton) view7f090107).setOnCheckedChangeListener(null);
    view7f090107 = null;
    ((CompoundButton) view7f090108).setOnCheckedChangeListener(null);
    view7f090108 = null;
    ((CompoundButton) view7f090109).setOnCheckedChangeListener(null);
    view7f090109 = null;
    ((CompoundButton) view7f09010a).setOnCheckedChangeListener(null);
    view7f09010a = null;
    ((CompoundButton) view7f0900e9).setOnCheckedChangeListener(null);
    view7f0900e9 = null;
    ((CompoundButton) view7f0900ea).setOnCheckedChangeListener(null);
    view7f0900ea = null;
    ((CompoundButton) view7f0900eb).setOnCheckedChangeListener(null);
    view7f0900eb = null;
    ((CompoundButton) view7f0900ec).setOnCheckedChangeListener(null);
    view7f0900ec = null;
    ((CompoundButton) view7f0900ed).setOnCheckedChangeListener(null);
    view7f0900ed = null;
    ((CompoundButton) view7f0900ee).setOnCheckedChangeListener(null);
    view7f0900ee = null;
    ((CompoundButton) view7f0900ef).setOnCheckedChangeListener(null);
    view7f0900ef = null;
    ((CompoundButton) view7f0900f0).setOnCheckedChangeListener(null);
    view7f0900f0 = null;
    ((CompoundButton) view7f0900f1).setOnCheckedChangeListener(null);
    view7f0900f1 = null;
    ((CompoundButton) view7f0900f2).setOnCheckedChangeListener(null);
    view7f0900f2 = null;
    ((CompoundButton) view7f0900f4).setOnCheckedChangeListener(null);
    view7f0900f4 = null;
    ((CompoundButton) view7f0900f5).setOnCheckedChangeListener(null);
    view7f0900f5 = null;
    ((CompoundButton) view7f0900f6).setOnCheckedChangeListener(null);
    view7f0900f6 = null;
    ((CompoundButton) view7f0900f7).setOnCheckedChangeListener(null);
    view7f0900f7 = null;
    ((CompoundButton) view7f0900f8).setOnCheckedChangeListener(null);
    view7f0900f8 = null;
    ((CompoundButton) view7f0900f9).setOnCheckedChangeListener(null);
    view7f0900f9 = null;
    ((CompoundButton) view7f0900fa).setOnCheckedChangeListener(null);
    view7f0900fa = null;
    ((CompoundButton) view7f0900fb).setOnCheckedChangeListener(null);
    view7f0900fb = null;
    ((CompoundButton) view7f0900fc).setOnCheckedChangeListener(null);
    view7f0900fc = null;
    ((CompoundButton) view7f0900fd).setOnCheckedChangeListener(null);
    view7f0900fd = null;
    ((CompoundButton) view7f0900ff).setOnCheckedChangeListener(null);
    view7f0900ff = null;
    ((CompoundButton) view7f090100).setOnCheckedChangeListener(null);
    view7f090100 = null;
    ((CompoundButton) view7f090101).setOnCheckedChangeListener(null);
    view7f090101 = null;
    ((CompoundButton) view7f090102).setOnCheckedChangeListener(null);
    view7f090102 = null;
    ((CompoundButton) view7f090103).setOnCheckedChangeListener(null);
    view7f090103 = null;
    ((CompoundButton) view7f090104).setOnCheckedChangeListener(null);
    view7f090104 = null;
    view7f0901be.setOnClickListener(null);
    view7f0901be = null;
    view7f0901cb.setOnClickListener(null);
    view7f0901cb = null;
    view7f0901d4.setOnClickListener(null);
    view7f0901d4 = null;
    view7f0901f8.setOnClickListener(null);
    view7f0901f8 = null;
    view7f0901e3.setOnClickListener(null);
    view7f0901e3 = null;
    view7f0901db.setOnClickListener(null);
    view7f0901db = null;
  }
}
