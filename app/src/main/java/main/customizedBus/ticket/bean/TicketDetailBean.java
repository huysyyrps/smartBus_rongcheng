package main.customizedBus.ticket.bean;

import java.io.Serializable;

public class TicketDetailBean implements Serializable {


    /**
     * msg :
     * code : 0
     * success : false
     * extraData : null
     * noticeData : null
     * data : {"id":92,"version":2,"gmtCreate":1569574585617,"gmtModify":1569575862070,"deleted":0,"passenger":"13361063873","lineId":52,"schedulId":10095,"ticketCountId":573,"orderId":"897198405de740afa142d5d1593d4eef","lineName":"1路","schedulTime":"07:00","rideDate":1569600000000,"rideTime":1569625200000,"num":1,"startStaion":"灵宝体育馆","endStation":"灵宝市国税局","payType":1,"money":0.01,"preferentialMoney":0.01,"refundApplyTime":1569575862063,"refundTime":null,"refundRemark":null,"payStatus":1,"status":0,"operType":1,"startRideDate":null,"endRideDate":null,"startRefundApplyTime":null,"endRefundApplyTime":null,"returnFlag":1,"nowTime":1569648166463,"startStationTime":"07:00","endStationTime":"08:00"}
     * count : null
     * nextUrl : null
     */

    private String msg;
    private int code;
    private boolean success;
    private Object extraData;
    private Object noticeData;
    private DataBean data;
    private Object count;
    private Object nextUrl;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getExtraData() {
        return extraData;
    }

    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }

    public Object getNoticeData() {
        return noticeData;
    }

    public void setNoticeData(Object noticeData) {
        this.noticeData = noticeData;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
        this.count = count;
    }

    public Object getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(Object nextUrl) {
        this.nextUrl = nextUrl;
    }

    public static class DataBean {
        /**
         * id : 92
         * version : 2
         * gmtCreate : 1569574585617
         * gmtModify : 1569575862070
         * deleted : 0
         * passenger : 13361063873
         * lineId : 52
         * schedulId : 10095
         * ticketCountId : 573
         * orderId : 897198405de740afa142d5d1593d4eef
         * lineName : 1路
         * schedulTime : 07:00
         * rideDate : 1569600000000
         * rideTime : 1569625200000
         * num : 1
         * startStaion : 灵宝体育馆
         * endStation : 灵宝市国税局
         * payType : 1
         * money : 0.01
         * preferentialMoney : 0.01
         * refundApplyTime : 1569575862063
         * refundTime : null
         * refundRemark : null
         * payStatus : 1
         * status : 0
         * operType : 1
         * startRideDate : null
         * endRideDate : null
         * startRefundApplyTime : null
         * endRefundApplyTime : null
         * returnFlag : 1
         * nowTime : 1569648166463
         * startStationTime : 07:00
         * endStationTime : 08:00
         */

        private int id;
        private int version;
        private long gmtCreate;
        private long gmtModify;
        private int deleted;
        private String passenger;
        private int lineId;
        private int schedulId;
        private int ticketCountId;
        private String orderId;
        private String lineName;
        private String schedulTime;
        private long rideDate;
        private long rideTime;
        private int num;
        private String startStaion;
        private String endStation;
        private int payType;
        private double money;
        private double preferentialMoney;
        private long refundApplyTime;
        private Object refundTime;
        private String refundRemark;
        private int payStatus;
        private int status;
        private int operType;
        private Object startRideDate;
        private Object endRideDate;
        private Object startRefundApplyTime;
        private Object endRefundApplyTime;
        private int returnFlag;
        private int showFlag;
        private long nowTime;
        private String startStationTime;
        private String endStationTime;

        public int getShowFlag() {
            return showFlag;
        }

        public void setShowFlag(int showFlag) {
            this.showFlag = showFlag;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public long getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(long gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public long getGmtModify() {
            return gmtModify;
        }

        public void setGmtModify(long gmtModify) {
            this.gmtModify = gmtModify;
        }

        public int getDeleted() {
            return deleted;
        }

        public void setDeleted(int deleted) {
            this.deleted = deleted;
        }

        public String getPassenger() {
            return passenger;
        }

        public void setPassenger(String passenger) {
            this.passenger = passenger;
        }

        public int getLineId() {
            return lineId;
        }

        public void setLineId(int lineId) {
            this.lineId = lineId;
        }

        public int getSchedulId() {
            return schedulId;
        }

        public void setSchedulId(int schedulId) {
            this.schedulId = schedulId;
        }

        public int getTicketCountId() {
            return ticketCountId;
        }

        public void setTicketCountId(int ticketCountId) {
            this.ticketCountId = ticketCountId;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getLineName() {
            return lineName;
        }

        public void setLineName(String lineName) {
            this.lineName = lineName;
        }

        public String getSchedulTime() {
            return schedulTime;
        }

        public void setSchedulTime(String schedulTime) {
            this.schedulTime = schedulTime;
        }

        public long getRideDate() {
            return rideDate;
        }

        public void setRideDate(long rideDate) {
            this.rideDate = rideDate;
        }

        public long getRideTime() {
            return rideTime;
        }

        public void setRideTime(long rideTime) {
            this.rideTime = rideTime;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getStartStaion() {
            return startStaion;
        }

        public void setStartStaion(String startStaion) {
            this.startStaion = startStaion;
        }

        public String getEndStation() {
            return endStation;
        }

        public void setEndStation(String endStation) {
            this.endStation = endStation;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public double getPreferentialMoney() {
            return preferentialMoney;
        }

        public void setPreferentialMoney(double preferentialMoney) {
            this.preferentialMoney = preferentialMoney;
        }

        public long getRefundApplyTime() {
            return refundApplyTime;
        }

        public void setRefundApplyTime(long refundApplyTime) {
            this.refundApplyTime = refundApplyTime;
        }

        public Object getRefundTime() {
            return refundTime;
        }

        public void setRefundTime(Object refundTime) {
            this.refundTime = refundTime;
        }

        public String getRefundRemark() {
            return refundRemark;
        }

        public void setRefundRemark(String refundRemark) {
            this.refundRemark = refundRemark;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getOperType() {
            return operType;
        }

        public void setOperType(int operType) {
            this.operType = operType;
        }

        public Object getStartRideDate() {
            return startRideDate;
        }

        public void setStartRideDate(Object startRideDate) {
            this.startRideDate = startRideDate;
        }

        public Object getEndRideDate() {
            return endRideDate;
        }

        public void setEndRideDate(Object endRideDate) {
            this.endRideDate = endRideDate;
        }

        public Object getStartRefundApplyTime() {
            return startRefundApplyTime;
        }

        public void setStartRefundApplyTime(Object startRefundApplyTime) {
            this.startRefundApplyTime = startRefundApplyTime;
        }

        public Object getEndRefundApplyTime() {
            return endRefundApplyTime;
        }

        public void setEndRefundApplyTime(Object endRefundApplyTime) {
            this.endRefundApplyTime = endRefundApplyTime;
        }

        public int getReturnFlag() {
            return returnFlag;
        }

        public void setReturnFlag(int returnFlag) {
            this.returnFlag = returnFlag;
        }

        public long getNowTime() {
            return nowTime;
        }

        public void setNowTime(long nowTime) {
            this.nowTime = nowTime;
        }

        public String getStartStationTime() {
            return startStationTime;
        }

        public void setStartStationTime(String startStationTime) {
            this.startStationTime = startStationTime;
        }

        public String getEndStationTime() {
            return endStationTime;
        }

        public void setEndStationTime(String endStationTime) {
            this.endStationTime = endStationTime;
        }
    }
}
