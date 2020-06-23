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
//获取上一个月日期
public static Date getLastMonth(Date date) {
             Calendar calendar = Calendar.getInstance();
            calendar.setTime(date); // 设置为当前时间
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
            date = calendar.getTime();
            return  date;
     }
    //获取下一个月日期
    public static Date getNextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1); // 设置为下一个月
        date = calendar.getTime();
        return  date;
    }

//格式化日期
    public static String getDateFormat(Date date,String format){
        String dateStr = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateStr = dateFormat.format(date);
        return dateStr;
    }
    //获取每月的第一天
//获取某月第一天Calendar对象
   public static Calendar getCalendarDate(Date date){
       int year = Integer.parseInt(getDateFormat(date,FORMAYYYY));
       int month = Integer.parseInt(getDateFormat(date,FORMAMM));
       int day = Integer.parseInt(getDateFormat(date,FORMADD));
       // 获得当前日期对象
       Calendar cal = Calendar.getInstance();
       cal.clear();// 清除信息
       cal.set(Calendar.YEAR, year);
       // 1月从0开始
       cal.set(Calendar.MONTH, month-1 );
       // 当月1号
       cal.set(Calendar.DAY_OF_MONTH,day);
       return cal;
   }
   //获取日期是一周周几
    public static String getWeekDayStr(Date date){
        String week = "星期日";
        String[] weeks = new String[]{"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        Calendar cal = getCalendarDate(date);
        // Java中Calendar.DAY_OF_WEEK其实表示：一周中的第几天，所以他会受到第一天是 星期一 还是 星期日 的影响
        int d = cal.get(Calendar.DAY_OF_WEEK)-1;
        week =  weeks[d];
     return  week;
        //
    }
    //获取日期是周中第几天
    public static int getWhatDayOfWeekInt(Date date){
        int week = 1;
        int[] weeks = new int[]{7,1,2,3,4,5,6};
        Calendar cal = getCalendarDate(date);
        // Java中Calendar.DAY_OF_WEEK其实表示：一周中的第几天，所以他会受到第一天是 星期一 还是 星期日 的影响
        int d = cal.get(Calendar.DAY_OF_WEEK);

        return  d;
        //
    }
    /**
     *  java 获取 获取某年某月 所有日期（yyyy-mm-dd格式字符串）

     * @return
     */
    public static List<DateBean> getMonthFullDay(Date date){
        int year = Integer.parseInt(getDateFormat(date,FORMAYYYY));
        int month = Integer.parseInt(getDateFormat(date,FORMAMM));
        List<DateBean> fullDayList = new ArrayList<>(32);
        // 获得当前日期对象
        Calendar cal = Calendar.getInstance();
        cal.clear();// 清除信息
        cal.set(Calendar.YEAR, year);
        // 1月从0开始
        cal.set(Calendar.MONTH, month-1 );
        // 当月1号
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

       private int whatDayOfWeek = 0;//周中第几天 第一天为周日



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
