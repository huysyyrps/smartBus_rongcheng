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
	private String favorName;// 主键

	@DatabaseField
	private String lineCode;// 线路编号
	@DatabaseField
	private int lineSxx;//用来标志上下行 了  0下行1上行
	@DatabaseField
	private String lineName;// 线路名称
	@DatabaseField
	private int cityCode;// 城市编号
	@DatabaseField
	private String beginStation;// 开始站名
	@DatabaseField
	private String endStation;// 结束站名

	@DatabaseField
	private String jd;// 开始站名
	@DatabaseField
	private String wd;// 结束站名
	@DatabaseField
	private String juli;// 结束站名

	private String zdname;//站点名字
//	@DatabaseField
//	private String beginTime;//首末班发车时间
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
	private Float[] stationDistances;//站点距离
//怎么用？
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	private byte[] stationSerial;//所有的站点信息 byte[]
	private ArrayList<StationBean> stations;// 线路上的站点
	
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
	 * 获取线路上的站点列表
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
