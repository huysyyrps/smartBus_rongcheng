package main.smart.common.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PageIndicator extends LinearLayout
{
  private int mChildCount;
  private Context mContext;
  private int mCurrFocusIndex;
  private int mFocusResId;
  private int mUnFocusResId;

  public PageIndicator(Context paramContext)
  {
    this(paramContext, null);
  }

  public PageIndicator(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
  }

  public void init(int paramInt1, int paramInt2, int paramInt3)
  {
    this.mChildCount = paramInt1;
    this.mFocusResId = paramInt2;
    this.mUnFocusResId = paramInt3;
    removeAllViews();
	int l = 0;
	do
	{
		if (l >= mChildCount)
			return;
		ImageView imageview = new ImageView(mContext);
		imageview.setLayoutParams(new android.widget.LinearLayout.LayoutParams(-2, -2));
		imageview.setPadding(5, 0, 5, 0);
		if (l == mCurrFocusIndex)
			imageview.setImageResource(mFocusResId);
		else
			imageview.setImageResource(mUnFocusResId);
		addView(imageview);
		l++;
	} while (true);
  }

  public void showIndex(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.mChildCount))
      return;
    ((ImageView)getChildAt(paramInt)).setImageResource(this.mFocusResId);
    if ((paramInt != this.mCurrFocusIndex) && (this.mCurrFocusIndex < getChildCount()))
      ((ImageView)getChildAt(this.mCurrFocusIndex)).setImageResource(this.mUnFocusResId);
    this.mCurrFocusIndex = paramInt;
  }

  public void showNext()
  {
    if (this.mCurrFocusIndex >= -1 + this.mChildCount)
      return;
    ((ImageView)getChildAt(1 + this.mCurrFocusIndex)).setImageResource(this.mFocusResId);
    ((ImageView)getChildAt(this.mCurrFocusIndex)).setImageResource(this.mUnFocusResId);
    this.mCurrFocusIndex = (1 + this.mCurrFocusIndex);
  }

  public void showPrevious()
  {
    if (this.mCurrFocusIndex <= 1)
      return;
    ((ImageView)getChildAt(-1 + this.mCurrFocusIndex)).setImageResource(this.mFocusResId);
    ((ImageView)getChildAt(this.mCurrFocusIndex)).setImageResource(this.mUnFocusResId);
    this.mCurrFocusIndex = (-1 + this.mCurrFocusIndex);
  }
}
