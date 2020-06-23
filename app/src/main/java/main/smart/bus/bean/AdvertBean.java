package main.smart.bus.bean;

import java.util.ArrayList;
import java.util.List;

import main.smart.common.util.CharUtil;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable (tableName = "Advertinfo")
public class AdvertBean {

	public AdvertBean() {
		
		// TODO Auto-generated constructor stub
	}

	@DatabaseField(id=true)
	private String page;
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	private byte[] interfaceSerial;//所有的广告信息 byte[]
	private ArrayList<InterfaceBean> iList;
	@DatabaseField
	private String showType;
	@DatabaseField
	private int delay = 0;
	
	
	public byte[] getInterfaceSerial() {
		return interfaceSerial;
	}
	public void setInterfaceSerial(byte[] interfaceSerial) {
		this.interfaceSerial = interfaceSerial;
	}
	
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public List<InterfaceBean> getList() {
		if ((this.iList == null) && (this.interfaceSerial != null)){
			this.iList = (ArrayList)CharUtil
					.byteToObject(this.interfaceSerial);
//			Object[] ss = (Object[]) inObj;
//			iList = new ArrayList<InterfaceBean>();
//			for(int i=0;i<ss.length;i++){
//				Object  bean = ss[i] ;
//				if(bean instanceof InterfaceBean)
//					iList.add((InterfaceBean) bean);
//			}
//			 
		}
		return iList;
	}
	public void setList(ArrayList<InterfaceBean> paramList) {
		this.iList = paramList;
		setInterfaceSerial(CharUtil.objectToByte(paramList));
	}
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	
}
