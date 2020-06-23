package main.smart.bus.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable (tableName = "businfo")
public class BusBean {
	@DatabaseField(id=true)
	private String gid;// ����
	@DatabaseField
	  private String lat;//γ��

	  @DatabaseField
	  private String lng;//����
	  
	  @DatabaseField
	  private String busCode;//����
	  
	  @DatabaseField
	  private String sxx;//������
	  
	  @DatabaseField
	  private int lineId;;//��·
	  @DatabaseField
	  private String leftDistance;
	  @DatabaseField
	  private Integer leftStation;

	  private String speed;//�����ٶ�
	  
	  private String busNum;//���ƺ�
	  private String showCPH;//����1 ��ʾ����  ����0 ��ʾ�Ա��
	
	public String getShowCPH() {
		return showCPH;
	}
	public void setShowCPH(String showCPH) {
		this.showCPH = showCPH;
	}
	public BusBean(){
		
	}
	public String getGid() {
		return gid;
	}

	public String getLeftDistance() {
		return leftDistance;
	}
	public void setLeftDistance(String leftDistance) {
		this.leftDistance = leftDistance;
	}
	public Integer getLeftStation() {
		return leftStation;
	}
	public void setLeftStation(Integer leftStation) {
		this.leftStation = leftStation;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}

	  public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getBusCode() {
		return busCode;
	}

	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}

	public String getSxx() {
		return sxx;
	}

	public void setSxx(String sxx) {
		this.sxx = sxx;
	}

	public int getLineId() {
		return lineId;
	}

	public void setLineId(int lineId) {
		this.lineId = lineId;
	}

	public String getSpeed() {
		return speed;
	}
	
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	
	public String getBusNum() {
		return busNum;
	}
	public void setBusNum(String busNum) {
		this.busNum = busNum;
	}

}
