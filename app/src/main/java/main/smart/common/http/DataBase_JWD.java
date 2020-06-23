package main.smart.common.http;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DataBase_JWD extends SQLiteOpenHelper{

	private static final int ver=3;
	public DataBase_JWD(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public DataBase_JWD(Context context, String name) {
		this(context, name, ver);
		// TODO Auto-generated constructor stub
	}
	
	public DataBase_JWD(Context context, String name, int version) {
		this(context, name, null, version);
		// TODO Auto-generated constructor stub
	}


	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		System.out.println("�������ݿ�");
		db.execSQL("create table JWD(id integer PRIMARY KEY autoincrement,address varchar(100),latitude varchar(100),longitude varchar(100))");//������γ�ȼ�¼
		
		System.out.println("�������");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		System.out.println("update");
		
	}





}