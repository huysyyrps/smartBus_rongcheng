package main.smart.common.util;

import main.smart.rcgj.R;
import main.smart.common.SmartBusApp;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class Utils {

	public static void showLong(int paramInt)
	  {
	    Toast.makeText(SmartBusApp.getInstance(), paramInt, Toast.LENGTH_SHORT).show();
	  }

	  public static void showLong(String paramString)
	  {
	    Toast.makeText(SmartBusApp.getInstance(), paramString, Toast.LENGTH_SHORT).show();
	  }

	  public static void showMessageBox(Context paramContext, int paramInt)
	  {
	    new AlertDialog.Builder(paramContext).setTitle(R.string.alert).setMessage(paramInt).setPositiveButton(R.string.confirm, null).create().show();
	  }

	  public static void showMessageBox(Context paramContext, String paramString)
	  {
	    new AlertDialog.Builder(paramContext).setTitle(R.string.alert).setMessage(paramString).setPositiveButton(R.string.confirm, null).create().show();
	  }

	  public static void showMessageBox(Context paramContext, String paramString, CharSequence paramCharSequence)
	  {
	    new AlertDialog.Builder(paramContext).setTitle(paramString).setMessage(paramCharSequence).setPositiveButton(R.string.confirm, null).create().show();
	  }

	  public static void showShort(int paramInt)
	  {
	    Toast.makeText(SmartBusApp.getInstance(), paramInt, Toast.LENGTH_SHORT).show();
	  }

	  public static void showShort(String paramString)
	  {
	    Toast.makeText(SmartBusApp.getInstance(), paramString, Toast.LENGTH_SHORT).show();
	  }
	public static boolean isReverse(String hexStr1, String hexStr2) {

		String anotherBinary = hexString2binaryString(hexStr1);
		String thisBinary = hexString2binaryString(hexStr2);
		System.out.println(anotherBinary);
		System.out.println(thisBinary);

		if (thisBinary.length() != anotherBinary.length()) {
			return false;
		} else {
			String result = "";
			for (int i = 0; i < anotherBinary.length(); i++) {
				if (anotherBinary.charAt(i) == thisBinary.charAt(i)) {
					// false
					return false;
				} else {
					continue;
				}
			}

		}
		return true;
	}


	public static String hexString2binaryString(String hexString) {
		if (hexString == null || hexString.length() % 2 != 0)
			return null;
		String bString = "", tmp;
		for (int i = 0; i < hexString.length(); i++) {
			tmp = "0000"
					+ Integer.toBinaryString(Integer.parseInt(
					hexString.substring(i, i + 1), 16));
			bString += tmp.substring(tmp.length() - 4);
		}
		return bString;
	}
	/**
	 * 16进制字符串转为字节数组
	 * @param hex 16进制字符串
	 * @return
	 */
	public static byte[] hexString2Bytes(String hex) {

		if ((hex == null) || (hex.equals(""))) {
			return null;
		} else if (hex.length() % 2 != 0) {
			return null;
		} else {
			hex = hex.toUpperCase();
			int len = hex.length() / 2;
			byte[] b = new byte[len];
			char[] hc = hex.toCharArray();
			for (int i = 0; i < len; i++) {
				int p = 2 * i;
				b[i] = (byte) (charToByte(hc[p]) << 4 | charToByte(hc[p + 1]));
			}
			return b;
		}

	}
	/**
	 * ???????????16???????
	 * */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		char[] buffer = new char[2];
		for (int i = 0; i < src.length; i++) {
			buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
			buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
			stringBuilder.append(buffer);
			//     stringBuilder.append("");
		}
		return stringBuilder.toString();
	}
	/**
	 * ????????????е????
	 * ??16???????
	 * */
	public static String bytesToHexString(byte[] src, int start, int end) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		if(src.length<end){
			return null;
		}
		byte[] newByte=new byte[end-start+1];
		int count=0;
		for(int i=start;i<=end;i++){
			newByte[count++]=src[i];
		}
		char[] buffer = new char[2];
		for (int i = 0; i < newByte.length; i++) {
			buffer[0] = Character.forDigit((newByte[i] >>> 4) & 0x0F, 16);
			buffer[1] = Character.forDigit(newByte[i] & 0x0F, 16);
			stringBuilder.append(buffer);
			//     stringBuilder.append("");
		}
		return stringBuilder.toString();
	}
	/**
	 * 2?????16????
	 * */
	public static String binaryString2hexString(String bString)
	{
		if (bString == null || bString.equals("") || bString.length() % 8 != 0)
			return null;
		StringBuffer tmp = new StringBuffer();
		int iTmp = 0;
		for (int i = 0; i < bString.length(); i += 4)
		{
			iTmp = 0;
			for (int j = 0; j < 4; j++)
			{
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}
	/**
	 * 16?????????
	 * */
	public static String getReverseHexString(String hexString){
		String thisBinary=hexString2binaryString(hexString);
		String resultBinary="";
		for(int i=0;i<thisBinary.length();i++){
			if(thisBinary.charAt(i)=='0'){
				resultBinary+="1";
			}else{
				resultBinary+="0";
			}
		}
		return  binaryString2hexString(resultBinary);

	}
	/**
	 * ??????????
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
	/**
	 * 16????????????????16???????
	 * */
	public static String hexStringReverseOrder(String hexStr, int totalLen){
		List<String> newList=new ArrayList<String>();
		String reverseHexStr="";
		for(int i=0;i<hexStr.length()/2;i++){
			newList.add(hexStr.substring(i*2, i*2+2));
		}
		//??????
		for(int i=newList.size()-1;i>=0;i--){
			reverseHexStr+=newList.get(i);
		}
		//????? 4?????????????????0
		while(reverseHexStr.length()<totalLen){
			reverseHexStr+="0";
		}
		return reverseHexStr;
	}
	/**
	 * byte[] ??????
	 * flag==0 ????
	 * flag==1 ????? ???????
	 * */
	public static int bytes2Int(byte[]b,int start,int end,int flag){
		String str="";
		if(flag==0){
			for(int i=start;i<=end;i++){
				str+=bytesToHexString(new byte[]{b[i]}).trim();
			}
		}else if(flag==1){
			for(int i=end;i>=start;i--){
				str+=bytesToHexString(new byte[]{b[i]}).trim();

			}
		}
		int result= Integer.parseInt(str,16);
		return result;
	}
	/**
	 * ???????
	 * @param num ?????????
	 * @return
	 */
	public static String FenToYuan(Object num){
		double nums = Double.parseDouble(num.toString())/100;
		DecimalFormat fnum = new DecimalFormat("0.00");
		return fnum.format(nums);
	}

	/**
	 * 对 URL 中的 QueryString 进行解析的工具类
	 * @param queryString
	 * @return
	 */
	public static Map<String, String> queryStringParser(String queryString) {
		Map<String, String> mapParam = new HashMap<String, String>();
		StringTokenizer st = new StringTokenizer(queryString, "&");
		while (st.hasMoreTokens()) {
			String strPairs = st.nextToken();
			String strKey = strPairs.substring(0, strPairs.indexOf('='));
			String strValue = strPairs.substring(strPairs.indexOf('=') + 1);
			mapParam.put(strKey, strValue);
		}
		return mapParam;
	}

	public static void main(String args[]) {
		//	boolean result = isReverse("10270000", "EFD8FFFF");
		//   String hexStr=           Integer.toHexString(10000);
//		byte [] bb =hexString2Bytes("047a76469181a6a4b6e94ae7b18b81147b4faabb194fa0916476cbaa8073aaf0c491a3f612b78e84b627a0e5949c42f1421574d09f92383e40b80691a968c6ba1de1ca524eb2f45d9dabea5b1efc2202513ee5cbe84f699ca434d11d2255fbaaa578b49ebb036d66ec4e538af7110c7df4c158159fd199630f30dcdcfa5451dba32b87c64649c66a303759b817742c9c4aee69c675a12d6d4fba68a6857876c705ea10677d0533b139915dece7f6e94d26019c31cf105942fccb0d23059ba178d6fbbe139ca1ec1c1a18ad740683fc34cc025cce05ef87177e183bf0dc3d3a01c5411116ff2e52153b4d49ee445f416fa512955a7c9a7db41f4501c11821d1ee");
//
//
//        System.out.println(bb.length);
		System.out.println(FenToYuan("24"));
	}

}
