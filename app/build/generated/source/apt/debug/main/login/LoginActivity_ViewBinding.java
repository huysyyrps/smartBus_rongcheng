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

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view7f09008b;

  private View view7f0903c2;

  private View view7f0903c6;

  private View view7f0903b0;

  private View view7f0903b1;

  private View view7f090224;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
    target.etUserName = Utils.findRequiredViewAsType(source, R.id.etUserName, "field 'etUserName'", EditText.class);
    target.etPassWord = Utils.findRequiredViewAsType(source, R.id.etPassWord, "field 'etPassWord'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_login, "field 'btnLogin' and method 'onViewClicked'");
    target.btnLogin = Utils.castView(view, R.id.btn_login, "field 'btnLogin'", Button.class);
    view7f09008b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tvForgrtPassword, "field 'tvForgrtPassword' and method 'onViewClicked'");
    target.tvForgrtPassword = Utils.castView(view, R.id.tvForgrtPassword, "field 'tvForgrtPassword'", TextView.class);
    view7f0903c2 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tvRegister, "field 'tvRegister' and method 'onViewClicked'");
    target.tvRegister = Utils.castView(view, R.id.tvRegister, "field 'tvRegister'", TextView.class);
    view7f0903c6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv1, "field 'tv1' and method 'onViewClicked'");
    target.tv1 = Utils.castView(view, R.id.tv1, "field 'tv1'", TextView.class);
    view7f0903b0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv2, "field 'tv2' and method 'onViewClicked'");
    target.tv2 = Utils.castView(view, R.id.tv2, "field 'tv2'", TextView.class);
    view7f0903b1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ivSeeNewPw, "field 'ivSeeNewPw' and method 'onViewClicked'");
    target.ivSeeNewPw = Utils.castView(view, R.id.ivSeeNewPw, "field 'ivSeeNewPw'", ImageView.class);
    view7f090224 = view;
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
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
    target.etUserName = null;
    target.etPassWord = null;
    target.btnLogin = null;
    target.tvForgrtPassword = null;
    target.tvRegister = null;
    target.tv1 = null;
    target.tv2 = null;
    target.ivSeeNewPw = null;

    view7f09008b.setOnClickListener(null);
    view7f09008b = null;
    view7f0903c2.setOnClickListener(null);
    view7f0903c2 = null;
    view7f0903c6.setOnClickListener(null);
    view7f0903c6 = null;
    view7f0903b0.setOnClickListener(null);
    view7f0903b0 = null;
    view7f0903b1.setOnClickListener(null);
    view7f0903b1 = null;
    view7f090224.setOnClickListener(null);
    view7f090224 = null;
  }
}
