package main.smart.bus.bean;

public class HCXX {

	private String name; //换乘信息展示页面的条目名字
	private String qd;   //起点名字
	private String zd;   //终点名字
	private String zj;   //换乘点名字
	private int qzs;    //起点到换乘的站数
	private int jzs;    //换乘到终点的站数
	private int zds;    //直达站数
	private String memo;//详情
	private String qzfx;
	private String hcfx;
	
	public String getHcfx() {
		return hcfx;
	}
	public void setHcfx(String hcfx) {
		this.hcfx = hcfx;
	}
	public String getQzfx() {
		return qzfx;
	}
	public void setQzfx(String qzfx) {
		this.qzfx = qzfx;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQd() {
		return qd;
	}
	public void setQd(String qd) {
		this.qd = qd;
	}
	public String getZd() {
		return zd;
	}
	public void setZd(String zd) {
		this.zd = zd;
	}
	public String getZj() {
		return zj;
	}
	public void setZj(String zj) {
		this.zj = zj;
	}
	public int getQzs() {
		return qzs;
	}
	public void setQzs(int qzs) {
		this.qzs = qzs;
	}
	public int getJzs() {
		return jzs;
	}
	public void setJzs(int jzs) {
		this.jzs = jzs;
	}
	public int getZds() {
		return zds;
	}
	public void setZds(int zds) {
		this.zds = zds;
	}
	
}
