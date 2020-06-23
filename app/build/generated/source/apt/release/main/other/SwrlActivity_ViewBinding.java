// Generated code from Butter Knife. Do not modify!
package main.other;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;
import main.utils.views.Header;

public class SwrlActivity_ViewBinding implements Unbinder {
  private SwrlActivity target;

  @UiThread
  public SwrlActivity_ViewBinding(SwrlActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SwrlActivity_ViewBinding(SwrlActivity target, View source) {
    this.target = target;

    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
    target.etTitle = Utils.findRequiredViewAsType(source, R.id.etTitle, "field 'etTitle'", EditText.class);
    target.tvTime = Utils.findRequiredViewAsType(source, R.id.tvTime, "field 'tvTime'", TextView.class);
    target.etLine = Utils.findRequiredViewAsType(source, R.id.etLine, "field 'etLine'", EditText.class);
    target.etContent = Utils.findRequiredViewAsType(source, R.id.etContent, "field 'etContent'", EditText.class);
    target.imAdd01 = Utils.findRequiredViewAsType(source, R.id.imAdd01, "field 'imAdd01'", ImageView.class);
    target.imAdd02 = Utils.findRequiredViewAsType(source, R.id.imAdd02, "field 'imAdd02'", ImageView.class);
    target.imAdd03 = Utils.findRequiredViewAsType(source, R.id.imAdd03, "field 'imAdd03'", ImageView.class);
    target.btnFirst = Utils.findRequiredViewAsType(source, R.id.btnUp, "field 'btnFirst'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SwrlActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
    target.etTitle = null;
    target.tvTime = null;
    target.etLine = null;
    target.etContent = null;
    target.imAdd01 = null;
    target.imAdd02 = null;
    target.imAdd03 = null;
    target.btnFirst = null;
  }
}
