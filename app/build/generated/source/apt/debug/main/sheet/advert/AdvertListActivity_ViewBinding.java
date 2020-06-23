// Generated code from Butter Knife. Do not modify!
package main.sheet.advert;

import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.refreshview.CustomRefreshView;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;
import main.utils.views.Header;

public class AdvertListActivity_ViewBinding implements Unbinder {
  private AdvertListActivity target;

  @UiThread
  public AdvertListActivity_ViewBinding(AdvertListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AdvertListActivity_ViewBinding(AdvertListActivity target, View source) {
    this.target = target;

    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", CustomRefreshView.class);
    target.llNoContent = Utils.findRequiredViewAsType(source, R.id.liContent1, "field 'llNoContent'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AdvertListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
    target.recyclerView = null;
    target.llNoContent = null;
  }
}
