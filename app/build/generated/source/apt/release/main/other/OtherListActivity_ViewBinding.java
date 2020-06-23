// Generated code from Butter Knife. Do not modify!
package main.other;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;
import main.utils.views.Header;

public class OtherListActivity_ViewBinding implements Unbinder {
  private OtherListActivity target;

  @UiThread
  public OtherListActivity_ViewBinding(OtherListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OtherListActivity_ViewBinding(OtherListActivity target, View source) {
    this.target = target;

    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OtherListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
    target.recyclerView = null;
  }
}
