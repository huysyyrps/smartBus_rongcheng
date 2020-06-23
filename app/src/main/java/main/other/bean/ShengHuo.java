package main.other.bean;

import java.io.Serializable;
import java.util.List;

public class ShengHuo implements Serializable {

    /**
     * msg :
     * code : 0
     * success : true
     * extraData : null
     * noticeData : null
     * data : [{"id":8,"version":0,"gmtCreate":1573194282183,"gmtModify":1573194282183,"deleted":0,"gmtDeleted":null,"title":"给对方回家考虑","isRelease":1,"releaseTime":"2019-11-08","briefIntroduction":null,"showPictures":null,"details":"szbxbcvbnm,1","startReleaseDate":null,"endReleaseDate":null}]
     * customData : null
     * count : 1
     * nextUrl : null
     */

    private String msg;
    private int code;
    private boolean success;
    private Object extraData;
    private Object noticeData;
    private Object customData;
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

    public Object getCustomData() {
        return customData;
    }

    public void setCustomData(Object customData) {
        this.customData = customData;
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
         * id : 8
         * version : 0
         * gmtCreate : 1573194282183
         * gmtModify : 1573194282183
         * deleted : 0
         * gmtDeleted : null
         * title : 给对方回家考虑
         * isRelease : 1
         * releaseTime : 2019-11-08
         * briefIntroduction : null
         * showPictures : null
         * details : szbxbcvbnm,1
         * startReleaseDate : null
         * endReleaseDate : null
         */

        private int id;
        private int version;
        private long gmtCreate;
        private long gmtModify;
        private int deleted;
        private Object gmtDeleted;
        private String title;
        private int isRelease;
        private String releaseTime;
        private Object briefIntroduction;
        private Object showPictures;
        private String details;
        private Object startReleaseDate;
        private Object endReleaseDate;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIsRelease() {
            return isRelease;
        }

        public void setIsRelease(int isRelease) {
            this.isRelease = isRelease;
        }

        public String getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(String releaseTime) {
            this.releaseTime = releaseTime;
        }

        public Object getBriefIntroduction() {
            return briefIntroduction;
        }

        public void setBriefIntroduction(Object briefIntroduction) {
            this.briefIntroduction = briefIntroduction;
        }

        public Object getShowPictures() {
            return showPictures;
        }

        public void setShowPictures(Object showPictures) {
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
    }
}
