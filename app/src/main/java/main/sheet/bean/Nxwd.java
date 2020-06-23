package main.sheet.bean;

import java.io.Serializable;
import java.util.List;

public class Nxwd implements Serializable {

    /**
     * msg :
     * code : 0
     * success : true
     * extraData : null
     * noticeData : null
     * data : [{"id":6,"version":0,"gmtCreate":1569652631833,"gmtModify":1569652631833,"deleted":0,"gmtDeleted":null,"networkName":"测试网点1","effective":"1","showPictures":"ea799a1a-c5cc-4021-a42b-7c82273c741e.png","introduction":"","contactNumber":"123456789","siteAddress":"河南省三门峡市灵宝市金城大道19","longitude":"110.90055","latitude":"34.523144","details":"测试网点设置2"},{"id":5,"version":0,"gmtCreate":1569652600320,"gmtModify":1569652600320,"deleted":0,"gmtDeleted":null,"networkName":"测试网点","effective":"1","showPictures":"51db09a6-bece-49d8-91df-d5fa19f7fefb.png","introduction":"","contactNumber":"15269179758","siteAddress":"济南市留学人员创业园","longitude":"117.097018","latitude":"36.692324","details":"测试使用网点设置"}]
     * count : 2
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
         * id : 6
         * version : 0
         * gmtCreate : 1569652631833
         * gmtModify : 1569652631833
         * deleted : 0
         * gmtDeleted : null
         * networkName : 测试网点1
         * effective : 1
         * showPictures : ea799a1a-c5cc-4021-a42b-7c82273c741e.png
         * introduction :
         * contactNumber : 123456789
         * siteAddress : 河南省三门峡市灵宝市金城大道19
         * longitude : 110.90055
         * latitude : 34.523144
         * details : 测试网点设置2
         */

        private int id;
        private int version;
        private long gmtCreate;
        private long gmtModify;
        private int deleted;
        private Object gmtDeleted;
        private String networkName;
        private String effective;
        private String showPictures;
        private String introduction;
        private String contactNumber;
        private String siteAddress;
        private String longitude;
        private String latitude;
        private String details;

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

        public Object getGmtDeleted() {
            return gmtDeleted;
        }

        public void setGmtDeleted(Object gmtDeleted) {
            this.gmtDeleted = gmtDeleted;
        }

        public String getNetworkName() {
            return networkName;
        }

        public void setNetworkName(String networkName) {
            this.networkName = networkName;
        }

        public String getEffective() {
            return effective;
        }

        public void setEffective(String effective) {
            this.effective = effective;
        }

        public String getShowPictures() {
            return showPictures;
        }

        public void setShowPictures(String showPictures) {
            this.showPictures = showPictures;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public String getSiteAddress() {
            return siteAddress;
        }

        public void setSiteAddress(String siteAddress) {
            this.siteAddress = siteAddress;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }
}
