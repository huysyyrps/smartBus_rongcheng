// Generated code from Butter Knife. Do not modify!
package main.sheet.complaints;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;
import main.utils.views.Header;

public class ComplaintsListActivity_ViewBinding implements Unbinder {
  private ComplaintsListActivity target;

  private View view7f0903c7;

  private View view7f0903c0;

  @UiThread
  public ComplaintsListActivity_ViewBinding(ComplaintsListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ComplaintsListActivity_ViewBinding(final ComplaintsListActivity target, View source) {
    this.target = target;

    View view;
    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
    view = Utils.findRequiredView(source, R.id.tvStartTime, "field 'tvStartTime' and method 'onViewClicked'");
    target.tvStartTime = Utils.castView(view, R.id.tvStartTime, "field 'tvStartTime'", TextView.class);
    view7f0903c7 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.line1 = Utils.findRequiredView(source, R.id.line1, "field 'line1'");
    view = Utils.findRequiredView(source, R.id.tvEndTime, "field 'tvEndTime' and method 'onViewClicked'");
    target.tvEndTime = Utils.castView(view, R.id.tvEndTime, "field 'tvEndTime'", TextView.class);
    view7f0903c0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.ll = Utils.findRequiredViewAsType(source, R.id.ll, "field 'll'", LinearLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.spinner = Utils.findRequiredViewAsType(source, R.id.spinner, "field 'spinner'", Spinner.class);
    target.llNoContent = Utils.findRequiredViewAsType(source, R.id.llNoContent, "field 'llNoContent'", LinearLayout.class);
    target.llNocontent = Utils.findRequiredViewAsType(source, R.id.ll_nocontent, "field 'llNocontent'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ComplaintsListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
    target.tvStartTime = null;
    target.line1 = null;
    target.tvEndTime = null;
    target.ll = null;
    target.recyclerView = null;
    target.spinner = null;
    target.llNoContent = null;
    target.llNocontent = null;

    view7f0903c7.setOnClickListener(null);
    view7f0903c7 = null;
    view7f0903c0.setOnClickListener(null);
    view7f0903c0 = null;
  }
}
