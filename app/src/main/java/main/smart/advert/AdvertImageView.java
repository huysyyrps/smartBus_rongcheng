package main.smart.advert;

import main.smart.common.util.ScreenObserver;

import android.content.Context;

/**
 * 初始化界面
 * 
 * */
public class AdvertImageView extends AdvertImageBase
  implements ScreenObserver.ScreenStateListener
{
  protected int mLevel;

  public AdvertImageView(Context paramContext, int paramInt1, int paramInt2)
  {
    super(paramContext, paramInt2);
    this.mLevel = paramInt1;
   // initAdvertInfo();
    initAdvertView(paramContext);
  }
/*
  public AdvertImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.AdvertImageView);
    this.mLevel = localTypedArray.getInt(0, 0);
    this.mDefaultImageRes = localTypedArray.getResourceId(1, 0);
    localTypedArray.recycle();
    initAdvertInfo();
    initAdvertView(paramContext);
  }

  protected void initAdvertInfo()
  {
    List localList = AdvertManager.queryAdvertRandom(this.mLevel, 1L);
    if (localList.size() <= 0)
      return;
    this.mAdvert = ((AdvertInfo)localList.get(0));
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    ScreenObserver.requestScreenStateUpdate(this);
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    ScreenObserver.removeScreenStateUpdate(this);
    if (this.mClickInfos.size() > 0)
      AdvertManager.uploadAdvertClickStats(this.mClickInfos, null);
    if (this.mBrowseInfos.size() <= 0)
      return;
    AdvertManager.uploadAdvertBrowseStats(this.mBrowseInfos, null);
  }
*/
  public void onScreenOff()
  {
    if (getWindowVisibility() != 0)
      return;
    onAdvertOff();
  }

  public void onScreenOn()
  {
    if (getWindowVisibility() != 0)
      return;
    onAdvertOn();
  }

  protected void onWindowVisibilityChanged(int paramInt)
  {
    super.onWindowVisibilityChanged(paramInt);
    if (paramInt == 0)
    {
      onAdvertOn();
      return;
    }
    onAdvertOff();
  }
}