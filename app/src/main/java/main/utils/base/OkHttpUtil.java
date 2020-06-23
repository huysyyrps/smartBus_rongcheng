package main.utils.base;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import main.SharedPreferencesHelper;
import main.utils.utils.MyApplication;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {
    private volatile static OkHttpUtil okHttpUtil;//�ᱻ���߳�ʹ�ã�����ʹ�ùؼ���volatile
    private OkHttpClient client;
    private Handler mHandler;
    //˽�л����췽��
    private OkHttpUtil(Context context){
        File sdcache = context.getExternalCacheDir();
        int cacheSize = 10 * 1024 *1024;//���û����С
        OkHttpClient.Builder builder= new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20,TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(),cacheSize));//���û����·��
        client = builder.build();
        mHandler = new Handler();
    }
    //����ģʽ��ȫ�ֵõ�һ��OkHttpUtil����
    public static OkHttpUtil getInstance(Context context){
        if (okHttpUtil == null){
            synchronized (OkHttpUtil.class){
                if (okHttpUtil == null){
                    okHttpUtil = new OkHttpUtil(context);
                }
            }
        }
        return okHttpUtil;
    }

    /**get�첽����
     * @param url
     * @param callback
     */
    public void getAsynHttp(String url, final ResultCallback callback){
        String Session = new SharedPreferencesHelper(MyApplication.getContext(), "login").getData(MyApplication.getContext(), "session", "");
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie",Session)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedCallback(call,e,callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendSuccessCallback(response,callback);
            }
        });
    }

    /**�ύ������
     * @param url
     * @param map
     * @param callback
     */
    public void postForm(String url, Map<String,String > map, final ResultCallback callback){
        FormBody.Builder form = new FormBody.Builder();//�����󣬰�����input��ʼ�Ķ���,��html��Ϊ��
        if (map != null && !map.isEmpty()){
            //����Map����
            for(Map.Entry<String ,String> entry : map.entrySet()){
                form.add(entry.getKey(),entry.getValue());
            }
            RequestBody body = form.build();
            Request request = new Request.Builder().url(url).post(body).build();//����post�ύ����
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    sendFailedCallback(call,e,callback);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()&&response != null){
                        sendSuccessCallback(response,callback);
                    }
                }
            });
        }

    }

    /**������ʧ��ʱ����������������
     * @param call
     * @param e
     * @param callback
     */
    private void sendFailedCallback(final Call call, final IOException e, final ResultCallback callback){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.i("main","��ǰ�̣߳�"+Thread.currentThread().getName());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null){
                            callback.onError(call.request(),e);
                        }
                    }
                }).start();
            }
        });
    }

    /**����ɹ����ø÷���
     * @param response  ���ص�����
     * @param callback �ص��Ľӿ�
     */
    private void sendSuccessCallback(final Response response, final ResultCallback callback){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.i("main","��ǰ�̣߳�"+Thread.currentThread().getName());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null){
                            try {
                                callback.onResponse(response);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
    }

    //�����ӿڣ��ص���������
    public interface ResultCallback{
        void onError(Request request, Exception e);
        void onResponse(Response response) throws IOException;
    }
}