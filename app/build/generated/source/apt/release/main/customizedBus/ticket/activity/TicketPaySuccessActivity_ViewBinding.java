// Generated code from Butter Knife. Do not modify!
package main.customizedBus.ticket.activity;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;

public class TicketPaySuccessActivity_ViewBinding implements Unbinder {
  private TicketPaySuccessActivity target;

  private View view7f0901f7;

  @UiThread
  public TicketPaySuccessActivity_ViewBinding(TicketPaySuccessActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TicketPaySuccessActivity_ViewBinding(final TicketPaySuccessActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.id_see_myticket_btn, "method 'onViewClicked'");
    view7f0901f7 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view7f0901f7.setOnClickListener(null);
    view7f0901f7 = null;
  }
}
