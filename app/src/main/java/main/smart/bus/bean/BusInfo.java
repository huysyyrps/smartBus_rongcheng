package main.smart.bus.bean;

import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "businfo")
public class BusInfo implements Comparable<BusInfo>{

	public BusInfo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compareTo(BusInfo another) {
		// TODO Auto-generated method stub
		return 0;
	}

}
