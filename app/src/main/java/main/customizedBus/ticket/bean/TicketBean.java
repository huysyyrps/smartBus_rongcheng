package main.customizedBus.ticket.bean;

import java.io.Serializable;
import java.util.List;

public class TicketBean implements Serializable {
    /**
     * msg :
     * code : 0
     * success : false
     * extraData : null
     * noticeData : null
     * data : [{"id":147,"version":4,"gmtCreate":1569745451390,"gmtModify":1569745706007,"deleted":0,"passenger":"13361063873","lineId":52,"schedulId":10095,"ticketCountId":575,"orderId":"6847c2f184a04b9195614b67409f5dd1","lineName":"1路","schedulTime":"07:00","rideDate":1569772800000,"rideTime":1569798000000,"num":1,"startStaion":"灵宝体育馆","endStation":"灵宝市国税局","payType":1,"money":0.01,"preferentialMoney":0.01,"refundApplyTime":1569745617170,"refundTime":1569745707173,"refundRemark":null,"payStatus":4,"status":0,"operType":1,"startRideDate":null,"endRideDate":null,"startRefundApplyTime":null,"endRefundApplyTime":null,"returnFlag":null,"nowTime":null,"startStationTime":null,"endStationTime":null,"mobileStatus":"已退款","starttime":1569711600000,"endtime":1569715200000},{"id":146,"version":4,"gmtCreate":1569745419387,"gmtModify":1569745663267,"deleted":0,"passenger":"13361063873","lineId":52,"schedulId":10095,"ticketCountId":575,"orderId":"158425fa4d2a4ebcacad51b308b5fa22","lineName":"1路","schedulTime":"07:00","rideDate":1569772800000,"rideTime":1569798000000,"num":1,"startStaion":"灵宝体育馆","endStation":"灵宝市国税局","payType":0,"money":0.01,"preferentialMoney":0.01,"refundApplyTime":1569745625847,"refundTime":1569745664427,"refundRemark":null,"payStatus":4,"status":0,"operType":1,"startRideDate":null,"endRideDate":null,"startRefundApplyTime":null,"endRefundApplyTime":null,"returnFlag":null,"nowTime":null,"startStationTime":null,"endStationTime":null,"mobileStatus":"已退款","starttime":1569711600000,"endtime":1569715200000},{"id":112,"version":1,"gmtCreate":1569740280700,"gmtModify":1569740305350,"deleted":0,"passenger":"13361063873","lineId":52,"schedulId":10095,"ticketCountId":575,"orderId":"0c7e5d70f9e04d8c97a9c8a14e19bf70","lineName":"1路","schedulTime":"07:00","rideDate":1569772800000,"rideTime":1569798000000,"num":1,"startStaion":"灵宝体育馆","endStation":"灵宝市国税局","payType":0,"money":0.01,"preferentialMoney":0.01,"refundApplyTime":null,"refundTime":null,"refundRemark":null,"payStatus":1,"status":0,"operType":0,"startRideDate":null,"endRideDate":null,"startRefundApplyTime":null,"endRefundApplyTime":null,"returnFlag":null,"nowTime":null,"startStationTime":null,"endStationTime":null,"mobileStatus":"已过期","starttime":1569711600000,"endtime":1569715200000},{"id":110,"version":1,"gmtCreate":1569738896570,"gmtModify":1569738915973,"deleted":0,"passenger":"13361063873","lineId":52,"schedulId":10095,"ticketCountId":575,"orderId":"c98e2d25924640de8debfd55c30db758","lineName":"1路","schedulTime":"07:00","rideDate":1569772800000,"rideTime":1569798000000,"num":1,"startStaion":"灵宝体育馆","endStation":"灵宝市国税局","payType":0,"money":0.01,"preferentialMoney":0.01,"refundApplyTime":null,"refundTime":null,"refundRemark":null,"payStatus":1,"status":0,"operType":0,"startRideDate":null,"endRideDate":null,"startRefundApplyTime":null,"endRefundApplyTime":null,"returnFlag":null,"nowTime":null,"startStationTime":null,"endStationTime":null,"mobileStatus":"已过期","starttime":1569711600000,"endtime":1569715200000},{"id":108,"version":1,"gmtCreate":1569738635060,"gmtModify":1569738651910,"deleted":0,"passenger":"13361063873","lineId":52,"schedulId":10095,"ticketCountId":575,"orderId":"f8486eb81b024779b40422283bb86799","lineName":"1路","schedulTime":"07:00","rideDate":1569772800000,"rideTime":1569798000000,"num":1,"startStaion":"灵宝体育馆","endStation":"灵宝市国税局","payType":0,"money":0.01,"preferentialMoney":0.01,"refundApplyTime":null,"refundTime":null,"refundRemark":null,"payStatus":1,"status":0,"operType":0,"startRideDate":null,"endRideDate":null,"startRefundApplyTime":null,"endRefundApplyTime":null,"returnFlag":null,"nowTime":null,"startStationTime":null,"endStationTime":null,"mobileStatus":"已过期","starttime":1569711600000,"endtime":1569715200000},{"id":107,"version":1,"gmtCreate":1569725174907,"gmtModify":1569725185627,"deleted":0,"passenger":"13361063873","lineId":52,"schedulId":10095,"ticketCountId":575,"orderId":"f09853e22199453e9610cd5829e1c546","lineName":"1路","schedulTime":"07:00","rideDate":1569772800000,"rideTime":1569798000000,"num":1,"startStaion":"灵宝体育馆","endStation":"灵宝市国税局","payType":0,"money":0.01,"preferentialMoney":0.01,"refundApplyTime":null,"refundTime":null,"refundRemark":null,"payStatus":1,"status":0,"operType":0,"startRideDate":null,"endRideDate":null,"startRefundApplyTime":null,"endRefundApplyTime":null,"returnFlag":null,"nowTime":null,"startStationTime":null,"endStationTime":null,"mobileStatus":"已过期","starttime":1569711600000,"endtime":1569715200000},{"id":106,"version":1,"gmtCreate":1569724535953,"gmtModify":1569724544770,"deleted":0,"passenger":"13361063873","lineId":52,"schedulId":10095,"ticketCountId":575,"orderId":"b7a38ea3482b434ab74856f11c7fc4a1","lineName":"1路","schedulTime":"07:00","rideDate":1569772800000,"rideTime":1569798000000,"num":1,"startStaion":"灵宝体育馆","endStation":"灵宝市国税局","payType":0,"money":0.01,"preferentialMoney":0.01,"refundApplyTime":null,"refundTime":null,"refundRemark":null,"payStatus":1,"status":0,"operType":0,"startRideDate":null,"endRideDate":null,"startRefundApplyTime":null,"endRefundApplyTime":null,"returnFlag":null,"nowTime":null,"startStationTime":null,"endStationTime":null,"mobileStatus":"已过期","starttime":1569711600000,"endtime":1569715200000},{"id":105,"version":1,"gmtCreate":1569724477830,"gmtModify":1569724494797,"deleted":0,"passenger":"13361063873","lineId":52,"schedulId":10095,"ticketCountId":575,"orderId":"7a2334fdb7234e0f8b2e50d2b602abf9","lineName":"1路","schedulTime":"07:00","rideDate":1569772800000,"rideTime":1569798000000,"num":1,"startStaion":"灵宝体育馆","endStation":"灵宝市国税局","payType":0,"money":0.01,"preferentialMoney":0.01,"refundApplyTime":null,"refundTime":null,"refundRemark":null,"payStatus":1,"status":0,"operType":0,"startRideDate":null,"endRideDate":null,"startRefundApplyTime":null,"endRefundApplyTime":null,"returnFlag":null,"nowTime":null,"startStationTime":null,"endStationTime":null,"mobileStatus":"已过期","starttime":1569711600000,"endtime":1569715200000},{"id":103,"version":4,"gmtCreate":1569663390790,"gmtModify":1569663566447,"deleted":0,"passenger":"13361063873","lineId":52,"schedulId":10095,"ticketCountId":574,"orderId":"99eef29558734c2ba68d592104f20477","lineName":"1路","schedulTime":"07:00","rideDate":1569686400000,"rideTime":1569711600000,"num":1,"startStaion":"灵宝体育馆","endStation":"灵宝市国税局","payType":0,"money":0.01,"preferentialMoney":0.01,"refundApplyTime":1569663486213,"refundTime":null,"refundRemark":null,"payStatus":4,"status":0,"operType":1,"startRideDate":null,"endRideDate":null,"startRefundApplyTime":null,"endRefundApplyTime":null,"returnFlag":null,"nowTime":null,"startStationTime":null,"endStationTime":null,"mobileStatus":"已退款","starttime":1569711600000,"endtime":1569715200000},{"id":102,"version":3,"gmtCreate":1569660616607,"gmtModify":1569660715850,"deleted":0,"passenger":"13361063873","lineId":52,"schedulId":10095,"ticketCountId":574,"orderId":"7cfc365993e842b5a5223fdecae643f2","lineName":"1路","schedulTime":"07:00","rideDate":1569686400000,"rideTime":1569711600000,"num":1,"startStaion":"灵宝体育馆","endStation":"灵宝市国税局","payType":0,"money":0.01,"preferentialMoney":0.01,"refundApplyTime":1569660696840,"refundTime":null,"refundRemark":null,"payStatus":3,"status":0,"operType":1,"startRideDate":null,"endRideDate":null,"startRefundApplyTime":null,"endRefundApplyTime":null,"returnFlag":null,"nowTime":null,"startStationTime":null,"endStationTime":null,"mobileStatus":"退款中","starttime":1569711600000,"endtime":1569715200000}]
     * count : 27
     * nextUrl : null
     */

    private String msg;
    private int code;
    private boolean success;
    private Object extraData;
    private Object noticeData;
    private int count;
    private Object nextUrl;
    private List<DataBean> data;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(Object nextUrl) {
        this.nextUrl = nextUrl;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 147
         * version : 4
         * gmtCreate : 1569745451390
         * gmtModify : 1569745706007
         * deleted : 0
         * passenger : 13361063873
         * lineId : 52
         * schedulId : 10095
         * ticketCountId : 575
         * orderId : 6847c2f184a04b9195614b67409f5dd1
         * lineName : 1路
         * schedulTime : 07:00
         * rideDate : 1569772800000
         * rideTime : 1569798000000
         * num : 1
         * startStaion : 灵宝体育馆
         * endStation : 灵宝市国税局
         * payType : 1
         * money : 0.01
         * preferentialMoney : 0.01
         * refundApplyTime : 1569745617170
         * refundTime : 1569745707173
         * refundRemark : null
         * payStatus : 4
         * status : 0
         * operType : 1
         * startRideDate : null
         * endRideDate : null
         * startRefundApplyTime : null
         * endRefundApplyTime : null
         * returnFlag : null
         * nowTime : null
         * startStationTime : null
         * endStationTime : null
         * mobileStatus : 已退款
         * starttime : 1569711600000
         * endtime : 1569715200000
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
        private long refundTime;
        private Object refundRemark;
        private int payStatus;
        private int status;
        private int operType;
        private Object startRideDate;
        private Object endRideDate;
        private Object startRefundApplyTime;
        private Object endRefundApplyTime;
        private Object returnFlag;
        private Object nowTime;
        private Object startStationTime;
        private Object endStationTime;
        private String mobileStatus;
        private long starttime;
        private long endtime;

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

        public long getRefundTime() {
            return refundTime;
        }

        public void setRefundTime(long refundTime) {
            this.refundTime = refundTime;
        }

        public Object getRefundRemark() {
            return refundRemark;
        }

        public void setRefundRemark(Object refundRemark) {
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

        public Object getReturnFlag() {
            return returnFlag;
        }

        public void setReturnFlag(Object returnFlag) {
            this.returnFlag = returnFlag;
        }

        public Object getNowTime() {
            return nowTime;
        }

        public void setNowTime(Object nowTime) {
            this.nowTime = nowTime;
        }

        public Object getStartStationTime() {
            return startStationTime;
        }

        public void setStartStationTime(Object startStationTime) {
            this.startStationTime = startStationTime;
        }

        public Object getEndStationTime() {
            return endStationTime;
        }

        public void setEndStationTime(Object endStationTime) {
            this.endStationTime = endStationTime;
        }

        public String getMobileStatus() {
            return mobileStatus;
        }

        public void setMobileStatus(String mobileStatus) {
            this.mobileStatus = mobileStatus;
        }

        public long getStarttime() {
            return starttime;
        }

        public void setStarttime(long starttime) {
            this.starttime = starttime;
        }

        public long getEndtime() {
            return endtime;
        }

        public void setEndtime(long endtime) {
            this.endtime = endtime;
        }
    }

//    /**
//     * msg :
//     * code : 0
//     * success : false
//     * extraData : null
//     * noticeData : null
//     * data : [{"id":1,"version":3,"gmtCreate":1566186750000,"gmtModify":1567390202627,"deleted":0,"passenger":"15212345678","lineId":1,"schedulId":1,"ticketCountId":433,"orderId":"1","lineName":"D1","schedulTime":"15:00","rideDate":1566057600000,"rideTime":1566111600000,"num":2,"startStaion":"大明湖","endStation":"千佛山","payType":0,"money":30,"preferentialMoney":null,"refundApplyTime":null,"refundTime":null,"refundRemark":"111","payStatus":2,"status":1,"operType":0,"startRideDate":null,"endRideDate":null,"startRefundApplyTime":null,"endRefundApplyTime":null},{"id":3,"version":6,"gmtCreate":1566130356000,"gmtModify":1567390202650,"deleted":0,"passenger":"15212345678","lineId":1,"schedulId":1,"ticketCountId":433,"orderId":"1","lineName":"D1","schedulTime":"15:00","rideDate":1566057600000,"rideTime":1566111600000,"num":1,"startStaion":"大明湖","endStation":"千佛山","payType":0,"money":15,"preferentialMoney":null,"refundApplyTime":1566630625000,"refundTime":null,"refundRemark":"22222222223333333333","payStatus":2,"status":0,"operType":1,"startRideDate":null,"endRideDate":null,"startRefundApplyTime":null,"endRefundApplyTime":null}]
//     * count : 2
//     * nextUrl : null
//     */
//
//    private String msg;
//    private int code;
//    private boolean success;
//    private int count;
//    private List<DataBean> data;
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public boolean isSuccess() {
//        return success;
//    }
//
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }
//
//    public int getCount() {
//        return count;
//    }
//
//    public void setCount(int count) {
//        this.count = count;
//    }
//
//    public List<DataBean> getData() {
//        return data;
//    }
//
//    public void setData(List<DataBean> data) {
//        this.data = data;
//    }
//
//    public static class DataBean {
//        /**
//         * id : 1
//         * version : 3
//         * gmtCreate : 1566186750000
//         * gmtModify : 1567390202627
//         * deleted : 0
//         * passenger : 15212345678
//         * lineId : 1
//         * schedulId : 1
//         * ticketCountId : 433
//         * orderId : 1
//         * lineName : D1
//         * schedulTime : 15:00
//         * rideDate : 1566057600000
//         * rideTime : 1566111600000
//         * num : 2
//         * startStaion : 大明湖
//         * endStation : 千佛山
//         * payType : 0
//         * money : 30.0
//         * preferentialMoney : null
//         * refundApplyTime : null
//         * refundTime : null
//         * refundRemark : 111
//         * payStatus : 2
//         * status : 1
//         * operType : 0
//         * startRideDate : null
//         * endRideDate : null
//         * startRefundApplyTime : null
//         * endRefundApplyTime : null
//         */
//
//        private int id;
//        private int version;
//        private long gmtCreate;
//        private long gmtModify;
//        private int deleted;
//        private String passenger;
//        private int lineId;
//        private int schedulId;
//        private int ticketCountId;
//        private String orderId;
//        private String lineName;
//        private String schedulTime;
//        private long rideDate;
//        private long rideTime;
//        private int num;
//        private String startStaion;
//        private String endStation;
//        private int payType;
//        private double money;
//        private Object preferentialMoney;
//        private Object refundApplyTime;
//        private Object refundTime;
//        private String refundRemark;
//        private int payStatus;
//        private int status;
//        private int operType;
//        private Object startRideDate;
//        private Object endRideDate;
//        private Object startRefundApplyTime;
//        private Object endRefundApplyTime;
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public int getVersion() {
//            return version;
//        }
//
//        public void setVersion(int version) {
//            this.version = version;
//        }
//
//        public long getGmtCreate() {
//            return gmtCreate;
//        }
//
//        public void setGmtCreate(long gmtCreate) {
//            this.gmtCreate = gmtCreate;
//        }
//
//        public long getGmtModify() {
//            return gmtModify;
//        }
//
//        public void setGmtModify(long gmtModify) {
//            this.gmtModify = gmtModify;
//        }
//
//        public int getDeleted() {
//            return deleted;
//        }
//
//        public void setDeleted(int deleted) {
//            this.deleted = deleted;
//        }
//
//        public String getPassenger() {
//            return passenger;
//        }
//
//        public void setPassenger(String passenger) {
//            this.passenger = passenger;
//        }
//
//        public int getLineId() {
//            return lineId;
//        }
//
//        public void setLineId(int lineId) {
//            this.lineId = lineId;
//        }
//
//        public int getSchedulId() {
//            return schedulId;
//        }
//
//        public void setSchedulId(int schedulId) {
//            this.schedulId = schedulId;
//        }
//
//        public int getTicketCountId() {
//            return ticketCountId;
//        }
//
//        public void setTicketCountId(int ticketCountId) {
//            this.ticketCountId = ticketCountId;
//        }
//
//        public String getOrderId() {
//            return orderId;
//        }
//
//        public void setOrderId(String orderId) {
//            this.orderId = orderId;
//        }
//
//        public String getLineName() {
//
//            return NonNullString.getString(lineName);
//        }
//
//        public void setLineName(String lineName) {
//            this.lineName = lineName;
//        }
//
//        public String getSchedulTime() {
//            return schedulTime;
//        }
//
//        public void setSchedulTime(String schedulTime) {
//            this.schedulTime = schedulTime;
//        }
//
//        public long getRideDate() {
//            return rideDate;
//        }
//
//        public void setRideDate(long rideDate) {
//            this.rideDate = rideDate;
//        }
//
//        public long getRideTime() {
//            return rideTime;
//        }
//
//        public void setRideTime(long rideTime) {
//            this.rideTime = rideTime;
//        }
//
//        public int getNum() {
//            return num;
//        }
//
//        public void setNum(int num) {
//            this.num = num;
//        }
//
//        public String getStartStaion() {
//            return NonNullString.getString(startStaion);
//        }
//
//        public void setStartStaion(String startStaion) {
//            this.startStaion = startStaion;
//        }
//
//        public String getEndStation() {
//            return NonNullString.getString(endStation);
//        }
//
//        public void setEndStation(String endStation) {
//            this.endStation = endStation;
//        }
//
//        public int getPayType() {
//            return payType;
//        }
//
//        public void setPayType(int payType) {
//            this.payType = payType;
//        }
//
//        public double getMoney() {
//            return money;
//        }
//
//        public void setMoney(double money) {
//            this.money = money;
//        }
//
//        public Object getPreferentialMoney() {
//            return preferentialMoney;
//        }
//
//        public void setPreferentialMoney(Object preferentialMoney) {
//            this.preferentialMoney = preferentialMoney;
//        }
//
//        public Object getRefundApplyTime() {
//            return refundApplyTime;
//        }
//
//        public void setRefundApplyTime(Object refundApplyTime) {
//            this.refundApplyTime = refundApplyTime;
//        }
//
//        public Object getRefundTime() {
//            return refundTime;
//        }
//
//        public void setRefundTime(Object refundTime) {
//            this.refundTime = refundTime;
//        }
//
//        public String getRefundRemark() {
//            return refundRemark;
//        }
//
//        public void setRefundRemark(String refundRemark) {
//            this.refundRemark = refundRemark;
//        }
//
//        public int getPayStatus() {
//            return payStatus;
//        }
//
//        public void setPayStatus(int payStatus) {
//            this.payStatus = payStatus;
//        }
//
//        public int getStatus() {
//            return status;
//        }
//
//        public void setStatus(int status) {
//            this.status = status;
//        }
//
//        public int getOperType() {
//            return operType;
//        }
//
//        public void setOperType(int operType) {
//            this.operType = operType;
//        }
//
//        public Object getStartRideDate() {
//            return startRideDate;
//        }
//
//        public void setStartRideDate(Object startRideDate) {
//            this.startRideDate = startRideDate;
//        }
//
//        public Object getEndRideDate() {
//            return endRideDate;
//        }
//
//        public void setEndRideDate(Object endRideDate) {
//            this.endRideDate = endRideDate;
//        }
//
//        public Object getStartRefundApplyTime() {
//            return startRefundApplyTime;
//        }
//
//        public void setStartRefundApplyTime(Object startRefundApplyTime) {
//            this.startRefundApplyTime = startRefundApplyTime;
//        }
//
//        public Object getEndRefundApplyTime() {
//            return endRefundApplyTime;
//        }
//
//        public void setEndRefundApplyTime(Object endRefundApplyTime) {
//            this.endRefundApplyTime = endRefundApplyTime;
//        }
//    }

}
