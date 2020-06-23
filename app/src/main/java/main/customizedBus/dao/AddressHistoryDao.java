package main.customizedBus.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import java.util.ArrayList;
import java.util.List;

import main.customizedBus.home.bean.AddressBean;
import main.utils.sqliteDB.SqliteHelper;

public class AddressHistoryDao  {
    private SqliteHelper helper;
    private final static String  tableName = "address_history";
    private Context context;

    //单例
    private static AddressHistoryDao addressHistoryDao;
    public  static AddressHistoryDao getInstance(Context context){
        if (null==addressHistoryDao){
            synchronized (AddressHistoryDao.class){
                if (null==addressHistoryDao){
                    addressHistoryDao = new AddressHistoryDao(context);
                }
            }
        }
        return addressHistoryDao;
    }

    public AddressHistoryDao(Context context) {
        this.context = context;
        helper = new SqliteHelper(context);
    }

    private void createTable(){


    }

    public void insert(AddressBean bean){
      //  helper.getDb().execSQL("insert into address_history(name,addr,province,city,area,latitude,longitude) values(?,?,?,?,?,?,?)",new String[]{bean.getName(),bean.getAddr(),bean.getProvince(),bean.getCity(),bean.getArea(),bean.getLatitude(),bean.getLongitude()});
      if (getAllDataInName(bean.getName()).size()>0){
          return;
      }
       try {
           ContentValues contentValues = new ContentValues();
           contentValues.put("name",bean.getName());
           contentValues.put("addr",bean.getAddr());
           contentValues.put("province",bean.getProvince());
           contentValues.put("city",bean.getCity());
           contentValues.put("area",bean.getArea());
           contentValues.put("latitude",bean.getLatitude());
           contentValues.put("longitude",bean.getLongitude());
           helper.getDb().insert("address_history",null,contentValues);
       }catch (Exception e){

       }
    }


    public List<AddressBean> getAllData(){
        List<AddressBean> list = new ArrayList<AddressBean>();
        SQLiteDatabase db = helper.getReadableDatabase();
       try {
           String sql = "select * from '" + tableName + "'order by id desc ";
           Cursor cursor = db.rawQuery(sql, null);
           while(cursor.moveToNext()){
               String name = cursor.getString(cursor.getColumnIndex("name"));
               String addr = cursor.getString(cursor.getColumnIndex("addr"));
               String province = cursor.getString(cursor.getColumnIndex("province"));
               String city = cursor.getString(cursor.getColumnIndex("city"));
               String area = cursor.getString(cursor.getColumnIndex("area"));
               double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
               double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
               AddressBean bean = new AddressBean();
               bean.setName(name);
               bean.setAddr(addr);
               bean.setProvince(province);
               bean.setCity(city);
               bean.setArea(area);
               bean.setLatitude(latitude);
               bean.setLongitude(longitude);
               list.add(bean);
           }
       }catch (Exception e){

       }
        return list;
    }

    public List<AddressBean> getAllDataInName(String namestr){
        List<AddressBean> list = new ArrayList<AddressBean>();
        SQLiteDatabase db = helper.getWritableDatabase();
        try {

            Cursor cursor = db.rawQuery("select * from  '" + tableName + "'where name = '" + namestr + "'", null);
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String addr = cursor.getString(cursor.getColumnIndex("addr"));
                String province = cursor.getString(cursor.getColumnIndex("province"));
                String city = cursor.getString(cursor.getColumnIndex("city"));
                String area = cursor.getString(cursor.getColumnIndex("area"));
                double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                AddressBean bean = new AddressBean();
                bean.setName(name);
                bean.setAddr(addr);
                bean.setProvince(province);
                bean.setCity(city);
                bean.setArea(area);
                bean.setLatitude(latitude);
                bean.setLongitude(longitude);
                list.add(bean);
            }
        }catch (Exception e){

        }
        return list;
    }

}
