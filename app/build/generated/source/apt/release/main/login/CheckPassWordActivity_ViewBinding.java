// Generated code from Butter Knife. Do not modify!
package main.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;
import main.utils.views.Header;

public class CheckPassWordActivity_ViewBinding implements Unbinder {
  private CheckPassWordActivity target;

  private View view7f0903ba;

  private View view7f090225;

  private View view7f090087;

  @UiThread
  public CheckPassWordActivity_ViewBinding(CheckPassWordActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CheckPassWordActivity_ViewBinding(final CheckPassWordActivity target, View source) {
    this.target = target;

    View view;
    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
    target.etPhone = Utils.findRequiredViewAsType(source, R.id.etPhone, "field 'etPhone'", EditText.class);
    target.etCode = Utils.findRequiredViewAsType(source, R.id.etCode, "field 'etCode'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tvCode, "field 'tvCode' and method 'onViewClicked'");
    target.tvCode = Utils.castView(view, R.id.tvCode, "field 'tvCode'", TextView.class);
    view7f0903ba = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.etPassWordAgin = Utils.findRequiredViewAsType(source, R.id.etPassWordAgin, "field 'etPassWordAgin'", EditText.class);
    view = Utils.findRequiredView(source, R.id.ivSeeNewPwAgain, "field 'ivSeeNewPwAgain' and method 'onViewClicked'");
    target.ivSeeNewPwAgain = Utils.castView(view, R.id.ivSeeNewPwAgain, "field 'ivSeeNewPwAgain'", ImageView.class);
    view7f090225 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnUp, "field 'btnUp' and method 'onViewClicked'");
    target.btnUp = Utils.castView(view, R.id.btnUp, "field 'btnUp'", Button.class);
    view7f090087 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    CheckPassWordActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
    target.etPhone = null;
    target.etCode = null;
    target.tvCode = null;
    target.etPassWordAgin = null;
    target.ivSeeNewPwAgain = null;
    target.btnUp = null;

    view7f0903ba.setOnClickListener(null);
    view7f0903ba = null;
    view7f090225.setOnClickListener(null);
    view7f090225 = null;
    view7f090087.setOnClickListener(null);
    view7f090087 = null;
  }
}
