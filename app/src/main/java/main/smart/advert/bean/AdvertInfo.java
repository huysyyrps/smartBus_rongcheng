package main.smart.advert.bean;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
//必须要有无参的构造函数
@DatabaseTable (tableName = "advertinfo")
public class AdvertInfo
{
  public static final String FIELD_SHOW_LEVEL = "showLevel";

  @DatabaseField
  public Integer adType;

  public Integer getAdType() {
	return adType;
}

public void setAdType(Integer adType) {
	this.adType = adType;
}

public String getAdvertiser() {
	return advertiser;
}

public void setAdvertiser(String advertiser) {
	this.advertiser = advertiser;
}

public String getAndroidUrl() {
	return androidUrl;
}

public void setAndroidUrl(String androidUrl) {
	this.androidUrl = androidUrl;
}

public Integer getArea() {
	return area;
}

public void setArea(Integer area) {
	this.area = area;
}

public Long getBeginTime() {
	return beginTime;
}

public void setBeginTime(Long beginTime) {
	this.beginTime = beginTime;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public Long getEndTime() {
	return endTime;
}

public void setEndTime(Long endTime) {
	this.endTime = endTime;
}

public String getFileId() {
	return fileId;
}

public void setFileId(String fileId) {
	this.fileId = fileId;
}

public Integer getFileStyle() {
	return fileStyle;
}

public void setFileStyle(Integer fileStyle) {
	this.fileStyle = fileStyle;
}

public String getId() {
	return id;
}

public void setId(String id) {
	this.id = id;
}

public String getIosUrl() {
	return iosUrl;
}

public void setIosUrl(String iosUrl) {
	this.iosUrl = iosUrl;
}

public String getNextUrl() {
	return nextUrl;
}

public void setNextUrl(String nextUrl) {
	this.nextUrl = nextUrl;
}

public Integer getShowLevel() {
	return showLevel;
}

public void setShowLevel(Integer showLevel) {
	this.showLevel = showLevel;
}

public Integer getState() {
	return state;
}

public void setState(Integer state) {
	this.state = state;
}

public Long getTimestamp() {
	return timestamp;
}

public void setTimestamp(Long timestamp) {
	this.timestamp = timestamp;
}

public String getVendor() {
	return vendor;
}

public void setVendor(String vendor) {
	this.vendor = vendor;
}

public String getWpUrll() {
	return wpUrll;
}

public void setWpUrll(String wpUrll) {
	this.wpUrll = wpUrll;
}

@DatabaseField
  public String advertiser;

  @DatabaseField
  public String androidUrl;

  @DatabaseField
  public Integer area;

  @DatabaseField
  public Long beginTime;

  @DatabaseField
  public String description;

  @DatabaseField
  public Long endTime;

  @DatabaseField
  public String fileId;

  @DatabaseField
  public Integer fileStyle;

  @DatabaseField(id=true)
  public String id;

  @DatabaseField
  public String iosUrl;

  @DatabaseField
  public String nextUrl;

  @DatabaseField
  public Integer showLevel;

  @DatabaseField
  public Integer state;

  @DatabaseField
  public Long timestamp;

  @DatabaseField
  public String vendor;

  @DatabaseField
  public String wpUrll;
}
