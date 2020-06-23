package main.sheet.bean;

import java.io.Serializable;
import java.util.List;

public class ComplaintsList implements Serializable {

    /**
     * msg :
     * code : 0
     * success : true
     * extraData : null
     * noticeData : null
     * data : [{"id":2,"version":2,"gmtCreate":null,"gmtModify":1569287738913,"deleted":0,"content":"456","feedback":"≤‚ ‘","telephone":"234","complaintTime":"2019-08-05 13:28:25","processingStatus":"1","processingTime":"2019-09-02","processingResults":"≤‚ ‘","startComplaintDate":null,"endComplaintDate":null},{"id":1,"version":1,"gmtCreate":null,"gmtModify":1568093158243,"deleted":0,"content":"123","feedback":"∫«∫«","telephone":"123","complaintTime":"2019-09-10 13:23:59","processingStatus":"1","processingTime":"2019-09-09","processingResults":"∫«∫«","startComplaintDate":null,"endComplaintDate":null}]
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
         * id : 2
         * version : 2
         * gmtCreate : null
         * gmtModify : 1569287738913
         * deleted : 0
         * content : 456
         * feedback : ≤‚ ‘
         * telephone : 234
         * complaintTime : 2019-08-05 13:28:25
         * processingStatus : 1
         * processingTime : 2019-09-02
         * processingResults : ≤‚ ‘
         * startComplaintDate : null
         * endComplaintDate : null
         */

        private String id;
        private int version;
        private Object gmtCreate;
        private long gmtModify;
        private int deleted;
        private String content;
        private String feedback;
        private String telephone;
        private String complaintTime;
        private String processingStatus;
        private String processingTime;
        private String processingResults;
        private Object startComplaintDate;
        private Object endComplaintDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public Object getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(Object gmtCreate) {
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFeedback() {
            return feedback;
        }

        public void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getComplaintTime() {
            return complaintTime;
        }

        public void setComplaintTime(String complaintTime) {
            this.complaintTime = complaintTime;
        }

        public String getProcessingStatus() {
            return processingStatus;
        }

        public void setProcessingStatus(String processingStatus) {
            this.processingStatus = processingStatus;
        }

        public String getProcessingTime() {
            return processingTime;
        }

        public void setProcessingTime(String processingTime) {
            this.processingTime = processingTime;
        }

        public String getProcessingResults() {
            return processingResults;
        }

        public void setProcessingResults(String processingResults) {
            this.processingResults = processingResults;
        }

        public Object getStartComplaintDate() {
            return startComplaintDate;
        }

        public void setStartComplaintDate(Object startComplaintDate) {
            this.startComplaintDate = startComplaintDate;
        }

        public Object getEndComplaintDate() {
            return endComplaintDate;
        }

        public void setEndComplaintDate(Object endComplaintDate) {
            this.endComplaintDate = endComplaintDate;
        }
    }
}
