package main.smart.common.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.smart.bus.bean.AdvertBean;
import main.smart.bus.bean.InterfaceBean;
import main.smart.bus.bean.NoticeInfo;
import main.smart.bus.bean.StationBean;
import main.smart.bus.bean.ZDXX;
import main.smart.common.bean.SwitchCity;
import main.smart.common.util.ConstData;
import main.smart.common.util.PreferencesHelper;

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
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class DBHandler {
	
	private String newwebIp="";
	private String newbsPort="";
	private String socketPort="";
	
	private static String weather="https://www.tianqiapi.com/api/?version=v1&cityid=";
	private static String  citycode="https://cdn.huyahaha.com/tianqiapi/city.json";
	// ��ѯվ��汾
	public String getVerson(String url, String id) {
		String versor = "";
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 6000);
		List<NameValuePair> nvs = new ArrayList<NameValuePair>();
		nvs.add(new BasicNameValuePair("version", id));
		HttpPost httpRequst = new HttpPost(url);
		try {
			// ��������ӵ�POST������
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvs, "utf-8");
			httpRequst.setEntity(uefEntity);
			// ��������
			HttpResponse res = httpClient.execute(httpRequst);
			HttpEntity entity = res.getEntity();
			// ��ȡ����ֵ
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			reader.close();
			JSONObject json = new JSONObject(sb.toString());
			if (json.length() > 0) {
				versor = json.get("versionNumber").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versor;
	}

	// �޸�����
	public String cheengPassWord(String url, String phone, String password) {
		String versor = "";
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 6000);
		List<NameValuePair> nvs = new ArrayList<NameValuePair>();
		nvs.add(new BasicNameValuePair("account", phone));
		nvs.add(new BasicNameValuePair("newPwd", password));
		HttpPost httpRequst = new HttpPost(url);
		try {
			// ��������ӵ�POST������
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvs, "utf-8");
			httpRequst.setEntity(uefEntity);
			// ��������
			HttpResponse res = httpClient.execute(httpRequst);
			HttpEntity entity = res.getEntity();
			// ��ȡ����ֵ
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			reader.close();
			JSONObject json = new JSONObject(sb.toString());
			if (json.length() > 0) {
				versor = json.get("versionNumber").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versor;
	}

	// ����վ������
	public List<ZDXX> getResult(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
		List<NameValuePair> nvs = new ArrayList<NameValuePair>();
		HttpPost httpRequst = new HttpPost(url);
		List<ZDXX> list = new ArrayList<ZDXX>();
		try {
			
			// ��������ӵ�POST������
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvs, "utf-8");
			httpRequst.setEntity(uefEntity);
			// ��������
			HttpResponse res = httpClient.execute(httpRequst);
			HttpEntity entity = res.getEntity();
			// ��ȡ����ֵ
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			reader.close();
			JSONObject json = new JSONObject(sb.toString());

			JSONArray jsonArg = json.getJSONArray("list");
			// /������ֵ
			if (json != null) {
				for (int i = 0; i < jsonArg.length(); i++) {
					json = jsonArg.getJSONObject(i);
					ZDXX zdxx = new ZDXX();
					String xlname = "";
					String zdname = json.get("ZDNAME").toString();

					if (json.get("XLMC") != null) {
						xlname = json.get("XLMC").toString();
					}
					int xl = Integer.parseInt(json.get("XL").toString());
					int zd = Integer.parseInt(json.get("ZD").toString());// վ�����
					int sxx = Integer.parseInt(json.get("SXX").toString());
					int jd = Integer.parseInt(json.get("JD").toString());
					int wd = Integer.parseInt(json.get("WD").toString());

					zdxx.setSxx(sxx);
					zdxx.setXl(xl);
					zdxx.setZd(zd);
					zdxx.setZdname(zdname);
					zdxx.setJd(jd);
					zdxx.setWd(wd);
					zdxx.setXlname(xlname);

					list.add(zdxx);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// ��ȡ�����б�
	public List<SwitchCity> getCityList(String url) {
		// String url="http://123.232.38.10:9999/androidData";
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
		List<NameValuePair> nvs = new ArrayList<NameValuePair>();
		HttpPost httpRequst = new HttpPost(url);
		List<SwitchCity> list = new ArrayList<SwitchCity>();
		try {
			// ��������ӵ�POST������
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvs, "utf-8");
			httpRequst.setEntity(uefEntity);

			// ��������
			HttpResponse res = httpClient.execute(httpRequst);
			HttpEntity entity = res.getEntity();

			// ��ȡ����ֵ
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			reader.close();
			JSONArray jsonArg = new JSONArray(sb.toString());
			// /������ֵ
			if (jsonArg != null) {
				
				
				for (int i = 0; i < jsonArg.length(); i++) {
					JSONObject json = jsonArg.getJSONObject(i);
					SwitchCity sc = new SwitchCity();
					sc.setCenterX(json.get("Center_X").toString());
					sc.setCenterY(json.get("Center_Y").toString());
					sc.setIp(json.get("IP").toString());
					sc.setGoServerPort(json.get("Socket_Port").toString());
					sc.setUrl("http://" + json.get("IP").toString() + ":" + json.get("BS_Port").toString()
							+ "/sdhyschedule/PhoneQueryAction");
					if (json.get("CityCode").toString().equals("257000")){
						sc.setUrl("http://123.134.33.30"  + ":" +  "7012/sdhyschedule/PhoneQueryAction");
						
					}else {
						sc.setUrl("http://" + json.get("IP").toString() + ":" + json.get("BS_Port").toString()
								+ "/sdhyschedule/PhoneQueryAction");
					}
					sc.setCityCode(Integer.parseInt(json.get("CityCode").toString()));
					sc.setCityName(json.get("CityName").toString());
					
					
//					sc.setIp("123.134.33.30");
//					sc.setGoServerPort("7011");
//					sc.setUrl("http://123.134.33.30"  + ":" +  "80/sdhyschedule/PhoneQueryAction");
//					sc.setCityCode(Integer.parseInt(json.get("CityCode").toString()));
//					sc.setCityName("������");
					
					Log.e(null, json.get("CityName").toString()+"��ȡ�����ĳ����б�"+sc);
					list.add(sc);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	// ��ȡ��վʱ��
	public String getCometime(String url, String line, int sxx, int zd) {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 6000);
		List<NameValuePair> nvs = new ArrayList<NameValuePair>();
		nvs.add(new BasicNameValuePair("xl", line));
		nvs.add(new BasicNameValuePair("zd", zd + ""));
		nvs.add(new BasicNameValuePair("sxx", sxx + ""));
		HttpPost httpRequst = new HttpPost(url);
		try {
			// ��������ӵ�POST������
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvs, "utf-8");
			httpRequst.setEntity(uefEntity);
			// ��������
			HttpResponse res = httpClient.execute(httpRequst);
			HttpEntity entity = res.getEntity();
			// ��ȡ����ֵ
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String str = reader.readLine();
			if (str == null) {
				return "";
			} else {
				return str;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// ��ѯ����ʱ��
	public String getBusTime(String url, String line, String sxx) {
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 6000);
		List<NameValuePair> nvs = new ArrayList<NameValuePair>();
		nvs.add(new BasicNameValuePair("xl", line));
		nvs.add(new BasicNameValuePair("sxx", sxx));
		HttpPost httpRequst = new HttpPost(url);

		try {
			// ��������ӵ�POST������
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvs, "utf-8");
			httpRequst.setEntity(uefEntity);
			// ��������
			HttpResponse res = httpClient.execute(httpRequst);
			HttpEntity entity = res.getEntity();
			// ��ȡ����ֵ
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String str = reader.readLine();
			if (str == null) {
				return "";
			} else {
				return str;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// ��ȡ������Ϣ
	// public String getNoticeInfo(String url,int pos, int len,
	// ArrayList<String> titleList,ArrayList<String> contentList){
	//
	// HashMap<String,String> map = new HashMap<String,String>();
	// HttpClient httpClient = new DefaultHttpClient();
	// httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
	// 10000);
	// //httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
	// 6000);
	// List<NameValuePair> nvs = new ArrayList<NameValuePair>();
	// nvs.add(new BasicNameValuePair("B",String.valueOf(pos)));
	// nvs.add(new BasicNameValuePair("Len", String.valueOf(len)));
	// HttpPost httpRequst = new HttpPost(url);
	//
	// try {
	// // ��������ӵ�POST������
	// UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvs,"utf-8");
	// httpRequst.setEntity(uefEntity);
	// // ��������
	// HttpResponse res = httpClient.execute(httpRequst);
	// HttpEntity entity = res.getEntity();
	//// // ��ȡ����ֵ
	//// BufferedReader reader = new BufferedReader(new
	// InputStreamReader(entity.getContent()));
	//// String str= reader.readLine();
	//
	// // ��ȡ����ֵ
	// BufferedReader reader = new BufferedReader(new
	// InputStreamReader(entity.getContent()));
	// StringBuffer sb = new StringBuffer();
	// String line = null;
	// while ((line = reader.readLine()) != null) {
	// sb.append(line + "\n");
	// }
	// reader.close();
	//
	//// if(str==null){
	//// return null;
	//// }else{
	//
	// JSONObject json = new JSONObject(sb.toString());
	// if(json != null)
	// {
	//
	// JSONArray jsonArr = json.getJSONArray("Data");
	// //JSONObject json = new JSONObject(str);
	// if(jsonArr != null)
	// {
	// for (int i = 0; i < jsonArr.length(); i++)
	// {
	// JSONObject jData = jsonArr.getJSONObject(i);
	// String title = jData.getString("Title");
	// String content = jData.getString("Content");
	// titleList.add(title);
	// contentList.add(content);
	// }
	//
	//
	// }
	// String jEnd = json.getString("IsEnd");
	// return jEnd;
	// }
	// return null;
	//
	// //}
	// }catch (Exception e) {
	// e.printStackTrace();
	// return null;
	// }
	// }

	// ��ȡ������Ϣ
	public String getNoticeInfo(String url, int pos, int len, String date, ArrayList<NoticeInfo> nList) {

		HashMap<String, String> map = new HashMap<String, String>();
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		// httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
		// 6000);
		List<NameValuePair> nvs = new ArrayList<NameValuePair>();
		nvs.add(new BasicNameValuePair("B", String.valueOf(pos)));
		nvs.add(new BasicNameValuePair("Len", String.valueOf(len)));
		Log.d("noticenvspos", pos + "");
		Log.d("noticenvslen", len + "");
		Log.d("noticenvsurl", url);
		if (!date.equals("") && date != null) {
			nvs.add(new BasicNameValuePair("Date", date));
			Log.d("noticedate", date);
		}
		HttpPost httpRequst = new HttpPost(url);

		try {
			// ��������ӵ�POST������
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvs, "utf-8");
			httpRequst.setEntity(uefEntity);
			// ��������
			HttpResponse res = httpClient.execute(httpRequst);
			HttpEntity entity = res.getEntity();
			// // ��ȡ����ֵ
			// BufferedReader reader = new BufferedReader(new
			// InputStreamReader(entity.getContent()));
			// String str= reader.readLine();

			// ��ȡ����ֵ
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			reader.close();

			// if(str==null){
			// return null;
			// }else{

			JSONObject json = new JSONObject(sb.toString());
			if (json != null) {

				JSONArray jsonArr = json.getJSONArray("Data");
				// JSONObject json = new JSONObject(str);
				if (jsonArr != null) {

					for (int i = 0; i < jsonArr.length(); i++) {
						JSONObject jData = jsonArr.getJSONObject(i);
						String title = jData.getString("Title");
						String content = jData.getString("Content");
						String strDate = null;
						try {
							strDate = jData.getString("Date");
						} catch (Exception e) {
							// TODO: handle exception
							strDate = null;
						}

						NoticeInfo info = new NoticeInfo();
						info.setTitle(title);
						info.setInfo(content);
						if (strDate != null) {
							info.setDate(strDate);
						} else {
							info.setDate("");
						}
						nList.add(info);
					}

				}
				String jEnd = json.getString("IsEnd");
				return jEnd;
			}
			return null;

			// }
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ��ȡ�������
	public String getNoticeInfo_(String url, int pos, int len, String date) {

		HashMap<String, String> map = new HashMap<String, String>();
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		// httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
		// 6000);
		List<NameValuePair> nvs = new ArrayList<NameValuePair>();
		nvs.add(new BasicNameValuePair("B", String.valueOf(pos)));
		nvs.add(new BasicNameValuePair("Len", String.valueOf(len)));
		Log.d("noticenvspos", pos + "");
		Log.d("noticenvslen", len + "");
		Log.d("noticenvsurl", url);
		if (!date.equals("") && date != null) {
			nvs.add(new BasicNameValuePair("Date", date));
			Log.d("noticedate", date);
		}
		HttpPost httpRequst = new HttpPost(url);

		try {
			// ��������ӵ�POST������
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvs, "utf-8");
			httpRequst.setEntity(uefEntity);
			// ��������
			HttpResponse res = httpClient.execute(httpRequst);
			HttpEntity entity = res.getEntity();
			// // ��ȡ����ֵ
			// BufferedReader reader = new BufferedReader(new
			// InputStreamReader(entity.getContent()));
			// String str= reader.readLine();

			// ��ȡ����ֵ
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			reader.close();

			// if(str==null){
			// return null;
			// }else{

			JSONObject json = new JSONObject(sb.toString());
			if (json != null) {
				JSONArray jsonArr = json.getJSONArray("Data");
				// JSONObject json = new JSONObject(str);
				if (jsonArr != null) {
					JSONObject jsonObject=jsonArr.getJSONObject(0);
					String title = jsonObject.getString("Title");
					return title;
				}			
			}
			return null;

			// }
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ��ѯ���沼��
	public ArrayList<InterfaceBean> getInterface(String url, PreferencesHelper helper) {
		ArrayList<InterfaceBean> inList = null;
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 6000);
		List<NameValuePair> nvs = new ArrayList<NameValuePair>();
		HttpPost httpRequst = new HttpPost(url);

		HashMap<String, AdvertBean> adList = new HashMap<String, AdvertBean>();
		try {
			// ��������ӵ�POST������
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvs, "utf-8");
			httpRequst.setEntity(uefEntity);
			// ��������
			HttpResponse res = httpClient.execute(httpRequst);
			HttpEntity entity = res.getEntity();

			// ��ȡ����ֵ
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer sb = new StringBuffer();
			String str = null;
			while ((str = reader.readLine()) != null) {
				sb.append(str + "\n");
			}
			reader.close();

			JSONObject json = new JSONObject(sb.toString());
			Log.d("Interface json", sb.toString());
			if (json.length() > 0) {
				inList = new ArrayList<InterfaceBean>();
				try {// �Զ�������ȡ
					JSONArray jsonArg = json.getJSONArray("Interface");

					for (int i = 0; i < jsonArg.length(); i++) {
						InterfaceBean bean = new InterfaceBean();
						JSONObject obj = jsonArg.getJSONObject(i);
						String title = obj.getString("title");
						String icon = obj.getString("icon");
						String iconType = obj.getString("iconType");
						String webURL = obj.getString("webURL");

						bean.setTitle(title);
						bean.setIcon(icon);
						bean.setIconType(iconType);
						bean.setWebURL(webURL);

						inList.add(bean);

					}
				} catch (Exception e) {
					// TODO: handle exception
					Log.d("DBHandler", "��ȡ�Զ������ʧ��");
				}

				try {// ����ȡ:advertisement
						// JSONObject object =
						// json.getJSONObject("advertisement");
					String ver = json.getString("adVersion");
					int verCode = Integer.valueOf(ver);
					ConstData.adVer = verCode;
					if (verCode != helper.getADVersion()) {
						ConstData.bUpdateAD = true;

					} else {
						ConstData.bUpdateAD = false;
					}
					JSONArray jsonArg = json.getJSONArray("advertisement");
					ConstData.adPageMap.clear();
					for (int i = 0; i < jsonArg.length(); i++) {
						JSONObject obj = jsonArg.getJSONObject(i);
						AdvertBean adBean = getSinglePageAdvert(obj);

						ConstData.adPageMap.put(adBean.getPage(), adBean);
					}
					// 20170222 GPX ���ӹ���������ʾ
					if (json.has("hotLine")) {
						ConstData.busHotLine = json.getString("hotLine");
					} else {
						ConstData.busHotLine = "";
					}
				} catch (Exception e) {
					// TODO: handle exception
					Log.d("DBHandler", "��ȡ�����Ϣʧ��");
				}
			}

			return inList;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public AdvertBean getSinglePageAdvert(JSONObject json) throws JSONException {
		AdvertBean adBean = new AdvertBean();

		JSONArray jsonArg = json.getJSONArray("display");
		String type = json.getString("showType");
		String delay = json.getString("delay");
		String page = json.getString("page");
		int d = Integer.valueOf(delay);
		ArrayList<InterfaceBean> inList = new ArrayList<InterfaceBean>();
		for (int i = 0; i < jsonArg.length(); i++) {
			InterfaceBean bean = new InterfaceBean();
			JSONObject obj = jsonArg.getJSONObject(i);
			String content = obj.getString("content");
			String webURL = obj.getString("webURL");

			if (type.equals("0")) {
				if (content != null && !content.equals("")) {
					int n = content.lastIndexOf("/");
					String fileName = content.substring(n + 1);
					bean.setPath(fileName);
				}
			}
			bean.setIcon(content);
			bean.setWebURL(webURL);

			inList.add(bean);
		}
		adBean.setPage(page);
		adBean.setDelay(d);
		adBean.setList(inList);
		adBean.setShowType(type);
		Log.d("DBHandler", "interfaceSerial=" + String.valueOf(adBean.getInterfaceSerial()));

		return adBean;
	}

	// ��ȡ���µĹ�������
	public String getNoticeLastDate(String url) {

		HashMap<String, String> map = new HashMap<String, String>();
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		// httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
		// 6000);
		// List<NameValuePair> nvs = new ArrayList<NameValuePair>();
		// nvs.add(new BasicNameValuePair("B",String.valueOf(pos)));
		// nvs.add(new BasicNameValuePair("Len", String.valueOf(len)));
		HttpPost httpRequst = new HttpPost(url);

		try {
			// ��������ӵ�POST������
			// UrlEncodedFormEntity uefEntity = new
			// UrlEncodedFormEntity(nvs,"utf-8");
			// httpRequst.setEntity(uefEntity);
			// ��������
			HttpResponse res = httpClient.execute(httpRequst);
			HttpEntity entity = res.getEntity();
			// // ��ȡ����ֵ
			// BufferedReader reader = new BufferedReader(new
			// InputStreamReader(entity.getContent()));
			// String str= reader.readLine();

			// ��ȡ����ֵ
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			reader.close();

			// if(str==null){
			// return null;
			// }else{

			JSONObject json = new JSONObject(sb.toString());
			if (json != null) {

				String strDate = json.getString("NoticeLastDate");
				// JSONObject json = new JSONObject(str);
				if (strDate != null) {
					return strDate;

				}

			}
			return null;

			// }
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ��ȡ���µĹ�������
	public List<StationBean> getBaiduLineSpot(String url, String xl, String sxx) {

		List<StationBean> list = null;
		HashMap<String, String> map = new HashMap<String, String>();
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		// httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
		// 6000);
		List<NameValuePair> nvs = new ArrayList<NameValuePair>();
		nvs.add(new BasicNameValuePair("xl", String.valueOf(xl)));
		nvs.add(new BasicNameValuePair("sxx", String.valueOf(sxx)));
		HttpPost httpRequst = new HttpPost(url);

		try {
			// ��������ӵ�POST������
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvs, "utf-8");
			httpRequst.setEntity(uefEntity);
			// ��������
			HttpResponse res = httpClient.execute(httpRequst);
			HttpEntity entity = res.getEntity();
			// // ��ȡ����ֵ
			// BufferedReader reader = new BufferedReader(new
			// InputStreamReader(entity.getContent()));
			// String str= reader.readLine();

			// ��ȡ����ֵ
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuffer sb = new StringBuffer();
			String str = null;
			while ((str = reader.readLine()) != null) {
				sb.append(str + "\n");
			}
			reader.close();

			// if(str==null){
			// return null;
			// }else{

			Log.d("DBHandler:linePot", sb.toString());
			JSONObject json = new JSONObject(sb.toString());
			if (json != null) {

				list = new ArrayList<StationBean>();
				String type = json.getString("Type");
				String status = json.getString("Status");
				JSONArray jsonArray = json.getJSONArray("Data");
				if (status.equals("0")) {
					return null;
				}
				if (type.equals("2")) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject obj = jsonArray.getJSONObject(i);
						double jd = Double.parseDouble(obj.getString("JD"));
						double wd = Double.parseDouble(obj.getString("WD"));
						StationBean bean = new StationBean();
						bean.setLat(wd);
						bean.setLng(jd);
						list.add(bean);

					}

				}
				return list;
			} else {
				return null;
			}
			// }
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * ����
	 * */
	@SuppressWarnings("deprecation")
	public static String getweaather( String city){
	  	String result="";
	  	BasicHttpParams httpParams = new BasicHttpParams();  
	    HttpConnectionParams.setConnectionTimeout(httpParams, 10*1000);  
	    HttpConnectionParams.setSoTimeout(httpParams, 10*1000); 
	    
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		// ���Ͳ�����װ
		List<NameValuePair> nvs = new ArrayList<NameValuePair>();
		
		//���ż���
		HttpPost httpRequst = new HttpPost(weather+city);
		try {
			// ��������ӵ�POST������
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvs, "utf-8");
			httpRequst.setEntity(uefEntity);
		
			Log.e(null, "###########################################"+weather+city);
			// ��������
			HttpResponse res = httpClient.execute(httpRequst);
			Log.e(null, "3333999999###########################################"+res.getStatusLine().getStatusCode());
			// ��״̬���л�ȡ״̬�룬�ж���Ӧ���Ƿ����Ҫ��   ���������Ӧ����200�����ʾ�ɹ�
			if (res.getStatusLine().getStatusCode() == 200) {  
				HttpEntity entity = res.getEntity();
				// ��ȡ����ֵ
				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(),"utf-8"));
				StringBuffer sb = new StringBuffer();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				reader.close();
				result=sb.toString();
				Log.e(null, "11113333999999###########################################"+result);
			}   
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result="�޷����ӷ�����";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(null, "�޷����ӷ�����"+e);
			result="�޷����ӷ�����";
		}  catch (Exception e) {
			 Log.e(null, Log.getStackTraceString(e));
			 result="�޷����ӷ�����";
		}
		return result;
	}
	
	
	/**
	 * ����
	 * */
	@SuppressWarnings("deprecation")
	public static String getcitycode( ){
	  	String result="";
	  	BasicHttpParams httpParams = new BasicHttpParams();  
	    HttpConnectionParams.setConnectionTimeout(httpParams, 10*1000);  
	    HttpConnectionParams.setSoTimeout(httpParams, 10*1000); 
	    
		HttpClient httpClient = new DefaultHttpClient(httpParams);
		// ���Ͳ�����װ
		List<NameValuePair> nvs = new ArrayList<NameValuePair>();
		
		//���ż���
		HttpPost httpRequst = new HttpPost(citycode);
		try {
			// ��������ӵ�POST������
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvs, "utf-8");
			httpRequst.setEntity(uefEntity);
		
			Log.e(null, "###########################################"+citycode);
			// ��������
			HttpResponse res = httpClient.execute(httpRequst);
			Log.e(null, "3333999999###########################################"+res.getStatusLine().getStatusCode());
			// ��״̬���л�ȡ״̬�룬�ж���Ӧ���Ƿ����Ҫ��   ���������Ӧ����200�����ʾ�ɹ�
			if (res.getStatusLine().getStatusCode() == 200) {  
				HttpEntity entity = res.getEntity();
				// ��ȡ����ֵ
				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(),"utf-8"));
				StringBuffer sb = new StringBuffer();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				reader.close();
				result=sb.toString();
				Log.e(null, "11113333999999###########################################"+result);
			}   
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e(null, "�޷����ӷ�����"+e);
		}  catch (Exception e) {
			 Log.e(null, Log.getStackTraceString(e));
		}
		return result;
	}
	//��ѯƱ��
	public String getTicketPrice(String url,String line,int sxx)
	{
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 6000);
		List<NameValuePair> nvs = new ArrayList<NameValuePair>();
		nvs.add(new BasicNameValuePair("xl",line));
		nvs.add(new BasicNameValuePair("sxx",String.valueOf(sxx)));
		HttpPost httpRequst = new HttpPost(url);

		try {
			// ��������ӵ�POST������
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(nvs,"utf-8");
			httpRequst.setEntity(uefEntity);
			// ��������
			HttpResponse res = httpClient.execute(httpRequst);
			HttpEntity entity = res.getEntity();
			// ��ȡ����ֵ
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
			String str= reader.readLine();
			if(str==null){
				String txt = "��Ӫʱ�䣺�� Ʊ�ۣ���";
				return txt;
			}else{

				JSONObject  json =new JSONObject(str);
				if(json!=null)
				{
					String pj = json.getString("PJ");

					String time = json.getString("RunTime");
					String txt = "��Ӫʱ�䣺";
					if (!time.equals(""))
					{
						txt =  pj+"Ԫ";
					}
					else
					{
						txt = txt + "��";
					}
//		         		   txt = txt + " " + "Ʊ�ۣ�";
//		         		   if (pj != null)
//		         		   {
//		         			   txt = txt +pj+"Ԫ";
//		         		   }
//		         		   else
//		         		   {
//		         			   txt = txt + "��";
//		         		   }
					return txt ;
				}
				else
				{
					String txt = "��Ӫʱ�䣺��";// Ʊ�ۣ���";
					return txt;
				}


			}
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}
	
	/**
	 * ��ȡȫ����·��Ϣ
	 * */
//		public void getAllLine() {
//			// 3.��ѯsqlserver���ݿ��������뱾��
//			
//			String url = ConstData.URL + "!getLineInfo.shtml";
////			String url = ConstData.goURL + "/getLineList";
//			
//			RequestParams param = new RequestParams();
//			
//			RequstClient.post(url,
//			param, new LoadCacheResponseLoginouthandler(SmartBusApp.getInstance(),
//					new LoadDatahandler() {
//						@Override
//						public void onStart() {
//							super.onStart();
//							
//							Log.d("getLineInfo:", "get line  data");
//						}
//
//						@Override
//						public void onSuccess(String data) {
//							super.onSuccess(data);
//							try {
////								JSONArray lineArr = new JSONArray(data.toString());
//								JSONObject json = new JSONObject(data.toString());
//								JSONArray lineArr=json.getJSONArray("lineList");
//
//								if((lineArr.getJSONObject(0).getString("webIp"))!=null){
//									if(!(lineArr.getJSONObject(0).getString("webIp")).equals("")){
//										SwitchCity sc = new SwitchCity();
//										sc.setIp(lineArr.getJSONObject(0).getString("webIp"));
//										sc.setGoServerPort(lineArr.getJSONObject(0).getString("socketPort"));
//										sc.setUrl("http://"  + lineArr.getJSONObject(0).getString("webIp")+":" +lineArr.getJSONObject(0).getString("bsPort")  +"/sdhyschedule/PhoneQueryAction");
//									}else {
//										Log.e(null, "`````````````"+lineArr.getJSONObject(0)
//										.getString("webIp"));	
//									}
//								}
//							} catch (JSONException e) {
//								// TODO Auto-generated catch block
//								Log.e("���ݷ��ش���", "δ�������ص���·վ��");
//							}
//						}
//
//						@Override
//						public void onFailure(String error, String message) {
//							super.onFailure(error, message);
//							Log.e("�������ݿ����", "�������粻ͨ��Ip��ַ����");
//						}
//
//						@Override
//						public void onFinish() {
//							super.onFinish();
//						}
//					}));
//
//		}
}
