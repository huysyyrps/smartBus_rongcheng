package main.smart.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import android.graphics.Paint;

public class CharUtil {

	/**
	 * byte数组转对象
	 * */
	public static Object byteToObject(byte[] paramArrayOfByte) {
		Object localObject = null;
		try {
			ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(
					paramArrayOfByte);
			ObjectInputStream localObjectInputStream = new ObjectInputStream(
					localByteArrayInputStream);
			localObject = localObjectInputStream.readObject();
			localByteArrayInputStream.close();
			localObjectInputStream.close();
			return localObject;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return localObject;
	}

	/**
	 * 对象序列 转 byte数组
	 * */
	public static byte[] objectToByte(Serializable paramSerializable) {
		byte[] arrayOfByte = null;
		try {
			ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(
					localByteArrayOutputStream);
			localObjectOutputStream.writeObject(paramSerializable);
			arrayOfByte = localByteArrayOutputStream.toByteArray();
			localByteArrayOutputStream.close();
			localObjectOutputStream.close();
			return arrayOfByte;
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return arrayOfByte;
	}

	// /画线路图时用
	public static ArrayList<String> staticLayout(int paramInt,
			String stationName, Paint paramPaint) {
		ArrayList<String> localArrayList = new ArrayList<String>();
		int len = stationName.length();
	//	stationName="你好\n第二行 空格";
		int j = 0;
		float[] arrayOfFloat = { 0.0F };
	/*	String str[]=stationName.split("");
		for(int k=0;k<i;k++){
			localArrayList.add(str[k]);
		}
		return localArrayList;*/
	 	while (j<len) {
			 
			//换行
			int k = paramPaint.breakText(stationName, j, len, true, paramInt,
					arrayOfFloat);
			int l = stationName.substring(j, j + k).indexOf('\n');
			int i1 = stationName.substring(j, j + k).lastIndexOf(' ');
			if (l <= 0) {
				if (i1 <= 0) {
					localArrayList.add(stationName.substring(j, j + k));
					j += k;
				}  else{
					localArrayList.add(stationName.substring(j, 1 + j + i1));
					j += i1 + 1;
				}
			} else{
				localArrayList.add(stationName.substring(j, j + l));
				j += l + 1;
			}
			 
		} 
	 	return localArrayList;
	}
}
