package main.sheet.bean;

import java.io.Serializable;
import java.util.List;

public class Swrllist implements Serializable {

    /**
     * msg :
     * code : 0
     * success : true
     * extraData : null
     * noticeData : null
     * data : [{"id":10,"version":1,"gmtCreate":1569720000647,"gmtModify":1569720007730,"deleted":0,"title":"发现一张乘客身份证","busRoutes":"32路","occurrenceTime":"2019-09-29 09:16:10","detailedDescription":"<div align=\"center\"><p><i><b>关于32乘客在车内捡到身份证的说明 <br><\/b><\/i><\/p><p align=\"left\">大家好，我说乘客张三，在坐32路的时候，在车厢的最后一排发现名为\u201c王五\u201d的人员身份证<br><i><\/i><\/p><\/div>","state":"1","claimTime":null,"claimName":null,"contactInformation":null,"remarks":null,"occurrenceTimeSTR":null,"occurrenceTimeStart":null,"rideTimeEnd":null},{"id":9,"version":1,"gmtCreate":1569553342263,"gmtModify":1569553352690,"deleted":0,"title":"共同体人","busRoutes":"干热风个人","occurrenceTime":"2019-09-27 11:02:18","detailedDescription":"大幅度人防","state":"2","claimTime":null,"claimName":null,"contactInformation":null,"remarks":null,"occurrenceTimeSTR":null,"occurrenceTimeStart":null,"rideTimeEnd":null},{"id":8,"version":0,"gmtCreate":1569481905240,"gmtModify":1569481905240,"deleted":0,"title":"地方","busRoutes":"电风扇给对方","occurrenceTime":"2019-09-26 00:00:00","detailedDescription":"<a target=\"_self\" href=\"http://123.232.38.10:81/zentao\">http://123.232.38.10:81/zentao<\/a>","state":"1","claimTime":null,"claimName":null,"contactInformation":null,"remarks":null,"occurrenceTimeSTR":null,"occurrenceTimeStart":null,"rideTimeEnd":null},{"id":7,"version":2,"gmtCreate":1569477819043,"gmtModify":1569478148227,"deleted":0,"title":"钥匙认领","busRoutes":"9031路","occurrenceTime":"2019-09-26 00:00:00","detailedDescription":"测试test","state":"3","claimTime":"2019-09-26 00:00:00","claimName":"test认领","contactInformation":"1212115","remarks":null,"occurrenceTimeSTR":null,"occurrenceTimeStart":null,"rideTimeEnd":null},{"id":6,"version":0,"gmtCreate":1569460100600,"gmtModify":1569460100600,"deleted":0,"title":"手机认领","busRoutes":"2路","occurrenceTime":"2019-09-26 00:00:00","detailedDescription":"车上落了一个黑色的华为手机，粉色手机壳<br>","state":"1","claimTime":null,"claimName":null,"contactInformation":null,"remarks":null,"occurrenceTimeSTR":null,"occurrenceTimeStart":null,"rideTimeEnd":null}]
     * count : 5
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

    public static class DataBean implements Serializable{
        /**
         * id : 10
         * version : 1
         * gmtCreate : 1569720000647
         * gmtModify : 1569720007730
         * deleted : 0
         * title : 发现一张乘客身份证
         * busRoutes : 32路
         * occurrenceTime : 2019-09-29 09:16:10
         * detailedDescription : <div align="center"><p><i><b>关于32乘客在车内捡到身份证的说明 <br></b></i></p><p align="left">大家好，我说乘客张三，在坐32路的时候，在车厢的最后一排发现名为“王五”的人员身份证<br><i></i></p></div>
         * state : 1
         * claimTime : null
         * claimName : null
         * contactInformation : null
         * remarks : null
         * occurrenceTimeSTR : null
         * occurrenceTimeStart : null
         * rideTimeEnd : null
         */

        private int id;
        private int version;
        private long gmtCreate;
        private long gmtModify;
        private int deleted;
        private String title;
        private String busRoutes;
        private String occurrenceTime;
        private String detailedDescription;
        private String state;
        private Object claimTime;
        private Object claimName;
        private Object contactInformation;
        private Object remarks;
        private Object occurrenceTimeSTR;
        private Object occurrenceTimeStart;
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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBusRoutes() {
            return busRoutes;
        }

        public void setBusRoutes(String busRoutes) {
            this.busRoutes = busRoutes;
        }

        public String getOccurrenceTime() {
            return occurrenceTime;
        }

        public void setOccurrenceTime(String occurrenceTime) {
            this.occurrenceTime = occurrenceTime;
        }

        public String getDetailedDescription() {
            return detailedDescription;
        }

        public void setDetailedDescription(String detailedDescription) {
            this.detailedDescription = detailedDescription;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public Object getClaimTime() {
            return claimTime;
        }

        public void setClaimTime(Object claimTime) {
            this.claimTime = claimTime;
        }

        public Object getClaimName() {
            return claimName;
        }

        public void setClaimName(Object claimName) {
            this.claimName = claimName;
        }

        public Object getContactInformation() {
            return contactInformation;
        }

        public void setContactInformation(Object contactInformation) {
            this.contactInformation = contactInformation;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

        public Object getOccurrenceTimeSTR() {
            return occurrenceTimeSTR;
        }

        public void setOccurrenceTimeSTR(Object occurrenceTimeSTR) {
            this.occurrenceTimeSTR = occurrenceTimeSTR;
        }

        public Object getOccurrenceTimeStart() {
            return occurrenceTimeStart;
        }

        public void setOccurrenceTimeStart(Object occurrenceTimeStart) {
            this.occurrenceTimeStart = occurrenceTimeStart;
        }

        public Object getRideTimeEnd() {
            return rideTimeEnd;
        }

        public void setRideTimeEnd(Object rideTimeEnd) {
            this.rideTimeEnd = rideTimeEnd;
        }
    }
}
