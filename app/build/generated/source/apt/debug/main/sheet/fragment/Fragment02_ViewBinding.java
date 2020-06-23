// Generated code from Butter Knife. Do not modify!
package main.sheet.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;

public class Fragment02_ViewBinding implements Unbinder {
  private Fragment02 target;

  @UiThread
  public Fragment02_ViewBinding(Fragment02 target, View source) {
    this.target = target;

    target.imgCode = Utils.findRequiredViewAsType(source, R.id.iv, "field 'imgCode'", ImageView.class);
    target.qrcodechong = Utils.findRequiredViewAsType(source, R.id.qrcodechong, "field 'qrcodechong'", LinearLayout.class);
    target.qrcodeyu = Utils.findRequiredViewAsType(source, R.id.qrcodeyu, "field 'qrcodeyu'", LinearLayout.class);
    target.qrcodeji = Utils.findRequiredViewAsType(source, R.id.qrcodeji, "field 'qrcodeji'", LinearLayout.class);
    target.onLineBtn = Utils.findRequiredViewAsType(source, R.id.onLineBtn, "field 'onLineBtn'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Fragment02 target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgCode = null;
    target.qrcodechong = null;
    target.qrcodeyu = null;
    target.qrcodeji = null;
    target.onLineBtn = null;
  }
}
