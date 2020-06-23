package main.smart.common.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
//
@DatabaseTable(tableName = "phone_switchcity")
public class SwitchCity {
	 @DatabaseField
	  private String cityName;//
	  public String getCityName() {
		return cityName;
	}


	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	@DatabaseField
	private String cityHelp;
	
	
	

	public String getCityHelp() {
		return cityHelp;
	}


	public void setCityHelp(String cityHelp) {
		this.cityHelp = cityHelp;
	}

	@DatabaseField(id=true)
	  private int cityCode;
	  @DatabaseField
	  private String url;//
	  @DatabaseField
	  private String goServerPort;
	  // lonlat of the city
	  @DatabaseField
	  private String centerX;
	  @DatabaseField
	  private String centerY;
	  @DatabaseField
	  private String ip;
	   
	public SwitchCity(String cityName,String ip){
		this.cityName=cityName;
		this.ip=ip;
	}
	public int getCityCode() {
		return cityCode;
	}


	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getGoServerPort() {
		return goServerPort;
	}


	public void setGoServerPort(String goUrl) {
		this.goServerPort = goUrl;
	}


	public String getCenterX() {
		return centerX;
	}


	public void setCenterX(String centerX) {
		this.centerX = centerX;
	}


	public String getCenterY() {
		return centerY;
	}


	public void setCenterY(String centerY) {
		this.centerY = centerY;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public SwitchCity() {
		// TODO Auto-generated constructor stub
	}

}
