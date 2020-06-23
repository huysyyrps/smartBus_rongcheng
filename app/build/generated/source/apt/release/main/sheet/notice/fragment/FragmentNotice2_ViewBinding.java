// Generated code from Butter Knife. Do not modify!
package main.sheet.notice.fragment;

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

public class FragmentNotice2_ViewBinding implements Unbinder {
  private FragmentNotice2 target;

  @UiThread
  public FragmentNotice2_ViewBinding(FragmentNotice2 target, View source) {
    this.target = target;

    target.llNoContent = Utils.findRequiredViewAsType(source, R.id.liContent1, "field 'llNoContent'", LinearLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", CustomRefreshView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    FragmentNotice2 target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.llNoContent = null;
    target.recyclerView = null;
  }
}
