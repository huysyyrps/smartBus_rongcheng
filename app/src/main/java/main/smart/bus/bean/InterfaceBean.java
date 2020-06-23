package main.smart.bus.bean;

import java.io.Serializable;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "Interfaceinfo")
public class InterfaceBean implements Serializable{

	public InterfaceBean() {
		
		// TODO Auto-generated constructor stub
	}
	

	@DatabaseField(id=true)
	private String gid;// 主键
	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	@DatabaseField
	private String icon;// 图标名称/路径

	@DatabaseField
	private String iconType;// 图标类型："0"图标名称；"1"图标路径；
	@DatabaseField
	private String title;// 标题
	@DatabaseField
	private String webURL;// 打开页面网站
	@DatabaseField
	private int drawable;// iconType=0 本地图标资源 iconType=2 广告加载顺序
	@DatabaseField
	private String path;// 本地图片保存路径
	
	public Drawable getDraw() {
		return draw;
	}

	public void setDraw(Drawable draw) {
		this.draw = draw;
	}

	public Bitmap getBmp() {
		return bmp;
	}

	public void setBmp(Bitmap bmp) {
		this.bmp = bmp;
	}


	private Drawable draw;
	private Bitmap bmp;
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconType() {
		return iconType;
	}

	public void setIconType(String iconType) {
		this.iconType = iconType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWebURL() {
		return webURL;
	}

	public void setWebURL(String webURL) {
		this.webURL = webURL;
	}

	public int getDrawable() {
		return drawable;
	}

	public void setDrawable(int drawable) {
		this.drawable = drawable;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


}
