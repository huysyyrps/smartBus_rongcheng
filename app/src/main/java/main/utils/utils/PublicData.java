package main.utils.utils;

import java.text.DecimalFormat;

public class PublicData {
    public static String userAccount = "";//��ǰ�û��˺�
    public static String netWorkErrorMsg = "�����������";//�������������ʾ
    public static String netWorkingMsg = "��ȡ������";//�������������ʾ
    public static int limit =20;//�б�ÿҳ��������
    public static    String getDoubleStr(double d){
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//��ʽ������

        return  decimalFormat.format(d);
    }
}
