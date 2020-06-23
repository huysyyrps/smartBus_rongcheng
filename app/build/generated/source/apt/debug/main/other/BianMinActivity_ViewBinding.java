// Generated code from Butter Knife. Do not modify!
package main.other;

import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;
import main.utils.views.Header;

public class BianMinActivity_ViewBinding implements Unbinder {
  private BianMinActivity target;

  @UiThread
  public BianMinActivity_ViewBinding(BianMinActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BianMinActivity_ViewBinding(BianMinActivity target, View source) {
    this.target = target;

    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
    target.llNoContent = Utils.findRequiredViewAsType(source, R.id.llNoContent, "field 'llNoContent'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BianMinActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
    target.recyclerView = null;
    target.llNoContent = null;
  }
}
