// Generated code from Butter Knife. Do not modify!
package main.Charge;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;

public class WalletActivity_ViewBinding implements Unbinder {
  private WalletActivity target;

  private View view7f09026e;

  private View view7f090269;

  private View view7f0900e4;

  private View view7f090059;

  @UiThread
  public WalletActivity_ViewBinding(WalletActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WalletActivity_ViewBinding(final WalletActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.mewallet, "field 'mewallet' and method 'onViewClicked'");
    target.mewallet = Utils.castView(view, R.id.mewallet, "field 'mewallet'", LinearLayout.class);
    view7f09026e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.meAccount, "field 'meAccount' and method 'onViewClicked'");
    target.meAccount = Utils.castView(view, R.id.meAccount, "field 'meAccount'", LinearLayout.class);
    view7f090269 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.charge, "field 'charge' and method 'onViewClicked'");
    target.charge = Utils.castView(view, R.id.charge, "field 'charge'", Button.class);
    view7f0900e4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.moneywallet = Utils.findRequiredViewAsType(source, R.id.moneywallet, "field 'moneywallet'", TextView.class);
    view = Utils.findRequiredView(source, R.id.alljob_black, "field 'alljob_black' and method 'onViewClicked'");
    target.alljob_black = Utils.castView(view, R.id.alljob_black, "field 'alljob_black'", RelativeLayout.class);
    view7f090059 = view;
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
    WalletActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mewallet = null;
    target.meAccount = null;
    target.charge = null;
    target.moneywallet = null;
    target.alljob_black = null;

    view7f09026e.setOnClickListener(null);
    view7f09026e = null;
    view7f090269.setOnClickListener(null);
    view7f090269 = null;
    view7f0900e4.setOnClickListener(null);
    view7f0900e4 = null;
    view7f090059.setOnClickListener(null);
    view7f090059 = null;
  }
}
