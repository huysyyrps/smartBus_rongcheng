// Generated code from Butter Knife. Do not modify!
package main.sheet.complaints;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;
import main.utils.views.Header;

public class ComplaintsDetailActivity_ViewBinding implements Unbinder {
  private ComplaintsDetailActivity target;

  @UiThread
  public ComplaintsDetailActivity_ViewBinding(ComplaintsDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ComplaintsDetailActivity_ViewBinding(ComplaintsDetailActivity target, View source) {
    this.target = target;

    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'tvTitle'", TextView.class);
    target.tvTime = Utils.findRequiredViewAsType(source, R.id.tvTime, "field 'tvTime'", TextView.class);
    target.tvContent = Utils.findRequiredViewAsType(source, R.id.tvContent, "field 'tvContent'", TextView.class);
    target.tvBack = Utils.findRequiredViewAsType(source, R.id.tvBack, "field 'tvBack'", TextView.class);
    target.tvPJ = Utils.findRequiredViewAsType(source, R.id.tvPJ, "field 'tvPJ'", TextView.class);
    target.ll = Utils.findRequiredViewAsType(source, R.id.ll, "field 'll'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ComplaintsDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
    target.tvTitle = null;
    target.tvTime = null;
    target.tvContent = null;
    target.tvBack = null;
    target.tvPJ = null;
    target.ll = null;
  }
}
