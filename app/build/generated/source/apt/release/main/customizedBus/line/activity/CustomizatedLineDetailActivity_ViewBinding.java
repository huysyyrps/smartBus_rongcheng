// Generated code from Butter Knife. Do not modify!
package main.customizedBus.line.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.baidu.mapapi.map.MapView;
import java.lang.IllegalStateException;
import java.lang.Override;
import main.smart.rcgj.R;

public class CustomizatedLineDetailActivity_ViewBinding implements Unbinder {
  private CustomizatedLineDetailActivity target;

  @UiThread
  public CustomizatedLineDetailActivity_ViewBinding(CustomizatedLineDetailActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CustomizatedLineDetailActivity_ViewBinding(CustomizatedLineDetailActivity target,
      View source) {
    this.target = target;

    target.baiduMapView = Utils.findRequiredViewAsType(source, R.id.id_baidu_map_view, "field 'baiduMapView'", MapView.class);
    target.buttonBuy = Utils.findRequiredViewAsType(source, R.id.id_button_buy, "field 'buttonBuy'", Button.class);
    target.remarkedTv = Utils.findRequiredViewAsType(source, R.id.id_remarked_tv, "field 'remarkedTv'", TextView.class);
    target.startStationTV = Utils.findRequiredViewAsType(source, R.id.id_satartstation_tv, "field 'startStationTV'", TextView.class);
    target.endStationTV = Utils.findRequiredViewAsType(source, R.id.id_endstation_tv, "field 'endStationTV'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CustomizatedLineDetailActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.baiduMapView = null;
    target.buttonBuy = null;
    target.remarkedTv = null;
    target.startStationTV = null;
    target.endStationTV = null;
  }
}
