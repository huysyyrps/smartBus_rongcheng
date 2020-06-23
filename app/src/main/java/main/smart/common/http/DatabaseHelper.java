package main.smart.common.http;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "Recharge"; //数据库名称  
    private static final int version = 1; //数据库版本  
       
    public DatabaseHelper(Context context) {  
        super(context, DB_NAME, null, version);  
        // TODO Auto-generated constructor stub  
    }    
    public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public DatabaseHelper(Context context, String name) {
		this(context, name, version);
		// TODO Auto-generated constructor stub
	}
	
	public DatabaseHelper(Context context, String name, int version) {
		this(context, name, null, version);
		// TODO Auto-generated constructor stub
	}
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//创建交接数据表
		String sql="CREATE TABLE data(id Integer primary key autoincrement,CardNo varchar(100) not null,PayMoney varchar(100) not null, orderId varchar(100) not null, Make varchar(100) not null, CARDTYPE varchar(100) not null, state varchar(100) not null);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
