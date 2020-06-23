package main.sheet.bean;

import java.io.Serializable;
import java.util.List;

public class AdvertDown implements Serializable {

    /**
     * msg :
     * code : 0
     * success : true
     * extraData : null
     * noticeData : null
     * data : [{"id":32,"version":1,"gmtCreate":1568166415340,"gmtModify":1568891174347,"deleted":0,"advName":" 广告位1","title":"112233","picturesTexts":"http://117.158.56.11:8099/IBAPP/2613c42f-3d37-4a10-829a-b27e0f8766a3.jpg","connectionAddress":"","typesettingOrder":11,"displayed":"1","remarks":"","avdId":2,"gmtDeleted":null,"rideTimeStart":null,"rideTimeEnd":null,"advertisementPushDTO":null,"positionName":" 广告位1","typeId":1},{"id":33,"version":0,"gmtCreate":1568190589227,"gmtModify":1568190589227,"deleted":0,"advName":" 广告位1","title":"aaaa","picturesTexts":"http://117.158.56.11:8099/IBAPP/4f07cb0f-cbb6-4c04-9687-5a3bdb047ccd.jpg","connectionAddress":"","typesettingOrder":11,"displayed":"1","remarks":"","avdId":2,"gmtDeleted":null,"rideTimeStart":null,"rideTimeEnd":null,"advertisementPushDTO":null,"positionName":" 广告位1","typeId":1}]
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
         * id : 32
         * version : 1
         * gmtCreate : 1568166415340
         * gmtModify : 1568891174347
         * deleted : 0
         * advName :  广告位1
         * title : 112233
         * picturesTexts : http://117.158.56.11:8099/IBAPP/2613c42f-3d37-4a10-829a-b27e0f8766a3.jpg
         * connectionAddress :
         * typesettingOrder : 11
         * displayed : 1
         * remarks :
         * avdId : 2
         * gmtDeleted : null
         * rideTimeStart : null
         * rideTimeEnd : null
         * advertisementPushDTO : null
         * positionName :  广告位1
         * typeId : 1
         */

        private int id;
        private int version;
        private long gmtCreate;
        private long gmtModify;
        private int deleted;
        private String advName;
        private String title;
        private String picturesTexts;
        private String connectionAddress;
        private int typesettingOrder;
        private String displayed;
        private String remarks;
        private int avdId;
        private Object gmtDeleted;
        private Object rideTimeStart;
        private Object rideTimeEnd;
        private Object advertisementPushDTO;
        private String positionName;
        private int typeId;

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

        public String getAdvName() {
            return advName;
        }

        public void setAdvName(String advName) {
            this.advName = advName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPicturesTexts() {
            return picturesTexts;
        }

        public void setPicturesTexts(String picturesTexts) {
            this.picturesTexts = picturesTexts;
        }

        public String getConnectionAddress() {
            return connectionAddress;
        }

        public void setConnectionAddress(String connectionAddress) {
            this.connectionAddress = connectionAddress;
        }

        public int getTypesettingOrder() {
            return typesettingOrder;
        }

        public void setTypesettingOrder(int typesettingOrder) {
            this.typesettingOrder = typesettingOrder;
        }

        public String getDisplayed() {
            return displayed;
        }

        public void setDisplayed(String displayed) {
            this.displayed = displayed;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public int getAvdId() {
            return avdId;
        }

        public void setAvdId(int avdId) {
            this.avdId = avdId;
        }

        public Object getGmtDeleted() {
            return gmtDeleted;
        }

        public void setGmtDeleted(Object gmtDeleted) {
            this.gmtDeleted = gmtDeleted;
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

        public Object getAdvertisementPushDTO() {
            return advertisementPushDTO;
        }

        public void setAdvertisementPushDTO(Object advertisementPushDTO) {
            this.advertisementPushDTO = advertisementPushDTO;
        }

        public String getPositionName() {
            return positionName;
        }

        public void setPositionName(String positionName) {
            this.positionName = positionName;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }
    }
}
