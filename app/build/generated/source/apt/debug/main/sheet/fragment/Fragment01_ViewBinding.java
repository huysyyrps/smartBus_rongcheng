// Generated code from Butter Knife. Do not modify!
package main.sheet.fragment;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.youth.banner.Banner;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;

public class Fragment01_ViewBinding implements Unbinder {
  private Fragment01 target;

  @UiThread
  public Fragment01_ViewBinding(Fragment01 target, View source) {
    this.target = target;

    target.banner = Utils.findRequiredViewAsType(source, R.id.banner, "field 'banner'", Banner.class);
    target.recyclerViewAdvert = Utils.findRequiredViewAsType(source, R.id.recyclerViewAdvert, "field 'recyclerViewAdvert'", RecyclerView.class);
    target.tvVersion = Utils.findRequiredViewAsType(source, R.id.tvVersion, "field 'tvVersion'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Fragment01 target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.banner = null;
    target.recyclerViewAdvert = null;
    target.tvVersion = null;
  }
}
