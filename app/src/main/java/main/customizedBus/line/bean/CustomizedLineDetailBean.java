package main.customizedBus.line.bean;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import main.utils.utils.PublicData;

public class CustomizedLineDetailBean implements Serializable{


    /**
     * msg :
     * code : 0
     * success : false
     * extraData : null
     * noticeData : null
     * data : {"id":10062,"version":2,"gmtCreate":1570780153997,"gmtModify":1570780187557,"deleted":0,"name":"21路","schedul":"16:00","startStation":"李二","endStation":"金碧","preferentialType":0,"price":0.01,"preferentialPrice":0.01,"middleTicketType":0,"preferentialMiddleType":0,"middlePrice":0.01,"preferentialMiddlePrice":0.01,"votes":85,"startDate":1570723200000,"endDate":1571932800000,"remark":null,"status":1,"sartdis":null,"enddis":null,"distance":null,"startLon":null,"startLat":null,"endLon":null,"endLat":null,"startDateStr":"2019-10-11","endDateStr":"2019-10-25","stationListJson":null,"stationList":[],"schedulDTOListJson":null,"schedulDTOList":[{"id":20117,"version":0,"gmtCreate":1570780172610,"gmtModify":1570780172610,"deleted":0,"line":10062,"schedulTime":"16:00","carNum":"","lineName":null,"lineDTOJson":null,"lineDTO":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"name":null,"schedul":null,"startStation":null,"endStation":null,"preferentialType":null,"price":null,"preferentialPrice":null,"middleTicketType":null,"preferentialMiddleType":null,"middlePrice":null,"preferentialMiddlePrice":null,"votes":null,"startDate":null,"endDate":null,"remark":null,"status":null,"sartdis":null,"enddis":null,"distance":null,"startLon":null,"startLat":null,"endLon":null,"endLat":null,"startDateStr":null,"endDateStr":null,"stationListJson":null,"stationList":[],"schedulDTOListJson":null,"schedulDTOList":[],"schedulStationDTOList":[],"linePlanDTOListJson":null,"linePlanDTOList":[]},"schedulStationDTOListJson":null,"schedulStationDTOList":[{"id":10444,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":20117,"stationId":20304,"time":"15:49","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":10062,"orderNum":1,"name":"李二","address":"个人","lon":"110.896633","lat":"34.528051","flag":0}},{"id":10445,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":20117,"stationId":20305,"time":"15:52","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":10062,"orderNum":2,"name":"金碧","address":"分管特","lon":"110.896202","lat":"34.518326","flag":1}}],"ticketCountDTOList":[{"id":11810,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1570723200000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11811,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1570809600000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11812,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1570896000000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11813,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1570982400000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11814,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571068800000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11815,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571155200000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11816,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571241600000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11817,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571328000000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11818,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571414400000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11819,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571500800000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11820,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571587200000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11821,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571673600000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11822,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571760000000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11823,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571846400000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11824,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571932800000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null}]}],"schedulStationDTOList":[],"linePlanDTOListJson":null,"linePlanDTOList":[{"id":1,"version":0,"gmtCreate":1570780154047,"gmtModify":1570780154047,"deleted":0,"lineId":10062,"orderNum":1,"lng":"110.896693","lat":"34.528081"},{"id":2,"version":0,"gmtCreate":1570780154047,"gmtModify":1570780154047,"deleted":0,"lineId":10062,"orderNum":2,"lng":"110.890872","lat":"34.522876"},{"id":3,"version":0,"gmtCreate":1570780154047,"gmtModify":1570780154047,"deleted":0,"lineId":10062,"orderNum":3,"lng":"110.896397","lat":"34.518222"}]}
     * count : null
     * nextUrl : null
     */

    private String msg;
    private int code;
    private boolean success;

    private DataBean data;

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


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }



    public static class DataBean  implements Serializable {
        /**
         * id : 10062
         * version : 2
         * gmtCreate : 1570780153997
         * gmtModify : 1570780187557
         * deleted : 0
         * name : 21路
         * schedul : 16:00
         * startStation : 李二
         * endStation : 金碧
         * preferentialType : 0
         * price : 0.01
         * preferentialPrice : 0.01
         * middleTicketType : 0
         * preferentialMiddleType : 0
         * middlePrice : 0.01
         * preferentialMiddlePrice : 0.01
         * votes : 85
         * startDate : 1570723200000
         * endDate : 1571932800000
         * remark : null
         * status : 1
         * sartdis : null
         * enddis : null
         * distance : null
         * startLon : null
         * startLat : null
         * endLon : null
         * endLat : null
         * startDateStr : 2019-10-11
         * endDateStr : 2019-10-25
         * stationListJson : null
         * stationList : []
         * schedulDTOListJson : null
         * schedulDTOList : [{"id":20117,"version":0,"gmtCreate":1570780172610,"gmtModify":1570780172610,"deleted":0,"line":10062,"schedulTime":"16:00","carNum":"","lineName":null,"lineDTOJson":null,"lineDTO":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"name":null,"schedul":null,"startStation":null,"endStation":null,"preferentialType":null,"price":null,"preferentialPrice":null,"middleTicketType":null,"preferentialMiddleType":null,"middlePrice":null,"preferentialMiddlePrice":null,"votes":null,"startDate":null,"endDate":null,"remark":null,"status":null,"sartdis":null,"enddis":null,"distance":null,"startLon":null,"startLat":null,"endLon":null,"endLat":null,"startDateStr":null,"endDateStr":null,"stationListJson":null,"stationList":[],"schedulDTOListJson":null,"schedulDTOList":[],"schedulStationDTOList":[],"linePlanDTOListJson":null,"linePlanDTOList":[]},"schedulStationDTOListJson":null,"schedulStationDTOList":[{"id":10444,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":20117,"stationId":20304,"time":"15:49","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":10062,"orderNum":1,"name":"李二","address":"个人","lon":"110.896633","lat":"34.528051","flag":0}},{"id":10445,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":20117,"stationId":20305,"time":"15:52","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":10062,"orderNum":2,"name":"金碧","address":"分管特","lon":"110.896202","lat":"34.518326","flag":1}}],"ticketCountDTOList":[{"id":11810,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1570723200000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11811,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1570809600000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11812,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1570896000000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11813,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1570982400000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11814,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571068800000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11815,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571155200000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11816,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571241600000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11817,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571328000000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11818,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571414400000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11819,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571500800000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11820,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571587200000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11821,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571673600000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11822,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571760000000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11823,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571846400000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11824,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571932800000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null}]}]
         * schedulStationDTOList : []
         * linePlanDTOListJson : null
         * linePlanDTOList : [{"id":1,"version":0,"gmtCreate":1570780154047,"gmtModify":1570780154047,"deleted":0,"lineId":10062,"orderNum":1,"lng":"110.896693","lat":"34.528081"},{"id":2,"version":0,"gmtCreate":1570780154047,"gmtModify":1570780154047,"deleted":0,"lineId":10062,"orderNum":2,"lng":"110.890872","lat":"34.522876"},{"id":3,"version":0,"gmtCreate":1570780154047,"gmtModify":1570780154047,"deleted":0,"lineId":10062,"orderNum":3,"lng":"110.896397","lat":"34.518222"}]
         */

        private int id;
        private int version;
        private long gmtCreate;
        private long gmtModify;
        private int deleted;
        private String name;
        private String schedul;
        private String startStation;
        private String endStation;
        private int preferentialType;
        private double price;
        private double preferentialPrice;
        private int middleTicketType;
        private int preferentialMiddleType;
        private double middlePrice;
        private double preferentialMiddlePrice;
        private int votes;
        private long startDate;
        private long endDate;
        private int status;

        private String startDateStr;
        private String endDateStr;

        private List<SchedulDTOListBean> schedulDTOList;
        private List<LinePlanDTOListBean> linePlanDTOList;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSchedul() {
            return schedul;
        }

        public void setSchedul(String schedul) {
            this.schedul = schedul;
        }

        public String getStartStation() {
            return startStation;
        }

        public void setStartStation(String startStation) {
            this.startStation = startStation;
        }

        public String getEndStation() {
            return endStation;
        }

        public void setEndStation(String endStation) {
            this.endStation = endStation;
        }
        public List<String> getSchedulArr(){
            List<String> schedulArr = new ArrayList<>();
            if (schedulDTOList!=null){
                for (int i = 0; i < schedulDTOList.size(); i++) {
                    SchedulDTOListBean dtoListBean = schedulDTOList.get(i);
                    schedulArr.add(dtoListBean.schedulTime);

                }

            }
            return schedulArr;
        }

        public int getPreferentialType() {
            return preferentialType;
        }

        public void setPreferentialType(int preferentialType) {
            this.preferentialType = preferentialType;
        }

        public double getPrice() {
            return price;
        }

        public  String getPriceStr(){
            return PublicData.getDoubleStr(getPrice());
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getPreferentialPrice() {
            return preferentialPrice;
        }

        public void setPreferentialPrice(double preferentialPrice) {
            this.preferentialPrice = preferentialPrice;
        }

        public int getMiddleTicketType() {
            return middleTicketType;
        }

        public void setMiddleTicketType(int middleTicketType) {
            this.middleTicketType = middleTicketType;
        }

        public int getPreferentialMiddleType() {
            return preferentialMiddleType;
        }

        public void setPreferentialMiddleType(int preferentialMiddleType) {
            this.preferentialMiddleType = preferentialMiddleType;
        }

        public double getMiddlePrice() {
            return middlePrice;
        }

        public void setMiddlePrice(double middlePrice) {
            this.middlePrice = middlePrice;
        }

        public double getPreferentialMiddlePrice() {
            return preferentialMiddlePrice;
        }

        public void setPreferentialMiddlePrice(double preferentialMiddlePrice) {
            this.preferentialMiddlePrice = preferentialMiddlePrice;
        }

        public int getVotes() {
            return votes;
        }

        public void setVotes(int votes) {
            this.votes = votes;
        }

        public long getStartDate() {
            return startDate;
        }

        public void setStartDate(long startDate) {
            this.startDate = startDate;
        }

        public long getEndDate() {
            return endDate;
        }

        public void setEndDate(long endDate) {
            this.endDate = endDate;
        }



        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }


        public String getStartDateStr() {
            return startDateStr;
        }

        public void setStartDateStr(String startDateStr) {
            this.startDateStr = startDateStr;
        }

        public String getEndDateStr() {
            return endDateStr;
        }

        public void setEndDateStr(String endDateStr) {
            this.endDateStr = endDateStr;
        }



        public List<SchedulDTOListBean> getSchedulDTOList() {
            return schedulDTOList;
        }

        public void setSchedulDTOList(List<SchedulDTOListBean> schedulDTOList) {
            this.schedulDTOList = schedulDTOList;
        }


        public List<LinePlanDTOListBean> getLinePlanDTOList() {
            return linePlanDTOList;
        }

        public void setLinePlanDTOList(List<LinePlanDTOListBean> linePlanDTOList) {
            this.linePlanDTOList = linePlanDTOList;
        }

        public static class SchedulDTOListBean  implements Serializable {
            /**
             * id : 20117
             * version : 0
             * gmtCreate : 1570780172610
             * gmtModify : 1570780172610
             * deleted : 0
             * line : 10062
             * schedulTime : 16:00
             * carNum :
             * lineName : null
             * lineDTOJson : null
             * lineDTO : {"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"name":null,"schedul":null,"startStation":null,"endStation":null,"preferentialType":null,"price":null,"preferentialPrice":null,"middleTicketType":null,"preferentialMiddleType":null,"middlePrice":null,"preferentialMiddlePrice":null,"votes":null,"startDate":null,"endDate":null,"remark":null,"status":null,"sartdis":null,"enddis":null,"distance":null,"startLon":null,"startLat":null,"endLon":null,"endLat":null,"startDateStr":null,"endDateStr":null,"stationListJson":null,"stationList":[],"schedulDTOListJson":null,"schedulDTOList":[],"schedulStationDTOList":[],"linePlanDTOListJson":null,"linePlanDTOList":[]}
             * schedulStationDTOListJson : null
             * schedulStationDTOList : [{"id":10444,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":20117,"stationId":20304,"time":"15:49","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":10062,"orderNum":1,"name":"李二","address":"个人","lon":"110.896633","lat":"34.528051","flag":0}},{"id":10445,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":20117,"stationId":20305,"time":"15:52","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":10062,"orderNum":2,"name":"金碧","address":"分管特","lon":"110.896202","lat":"34.518326","flag":1}}]
             * ticketCountDTOList : [{"id":11810,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1570723200000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11811,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1570809600000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11812,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1570896000000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11813,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1570982400000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11814,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571068800000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11815,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571155200000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11816,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571241600000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11817,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571328000000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11818,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571414400000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11819,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571500800000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11820,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571587200000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11821,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571673600000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11822,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571760000000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11823,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571846400000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null},{"id":11824,"version":0,"gmtCreate":1570780187513,"gmtModify":1570780187513,"deleted":0,"lineId":10062,"lineName":"21路","schedulId":20117,"schedulTime":"16:00","rideDate":1571932800000,"total":85,"saleCount":0,"ratio":0,"sign":null,"startRideDate":null,"endRideDate":null}]
             */

            private int id;
            private int version;
            private long gmtCreate;
            private long gmtModify;
            private int deleted;
            private int line;
            private String schedulTime;
            private String carNum;

            private LineDTOBean lineDTO;
            private List<SchedulStationDTOListBean> schedulStationDTOList;
            private List<TicketCountDTOListBean> ticketCountDTOList;

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

            public int getLine() {
                return line;
            }

            public void setLine(int line) {
                this.line = line;
            }

            public String getSchedulTime() {
                return schedulTime;
            }

            public void setSchedulTime(String schedulTime) {
                this.schedulTime = schedulTime;
            }

            public String getCarNum() {
                return carNum;
            }

            public void setCarNum(String carNum) {
                this.carNum = carNum;
            }



            public LineDTOBean getLineDTO() {
                return lineDTO;
            }

            public void setLineDTO(LineDTOBean lineDTO) {
                this.lineDTO = lineDTO;
            }



            public List<SchedulStationDTOListBean> getSchedulStationDTOList() {
                return schedulStationDTOList;
            }

            public void setSchedulStationDTOList(List<SchedulStationDTOListBean> schedulStationDTOList) {
                this.schedulStationDTOList = schedulStationDTOList;
            }

            public List<TicketCountDTOListBean> getTicketCountDTOList() {
                return ticketCountDTOList;
            }

            public void setTicketCountDTOList(List<TicketCountDTOListBean> ticketCountDTOList) {
                this.ticketCountDTOList = ticketCountDTOList;
            }

            public static class LineDTOBean  implements Serializable {
                /**
                 * id : null
                 * version : null
                 * gmtCreate : null
                 * gmtModify : null
                 * deleted : null
                 * name : null
                 * schedul : null
                 * startStation : null
                 * endStation : null
                 * preferentialType : null
                 * price : null
                 * preferentialPrice : null
                 * middleTicketType : null
                 * preferentialMiddleType : null
                 * middlePrice : null
                 * preferentialMiddlePrice : null
                 * votes : null
                 * startDate : null
                 * endDate : null
                 * remark : null
                 * status : null
                 * sartdis : null
                 * enddis : null
                 * distance : null
                 * startLon : null
                 * startLat : null
                 * endLon : null
                 * endLat : null
                 * startDateStr : null
                 * endDateStr : null
                 * stationListJson : null
                 * stationList : []
                 * schedulDTOListJson : null
                 * schedulDTOList : []
                 * schedulStationDTOList : []
                 * linePlanDTOListJson : null
                 * linePlanDTOList : []
                 */




            }

            public static class SchedulStationDTOListBean  implements Serializable, IPickerViewData {
                /**
                 * id : 10444
                 * version : null
                 * gmtCreate : null
                 * gmtModify : null
                 * deleted : null
                 * schedulId : 20117
                 * stationId : 20304
                 * time : 15:49
                 * station : {"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":10062,"orderNum":1,"name":"李二","address":"个人","lon":"110.896633","lat":"34.528051","flag":0}
                 */

                private int id;

                private int schedulId;
                private int stationId;
                private String time;
                private StationBean station;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }



                public int getSchedulId() {
                    return schedulId;
                }

                public void setSchedulId(int schedulId) {
                    this.schedulId = schedulId;
                }

                public int getStationId() {
                    return stationId;
                }

                public void setStationId(int stationId) {
                    this.stationId = stationId;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public StationBean getStation() {
                    return station;
                }

                public void setStation(StationBean station) {
                    this.station = station;
                }

                @Override
                public String getPickerViewText() {
                    return getStation().getName()+"("+getTime()+")";
                }

                public static class StationBean  implements Serializable {
                    /**
                     * id : null
                     * version : null
                     * gmtCreate : null
                     * gmtModify : null
                     * deleted : null
                     * gmtDeleted : null
                     * line : 10062
                     * orderNum : 1
                     * name : 李二
                     * address : 个人
                     * lon : 110.896633
                     * lat : 34.528051
                     * flag : 0
                     */

                    private int line;
                    private int orderNum;
                    private String name;
                    private String address;
                    private String lon;
                    private String lat;
                    private int flag;


                    public int getLine() {
                        return line;
                    }

                    public void setLine(int line) {
                        this.line = line;
                    }

                    public int getOrderNum() {
                        return orderNum;
                    }

                    public void setOrderNum(int orderNum) {
                        this.orderNum = orderNum;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getAddress() {
                        return address;
                    }

                    public void setAddress(String address) {
                        this.address = address;
                    }

                    public String getLon() {
                        return lon;
                    }

                    public void setLon(String lon) {
                        this.lon = lon;
                    }

                    public String getLat() {
                        return lat;
                    }

                    public void setLat(String lat) {
                        this.lat = lat;
                    }

                    public int getFlag() {
                        return flag;
                    }

                    public void setFlag(int flag) {
                        this.flag = flag;
                    }
                }
            }

            public static class TicketCountDTOListBean implements  Serializable {
                /**
                 * id : 11810
                 * version : 0
                 * gmtCreate : 1570780187513
                 * gmtModify : 1570780187513
                 * deleted : 0
                 * lineId : 10062
                 * lineName : 21路
                 * schedulId : 20117
                 * schedulTime : 16:00
                 * rideDate : 1570723200000
                 * total : 85
                 * saleCount : 0
                 * ratio : 0.0
                 * sign : null
                 * startRideDate : null
                 * endRideDate : null
                 */

                private int id;
                private int version;
                private long gmtCreate;
                private long gmtModify;
                private int deleted;
                private int lineId;
                private String lineName;
                private int schedulId;
                private String schedulTime;
                private long rideDate;
                private int total;
                private int saleCount;
                private double ratio;
                private boolean selected;


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

                public int getLineId() {
                    return lineId;
                }

                public void setLineId(int lineId) {
                    this.lineId = lineId;
                }

                public String getLineName() {
                    return lineName;
                }

                public void setLineName(String lineName) {
                    this.lineName = lineName;
                }

                public int getSchedulId() {
                    return schedulId;
                }

                public void setSchedulId(int schedulId) {
                    this.schedulId = schedulId;
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

                public int getTotal() {
                    return total;
                }

                public void setTotal(int total) {
                    this.total = total;
                }

                public int getSaleCount() {
                    return saleCount;
                }

                public void setSaleCount(int saleCount) {
                    this.saleCount = saleCount;
                }

                public double getRatio() {
                    return ratio;
                }

                public void setRatio(double ratio) {
                    this.ratio = ratio;
                }
                public int getStandbyCount(){
                    int standbyCount = getTotal() - getSaleCount();
                    return standbyCount;
                }
                public void setSelected(boolean selected) {
                    this.selected = selected;
                }
                public boolean isSelected() {
                    return selected;
                }


            }

        }

        public static class LinePlanDTOListBean  implements Serializable {
            /**
             * id : 1
             * version : 0
             * gmtCreate : 1570780154047
             * gmtModify : 1570780154047
             * deleted : 0
             * lineId : 10062
             * orderNum : 1
             * lng : 110.896693
             * lat : 34.528081
             */

            private int id;
            private int version;
            private long gmtCreate;
            private long gmtModify;
            private int deleted;
            private int lineId;
            private int orderNum;
            private String lng;
            private String lat;

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

            public int getLineId() {
                return lineId;
            }

            public void setLineId(int lineId) {
                this.lineId = lineId;
            }

            public int getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(int orderNum) {
                this.orderNum = orderNum;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }
        }
    }
}
