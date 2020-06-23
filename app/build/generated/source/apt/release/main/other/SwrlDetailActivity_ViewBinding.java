// Generated code from Butter Knife. Do not modify!
package main.other;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;
import main.utils.views.Header;

public class SwrlDetailActivity_ViewBinding implements Unbinder {
  private SwrlDetailActivity target;

  @UiThread
  public SwrlDetailActivity_ViewBinding(SwrlDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SwrlDetailActivity_ViewBinding(SwrlDetailActivity target, View source) {
    this.target = target;

    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'tvTitle'", TextView.class);
    target.tvTime = Utils.findRequiredViewAsType(source, R.id.tvTime, "field 'tvTime'", TextView.class);
    target.tvLine = Utils.findRequiredViewAsType(source, R.id.tvLine, "field 'tvLine'", TextView.class);
    target.tvContent = Utils.findRequiredViewAsType(source, R.id.tvContent, "field 'tvContent'", WebView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SwrlDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
    target.tvTitle = null;
    target.tvTime = null;
    target.tvLine = null;
    target.tvContent = null;
  }
}
