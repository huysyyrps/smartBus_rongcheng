package main.wheel.widget;

public abstract interface WheelAdapter
{
  public abstract String getItem(int paramInt);

  public abstract int getItemsCount();

  public abstract int getMaximumLength();
}