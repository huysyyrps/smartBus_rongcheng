// Generated code from Butter Knife. Do not modify!
package main.sheet.notice;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;
import main.utils.views.Header;

public class NoticeListActivity_ViewBinding implements Unbinder {
  private NoticeListActivity target;

  @UiThread
  public NoticeListActivity_ViewBinding(NoticeListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NoticeListActivity_ViewBinding(NoticeListActivity target, View source) {
    this.target = target;

    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
    target.rb1 = Utils.findRequiredViewAsType(source, R.id.rb1, "field 'rb1'", RadioButton.class);
    target.rb2 = Utils.findRequiredViewAsType(source, R.id.rb2, "field 'rb2'", RadioButton.class);
    target.radioGroup = Utils.findRequiredViewAsType(source, R.id.radio_group, "field 'radioGroup'", RadioGroup.class);
    target.frameLayout = Utils.findRequiredViewAsType(source, R.id.frame_layout, "field 'frameLayout'", FrameLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NoticeListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
    target.rb1 = null;
    target.rb2 = null;
    target.radioGroup = null;
    target.frameLayout = null;
  }
}
