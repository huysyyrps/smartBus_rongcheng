package main.net.londatiga.android;

import main.smart.rcgj.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActionItem extends LinearLayout
{
  private int actionId;
  private ImageView mIconView;
  private TextView mTitleView;
  private boolean sticky;

  private ActionItem(Context context, int paramInt)
  {
    super(context);
    this.actionId = -1;
	if (paramInt == 0)
		LayoutInflater.from(context).inflate(R.layout.action_item_horizontal, this, true);
	else
		LayoutInflater.from(context).inflate(R.layout.action_item_vertical, this, true);
	mIconView = (ImageView)findViewById(R.id.iv_icon);
	mTitleView = (TextView)findViewById(R.id.tv_title);
	setFocusable(true);
	setClickable(true);
	setBackgroundResource(R.drawable.action_item_btn);
    //=---------------
    
  /*  if (paramInt == 0)
      LayoutInflater.from(paramContext).inflate(R.layout.action_item_horizontal, this, true);
    else{
      this.mIconView = ((ImageView)findViewById(R.id.iv_icon));
      this.mTitleView = ((TextView)findViewById(R.id.tv_title));
      setFocusable(true);
      setClickable(true);
      setBackgroundResource(R.drawable.action_item_btn);
      LayoutInflater.from(paramContext).inflate(R.layout.action_item_vertical, this, true);
    }*/
  }

  public ActionItem(Context paramContext, int paramInt1, int paramInt2, String paramString)
  {
    this(paramContext, paramInt1);
    this.actionId = paramInt2;
    setTitle(paramString);
    setIconVisibility(8);
  }

  public ActionItem(Context paramContext, int paramInt1, int paramInt2, String paramString, int paramInt3)
  {
    this(paramContext, paramInt1);
    this.actionId = paramInt2;
    setTitle(paramString);
    setIcon(paramInt3);
  }

  public int getActionId()
  {
    return this.actionId;
  }

  public Drawable getIcon()
  {
    return this.mIconView.getDrawable();
  }

  public String getTitle()
  {
    return this.mTitleView.getText().toString();
  }

  public boolean isSticky()
  {
    return this.sticky;
  }

  public void setActionId(int paramInt)
  {
    this.actionId = paramInt;
  }

  public void setIcon(int paramInt)
  {
    this.mIconView.setImageResource(paramInt);
  }

  public void setIcon(Drawable paramDrawable)
  {
    this.mIconView.setImageDrawable(paramDrawable);
  }

  public void setIconVisibility(int paramInt)
  {
    this.mIconView.setVisibility(paramInt);
  }

  public void setSticky(boolean paramBoolean)
  {
    this.sticky = paramBoolean;
  }

  public void setTitle(String paramString)
  {
    this.mTitleView.setText(paramString);
  }

  public void setTitleColor(int paramInt)
  {
    this.mTitleView.setTextColor(paramInt);
  }

  public void setTitleSize(int paramInt1, int paramInt2)
  {
    this.mTitleView.setTextSize(paramInt1, paramInt2);
  }

  public void setTitleVisibility(int paramInt)
  {
    this.mTitleView.setVisibility(paramInt);
  }
}