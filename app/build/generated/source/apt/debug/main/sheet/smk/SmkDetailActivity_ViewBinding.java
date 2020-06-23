// Generated code from Butter Knife. Do not modify!
package main.sheet.smk;

import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.baidu.mapapi.map.MapView;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;
import main.utils.views.Header;

public class SmkDetailActivity_ViewBinding implements Unbinder {
  private SmkDetailActivity target;

  @UiThread
  public SmkDetailActivity_ViewBinding(SmkDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SmkDetailActivity_ViewBinding(SmkDetailActivity target, View source) {
    this.target = target;

    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'tvTitle'", TextView.class);
    target.tvAddress = Utils.findRequiredViewAsType(source, R.id.tvAddress, "field 'tvAddress'", TextView.class);
    target.webView = Utils.findRequiredViewAsType(source, R.id.webView, "field 'webView'", WebView.class);
    target.mapView = Utils.findRequiredViewAsType(source, R.id.mapView, "field 'mapView'", MapView.class);
    target.tvPhone = Utils.findRequiredViewAsType(source, R.id.tvPhone, "field 'tvPhone'", TextView.class);
    target.tvDetail = Utils.findRequiredViewAsType(source, R.id.tvDetail, "field 'tvDetail'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SmkDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
    target.tvTitle = null;
    target.tvAddress = null;
    target.webView = null;
    target.mapView = null;
    target.tvPhone = null;
    target.tvDetail = null;
  }
}
