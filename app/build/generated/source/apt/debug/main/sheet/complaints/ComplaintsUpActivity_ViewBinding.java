// Generated code from Butter Knife. Do not modify!
package main.sheet.complaints;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;
import main.utils.views.ForbidEmojiEditText;
import main.utils.views.Header;

public class ComplaintsUpActivity_ViewBinding implements Unbinder {
  private ComplaintsUpActivity target;

  private View view7f090087;

  @UiThread
  public ComplaintsUpActivity_ViewBinding(ComplaintsUpActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ComplaintsUpActivity_ViewBinding(final ComplaintsUpActivity target, View source) {
    this.target = target;

    View view;
    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
    target.etPhone = Utils.findRequiredViewAsType(source, R.id.etPhone, "field 'etPhone'", TextView.class);
    target.etContent = Utils.findRequiredViewAsType(source, R.id.etContent, "field 'etContent'", ForbidEmojiEditText.class);
    view = Utils.findRequiredView(source, R.id.btnUp, "field 'btnUp' and method 'onViewClicked'");
    target.btnUp = Utils.castView(view, R.id.btnUp, "field 'btnUp'", Button.class);
    view7f090087 = view;
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
    ComplaintsUpActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
    target.etPhone = null;
    target.etContent = null;
    target.btnUp = null;

    view7f090087.setOnClickListener(null);
    view7f090087 = null;
  }
}
