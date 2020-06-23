// Generated code from Butter Knife. Do not modify!
package main.smart.bus.activity;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;
import main.utils.views.Header;

public class BusStationSearchActivity_ViewBinding implements Unbinder {
  private BusStationSearchActivity target;

  @UiThread
  public BusStationSearchActivity_ViewBinding(BusStationSearchActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BusStationSearchActivity_ViewBinding(BusStationSearchActivity target, View source) {
    this.target = target;

    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BusStationSearchActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
  }
}
