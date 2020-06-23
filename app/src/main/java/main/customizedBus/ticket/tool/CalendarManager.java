package main.customizedBus.ticket.tool;

import main.customizedBus.line.bean.CustomizedLineBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarManager {
    public final  static String FORMATYYYYMMDDHHMM = "yyyy-MM-dd hh:mm";
    public final  static String FORMATYYYYMMDD = "yyyy-MM-dd";
    public final  static String FORMATYYYYMM = "yyyy-MM";
    public final  static String FORMAYYYY= "yyyy";
    public final  static String FORMAMM = "MM";
    public final  static String FORMADD = "dd";

    public  static Date getDate(String dateStr,String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date =null;
        try {
          date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
//��ȡ��һ��������
public static Date getLastMonth(Date date) {
             Calendar calendar = Calendar.getInstance();
            calendar.setTime(date); // ����Ϊ��ǰʱ��
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // ����Ϊ��һ����
            date = calendar.getTime();
            return  date;
     }
    //��ȡ��һ��������
    public static Date getNextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // ����Ϊ��ǰʱ��
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1); // ����Ϊ��һ����
        date = calendar.getTime();
        return  date;
    }

//��ʽ������
    public static String getDateFormat(Date date,String format){
        String dateStr = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateStr = dateFormat.format(date);
        return dateStr;
    }
    //��ȡÿ�µĵ�һ��
//��ȡĳ�µ�һ��Calendar����
   public static Calendar getCalendarDate(Date date){
       int year = Integer.parseInt(getDateFormat(date,FORMAYYYY));
       int month = Integer.parseInt(getDateFormat(date,FORMAMM));
       int day = Integer.parseInt(getDateFormat(date,FORMADD));
       // ��õ�ǰ���ڶ���
       Calendar cal = Calendar.getInstance();
       cal.clear();// �����Ϣ
       cal.set(Calendar.YEAR, year);
       // 1�´�0��ʼ
       cal.set(Calendar.MONTH, month-1 );
       // ����1��
       cal.set(Calendar.DAY_OF_MONTH,day);
       return cal;
   }
   //��ȡ������һ���ܼ�
    public static String getWeekDayStr(Date date){
        String week = "������";
        String[] weeks = new String[]{"������","����һ","���ڶ�","������","������","������","������"};
        Calendar cal = getCalendarDate(date);
        // Java��Calendar.DAY_OF_WEEK��ʵ��ʾ��һ���еĵڼ��죬���������ܵ���һ���� ����һ ���� ������ ��Ӱ��
        int d = cal.get(Calendar.DAY_OF_WEEK)-1;
        week =  weeks[d];
     return  week;
        //
    }
    //��ȡ���������еڼ���
    public static int getWhatDayOfWeekInt(Date date){
        int week = 1;
        int[] weeks = new int[]{7,1,2,3,4,5,6};
        Calendar cal = getCalendarDate(date);
        // Java��Calendar.DAY_OF_WEEK��ʵ��ʾ��һ���еĵڼ��죬���������ܵ���һ���� ����һ ���� ������ ��Ӱ��
        int d = cal.get(Calendar.DAY_OF_WEEK);

        return  d;
        //
    }
    /**
     *  java ��ȡ ��ȡĳ��ĳ�� �������ڣ�yyyy-mm-dd��ʽ�ַ�����

     * @return
     */
    public static List<DateBean> getMonthFullDay(Date date){
        int year = Integer.parseInt(getDateFormat(date,FORMAYYYY));
        int month = Integer.parseInt(getDateFormat(date,FORMAMM));
        List<DateBean> fullDayList = new ArrayList<>(32);
        // ��õ�ǰ���ڶ���
        Calendar cal = Calendar.getInstance();
        cal.clear();// �����Ϣ
        cal.set(Calendar.YEAR, year);
        // 1�´�0��ʼ
        cal.set(Calendar.MONTH, month-1 );
        // ����1��
        cal.set(Calendar.DAY_OF_MONTH,1);
        int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int j = 1; j <= count ; j++) {
            DateBean bean = new DateBean();
            bean.setDate(cal.getTime());
            fullDayList.add(bean);
            cal.add(Calendar.DAY_OF_MONTH,1);
        }
        return fullDayList;
    }

   public static class DateBean{
       private String dayStr ="";
       private String yearMonth = "";
       private String yearMonthDay = "";
       private Date date = new Date();
       private String weekStr = "";

       private int whatDayOfWeek = 0;//���еڼ��� ��һ��Ϊ����



      private void  reloadData(){
          weekStr = getWeekDayStr(date);
          dayStr = getDateFormat(date,FORMADD);
          yearMonthDay = getDateFormat(date,FORMATYYYYMMDD);
          yearMonth = getDateFormat(date,FORMATYYYYMM);
          whatDayOfWeek = getWhatDayOfWeekInt(date);
       }

       public String getDayStr() {
           return dayStr;
       }

       public String getYearMonthDay() {
           return yearMonthDay;
       }

       public Date getDate() {
           return date;
       }

       public String getWeekStr() {
           return weekStr;
       }



       public int getWhatDayOfWeek() {
           return whatDayOfWeek;
       }

       public String getYearMonth() {
           return yearMonth;
       }

       public void setDate(Date date) {
           this.date = date;
           reloadData();
       }


   }
}
