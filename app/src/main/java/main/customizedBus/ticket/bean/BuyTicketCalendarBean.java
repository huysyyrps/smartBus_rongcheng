package main.customizedBus.ticket.bean;

import main.customizedBus.line.bean.CustomizedLineDetailBean;
import main.customizedBus.ticket.tool.CalendarManager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BuyTicketCalendarBean  {
    private Date date = new Date();
    //
   public String getYYYYMMFormatDate(){
        String dateStr = CalendarManager.getDateFormat(date,CalendarManager.FORMATYYYYMM);
        return dateStr;
    }
//获取本月日期时间
    public List<BuyTicketDateBean> getMonthWeekDaysDataOfToday(List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.TicketCountDTOListBean> ticketList){
        List<BuyTicketDateBean> ticketDateBeans = new ArrayList<>();
        List<CalendarManager.DateBean> days = CalendarManager.getMonthFullDay(date);
        CalendarManager.DateBean firstBean = days.get(0) ;
        int weekOfFirstDay = firstBean.getWhatDayOfWeek();
        for (int i=1;i<weekOfFirstDay;i++){
            BuyTicketDateBean dateBean = new BuyTicketDateBean();
            ticketDateBeans.add(dateBean);
        }

        for (int i = 0; i < days.size(); i++) {
            CalendarManager.DateBean dateBean = days.get(i);
            BuyTicketDateBean ticketDateBean = new BuyTicketDateBean();
            ticketDateBean.setDateBean(dateBean);
            if (ticketList != null){
                for (int j = 0; j < ticketList.size(); j++) {
                    CustomizedLineDetailBean.DataBean.SchedulDTOListBean.TicketCountDTOListBean ticketBean = ticketList.get(j);
                    Date tickDate = new Date(ticketBean.getRideDate());
                    String ticktYYYYMMDDStr = CalendarManager.getDateFormat(tickDate,CalendarManager.FORMATYYYYMMDD);
                    String yyyyMMDDStr = dateBean.getYearMonthDay();
                    boolean isEql = ticktYYYYMMDDStr.equals(yyyyMMDDStr);
                    if (isEql){
                        ticketDateBean.setTicketBean(ticketBean);
                        break;
                    }
                }
            }

            ticketDateBeans.add(ticketDateBean);

        }

        for (int i=ticketDateBeans.size();i< 35;i++){
            BuyTicketDateBean dateBean = new BuyTicketDateBean();
            ticketDateBeans.add(dateBean);
        }
       return ticketDateBeans;
    }
//h获取下一个月日期
    public List<BuyTicketDateBean> getNextMonthWeekDaysDataOfToday(List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.TicketCountDTOListBean> ticketList){
        date = CalendarManager.getNextMonth(date);
        return getMonthWeekDaysDataOfToday(ticketList);
    }
    //h获取上一个月日期
    public List<BuyTicketDateBean> getLastMonthWeekDaysDataOfToday(List<CustomizedLineDetailBean.DataBean.SchedulDTOListBean.TicketCountDTOListBean> ticketList){
        date = CalendarManager.getLastMonth(date);
        return getMonthWeekDaysDataOfToday(ticketList);
    }


    public class BuyTicketDateBean {
        private boolean canBuy = false;
        private boolean selected = false;//是否选择
        private CalendarManager.DateBean dateBean;
        private CustomizedLineDetailBean.DataBean.SchedulDTOListBean.TicketCountDTOListBean ticketBean;

        public void setCanBuy(boolean canBuy) {
            this.canBuy = canBuy;
        }

        public void setSelected(boolean selected) {
            if (ticketBean==null)
            {
                return;
            }
            this.ticketBean.setSelected( selected);
        }

        public void setDateBean(CalendarManager.DateBean dateBean) {
            this.dateBean = dateBean;
        }

        public void setTicketBean(CustomizedLineDetailBean.DataBean.SchedulDTOListBean.TicketCountDTOListBean ticketBean) {
            this.ticketBean = ticketBean;
        }

        public boolean isCanBuy() {
            if (ticketBean != null&&ticketBean.getStandbyCount()>0)
            {
                canBuy = true;
            }

            return canBuy;
        }

        public boolean isSelected() {
            if (ticketBean==null){
                return false;
            }
            return this.ticketBean.isSelected();
        }

        public CalendarManager.DateBean getDateBean() {
            return dateBean;
        }

        public CustomizedLineDetailBean.DataBean.SchedulDTOListBean.TicketCountDTOListBean getTicketBean() {
            return ticketBean;
        }

        public String getShowStr(){

            String str = "";
            if (dateBean != null){
                str = dateBean.getDayStr();
            }
            if (ticketBean != null){
               int standbyCount = ticketBean.getStandbyCount();
                str = str + "\n" + String.valueOf(standbyCount) + "张";
            }
            return str;
        }
    }
}
