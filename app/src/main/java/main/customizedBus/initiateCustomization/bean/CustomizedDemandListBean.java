package main.customizedBus.initiateCustomization.bean;

import java.util.List;

public class CustomizedDemandListBean {

    /**
     * msg :
     * code : 0
     * success : false
     * extraData : null
     * noticeData : null
     * data : [{"id":21,"version":0,"gmtCreate":1568890912523,"gmtModify":1568890912523,"deleted":0,"passengerAccount":"13361063873","startAddress":"灵华租赁站","endAddress":"方华汽车电器精修","rideTime":-3960000,"rideTimeStr":null,"rideTimeStart":null,"rideTimeEnd":null},{"id":20,"version":0,"gmtCreate":1568890759907,"gmtModify":1568890759907,"deleted":0,"passengerAccount":"13361063873","startAddress":"灵华租赁站","endAddress":"方华汽车电器精修","rideTime":-3960000,"rideTimeStr":null,"rideTimeStart":null,"rideTimeEnd":null},{"id":19,"version":0,"gmtCreate":1568890711000,"gmtModify":1568890711000,"deleted":0,"passengerAccount":"14242342423","startAddress":"服务范围","endAddress":"人服务费","rideTime":4140000,"rideTimeStr":null,"rideTimeStart":null,"rideTimeEnd":null},{"id":18,"version":0,"gmtCreate":1568890701853,"gmtModify":1568890701853,"deleted":0,"passengerAccount":"14242342423","startAddress":"服务范围","endAddress":"人服务费","rideTime":4140000,"rideTimeStr":null,"rideTimeStart":null,"rideTimeEnd":null},{"id":17,"version":0,"gmtCreate":1568890640990,"gmtModify":1568890640990,"deleted":0,"passengerAccount":"13361063873","startAddress":"灵华租赁站","endAddress":"方华汽车电器精修","rideTime":-3960000,"rideTimeStr":null,"rideTimeStart":null,"rideTimeEnd":null},{"id":16,"version":0,"gmtCreate":1568890374323,"gmtModify":1568890374323,"deleted":0,"passengerAccount":"14242342423","startAddress":"服务范围","endAddress":"人服务费","rideTime":4140000,"rideTimeStr":null,"rideTimeStart":null,"rideTimeEnd":null},{"id":15,"version":0,"gmtCreate":1568599248193,"gmtModify":1568599248193,"deleted":0,"passengerAccount":"15212345678","startAddress":"11","endAddress":"55","rideTime":28800000,"rideTimeStr":null,"rideTimeStart":null,"rideTimeEnd":null},{"id":14,"version":0,"gmtCreate":1568599243120,"gmtModify":1568599243120,"deleted":0,"passengerAccount":"15212345678","startAddress":"11","endAddress":"55","rideTime":25200000,"rideTimeStr":null,"rideTimeStart":null,"rideTimeEnd":null},{"id":13,"version":0,"gmtCreate":1568599239260,"gmtModify":1568599239260,"deleted":0,"passengerAccount":"15212345678","startAddress":"11","endAddress":"55","rideTime":21600000,"rideTimeStr":null,"rideTimeStart":null,"rideTimeEnd":null},{"id":12,"version":0,"gmtCreate":1568599235860,"gmtModify":1568599235860,"deleted":0,"passengerAccount":"15212345678","startAddress":"11","endAddress":"55","rideTime":18000000,"rideTimeStr":null,"rideTimeStart":null,"rideTimeEnd":null},{"id":11,"version":0,"gmtCreate":1568599230970,"gmtModify":1568599230970,"deleted":0,"passengerAccount":"15212345678","startAddress":"11","endAddress":"55","rideTime":14400000,"rideTimeStr":null,"rideTimeStart":null,"rideTimeEnd":null},{"id":10,"version":0,"gmtCreate":1568598432080,"gmtModify":1568598432080,"deleted":0,"passengerAccount":"15212345678","startAddress":"11","endAddress":"55","rideTime":10800000,"rideTimeStr":null,"rideTimeStart":null,"rideTimeEnd":null}]
     * count : 12
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
         * id : 21
         * version : 0
         * gmtCreate : 1568890912523
         * gmtModify : 1568890912523
         * deleted : 0
         * passengerAccount : 13361063873
         * startAddress : 灵华租赁站
         * endAddress : 方华汽车电器精修
         * rideTime : -3960000
         * rideTimeStr : null
         * rideTimeStart : null
         * rideTimeEnd : null
         */

        private int id;
        private int version;
        private long gmtCreate;
        private long gmtModify;
        private int deleted;
        private String passengerAccount;
        private String startAddress;
        private String endAddress;
        private int rideTime;
        private Object rideTimeStr;
        private Object rideTimeStart;
        private Object rideTimeEnd;

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

        public String getPassengerAccount() {
            return passengerAccount;
        }

        public void setPassengerAccount(String passengerAccount) {
            this.passengerAccount = passengerAccount;
        }

        public String getStartAddress() {
            return startAddress;
        }

        public void setStartAddress(String startAddress) {
            this.startAddress = startAddress;
        }

        public String getEndAddress() {
            return endAddress;
        }

        public void setEndAddress(String endAddress) {
            this.endAddress = endAddress;
        }

        public int getRideTime() {
            return rideTime;
        }

        public void setRideTime(int rideTime) {
            this.rideTime = rideTime;
        }

        public Object getRideTimeStr() {
            return rideTimeStr;
        }

        public void setRideTimeStr(Object rideTimeStr) {
            this.rideTimeStr = rideTimeStr;
        }

        public Object getRideTimeStart() {
            return rideTimeStart;
        }

        public void setRideTimeStart(Object rideTimeStart) {
            this.rideTimeStart = rideTimeStart;
        }

        public Object getRideTimeEnd() {
            return rideTimeEnd;
        }

        public void setRideTimeEnd(Object rideTimeEnd) {
            this.rideTimeEnd = rideTimeEnd;
        }
    }
}
