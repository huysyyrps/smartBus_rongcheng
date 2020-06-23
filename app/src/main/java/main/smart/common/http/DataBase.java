package main.smart.common.http;

import main.smart.common.SmartBusApp;
import main.smart.common.util.CityManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.preference.PreferenceManager;

public class DataBase extends SQLiteOpenHelper{

	private static final int ver=3;
	public DataBase(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public DataBase(Context context, String name) {
		this(context, name, ver);
		// TODO Auto-generated constructor stub
	}
	
	public DataBase(Context context, String name, int version) {
		this(context, name, null, version);
		// TODO Auto-generated constructor stub
	}


	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		System.out.println("创建数据库");
		db.execSQL("create table ZDVer(ver int)");
		db.execSQL("create table ZDXX(line int,linename varchar(20),zd int,zdname varchar(20),sxx int,jd int,wd int)");//创建站点信息表
		db.execSQL("create table LSJL(id integer PRIMARY KEY autoincrement,zdname varchar(20),zdtype varchar(20))");//创建历史记录
		db.execSQL("create table LSXL(id integer PRIMARY KEY autoincrement,xlbh varchar(20),xlmc varchar(20),webIp varchar(20),bsPort varchar(20),socketPort varchar(20))");//创建上一次线路记录
		db.execSQL("create table JWD(id integer PRIMARY KEY autoincrement,address varchar(100),latitude varchar(100),longitude varchar(100))");//创建经纬度记录

		System.out.println("创建完毕");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		System.out.println("update");
		if(oldVersion<2){
			Cursor  cursor = db.rawQuery( "SELECT * FROM ZDXX" , null );
			int index=cursor.getColumnIndex("linename");
			if(index==-1){
				db.execSQL("ALTER TABLE ZDXX ADD linename default ''");
				CityManager.getInstance().setZdiden(false);
				SharedPreferences  prefer = PreferenceManager.getDefaultSharedPreferences(SmartBusApp.getInstance());
			    
			}
		}
		
		if(oldVersion<3)
		{
			db.execSQL("create table LSXL(id integer PRIMARY KEY autoincrement,xlbh varchar(20),xlmc varchar(20))");//创建上一次线路记录
		}
	}





}