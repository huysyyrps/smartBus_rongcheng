package main.wheel.widget.adapters;

import main.wheel.widget.WheelView;

public abstract interface OnWheelScrollListener
{
  public abstract void onScrollingFinished(WheelView paramWheelView);

  public abstract void onScrollingStarted(WheelView paramWheelView);
}
