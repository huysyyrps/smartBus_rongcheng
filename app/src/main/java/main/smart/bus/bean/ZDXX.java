package main.smart.bus.bean;

public class ZDXX {

	private int xl;//线路
	private String xlname;//线路名称
	private int zd;//站点
	private String zdname;//站点名字
	private int sxx;//上下行
	private int jd;
	private int wd;
	private String memo;
	
	public String getXlname() {
		return xlname;
	}
	public void setXlname(String xlname) {
		this.xlname = xlname;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getJd() {
		return jd;
	}
	public void setJd(int jd) {
		this.jd = jd;
	}
	public int getWd() {
		return wd;
	}
	public void setWd(int wd) {
		this.wd = wd;
	}
	public int getXl() {
		return xl;
	}
	public void setXl(int xl) {
		this.xl = xl;
	}
	public int getZd() {
		return zd;
	}
	public void setZd(int zd) {
		this.zd = zd;
	}
	public String getZdname() {
		return zdname;
	}
	public void setZdname(String zdname) {
		this.zdname = zdname;
	}
	public int getSxx() {
		return sxx;
	}
	public void setSxx(int sxx) {
		this.sxx = sxx;
	}
	
}
