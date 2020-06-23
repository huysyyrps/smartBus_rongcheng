// Generated code from Butter Knife. Do not modify!
package main.Charge;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;

public class RidecodeActivity_ViewBinding implements Unbinder {
  private RidecodeActivity target;

  private View view7f090222;

  private View view7f0902d4;

  private View view7f0902d6;

  private View view7f0902d5;

  @UiThread
  public RidecodeActivity_ViewBinding(RidecodeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RidecodeActivity_ViewBinding(final RidecodeActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.iv, "field 'imgCode' and method 'onViewClicked'");
    target.imgCode = Utils.castView(view, R.id.iv, "field 'imgCode'", ImageView.class);
    view7f090222 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.qrcodechong, "field 'qrcodechong' and method 'onViewClicked'");
    target.qrcodechong = Utils.castView(view, R.id.qrcodechong, "field 'qrcodechong'", LinearLayout.class);
    view7f0902d4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.qrcodeyu, "field 'qrcodeyu' and method 'onViewClicked'");
    target.qrcodeyu = Utils.castView(view, R.id.qrcodeyu, "field 'qrcodeyu'", LinearLayout.class);
    view7f0902d6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.qrcodeji, "field 'qrcodeji' and method 'onViewClicked'");
    target.qrcodeji = Utils.castView(view, R.id.qrcodeji, "field 'qrcodeji'", LinearLayout.class);
    view7f0902d5 = view;
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
    RidecodeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgCode = null;
    target.qrcodechong = null;
    target.qrcodeyu = null;
    target.qrcodeji = null;

    view7f090222.setOnClickListener(null);
    view7f090222 = null;
    view7f0902d4.setOnClickListener(null);
    view7f0902d4 = null;
    view7f0902d6.setOnClickListener(null);
    view7f0902d6 = null;
    view7f0902d5.setOnClickListener(null);
    view7f0902d5 = null;
  }
}
