package main.wheel.widget;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import main.smart.rcgj.R;
import main.wheel.widget.adapters.WheelViewAdapter;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

public class WheelView extends View
{
  private static final int DEF_VISIBLE_ITEMS = 5;
  private static final int ITEM_OFFSET_PERCENT = 10;
  private static final int PADDING = 10;
	private static final int SHADOWS_COLORS[] = {
		0xff111111, 0xaaaaaa, 0xaaaaaa
	};
  private GradientDrawable bottomShadow;
  private Drawable centerDrawable;
  private List<OnWheelChangedListener> changingListeners = new LinkedList();
  private List<OnWheelClickedListener> clickingListeners = new LinkedList();
  private int currentItem = 0;
  private int firstItem;
  boolean isCyclic = false;
  private boolean isScrollingPerformed;
  private int itemHeight = 0;
  private LinearLayout itemsLayout;
  private WheelRecycle recycle  ;
  private WheelScroller scroller;
  private DataSetObserver dataObserver = new DataSetObserver()
  {
    public void onChanged()
    {
      WheelView.this.invalidateWheel(false);
    }

    public void onInvalidated()
    {
      WheelView.this.invalidateWheel(true);
    }
  };

  WheelScroller.ScrollingListener scrollingListener = new WheelScroller.ScrollingListener()
  {
    public void onFinished()
    {
      if (WheelView.this.isScrollingPerformed)
      {
        WheelView.this.notifyScrollingListenersAboutEnd();
        WheelView.this.isScrollingPerformed = false;
      }
      WheelView.this.scrollingOffset = 0;
      WheelView.this.invalidate();
    }

    public void onJustify()
    {
      if (Math.abs(WheelView.this.scrollingOffset) <= 1)
        return;
      WheelView.this.scroller.scroll(WheelView.this.scrollingOffset, 0);
    }

    public void onScroll(int i)
    {
    	doScroll(i);
		int j = getHeight();
		if (scrollingOffset > j)
		{
			scrollingOffset = j;
			scroller.stopScrolling();
		} else
		if (scrollingOffset < -j)
		{
			scrollingOffset = -j;
			scroller.stopScrolling();
			return;
		}
    }

    public void onStarted()
    {
      WheelView.this.isScrollingPerformed = true;
      WheelView.this.notifyScrollingListenersAboutStart();
    }
  };
  private List<OnWheelScrollListener> scrollingListeners = new LinkedList();
  private int scrollingOffset;
  private GradientDrawable topShadow;
  private WheelViewAdapter viewAdapter;
  private int visibleItems = 5;

  public WheelView(Context paramContext)
  {
    super(paramContext);
    initData(paramContext);
  }

  public WheelView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initData(paramContext);
  }

  public WheelView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    initData(paramContext);
  }

	private boolean addViewItem(int i, boolean flag)
	{
		View view = getItemView(i);
		boolean flag1 = false;
		if (view != null)
		{
			if (flag)
				itemsLayout.addView(view, 0);
			else
				itemsLayout.addView(view);
			flag1 = true;
		}
		return flag1;
	}

	private void buildViewForMeasuring()
	{
		int i;
		int j;
		if (itemsLayout != null)
			recycle.recycleItems(itemsLayout, firstItem, new ItemsRange());
		else
			createItemsLayout();
		i = visibleItems / 2;
		j = i + currentItem;
		do
		{
			if (j < currentItem - i)
				return;
			if (addViewItem(j, true))
				firstItem = j;
			j--;
		} while (true);
	}

  private int calculateLayoutWidth(int paramInt1, int paramInt2)
  {
    initResourcesIfNecessary();
    this.itemsLayout.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
    this.itemsLayout.measure(View.MeasureSpec.makeMeasureSpec(paramInt1, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
    int i = this.itemsLayout.getMeasuredWidth();
    int l=0;
    if (paramInt2 == 0x40000000){
    	 l=paramInt1;
    }else{
    	l = Math.max(i + 20, getSuggestedMinimumWidth());
		if (paramInt2 == 0x80000000 && i < l){
			l = i;}
		else{
			 this.itemsLayout.measure(View.MeasureSpec.makeMeasureSpec(l - 20, 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
		       
		        l = Math.max(i + 20, getSuggestedMinimumWidth());
		}
    }
      return l;
  }

  private void createItemsLayout()
  {
    if (this.itemsLayout != null)
      return;
    this.itemsLayout = new LinearLayout(getContext());
    this.itemsLayout.setOrientation(1);
  }

  private void doScroll(int i)
  {
		int k;
		int l;
		int i1;
		int j1;
		scrollingOffset = i + scrollingOffset;
		int j = getItemHeight();
		k = scrollingOffset / j;
		l = currentItem - k;
		i1 = viewAdapter.getItemsCount();
		j1 = scrollingOffset % j;
		if (Math.abs(j1) <= j / 2)
			j1 = 0;
		
			 
		int k1;
		if (j1 > 0)
		{
			l--;
			k++;
		} else
		if (j1 < 0)
		{
			l++;
			k--;
		}
		if(l<0){
			l += i1;
		}else{
			l %= i1;
		}
		while(l<0){
			l += i1;
		}
		l %= i1;
		if (l < 0)
		{
			k = currentItem;
			l = 0;
		} else
		if (l >= i1)
		{
			k = 1 + (currentItem - i1);
			l = i1 - 1;
		} else
		if (l > 0 && j1 > 0)
		{
			l--;
			k++;
		} else
		if (l < i1 - 1 && j1 < 0)
		{
			l++;
			k--;
		}
		k1 = scrollingOffset;
		if (l != currentItem)
			setCurrentItem(l, false);
		else
			invalidate();
		scrollingOffset = k1 - k * j;
		if (scrollingOffset > getHeight())
			scrollingOffset = scrollingOffset % getHeight() + getHeight();
		return;
		//--------------------------------
 
  }

  private void drawCenterRect(Canvas paramCanvas)
  {
    int i = getHeight() / 2;
    int j = (int)(1.2D * getItemHeight() / 2);
    this.centerDrawable.setBounds(0, i - j, getWidth(), i + j);
    this.centerDrawable.draw(paramCanvas);
  }

  private void drawItems(Canvas paramCanvas)
  {
    paramCanvas.save();
    paramCanvas.translate(10.0F, -((this.currentItem - this.firstItem) * getItemHeight() + (getItemHeight() - getHeight()) / 2) + this.scrollingOffset);
    this.itemsLayout.draw(paramCanvas);
    paramCanvas.restore();
  }

  private void drawShadows(Canvas paramCanvas)
  {
    int i = (int)(1.5D * getItemHeight());
    this.topShadow.setBounds(0, 0, getWidth(), i);
    this.topShadow.draw(paramCanvas);
    this.bottomShadow.setBounds(0, getHeight() - i, getWidth(), getHeight());
    this.bottomShadow.draw(paramCanvas);
  }

  private int getDesiredHeight(LinearLayout paramLinearLayout)
  {
    if ((paramLinearLayout != null) && (paramLinearLayout.getChildAt(0) != null))
      this.itemHeight = paramLinearLayout.getChildAt(0).getMeasuredHeight();
    return Math.max(this.itemHeight * this.visibleItems - (10 * this.itemHeight / 50), getSuggestedMinimumHeight());
  }

  private int getItemHeight()
  {
    if (this.itemHeight != 0)
      return this.itemHeight;
    if ((this.itemsLayout != null) && (this.itemsLayout.getChildAt(0) != null))
    {
      this.itemHeight = this.itemsLayout.getChildAt(0).getHeight();
      return this.itemHeight;
    }
    return (getHeight() / this.visibleItems);
  }

  private View getItemView(int paramInt)
  {
    if ((this.viewAdapter == null) || (this.viewAdapter.getItemsCount() == 0))
      return null;
    int i = this.viewAdapter.getItemsCount();
    if (!(isValidItemIndex(paramInt)))
      return this.viewAdapter.getEmptyItem(this.recycle.getEmptyItem(), this.itemsLayout);
    do
      paramInt += i;
    while (paramInt < 0);
    int j = paramInt % i;
    return this.viewAdapter.getItem(j, this.recycle.getItem(), this.itemsLayout);
  }

  private ItemsRange getItemsRange()
  {
    if (getItemHeight() == 0)
      return null;
    int i = this.currentItem;
    for (int j = 1; ; j += 2)
    {
      if (j * getItemHeight() >= getHeight())
      {
        if (this.scrollingOffset != 0)
        {
          if (this.scrollingOffset > 0)
            --i;
          int k = j + 1;
          int l = this.scrollingOffset / getItemHeight();
          i -= l;
          j = (int)(k + Math.asin(l));
        }
        return new ItemsRange(i, j);
      }
      --i;
    }
  }

  private void initData(Context paramContext)
  {
    this.scroller = new WheelScroller(getContext(), this.scrollingListener);
  }

  private void initResourcesIfNecessary()
  {
    if (this.centerDrawable == null)
      this.centerDrawable = getContext().getResources().getDrawable(R.drawable.wheel_val);
    if (this.topShadow == null)
      this.topShadow = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, SHADOWS_COLORS);
    if (this.bottomShadow == null)
      this.bottomShadow = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, SHADOWS_COLORS);
    setBackgroundResource(R.drawable.wheel_bg);
  }

  private boolean isValidItemIndex(int paramInt)
  {
    return ((this.viewAdapter == null) || (this.viewAdapter.getItemsCount() <= 0) || ((!(this.isCyclic)) && (((paramInt < 0) || (paramInt >= this.viewAdapter.getItemsCount())))));
  }

  private void layout(int paramInt1, int paramInt2)
  {
    int i = paramInt1 - 20;
    this.itemsLayout.layout(0, 0, i, paramInt2);
  }

  private boolean rebuildItems()
  {
		int i;
		int j;
		ItemsRange itemsrange = getItemsRange();
		boolean flag;
		if (itemsLayout != null)
		{
			int l = recycle.recycleItems(itemsLayout, firstItem, itemsrange);
			if (firstItem != l)
				flag = true;
			else
				flag = false;
			firstItem = l;
		} else
		{
			createItemsLayout();
			flag = true;
		}
		if (!flag)
			if (firstItem == itemsrange.getFirst() && itemsLayout.getChildCount() == itemsrange.getCount())
				flag = false;
			else
				flag = true;
		if (firstItem > itemsrange.getFirst() && firstItem <= itemsrange.getLast())
		{
			int k = -1 + firstItem;
			while (k >= itemsrange.getFirst() && addViewItem(k, true)) 
			{
				firstItem = k;
				k--;
			}
		} else
		{
			firstItem = itemsrange.getFirst();
		}
		i = firstItem;
		j = itemsLayout.getChildCount();
 
		try {
			if (j >= itemsrange.getCount())
			{
				firstItem = i;
				return flag;
			}

			if (!addViewItem(j + firstItem, false) && itemsLayout.getChildCount() == 0)
				i++;
			j++;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 return true;
  }

  private void updateView()
  {
    if (!(rebuildItems()))
      return;
    calculateLayoutWidth(getWidth(), 1073741824);
    layout(getWidth(), getHeight());
  }

  public void addChangingListener(OnWheelChangedListener paramOnWheelChangedListener)
  {
    this.changingListeners.add(paramOnWheelChangedListener);
  }

  public void addClickingListener(OnWheelClickedListener paramOnWheelClickedListener)
  {
    this.clickingListeners.add(paramOnWheelClickedListener);
  }

  public void addScrollingListener(OnWheelScrollListener paramOnWheelScrollListener)
  {
    this.scrollingListeners.add(paramOnWheelScrollListener);
  }

  public int getCurrentItem()
  {
    return this.currentItem;
  }

  public WheelViewAdapter getViewAdapter()
  {
    return this.viewAdapter;
  }

  public int getVisibleItems()
  {
    return this.visibleItems;
  }

  public void invalidateWheel(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.recycle.clearAll();
      if (this.itemsLayout != null)
        this.itemsLayout.removeAllViews();
      this.scrollingOffset = 0;
    }
    while (true)
    {
      
      if (this.itemsLayout == null)
        continue;
      invalidate();
      
      this.recycle.recycleItems(this.itemsLayout, this.firstItem, new ItemsRange());
    }
  }

  public boolean isCyclic()
  {
    return this.isCyclic;
  }

  protected void notifyChangingListeners(int paramInt1, int paramInt2)
  {
    Iterator localIterator = this.changingListeners.iterator();
    while (true)
    {
      if (!(localIterator.hasNext()))
        return;
      ((OnWheelChangedListener)localIterator.next()).onChanged(this, paramInt1, paramInt2);
    }
  }

  protected void notifyClickListenersAboutClick(int paramInt)
  {
    Iterator localIterator = this.clickingListeners.iterator();
    while (true)
    {
      if (!(localIterator.hasNext()))
        return;
      ((OnWheelClickedListener)localIterator.next()).onItemClicked(this, paramInt);
    }
  }

  protected void notifyScrollingListenersAboutEnd()
  {
    Iterator localIterator = this.scrollingListeners.iterator();
    while (true)
    {
      if (!(localIterator.hasNext()))
        return;
      ((OnWheelScrollListener)localIterator.next()).onScrollingFinished(this);
    }
  }

  protected void notifyScrollingListenersAboutStart()
  {
    Iterator localIterator = this.scrollingListeners.iterator();
    while (true)
    {
      if (!(localIterator.hasNext()))
        return;
      ((OnWheelScrollListener)localIterator.next()).onScrollingStarted(this);
    }
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if ((this.viewAdapter != null) && (this.viewAdapter.getItemsCount() > 0))
    {
      updateView();
      drawItems(paramCanvas);
      drawCenterRect(paramCanvas);
    }
    drawShadows(paramCanvas);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    layout(paramInt3 - paramInt1, paramInt4 - paramInt2);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getMode(paramInt2);
    int k = View.MeasureSpec.getSize(paramInt1);
    int l = View.MeasureSpec.getSize(paramInt2);
    buildViewForMeasuring();
    int i1 = calculateLayoutWidth(k, i);
    if (j == 0x40000000){
    	int l1 = j;
    }
    for (int i2 = l; ; i2 = Math.min(i2, l))
      do
      {
        setMeasuredDimension(i1, i2);
        
        i2 = getDesiredHeight(this.itemsLayout);
      }
      while (j != -2147483648);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((!(isEnabled())) || (getViewAdapter() == null))
      return true;
    switch (paramMotionEvent.getAction())
    {
    default:
    case 2:
    case 1:
    }
 /*   do
      while (true)
      {
        return this.scroller.onTouchEvent(paramMotionEvent);
        if (getParent() == null)
          continue;
        getParent().requestDisallowInterceptTouchEvent(true);
      }
    while (this.isScrollingPerformed);
    int i = (int)paramMotionEvent.getY() - (getHeight() / 2);
    if (i > 0);
    for (int j = i + getItemHeight() / 2; ; j = i - (getItemHeight() / 2))
      while (true)
      {
        int k;
        do
          k = j / getItemHeight();
        while ((k == 0) || (!(isValidItemIndex(k + this.currentItem))));
        notifyClickListenersAboutClick(k + this.currentItem);
      }*/
    return true;
  }

  public void removeChangingListener(OnWheelChangedListener paramOnWheelChangedListener)
  {
    this.changingListeners.remove(paramOnWheelChangedListener);
  }

  public void removeClickingListener(OnWheelClickedListener paramOnWheelClickedListener)
  {
    this.clickingListeners.remove(paramOnWheelClickedListener);
  }

  public void removeScrollingListener(OnWheelScrollListener paramOnWheelScrollListener)
  {
    this.scrollingListeners.remove(paramOnWheelScrollListener);
  }

  public void scroll(int paramInt1, int paramInt2)
  {
    int i = paramInt1 * getItemHeight() - this.scrollingOffset;
    this.scroller.scroll(i, paramInt2);
  }

  public void setCurrentItem(int paramInt)
  {
    setCurrentItem(paramInt, false);
  }

  public void setCurrentItem(int paramInt, boolean paramBoolean)
  {
  /*  int i;
    if ((this.viewAdapter == null) || (this.viewAdapter.getItemsCount() == 0));
    do
    {
      do
      {
        return;
        i = this.viewAdapter.getItemsCount();
        if ((paramInt >= 0) && (paramInt < i))
          break label54;
      }
      while (!(this.isCyclic));
      if (paramInt < 0)
        break label129;
      label54: paramInt %= i;
    }
    while (paramInt == this.currentItem);
    if (paramBoolean)
    {
      int l;
      int k = paramInt - this.currentItem;
      if (this.isCyclic)
      {
        l = i + Math.min(paramInt, this.currentItem) - Math.max(paramInt, this.currentItem);
        if (l < Math.abs(k))
          if (k >= 0)
            break label136;
      }
      label129: label136: for (k = l; ; k = -l)
        while (true)
        {
          scroll(k, 0);
          return;
          paramInt += i;
        }
    }
    this.scrollingOffset = 0;
    int j = this.currentItem;
    this.currentItem = paramInt;
    notifyChangingListeners(j, this.currentItem);
    invalidate();*/
  }

  public void setCyclic(boolean paramBoolean)
  {
    this.isCyclic = paramBoolean;
    invalidateWheel(false);
  }

  public void setInterpolator(Interpolator paramInterpolator)
  {
    this.scroller.setInterpolator(paramInterpolator);
  }

  public void setViewAdapter(WheelViewAdapter paramWheelViewAdapter)
  {
    if (this.viewAdapter != null)
      this.viewAdapter.unregisterDataSetObserver(this.dataObserver);
    this.viewAdapter = paramWheelViewAdapter;
    if (this.viewAdapter != null)
      this.viewAdapter.registerDataSetObserver(this.dataObserver);
    invalidateWheel(true);
  }

  public void setVisibleItems(int paramInt)
  {
    this.visibleItems = paramInt;
  }

  public void stopScrolling()
  {
    this.scroller.stopScrolling();
  }
}
