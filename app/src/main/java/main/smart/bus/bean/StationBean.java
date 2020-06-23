package main.smart.bus.bean;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;

import main.smart.common.util.CharUtil;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable (tableName = "stationinfo")
public class StationBean  implements Serializable{
	  public static final String FIELD_ACTIVE_TIME = "activeTime";
	  public static final String FIELD_AREA = "area";

	  @DatabaseField
	  private Date activeTime;

	  @DatabaseField(canBeNull=false)
	  private Integer cityCode;
	  private ArrayList<LineBean> busLineList;

	  @DatabaseField(dataType=DataType.BYTE_ARRAY)
	  private byte[] busLineSerial;

	  @DatabaseField(id=true)
	  private String gid;

	  @DatabaseField(canBeNull=false)
	  private String id;

	  @DatabaseField
	  private Double lat;//纬度

	  public String getDis() {
		return dis;
	}

	public void setDis(String dis) {
		this.dis = dis;
	}

	@DatabaseField
	  private Double lng;//经度
	  @DatabaseField
	  private String dis;//站点间距

	  @DatabaseField
	  private String state;

	  @DatabaseField
	  private String stationName;

	  @DatabaseField
	  private Date updateTime;

	  public StationBean()
	  {
	  }

	  public StationBean(StationBean paramBusStation)
	  {
	    this.id = paramBusStation.id;
	    this.cityCode = paramBusStation.cityCode;
	    this.stationName = paramBusStation.stationName;
	    this.busLineList = new ArrayList(paramBusStation.busLineList);
	    this.lng = paramBusStation.lng;
	    this.lat = paramBusStation.lat;
	    this.state = paramBusStation.state;
	    this.updateTime = paramBusStation.updateTime;
	  }

	  public void generateGid()
	  {
	    this.gid = this.cityCode + "&" + this.id;
	  }

	  public Date getActiveTime()
	  {
	    return this.activeTime;
	  }

	  public Integer getArea()
	  {
	    return this.cityCode;
	  }

	  public ArrayList<LineBean> getBusLineList()
	  {
	    if ((this.busLineList == null) && (this.busLineSerial != null))
	      this.busLineList = ((ArrayList) CharUtil.byteToObject(this.busLineSerial));
	    return this.busLineList;
	  }

	  public byte[] getBusLineSerial()
	  {
	    return this.busLineSerial;
	  }

	  public String getGid()
	  {
	    return this.gid;
	  }

	  public String getId()
	  {
	    return this.id;
	  }

	  public Double getLat()
	  {
	    return this.lat;
	  }

	  public Double getLng()
	  {
	    return this.lng;
	  }

	  public String getState()
	  {
	    return this.state;
	  }

	  public String getStationName()
	  {
	    return this.stationName;
	  }

	  public Date getUpdateTime()
	  {
	    return this.updateTime;
	  }

	  public void setActiveTime(Date paramDate)
	  {
	    this.activeTime = paramDate;
	  }

	  public void setArea(Integer paramInteger)
	  {
	    this.cityCode = paramInteger;
	  }

	  public void setBusLineList(ArrayList<LineBean> paramArrayList)
	  {
	    this.busLineList = paramArrayList;
	    setBusLineSerial(CharUtil.objectToByte(paramArrayList));
	  }

	  public void setBusLineSerial(byte[] paramArrayOfByte)
	  {
	    this.busLineSerial = paramArrayOfByte;
	  }

	  public void setId(String paramString)
	  {
	    this.id = paramString;
	  }

	  public void setLat(Double paramDouble)
	  {
	    this.lat = paramDouble;
	  }

	  public void setLng(Double paramDouble)
	  {
	    this.lng = paramDouble;
	  }

	  public void setState(String paramString)
	  {
	    this.state = paramString;
	  }

	  public void setStationName(String paramString)
	  {
	    this.stationName = paramString;
	  }

	  public void setUpdateTime(Date paramDate)
	  {
	    this.updateTime = paramDate;
	  }

	  public String toString()
	  {
	    return this.stationName;
	  }
	}
