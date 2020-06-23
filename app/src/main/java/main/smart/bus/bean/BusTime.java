package main.smart.bus.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * 首末班发车时间
 * */
@DatabaseTable (tableName = "busTime")
public class BusTime {

	public BusTime() {
		// TODO Auto-generated constructor stub
	}
	@DatabaseField
	private String lineCode;// 线路编号
	@DatabaseField
	private String sxx;// 上下行 0：下行 1上行
	public String getLineCode() {
		return lineCode;
	}
	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}
	public String getSxx() {
		return sxx;
	}
	public void setSxx(String sxx) {
		this.sxx = sxx;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	@DatabaseField
	private String beginTime;// 首班时间
	@DatabaseField
	private String endTime;// 末班时间
	@DatabaseField(id=true)
	private String gid;// 主键
	public String getGid() {
		return gid;
	}
}
