package main.customizedBus.home.bean;

import com.baidu.mapapi.search.core.PoiInfo;
import main.utils.utils.NonNullString;

import java.io.Serializable;

public class AddressBean implements Serializable {
    private String name ="";
    private String addr ="";
    private String province ="";
    private String city ="";
    private String area ="";
    private double  latitude = 0;
    private double longitude = 0;
    private boolean isLoaction = false;
    public AddressBean(){

    }
    public AddressBean(PoiInfo poi) {
       setName(poi.getName());
        setAddr(poi.getAddress());
       setProvince(poi.getProvince());
        setCity(poi.getCity());
        setArea(poi.getArea());
        setLatitude(poi.getLocation().latitude);
        setLongitude(poi.getLocation().longitude);
    }

    public String getName() {
        return name;
    }

    public String getAddr() {
        return addr;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getArea() {
        return area;
    }

    public String getViewShowName() {
        return isLoaction==true? "当前位置" +"("+name+")"  :name;
    }

    public void setName(String name) {
        name = name.replace("在","");
        name = name.replace("附近","");
        this.name = NonNullString.getString(name) ;
    }

    public void setAddr(String addr) {
        this.addr =  NonNullString.getString(addr);
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void setProvince(String province) {
        this.province =  NonNullString.getString(province);
    }

    public void setCity(String city) {
        this.city =  NonNullString.getString(city);
    }

    public void setArea(String area) {
        this.area =  NonNullString.getString(area);
    }

    public void setLoaction(boolean loaction) {
        isLoaction = loaction;
    }
}
