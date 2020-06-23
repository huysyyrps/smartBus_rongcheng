// Generated code from Butter Knife. Do not modify!
package main.customizedBus.ticket.activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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

public class TicketOrderPaymentActivity_ViewBinding implements Unbinder {
  private TicketOrderPaymentActivity target;

  private View view7f0901e6;

  @UiThread
  public TicketOrderPaymentActivity_ViewBinding(TicketOrderPaymentActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TicketOrderPaymentActivity_ViewBinding(final TicketOrderPaymentActivity target,
      View source) {
    this.target = target;

    View view;
    target.onbusStationTv = Utils.findRequiredViewAsType(source, R.id.id_onbus_station_tv, "field 'onbusStationTv'", TextView.class);
    target.offbusStationTv = Utils.findRequiredViewAsType(source, R.id.id_offbus_station_tv, "field 'offbusStationTv'", TextView.class);
    target.schedulTv = Utils.findRequiredViewAsType(source, R.id.id_schedul_tv, "field 'schedulTv'", TextView.class);
    target.ticketNumTv = Utils.findRequiredViewAsType(source, R.id.id_ticket_num_tv, "field 'ticketNumTv'", TextView.class);
    target.totalPriceTv = Utils.findRequiredViewAsType(source, R.id.id_total_price_tv, "field 'totalPriceTv'", TextView.class);
    target.wxpayRadiobutton = Utils.findRequiredViewAsType(source, R.id.id_wxpay_radiobutton, "field 'wxpayRadiobutton'", RadioButton.class);
    target.alipayRadiobutton = Utils.findRequiredViewAsType(source, R.id.id_alipay_radiobutton, "field 'alipayRadiobutton'", RadioButton.class);
    target.radiogroup = Utils.findRequiredViewAsType(source, R.id.id_radiogroup, "field 'radiogroup'", RadioGroup.class);
    target.totalPriceBottomTv = Utils.findRequiredViewAsType(source, R.id.id_total_price_bottom_tv, "field 'totalPriceBottomTv'", TextView.class);
    view = Utils.findRequiredView(source, R.id.id_pay_btn, "field 'payBtn' and method 'onViewClicked'");
    target.payBtn = Utils.castView(view, R.id.id_pay_btn, "field 'payBtn'", Button.class);
    view7f0901e6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.horizontalscrollContentview = Utils.findRequiredViewAsType(source, R.id.id_horizontalscroll_contentview, "field 'horizontalscrollContentview'", LinearLayout.class);
    target.lineNameTv = Utils.findRequiredViewAsType(source, R.id.id_line_name_tv, "field 'lineNameTv'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TicketOrderPaymentActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.onbusStationTv = null;
    target.offbusStationTv = null;
    target.schedulTv = null;
    target.ticketNumTv = null;
    target.totalPriceTv = null;
    target.wxpayRadiobutton = null;
    target.alipayRadiobutton = null;
    target.radiogroup = null;
    target.totalPriceBottomTv = null;
    target.payBtn = null;
    target.horizontalscrollContentview = null;
    target.lineNameTv = null;

    view7f0901e6.setOnClickListener(null);
    view7f0901e6 = null;
  }
}
