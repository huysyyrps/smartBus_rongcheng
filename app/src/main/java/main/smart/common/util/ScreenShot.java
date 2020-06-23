package main.smart.common.util;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

public class ScreenShot
{
  // ERROR //
  public static void savePic(Bitmap paramBitmap, java.lang.String paramString)
  {}

  public static void shoot(Activity paramActivity)
  {
    savePic(takeScreenShot(paramActivity), "sdcard/xx.png");
  }

  public static Bitmap takeScreenShot(Activity paramActivity)
  {
    View localView = paramActivity.getWindow().getDecorView();
    localView.setDrawingCacheEnabled(true);
    localView.buildDrawingCache();
    Bitmap localBitmap1 = localView.getDrawingCache();
    Rect localRect = new Rect();
    paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
    int i = localRect.top;
    System.out.println(i);
    Bitmap localBitmap2 = Bitmap.createBitmap(localBitmap1, 0, i, paramActivity.getWindowManager().getDefaultDisplay().getWidth(), paramActivity.getWindowManager().getDefaultDisplay().getHeight() - i);
    localView.destroyDrawingCache();
    return localBitmap2;
  }
}
