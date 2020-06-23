package main.smart.common.util;
/**
 * ����汾�Զ�����
 * 
 * */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import main.smart.rcgj.R;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;


     public class UpdateVersionManager {
		private Context mContext;
		//��ʾ��
		private String updateMsg = "���°汾";
		//���صİ�װ��url
		private String apkUrl = "/SmartBus.apk";
		private Dialog noticeDialog;
		private Dialog downloadDialog;
		 /* ���ذ���װ·�� */ 
	    private static  String savePath = "/sdcard/updatedemo/";
	    private static  String saveFileName = "";

	    /* ��������֪ͨuiˢ�µ�handler��msg���� */
	    private ProgressBar mProgress;
	    private static final int DOWN_UPDATE = 1;
	    private static final int DOWN_OVER = 2;
	    private int progress;
	    private Thread downLoadThread;
	    private boolean interceptFlag = false;
	    private Handler mHandler = new Handler(){
	    	public void handleMessage(Message msg) {
	    		switch (msg.what) {
				case DOWN_UPDATE:
					mProgress.setProgress(progress);
					break;
				case DOWN_OVER:
					//������ϣ���װ����
					installApk();
					break;
				default:
					break;
				}
	    	};
	    };
	    
		public UpdateVersionManager(Context context) {
			this.mContext = context;
		}
		
		//�ⲿ�ӿ�����Activity����
		public void checkUpdateInfo(String url,int ver,String verName ){
			int oldVer=getVersionCode();
			String oldName=getVersionName();
			apkUrl =url+apkUrl;
			
		 	 if(ver>oldVer){
				//���°汾��Ҫ����
				 updateMsg="�����Ҫ����"+"\n"+"��ǰ�汾"+oldName+"\n"+"���°汾"+verName;
				showNoticeDialog();
	 		}
			
		}
		
		
		private void showNoticeDialog(){
			AlertDialog.Builder builder = new Builder(mContext);
			builder.setTitle("����汾����");
			builder.setMessage(updateMsg);
			builder.setCancelable(false);
			builder.setPositiveButton("����", new OnClickListener() {			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					downloadApk();
					//showDownloadDialog();			
				}
			});
			builder.setNegativeButton("�Ժ���˵", new OnClickListener() {			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();				
				}
			});
			noticeDialog = builder.create();
			noticeDialog.show();
			
		}
		
		private void showDownloadDialog(){
		 	/*AlertDialog.Builder builder = new Builder(mContext);
			builder.setTitle("�����������");
			builder.setCancelable(false);
			final LayoutInflater inflater = LayoutInflater.from(mContext);
			View v = inflater.inflate(R.layout.progress, null);
			mProgress = (ProgressBar)v.findViewById(R.id.progress);
			
		//	builder.setView(v);
			builder.setNegativeButton("ȡ��", new OnClickListener() {	
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					interceptFlag = true;
				}
			});
			
			
			downloadDialog = builder.create();
			downloadDialog.show(); 
			
			downloadApk();*/
			final LayoutInflater inflater = LayoutInflater.from(mContext);
			View v = inflater.inflate(R.layout.progress, null);
			mProgress = (ProgressBar)v.findViewById(R.id.progress);
			ProgressDialog  m_pDialog = new ProgressDialog(mContext);
			// ���ý�������񣬷��ΪԲ�Σ���ת��
			m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// ����ProgressDialog ����
			m_pDialog.setTitle("�汾����");
			// ����ProgressDialog ��ʾ��Ϣ
			m_pDialog.setMessage("�������������Ժ�");
			// ����ProgressDialog ����ͼ��
			m_pDialog.setIcon(R.drawable.ic_launcher);
			// ����ProgressDialog �Ľ������Ƿ���ȷ
			m_pDialog.setIndeterminate(false);
			// ����ProgressDialog �Ƿ���԰��˻ذ���ȡ��
			m_pDialog.setCancelable(true);
            //����ProgressDialog �������������ȡ��
			m_pDialog.setCanceledOnTouchOutside(false);
			// ����ProgressDialog ��һ��Button
			m_pDialog.setButton("ȡ��", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int i){
					interceptFlag=false;
					//�����ȷ����ť��ȡ���Ի���
					dialog.cancel();
				}
			});
			// ��ProgressDialog��ʾ
			m_pDialog.show();
			downloadApk();
			
		}
		//APK�����߳�
		private Runnable mdownApkRunnable = new Runnable() {	
			@Override
			public void run() {
				try {
					 // �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ��  
	                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))  
	                { 
	                	 // ��ô洢����·��  
	                    String sdpath = Environment.getExternalStorageDirectory() + "/";  
	                    savePath = sdpath + "download";  
	                    URL url = new URL(apkUrl);
	    				
						HttpURLConnection conn = (HttpURLConnection)url.openConnection();
						conn.connect();
						int length = conn.getContentLength();
						InputStream is = conn.getInputStream();
						
						File file = new File(savePath);
						if(!file.exists()){
							file.mkdir();
						}
						saveFileName=savePath + "/���Ϲ����ͻ���.apk";
						String apkFile = saveFileName;
						File ApkFile = new File(apkFile);
						FileOutputStream fos = new FileOutputStream(ApkFile);
						int count = 0;
						byte buf[] = new byte[1024];
						
						do{   		   		
				    		int numread = is.read(buf);
				    		count += numread;
				    	    progress =(int)(((float)count / length) * 100);
				    	    //���½���
				    	    mHandler.sendEmptyMessage(DOWN_UPDATE);
				    		if(numread <= 0){	
				    			//�������֪ͨ��װ
				    			mHandler.sendEmptyMessage(DOWN_OVER);
				    			break;
				    		}
				    		fos.write(buf,0,numread);
				    	}while(!interceptFlag);//���ȡ����ֹͣ����.
						fos.close();
						is.close();
	                }
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch(IOException e){
					e.printStackTrace();
				}
				
			}
		};
		
		//��ȡ����汾�� 
		private int getVersionCode(){  
		    int versionCode = 0;  
		    try  {  
		    	//��ȡ����汾�ţ���ӦAndroidManifest.xml��android:versionCode  
		    	
		        versionCode = mContext.getPackageManager().getPackageInfo(mContext.getApplicationInfo().packageName, 0).versionCode;  
		        Log.e("updateversionmanager","versioncode="+String.valueOf(versionCode));
		    } catch (NameNotFoundException e){  
		    	e.printStackTrace();
		    }  
		    return versionCode;  
		}	
		
		//��ȡ����汾��
		private String getVersionName(){
			String name="";
			try {
				  name=mContext.getPackageManager().getPackageInfo(mContext.getApplicationInfo().packageName, 0).versionName;
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return name;
		}
		
		//����apk
		private void downloadApk(){
			Intent intent = new Intent(Intent.ACTION_VIEW);
			Uri content_url = Uri.parse(apkUrl);   
			intent.setData(content_url);  
			mContext.startActivity(intent);
			//downLoadThread = new Thread(mdownApkRunnable);
			//downLoadThread.start();
		}
		
		//��װapk
		private void installApk(){
			File apkfile = new File(saveFileName);
	        if (!apkfile.exists()) {
	            return;
	        } 
	        noticeDialog.cancel();
	        Intent i = new Intent(Intent.ACTION_VIEW);
	        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive"); 
	        mContext.startActivity(i);
		
		}
	}

