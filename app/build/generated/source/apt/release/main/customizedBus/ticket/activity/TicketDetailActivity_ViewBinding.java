// Generated code from Butter Knife. Do not modify!
package main.customizedBus.ticket.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.baidu.mapapi.map.MapView;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;

public class TicketDetailActivity_ViewBinding implements Unbinder {
  private TicketDetailActivity target;

  private View view7f0901bc;

  private View view7f0901f1;

  @UiThread
  public TicketDetailActivity_ViewBinding(TicketDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TicketDetailActivity_ViewBinding(final TicketDetailActivity target, View source) {
    this.target = target;

    View view;
    target.baiduMapView = Utils.findRequiredViewAsType(source, R.id.id_baidu_map_view, "field 'baiduMapView'", MapView.class);
    target.noticeTv = Utils.findRequiredViewAsType(source, R.id.id_notice_tv, "field 'noticeTv'", TextView.class);
    target.lineNameTv = Utils.findRequiredViewAsType(source, R.id.id_line_name_tv, "field 'lineNameTv'", TextView.class);
    target.carnoTv = Utils.findRequiredViewAsType(source, R.id.id_carno_tv, "field 'carnoTv'", TextView.class);
    target.datePeoplenumTv = Utils.findRequiredViewAsType(source, R.id.id_date_peoplenum_tv, "field 'datePeoplenumTv'", TextView.class);
    target.onbusTime = Utils.findRequiredViewAsType(source, R.id.id_onbus_time, "field 'onbusTime'", TextView.class);
    target.onbusIcon = Utils.findRequiredViewAsType(source, R.id.id_onbus_icon, "field 'onbusIcon'", ImageView.class);
    target.onbusIconLinearlayout = Utils.findRequiredViewAsType(source, R.id.id_onbus_icon_linearlayout, "field 'onbusIconLinearlayout'", LinearLayout.class);
    target.onbusStationTv = Utils.findRequiredViewAsType(source, R.id.id_onbus_station, "field 'onbusStationTv'", TextView.class);
    target.line = Utils.findRequiredView(source, R.id.id_line, "field 'line'");
    target.offbusTime = Utils.findRequiredViewAsType(source, R.id.id_offbus_time, "field 'offbusTime'", TextView.class);
    target.offbusIcon = Utils.findRequiredViewAsType(source, R.id.id_offbus_icon, "field 'offbusIcon'", ImageView.class);
    target.offbusIconLinearlayout = Utils.findRequiredViewAsType(source, R.id.id_offbus_icon_linearlayout, "field 'offbusIconLinearlayout'", LinearLayout.class);
    target.offbusStationTv = Utils.findRequiredViewAsType(source, R.id.id_offbus_station, "field 'offbusStationTv'", TextView.class);
    target.checkTicketBgImg = Utils.findRequiredViewAsType(source, R.id.id_check_ticket_bg_img, "field 'checkTicketBgImg'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.id_check_ticket_button, "field 'checkTicketButton' and method 'onViewClicked'");
    target.checkTicketButton = Utils.castView(view, R.id.id_check_ticket_button, "field 'checkTicketButton'", Button.class);
    view7f0901bc = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.id_refund_button, "field 'refundButton' and method 'onViewClicked'");
    target.refundButton = Utils.castView(view, R.id.id_refund_button, "field 'refundButton'", Button.class);
    view7f0901f1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    TicketDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.baiduMapView = null;
    target.noticeTv = null;
    target.lineNameTv = null;
    target.carnoTv = null;
    target.datePeoplenumTv = null;
    target.onbusTime = null;
    target.onbusIcon = null;
    target.onbusIconLinearlayout = null;
    target.onbusStationTv = null;
    target.line = null;
    target.offbusTime = null;
    target.offbusIcon = null;
    target.offbusIconLinearlayout = null;
    target.offbusStationTv = null;
    target.checkTicketBgImg = null;
    target.checkTicketButton = null;
    target.refundButton = null;

    view7f0901bc.setOnClickListener(null);
    view7f0901bc = null;
    view7f0901f1.setOnClickListener(null);
    view7f0901f1 = null;
  }
}
