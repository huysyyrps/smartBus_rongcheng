package main.wheel.widget.adapters;

import android.content.Context;

public class NumericWheelAdapter extends AbstractWheelTextAdapter
{
  public static final int DEFAULT_MAX_VALUE = 9;
  private static final int DEFAULT_MIN_VALUE =0;
  private String format;
  private int maxValue;
  private int minValue;

  public NumericWheelAdapter(Context paramContext)
  {
    this(paramContext, 0, 9);
  }

  public NumericWheelAdapter(Context paramContext, int paramInt1, int paramInt2)
  {
    this(paramContext, paramInt1, paramInt2, null);
  }

  public NumericWheelAdapter(Context paramContext, int paramInt1, int paramInt2, String paramString)
  {
    super(paramContext);
    this.minValue = paramInt1;
    this.maxValue = paramInt2;
    this.format = paramString;
  }

  public CharSequence getItemText(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < getItemsCount()))
    {
      int i = paramInt + this.minValue;
      if (this.format != null)
      {
        String str = this.format;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(i);
        return String.format(str, arrayOfObject);
      }
      return Integer.toString(i);
    }
    return null;
  }

  public int getItemsCount()
  {
    return (1 + this.maxValue - this.minValue);
  }
}