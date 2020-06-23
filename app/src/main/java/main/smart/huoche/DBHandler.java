package main.smart.huoche;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;


import android.util.Log;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 与服务器交互查询数据库
 */
public class DBHandler {
	private static OkHttpClient client ;
	private static String URL="http://api.jisuapi.com/train/ticket";
	//http://api.jisuapi.com/train/ticket?appkey=db0da73a73abd23c&start=%E5%8C%97%E4%BA%AC&end=%E6%9D%AD%E5%B7%9E&date=2019-03-29
	

	/**
	 * 根据卡号查询充值记录
	 */
	@SuppressWarnings("deprecation")
	public static String getRecord( Map<String, String> map) {
		String result = "";

		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 10 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 10 * 1000);

		HttpClient httpClient = new DefaultHttpClient(httpParams);
		// 发送参数组装
		List<NameValuePair> nvs = new ArrayList<NameValuePair>();
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			nvs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		// 卡号加密
		HttpPost httpRequst = new HttpPost(URL);
		Log.e(null, "***************走了吗******"+nvs );
		try {
			// 将参数添加的POST请求中
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvs, "utf-8");
			Log.e(null, "***************zheline******" );
			httpRequst.setEntity(uefEntity);
			Log.e(null, "***************1111111111******");
			Log.d("查询", URL);
			// 发送请求
			HttpResponse res = httpClient.execute(httpRequst);

			Log.e(null, "**********************" + res.getStatusLine().getStatusCode());
			// 从状态行中获取状态码，判断响应码是否符合要求 如果请求响应码是200，则1表示成功
			if (res.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = res.getEntity();
				// 读取返回值
				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "utf-8"));
				StringBuffer sb = new StringBuffer();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				reader.close();
				result = sb.toString();
			} else {
				result = "无法连接服务器";
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			Log.e(null, "!!!!!!**********************" + e);
			result = "无法连接服务器";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(null, "@@@@@@@@@**********************" + e);
			result = "无法连接服务器";
		} catch (Exception e) {
			Log.e(null, "$$$$$$$$$$$$**********************" + e);
			result = "无法连接服务器";
		}
		return result;
	}
	
	
	
	
	
	
	public static void okrun()
    {
        client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("http://api.jisuapi.com/train/ticket?appkey=db0da73a73abd23c&start=北京&end=杭州&date=2019-03-29")
                
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                Log.e(null, "$$$$$$$$$$$$**********************" + response.body().string());
              
            }
        });

    }
}
