package main.smart.bus.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class ScrollViewPager extends ViewPager {

	private boolean isCanScroll = false;  
	  
    public ScrollViewPager(Context context) {  
        super(context);  
    }  
  
    public ScrollViewPager(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public void setScanScroll(boolean isCanScroll){  
        this.isCanScroll = isCanScroll;  
    }  

    @Override  
    public void scrollTo(int x, int y){  
    	super.scrollTo(x, y);  
    }  
    
    @Override
	public void setCurrentItem(int item) {
		// TODO Auto-generated method stub
		super.setCurrentItem(item);
	}

    @Override
	public boolean onTouchEvent(MotionEvent arg0) {
    	// TODO Auto-generated method stub
    	if (isCanScroll) {
    		return super.onTouchEvent(arg0);
    	} else {
    		return false;
    	}
    }

    @Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
    	// TODO Auto-generated method stub
    	if (isCanScroll) {
    		return super.onInterceptTouchEvent(arg0);
    	} else {
    		return false;
    	}
    }

}
