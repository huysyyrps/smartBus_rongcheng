// Generated code from Butter Knife. Do not modify!
package main.sheet;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;
import main.utils.views.Header;

public class AboutMyActivity_ViewBinding implements Unbinder {
  private AboutMyActivity target;

  private View view7f0903b0;

  private View view7f0903b1;

  @UiThread
  public AboutMyActivity_ViewBinding(AboutMyActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AboutMyActivity_ViewBinding(final AboutMyActivity target, View source) {
    this.target = target;

    View view;
    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
    target.tvVersion = Utils.findRequiredViewAsType(source, R.id.tvVersion, "field 'tvVersion'", TextView.class);
    target.tvPhone = Utils.findRequiredViewAsType(source, R.id.tvPhone, "field 'tvPhone'", TextView.class);
    target.tvStation = Utils.findRequiredViewAsType(source, R.id.tvStation, "field 'tvStation'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv1, "field 'tv1' and method 'onViewClicked'");
    target.tv1 = Utils.castView(view, R.id.tv1, "field 'tv1'", TextView.class);
    view7f0903b0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv2, "field 'tv2' and method 'onViewClicked'");
    target.tv2 = Utils.castView(view, R.id.tv2, "field 'tv2'", TextView.class);
    view7f0903b1 = view;
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
    AboutMyActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
    target.tvVersion = null;
    target.tvPhone = null;
    target.tvStation = null;
    target.tv1 = null;
    target.tv2 = null;

    view7f0903b0.setOnClickListener(null);
    view7f0903b0 = null;
    view7f0903b1.setOnClickListener(null);
    view7f0903b1 = null;
  }
}
