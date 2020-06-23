package main.smart.bus.bean;
import java.io.Serializable;
import java.util.ArrayList;

import main.smart.common.util.CharUtil;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable (tableName = "FavorInfo")
public class FavorLineBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public FavorLineBean() {
		// TODO Auto-generated constructor stub
	}
	
	@DatabaseField(id=true)
	private String favorName;// ����

	@DatabaseField
	private String lineCode;// ��·���
	@DatabaseField
	private int lineSxx;//������־������ ��  0����1����
	@DatabaseField
	private String lineName;// ��·����
	@DatabaseField
	private int cityCode;// ���б��
	@DatabaseField
	private String beginStation;// ��ʼվ��
	@DatabaseField
	private String endStation;// ����վ��

	@DatabaseField
	private String jd;// ��ʼվ��
	@DatabaseField
	private String wd;// ����վ��
	@DatabaseField
	private String juli;// ����վ��

	private String zdname;//վ������
//	@DatabaseField
//	private String beginTime;//��ĩ�෢��ʱ��
//	@DatabaseField
//	private String endTime;

	public String getJuli() {
		return juli;
	}

	public void setJuli(String juli) {
		this.juli = juli;
	}

	public String getZdname() {
		return zdname;
	}

	public void setZdname(String zdname) {
		this.zdname = zdname;
	}

	public String getJd() {
		return jd;
	}

	public void setJd(String jd) {
		this.jd = jd;
	}

	public String getWd() {
		return wd;
	}

	public void setWd(String wd) {
		this.wd = wd;
	}

	@DatabaseField(dataType = DataType.SERIALIZABLE)
	private Float[] stationDistances;//վ�����
//��ô�ã�
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	private byte[] stationSerial;//���е�վ����Ϣ byte[]
	private ArrayList<StationBean> stations;// ��·�ϵ�վ��
	
	public String getFavorName() {
		return favorName;
	}

	public void setFavorName(String name) {
		this.favorName = name;
	}
	
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

	public int getLineSxx() {
		return lineSxx;
	}

	public void setLineSxx(int sxx) {
		this.lineSxx = sxx;
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

//	public FavorLineBean(int id, String number, String speed, double latitude,
//				double longitude) {
//		super();
//		this.id = id;
//		this.number = number;
//		this.speed = speed;
//		this.latitude = latitude;
//		this.longitude = longitude;
//	}



}
