// Generated code from Butter Knife. Do not modify!
package main.login;

import android.view.View;
import android.webkit.WebView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;
import main.utils.views.Header;

public class AgreeActivity_ViewBinding implements Unbinder {
  private AgreeActivity target;

  @UiThread
  public AgreeActivity_ViewBinding(AgreeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AgreeActivity_ViewBinding(AgreeActivity target, View source) {
    this.target = target;

    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
    target.webView = Utils.findRequiredViewAsType(source, R.id.webView, "field 'webView'", WebView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AgreeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
    target.webView = null;
  }
}