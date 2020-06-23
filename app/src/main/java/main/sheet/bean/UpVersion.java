package main.sheet.bean;

import java.io.Serializable;
import java.util.List;

public class UpVersion implements Serializable {

    /**
     * msg :
     * code : 0
     * success : false
     * extraData : null
     * noticeData : null
     * data : [{"id":10019,"version":0,"gmtCreate":1569737219287,"gmtModify":1569737219287,"deleted":0,"vnumber":"1.03","vname":"V1.03","updateinformation":"版本升级","downloadlink":"","forcedupdating":"0"},{"id":10018,"version":0,"gmtCreate":1569737010650,"gmtModify":1569737010650,"deleted":0,"vnumber":"1.02","vname":"v1.02","updateinformation":"修改已知bug","downloadlink":"","forcedupdating":"1"},{"id":10016,"version":3,"gmtCreate":1569562291373,"gmtModify":1569736672987,"deleted":0,"vnumber":"1.01","vname":"V1.01","updateinformation":"基础版本","downloadlink":"1","forcedupdating":"1"},{"id":10014,"version":2,"gmtCreate":1569466452363,"gmtModify":1569577044167,"deleted":0,"vnumber":"1.0.2","vname":"V1.0.2","updateinformation":" ","downloadlink":"1","forcedupdating":"1"},{"id":10013,"version":1,"gmtCreate":1569466373253,"gmtModify":1569577057960,"deleted":0,"vnumber":"1.0.3","vname":"V1.0.3","updateinformation":" ","downloadlink":"","forcedupdating":"0"}]
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
         * id : 10019
         * version : 0
         * gmtCreate : 1569737219287
         * gmtModify : 1569737219287
         * deleted : 0
         * vnumber : 1.03
         * vname : V1.03
         * updateinformation : 版本升级
         * downloadlink :
         * forcedupdating : 0
         */

        private int id;
        private int version;
        private long gmtCreate;
        private long gmtModify;
        private int deleted;
        private String vnumber;
        private String vname;
        private String updateinformation;
        private String downloadlink;
        private String forcedupdating;

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

        public String getVnumber() {
            return vnumber;
        }

        public void setVnumber(String vnumber) {
            this.vnumber = vnumber;
        }

        public String getVname() {
            return vname;
        }

        public void setVname(String vname) {
            this.vname = vname;
        }

        public String getUpdateinformation() {
            return updateinformation;
        }

        public void setUpdateinformation(String updateinformation) {
            this.updateinformation = updateinformation;
        }

        public String getDownloadlink() {
            return downloadlink;
        }

        public void setDownloadlink(String downloadlink) {
            this.downloadlink = downloadlink;
        }

        public String getForcedupdating() {
            return forcedupdating;
        }

        public void setForcedupdating(String forcedupdating) {
            this.forcedupdating = forcedupdating;
        }
    }
}
