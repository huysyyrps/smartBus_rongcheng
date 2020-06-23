package main.smart.common.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "phone_city")
public class City {
	//���ݿ�� ����
	 public static final String FIELD_CITY = "city_code";
	 @DatabaseField
	private String province;//ʡ
	 @DatabaseField(id=true,columnName=FIELD_CITY)
	private int cityCode;
	 @DatabaseField
	private String cityName;
	  @DatabaseField
	  private String functions;
public String getFunctions() {
		return functions;
	}

	public void setFunctions(String functions) {
		this.functions = functions;
	}

/**
 * ���캯��
 * */
	public City(int cityCode, String cityName, String province)
	  {
	    this.cityCode = cityCode;
	    this.cityName = cityName;
	    this.province = province;
	  }

	  public City(String cityName, String province)
	  {
	    this.cityName = cityName;
	    this.province = province;
	  }
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public int getCityCode() {
		return cityCode;
	}

	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public City() {
		  // ORMLite ��Ҫһ���޲ι���
	}

}
