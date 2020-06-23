// Generated code from Butter Knife. Do not modify!
package main.other;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;
import main.utils.views.Header;

public class ShengHuoDetailActivity_ViewBinding implements Unbinder {
  private ShengHuoDetailActivity target;

  @UiThread
  public ShengHuoDetailActivity_ViewBinding(ShengHuoDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ShengHuoDetailActivity_ViewBinding(ShengHuoDetailActivity target, View source) {
    this.target = target;

    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'tvTitle'", TextView.class);
    target.tvTime = Utils.findRequiredViewAsType(source, R.id.tvTime, "field 'tvTime'", TextView.class);
    target.tvContent = Utils.findRequiredViewAsType(source, R.id.tvContent, "field 'tvContent'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ShengHuoDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
    target.tvTitle = null;
    target.tvTime = null;
    target.tvContent = null;
  }
}
