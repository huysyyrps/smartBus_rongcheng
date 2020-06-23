package main.customizedBus.ticket.bean;

public class BuyTicketOrderBean {


    /**
     * msg : ²Ù×÷³É¹¦£¡
     * code : 1
     * success : false
     * extraData : null
     * noticeData : null
     * data : {"orderId":"0b85a2f8e0414973904b4d2132486257"}
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
         * orderId : 817f5c39f4a24f959f53381dcffd4676
         * payTicketTime : 30
         */

        private String orderId;
        private int payTicketTime;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getPayTicketTime() {
            return payTicketTime;
        }

        public void setPayTicketTime(int payTicketTime) {
            this.payTicketTime = payTicketTime;
        }
    }
}
