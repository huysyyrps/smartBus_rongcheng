// Generated code from Butter Knife. Do not modify!
package main.sheet.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;

public class Fragment03_ViewBinding implements Unbinder {
  private Fragment03 target;

  @UiThread
  public Fragment03_ViewBinding(Fragment03 target, View source) {
    this.target = target;

    target.wallet = Utils.findRequiredViewAsType(source, R.id.wallet, "field 'wallet'", LinearLayout.class);
    target.Account = Utils.findRequiredViewAsType(source, R.id.me_Account, "field 'Account'", LinearLayout.class);
    target.transaction = Utils.findRequiredViewAsType(source, R.id.transaction, "field 'transaction'", LinearLayout.class);
    target.wangji = Utils.findRequiredViewAsType(source, R.id.wangji, "field 'wangji'", LinearLayout.class);
    target.tv_nickname = Utils.findRequiredViewAsType(source, R.id.tv_nickname, "field 'tv_nickname'", TextView.class);
    target.methreemoney = Utils.findRequiredViewAsType(source, R.id.methreemoney, "field 'methreemoney'", TextView.class);
    target.llSharde = Utils.findRequiredViewAsType(source, R.id.llSharde, "field 'llSharde'", LinearLayout.class);
    target.tixing = Utils.findRequiredViewAsType(source, R.id.tixing, "field 'tixing'", LinearLayout.class);
    target.llGuanYu = Utils.findRequiredViewAsType(source, R.id.llGuanYu, "field 'llGuanYu'", LinearLayout.class);
    target.llShengJi = Utils.findRequiredViewAsType(source, R.id.llShengJi, "field 'llShengJi'", LinearLayout.class);
    target.scrollView = Utils.findRequiredViewAsType(source, R.id.scrollView, "field 'scrollView'", ScrollView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Fragment03 target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.wallet = null;
    target.Account = null;
    target.transaction = null;
    target.wangji = null;
    target.tv_nickname = null;
    target.methreemoney = null;
    target.llSharde = null;
    target.tixing = null;
    target.llGuanYu = null;
    target.llShengJi = null;
    target.scrollView = null;
  }
}
