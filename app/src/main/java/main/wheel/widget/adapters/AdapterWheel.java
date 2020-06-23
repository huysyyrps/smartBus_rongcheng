package main.wheel.widget.adapters;

import main.wheel.widget.WheelAdapter;
import android.content.Context;

public class AdapterWheel extends AbstractWheelTextAdapter
{
  private WheelAdapter adapter;

  public AdapterWheel(Context paramContext, WheelAdapter paramWheelAdapter)
  {
    super(paramContext);
    this.adapter = paramWheelAdapter;
  }

  public WheelAdapter getAdapter()
  {
    return this.adapter;
  }

  protected CharSequence getItemText(int paramInt)
  {
    return this.adapter.getItem(paramInt);
  }

  public int getItemsCount()
  {
    return this.adapter.getItemsCount();
  }
}
