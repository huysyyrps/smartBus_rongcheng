// Generated code from Butter Knife. Do not modify!
package main.other;

import android.view.View;
import android.widget.ImageView;
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

public class SwrlListActivity_ViewBinding implements Unbinder {
  private SwrlListActivity target;

  private View view7f090230;

  @UiThread
  public SwrlListActivity_ViewBinding(SwrlListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SwrlListActivity_ViewBinding(final SwrlListActivity target, View source) {
    this.target = target;

    View view;
    target.tvTittle = Utils.findRequiredViewAsType(source, R.id.tv_tittle, "field 'tvTittle'", TextView.class);
    view = Utils.findRequiredView(source, R.id.iv_left, "field 'ivLeft' and method 'onViewClicked'");
    target.ivLeft = Utils.castView(view, R.id.iv_left, "field 'ivLeft'", ImageView.class);
    view7f090230 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
    target.spinner = Utils.findRequiredViewAsType(source, R.id.spinner, "field 'spinner'", Spinner.class);
    target.llNoContent = Utils.findRequiredViewAsType(source, R.id.llNoContent, "field 'llNoContent'", LinearLayout.class);
    target.llNocontent = Utils.findRequiredViewAsType(source, R.id.ll_nocontent, "field 'llNocontent'", LinearLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SwrlListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTittle = null;
    target.ivLeft = null;
    target.spinner = null;
    target.llNoContent = null;
    target.llNocontent = null;
    target.recyclerView = null;

    view7f090230.setOnClickListener(null);
    view7f090230 = null;
  }
}
