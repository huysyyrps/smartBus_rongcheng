package main;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class SharedPreferencesHelper {
    //存储的sharedpreferences文件�?
    private static final String FILE_NAME = "save_file_name";
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;

    public SharedPreferencesHelper(Context mContext, String preferenceName) {
        preferences = mContext.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 保存数据到文�?
     * @param context
     * @param key
     * @param data
     */
    public static void saveData(Context context, String key,String data){

        String type = data.getClass().getSimpleName();
        editor.putString(key, String.valueOf(data));
        editor.commit();
    }

    /**
     * 保存map
     */
    public static void saveMap(Context context, String key, List<Map<String, String>> datas) {
        JSONArray mJsonArray = new JSONArray();
        for (int i = 0; i < datas.size(); i++) {
            Map<String, String> itemMap = datas.get(i);
            Iterator<Map.Entry<String, String>> iterator = itemMap.entrySet().iterator();
            JSONObject object = new JSONObject();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                try {
                    object.put(entry.getKey(), entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mJsonArray.put(object);
        }

        editor.putString(key, mJsonArray.toString());
        editor.commit();
    }


    /**
     * 保存List
     * @param tag
     * @param datalist
     */
    public <T> void saveList(String tag, List<T> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        editor.clear();
        editor.putString(tag, strJson);
        editor.commit();

    }

    /**
     * 获取lIst
     * @param tag
     * @return
     */
    public <T> List<T> getList(String tag) {
        List<T> datalist=new ArrayList<T>();
        String strJson = preferences.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
        }.getType());
        return datalist;

    }
    /**
     * 取出map
     * */
    public static List<Map<String, String>> getMap(Context context, String key) {
        List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
        String result = preferences.getString(key, "");
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject itemObject = array.getJSONObject(i);
                Map<String, String> itemMap = new HashMap<String, String>();
                JSONArray names = itemObject.names();
                if (names != null) {
                    for (int j = 0; j < names.length(); j++) {
                        String name = names.getString(j);
                        String value = itemObject.getString(name);
                        itemMap.put(name, value);
                    }
                }
                datas.add(itemMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return datas;
    }


    /**
     * 从文件中读取数据
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static String getData(Context context, String key, Object defValue){

        String type = defValue.getClass().getSimpleName();
        return preferences.getString(key, (String)defValue);
    }
    /**
     * 删除数据
     */
    public static void removeData(Context context){
        preferences.edit().clear().commit();
    }
}
