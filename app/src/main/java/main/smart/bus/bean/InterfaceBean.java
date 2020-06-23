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
	private String gid;// ����
	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	@DatabaseField
	private String icon;// ͼ������/·��

	@DatabaseField
	private String iconType;// ͼ�����ͣ�"0"ͼ�����ƣ�"1"ͼ��·����
	@DatabaseField
	private String title;// ����
	@DatabaseField
	private String webURL;// ��ҳ����վ
	@DatabaseField
	private int drawable;// iconType=0 ����ͼ����Դ iconType=2 ������˳��
	@DatabaseField
	private String path;// ����ͼƬ����·��
	
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
