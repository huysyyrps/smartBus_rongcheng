package main.utils.sqliteDB;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {

   private    SQLiteDatabase db;
    public static final String CREATE_ADDRESS = "create table address_history (id integer primary key, name varchar(100), addr varchar(100),province varchar(100),city varchar(100), area varchar(100),latitude varchar(100), longitude double)";
   //单例
   private static  SqliteHelper sqliteHelper;
    public  static SqliteHelper getInstance(Context context){
        if (null==sqliteHelper){
            synchronized (SqliteHelper.class){
                if (null==sqliteHelper){
                    sqliteHelper = new SqliteHelper(context);

                }
            }
        }
        return  sqliteHelper;
    }
    public SqliteHelper(Context context) {
        super(context, "appdata.db", null, 1);
    }
    public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(CREATE_ADDRESS);
        }catch (Exception e){

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public SQLiteDatabase getDb() {
      return this.getWritableDatabase();
    }
}
