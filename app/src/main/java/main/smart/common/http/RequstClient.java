package main.smart.common.http;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RequstClient {
	/**
	 * ����һ���첽����ͻ��� Ĭ�ϳ�ʱδ5�� ��������Ĭ����������Ϊ5�� Ĭ�����������Ϊ10��
	 */
	private static AsyncHttpClient mClient = new AsyncHttpClient();
	static {
		mClient.setTimeout(15000);
	}
	public static void post(String url, AsyncHttpResponseHandler handler) {
		post(url, null, handler);
	}
	/**
	 * post ����
	 *
	 * @param url
	 *            API ��ַ
	 * @param params
	 *            ����Ĳ���
	 * @param handler
	 *            ���ݼ��ؾ������
	 */
	public static void post(String url, RequestParams params,
							AsyncHttpResponseHandler handler) {
		//System.out.println("����post");
		mClient.post(url, params, handler);
	}
	public static void get(String url, AsyncHttpResponseHandler handler) {

	}
	public static void get(String url, RequestParams params,
						   AsyncHttpResponseHandler handler) {
		System.out.println("����get");
		mClient.get(url, params, handler);
	}
}

