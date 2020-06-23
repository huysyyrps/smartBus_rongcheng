package main.smart.advert;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.smart.advert.bean.AdvertBrowseInfo;
import main.smart.advert.bean.AdvertClickInfo;
import main.smart.advert.bean.AdvertInfo;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AdvertImageBase extends ImageView
{
  protected AdvertInfo mAdvert;
  protected boolean mAdvertVisibility;
  protected List<AdvertBrowseInfo> mBrowseInfos;
  protected long mBrowseStatTime;
  protected List<AdvertClickInfo> mClickInfos;
  protected int mDefaultImageRes;
/**
 * 广告信息存储在数据库表
 * */
  public AdvertImageBase(Context paramContext, int paramInt)
  {
    this(paramContext, null);
    this.mDefaultImageRes = paramInt;
  }

  public AdvertImageBase(Context paramContext, int paramInt, AdvertInfo paramAdvertInfo)
  {
    this(paramContext, null);
    this.mDefaultImageRes = paramInt;
    this.mAdvert = paramAdvertInfo;
    initAdvertView(paramContext);
  }

  public AdvertImageBase(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mBrowseInfos = new ArrayList();
    this.mClickInfos = new ArrayList();
    this.mAdvertVisibility = false;
    setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    setScaleType(ImageView.ScaleType.FIT_XY);
  }

  protected void displayAdvertPicture()
  {
    if (this.mAdvert.fileId == null)
      return;
  //  String str = AdvertManager.getAdvertPicUrl(this.mAdvert.fileId);
    //ImageLoader异步加载图片框架
 //   ImageLoader.getInstance().displayImage(str, this, new DisplayImageOptions.Builder().showStubImage(this.mDefaultImageRes).cacheInMemory().cacheOnDisc().build());
  }

  protected void initAdvertView(Context paramContext)
  {
    if (this.mAdvert != null)
    {
      displayAdvertPicture();
    /*  if (!(TextUtils.isEmpty(this.mAdvert.nextUrl)))
        setOnClickListener(new View.OnClickListener(paramContext)
        {
          public void onClick(View paramView)
          {
            AdvertClickInfo localAdvertClickInfo = new AdvertClickInfo();
            localAdvertClickInfo.adId = AdvertImageBase.this.mAdvert.id;
            localAdvertClickInfo.statTime = new Date().getTime();
            localAdvertClickInfo.times = 1;
            AdvertImageBase.this.mClickInfos.add(localAdvertClickInfo);
            Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(AdvertImageBase.this.mAdvert.nextUrl));
            localIntent.addFlags(524288);
            this.val$context.startActivity(localIntent);
          }
        });*/
      return;
    }
    if (this.mDefaultImageRes == 0)
    {
      setVisibility(8);
      return;
    }
    setImageResource(this.mDefaultImageRes);
  }

  protected void onAdvertOff()
  {
    if (this.mAdvert != null){
    	 while (!(this.mAdvertVisibility)){
    		    this.mAdvertVisibility = false;
    		    AdvertBrowseInfo localAdvertBrowseInfo = new AdvertBrowseInfo();
    		    localAdvertBrowseInfo.statTime = this.mBrowseStatTime;
    		    localAdvertBrowseInfo.adId = this.mAdvert.id;
    		    localAdvertBrowseInfo.duration = (new Date().getTime() - this.mBrowseStatTime);
    		    this.mBrowseInfos.add(localAdvertBrowseInfo);
    		    }
    }
   
   
  }

  protected void onAdvertOn()
  {
    if (this.mAdvert == null);
    
    while (this.mAdvertVisibility){
    	 this.mAdvertVisibility = true;
    	    this.mBrowseStatTime = new Date().getTime();
    }
   
  }
}
