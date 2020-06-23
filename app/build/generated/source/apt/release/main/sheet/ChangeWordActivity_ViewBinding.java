// Generated code from Butter Knife. Do not modify!
package main.sheet;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;
import main.utils.views.Header;

public class ChangeWordActivity_ViewBinding implements Unbinder {
  private ChangeWordActivity target;

  private View view7f090080;

  @UiThread
  public ChangeWordActivity_ViewBinding(ChangeWordActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ChangeWordActivity_ViewBinding(final ChangeWordActivity target, View source) {
    this.target = target;

    View view;
    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
    target.etOldPassWord = Utils.findRequiredViewAsType(source, R.id.etOldPassWord, "field 'etOldPassWord'", EditText.class);
    target.etPassWord = Utils.findRequiredViewAsType(source, R.id.etPassWord, "field 'etPassWord'", EditText.class);
    target.etPassWordAgain = Utils.findRequiredViewAsType(source, R.id.etPassWordAgain, "field 'etPassWordAgain'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn, "field 'btn' and method 'onViewClicked'");
    target.btn = Utils.castView(view, R.id.btn, "field 'btn'", Button.class);
    view7f090080 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ChangeWordActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
    target.etOldPassWord = null;
    target.etPassWord = null;
    target.etPassWordAgain = null;
    target.btn = null;

    view7f090080.setOnClickListener(null);
    view7f090080 = null;
  }
}
