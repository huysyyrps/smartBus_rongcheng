package main.smart.bus.bean;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable (tableName = "linehisinfo")
public class LineHistory {

	
	 public static final String FIELD_ACTIVE_TIME = "activeTime";
	  public static final String FIELD_AREA = "city_code";

	  @DatabaseField
	  private Date activeTime;

	  @DatabaseField(canBeNull=false)
	  private int cityCode;

	  @DatabaseField(canBeNull=false, foreign=true, foreignAutoRefresh=true)
	  private LineBean busLine;

	  @DatabaseField(id=true)
	  private String lineHisId;

	  
	  public Date getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	public int getCityCode() {
		return cityCode;
	}
	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}
	public LineBean getBusLine() {
		return busLine;
	}
	public void setBusLine(LineBean busLine) {
		this.busLine = busLine;
	}
	public String getLineHisId() {
		return lineHisId;
	}
	public void setLineHisId(String lineHisId) {
		this.lineHisId = lineHisId;
	}
	public LineHistory(Date paramDate, LineBean paramBusLine)
	  {
		//lineInfor与lineHisInfo用同一个主键
	    this.lineHisId = paramBusLine.getGid();
	    this.cityCode = paramBusLine.getCityCode();
	    this.activeTime = paramDate;
	    this.busLine = paramBusLine;
	  }
	  public LineHistory() {
			// TODO Auto-generated constructor stub
		}
}
