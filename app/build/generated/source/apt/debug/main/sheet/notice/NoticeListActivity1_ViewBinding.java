// Generated code from Butter Knife. Do not modify!
package main.sheet.notice;

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

public class NoticeListActivity1_ViewBinding implements Unbinder {
  private NoticeListActivity1 target;

  @UiThread
  public NoticeListActivity1_ViewBinding(NoticeListActivity1 target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NoticeListActivity1_ViewBinding(NoticeListActivity1 target, View source) {
    this.target = target;

    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
    target.llNoContent = Utils.findRequiredViewAsType(source, R.id.llNoContent, "field 'llNoContent'", LinearLayout.class);
    target.llNocontent = Utils.findRequiredViewAsType(source, R.id.liContent1, "field 'llNocontent'", LinearLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", CustomRefreshView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NoticeListActivity1 target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
    target.llNoContent = null;
    target.llNocontent = null;
    target.recyclerView = null;
  }
}
