package main.customizedBus.home.bean;

import java.util.List;

public class CustomizedBusIntroduceBean {


    /**
     * msg :
     * code : 0
     * success : true
     * extraData : null
     * noticeData : null
     * data : [{"id":19,"version":0,"gmtCreate":1569664922397,"gmtModify":1569664922397,"deleted":0,"title":"购票须知","messageType":"3005","briefIntroduction":"","release":"1","releaseTime":"2019-09-28","showPictures":"","details":"<p>1.是佛水电费<\/p><p>2558686868<\/p><p>3.感受到感受到<\/p>","startReleaseDate":null,"endReleaseDate":null,"gmtDeleted":null,"name":"购票须知","parentId":106,"appId":null,"allPath":null,"sorted":5,"isLeaf":null,"status":1,"remark":"","sysNum":null,"oid":null}]
     * count : 1
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
         * id : 19
         * version : 0
         * gmtCreate : 1569664922397
         * gmtModify : 1569664922397
         * deleted : 0
         * title : 购票须知
         * messageType : 3005
         * briefIntroduction :
         * release : 1
         * releaseTime : 2019-09-28
         * showPictures :
         * details : <p>1.是佛水电费</p><p>2558686868</p><p>3.感受到感受到</p>
         * startReleaseDate : null
         * endReleaseDate : null
         * gmtDeleted : null
         * name : 购票须知
         * parentId : 106
         * appId : null
         * allPath : null
         * sorted : 5.0
         * isLeaf : null
         * status : 1
         * remark :
         * sysNum : null
         * oid : null
         */

        private int id;
        private int version;
        private long gmtCreate;
        private long gmtModify;
        private int deleted;
        private String title;
        private String messageType;
        private String briefIntroduction;
        private String release;
        private String releaseTime;
        private String showPictures;
        private String details;
        private Object startReleaseDate;
        private Object endReleaseDate;
        private Object gmtDeleted;
        private String name;
        private int parentId;
        private Object appId;
        private Object allPath;
        private double sorted;
        private Object isLeaf;
        private int status;
        private String remark;
        private Object sysNum;
        private Object oid;

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

        public String getMessageType() {
            return messageType;
        }

        public void setMessageType(String messageType) {
            this.messageType = messageType;
        }

        public String getBriefIntroduction() {
            return briefIntroduction;
        }

        public void setBriefIntroduction(String briefIntroduction) {
            this.briefIntroduction = briefIntroduction;
        }

        public String getRelease() {
            return release;
        }

        public void setRelease(String release) {
            this.release = release;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public String getShowPictures() {
            return showPictures;
        }

        public void setShowPictures(String showPictures) {
            this.showPictures = showPictures;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public Object getStartReleaseDate() {
            return startReleaseDate;
        }

        public void setStartReleaseDate(Object startReleaseDate) {
            this.startReleaseDate = startReleaseDate;
        }

        public Object getEndReleaseDate() {
            return endReleaseDate;
        }

        public void setEndReleaseDate(Object endReleaseDate) {
            this.endReleaseDate = endReleaseDate;
        }

        public Object getGmtDeleted() {
            return gmtDeleted;
        }

        public void setGmtDeleted(Object gmtDeleted) {
            this.gmtDeleted = gmtDeleted;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public Object getAppId() {
            return appId;
        }

        public void setAppId(Object appId) {
            this.appId = appId;
        }

        public Object getAllPath() {
            return allPath;
        }

        public void setAllPath(Object allPath) {
            this.allPath = allPath;
        }

        public double getSorted() {
            return sorted;
        }

        public void setSorted(double sorted) {
            this.sorted = sorted;
        }

        public Object getIsLeaf() {
            return isLeaf;
        }

        public void setIsLeaf(Object isLeaf) {
            this.isLeaf = isLeaf;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public Object getSysNum() {
            return sysNum;
        }

        public void setSysNum(Object sysNum) {
            this.sysNum = sysNum;
        }

        public Object getOid() {
            return oid;
        }

        public void setOid(Object oid) {
            this.oid = oid;
        }
    }
}
