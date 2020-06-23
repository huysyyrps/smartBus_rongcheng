package main.smart.bus.bean;

import java.util.ArrayList;

import main.smart.common.util.CharUtil;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable (tableName = "lineinfo")
public class LineBean {


	@DatabaseField(columnName = "lineCode")
	private String lineCode;// ��·���
	@DatabaseField(id=true)
	private String gid;// ����
	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	@DatabaseField(columnName = "lineId")
	private int lineId;//������־������ ��  0����1����
	@DatabaseField
	private String lineName;// ��·����
	@DatabaseField
	private int cityCode;// ���б��
	@DatabaseField
	private String beginStation;// ��ʼվ��
	@DatabaseField
	private String endStation;// ����վ��
	private String dwbhs;//��·��λ
	public String getDwbhs() {
		return dwbhs;
	}

	public void setDwbhs(String dwbhs) {
		this.dwbhs = dwbhs;
	}

	//	@DatabaseField
//	private String beginTime;//��ĩ�෢��ʱ��
//	@DatabaseField
//	private String endTime;
	private String webIp;
	public String getWebIp() {
		return webIp;
	}

	public void setWebIp(String webIp) {
		this.webIp = webIp;
	}

	public String getBsPort() {
		return bsPort;
	}

	public void setBsPort(String bsPort) {
		this.bsPort = bsPort;
	}

	public String getSocketPort() {
		return socketPort;
	}

	public void setSocketPort(String socketPort) {
		this.socketPort = socketPort;
	}

	private String bsPort;
	private String socketPort;
	@DatabaseField(dataType = DataType.SERIALIZABLE)
	private Float[] stationDistances;//վ�����
//��ô�ã�
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	private byte[] stationSerial;//���е�վ����Ϣ byte[]
	private ArrayList<StationBean> stations;// ��·�ϵ�վ��
	
	public Float[] getStationDistances() {
		return stationDistances;
	}

	public void setStationDistances(Float[] stationDistances) {
		this.stationDistances = stationDistances;
	}

	public byte[] getStationSerial() {
		return stationSerial;
	}

	 
	 public void setStationSerial(byte[] stationSerial)
	  {
	    this.stationSerial = stationSerial;
	  }
	 
	  public void setStations(ArrayList<StationBean> paramArrayList)
	  {
	    this.stations = paramArrayList;
	    setStationSerial(CharUtil.objectToByte(paramArrayList));
	  }
	public String getLineCode() {
		return lineCode;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	public int getLineId() {
		return lineId;
	}

	public void setLineId(int lineId) {
		this.lineId = lineId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	public String getBeginStation() {
		return beginStation;
	}

	public void setBeginStation(String beginStation) {
		this.beginStation = beginStation;
	}

	public String getEndStation() {
		return endStation;
	}

	public void setEndStation(String endStation) {
		this.endStation = endStation;
	}

	/**
	 * ��ȡ��·�ϵ�վ���б�
	 * **/
	public ArrayList<StationBean> getStations() {
		if ((this.stations == null) && (this.stationSerial != null)){
			Object staObj =CharUtil
					.byteToObject(this.stationSerial);
			Object[] ss= ((Object[])staObj );
			stations =new ArrayList<StationBean>();
			for(int i=0;i<ss.length;i++){
				Object  bean= ss[i] ;
				if(bean instanceof StationBean)
					stations.add((StationBean) bean);
			}
			 
		}
		
			
		return this.stations;
	}
/*	public String getBeginTime() {
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
	}*/
	public LineBean() {
		// TODO Auto-generated constructor stub
	}

}
