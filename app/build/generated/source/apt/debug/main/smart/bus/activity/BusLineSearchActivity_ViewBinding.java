// Generated code from Butter Knife. Do not modify!
package main.smart.bus.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;
import main.utils.views.Header;

public class BusLineSearchActivity_ViewBinding implements Unbinder {
  private BusLineSearchActivity target;

  private View view7f0903b9;

  @UiThread
  public BusLineSearchActivity_ViewBinding(BusLineSearchActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BusLineSearchActivity_ViewBinding(final BusLineSearchActivity target, View source) {
    this.target = target;

    View view;
    target.header = Utils.findRequiredViewAsType(source, R.id.header, "field 'header'", Header.class);
    target.etSearch = Utils.findRequiredViewAsType(source, R.id.etSearch, "field 'etSearch'", EditText.class);
    view = Utils.findRequiredView(source, R.id.tvCleam, "field 'tvCleam' and method 'onViewClicked'");
    target.tvCleam = Utils.castView(view, R.id.tvCleam, "field 'tvCleam'", TextView.class);
    view7f0903b9 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.rb1 = Utils.findRequiredViewAsType(source, R.id.rb1, "field 'rb1'", RadioButton.class);
    target.rb2 = Utils.findRequiredViewAsType(source, R.id.rb2, "field 'rb2'", RadioButton.class);
    target.rb3 = Utils.findRequiredViewAsType(source, R.id.rb3, "field 'rb3'", RadioButton.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BusLineSearchActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.header = null;
    target.etSearch = null;
    target.tvCleam = null;
    target.rb1 = null;
    target.rb2 = null;
    target.rb3 = null;
    target.recyclerView = null;

    view7f0903b9.setOnClickListener(null);
    view7f0903b9 = null;
  }
}
