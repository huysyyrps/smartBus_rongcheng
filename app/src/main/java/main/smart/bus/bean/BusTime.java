package main.smart.bus.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * ��ĩ�෢��ʱ��
 * */
@DatabaseTable (tableName = "busTime")
public class BusTime {

	public BusTime() {
		// TODO Auto-generated constructor stub
	}
	@DatabaseField
	private String lineCode;// ��·���
	@DatabaseField
	private String sxx;// ������ 0������ 1����
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
	private String beginTime;// �װ�ʱ��
	@DatabaseField
	private String endTime;// ĩ��ʱ��
	@DatabaseField(id=true)
	private String gid;// ����
	public String getGid() {
		return gid;
	}
}
