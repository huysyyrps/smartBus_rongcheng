package main.customizedBus.line.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import main.utils.utils.PublicData;

public class CustomizedLineBean implements Serializable {
    /**
     * msg :
     * code : 0
     * success : false
     * extraData : null
     * noticeData : null
     * data : [{"id":56,"version":4,"gmtCreate":1569575014867,"gmtModify":1569578179633,"deleted":0,"name":"131·","schedul":"09:00,17:54","startStation":"�Ĺ���","endStation":"����","preferentialType":0,"price":5,"preferentialPrice":5,"middleTicketType":1,"preferentialMiddleType":0,"middlePrice":1.5,"preferentialMiddlePrice":1.5,"votes":90,"startDate":1569513600000,"endDate":1570636800000,"remark":"���Ե�����","status":1,"sartdis":null,"enddis":null,"distance":null,"startLon":null,"startLat":null,"endLon":null,"endLat":null,"startDateStr":null,"endDateStr":null,"stationListJson":null,"stationList":[],"schedulDTOListJson":null,"schedulDTOList":[],"schedulStationDTOList":[{"id":371,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10209,"time":"03:04","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":1,"name":"�Ĺ���","lon":"89","lat":"145","flag":0}},{"id":372,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10210,"time":"02:04","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":2,"name":"����","lon":"123","lat":"75","flag":1}},{"id":373,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10211,"time":"17:54","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":3,"name":"����ɽ","lon":"86","lat":"41","flag":1}},{"id":374,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10212,"time":"14:03","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":4,"name":"��ͷ��","lon":"75","lat":"91","flag":1}},{"id":375,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10213,"time":"08:09","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":5,"name":"Ȫ���´�","lon":"32","lat":"117","flag":1}},{"id":376,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10214,"time":"12:11","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":6,"name":"������ˮ�⾰��","lon":"86","lat":"11","flag":1}},{"id":377,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10215,"time":"13:15","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":7,"name":"����Ͽˮ��","lon":"863","lat":"15","flag":1}},{"id":378,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10216,"time":"15:09","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":8,"name":"�ƺӹ�԰","lon":"333","lat":"88","flag":1}},{"id":379,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10217,"time":"22:43","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":9,"name":"�ļ�ׯ","lon":"45","lat":"0312","flag":1}},{"id":380,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10218,"time":"22:31","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":10,"name":"����","lon":"68","lat":"12","flag":1}}]},{"id":52,"version":3,"gmtCreate":1569550655867,"gmtModify":1569550743273,"deleted":0,"name":"1·","schedul":"07:00,18:00","startStation":"�鱦������","endStation":"�鱦�й�˰��","preferentialType":0,"price":0.01,"preferentialPrice":0.01,"middleTicketType":0,"preferentialMiddleType":0,"middlePrice":0.01,"preferentialMiddlePrice":0.01,"votes":100,"startDate":1569600000000,"endDate":1569772800000,"remark":"��������","status":1,"sartdis":null,"enddis":null,"distance":null,"startLon":null,"startLat":null,"endLon":null,"endLat":null,"startDateStr":null,"endDateStr":null,"stationListJson":null,"stationList":[],"schedulDTOListJson":null,"schedulDTOList":[],"schedulStationDTOList":[{"id":362,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10095,"stationId":10186,"time":"07:00","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":52,"orderNum":1,"name":"�鱦������","lon":"110.893327","lat":"34.521627","flag":0}},{"id":363,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10095,"stationId":10187,"time":"08:00","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":52,"orderNum":2,"name":"�鱦�й�˰��","lon":"110.906191","lat":"34.522995","flag":1}}]},{"id":45,"version":7,"gmtCreate":1569410085033,"gmtModify":1569464607097,"deleted":0,"name":"2·","schedul":"07:00,18:00","startStation":"�鱦����վ","endStation":"�鱦����۾�","preferentialType":0,"price":10,"preferentialPrice":10,"middleTicketType":0,"preferentialMiddleType":0,"middlePrice":10,"preferentialMiddlePrice":10,"votes":50,"startDate":1569427200000,"endDate":1569772800000,"remark":"��������","status":1,"sartdis":null,"enddis":null,"distance":null,"startLon":null,"startLat":null,"endLon":null,"endLat":null,"startDateStr":null,"endDateStr":null,"stationListJson":null,"stationList":[],"schedulDTOListJson":null,"schedulDTOList":[],"schedulStationDTOList":[{"id":355,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10092,"stationId":10170,"time":"07:00","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":45,"orderNum":1,"name":"�鱦����վ","lon":"110.882943","lat":"34.523798","flag":0}},{"id":356,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10092,"stationId":10171,"time":"08:00","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":45,"orderNum":2,"name":"�鱦����۾�","lon":"110.908706","lat":"34.524244","flag":1}}]}]
     * count : 3
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
         * id : 56
         * version : 4
         * gmtCreate : 1569575014867
         * gmtModify : 1569578179633
         * deleted : 0
         * name : 131·
         * schedul : 09:00,17:54
         * startStation : �Ĺ���
         * endStation : ����
         * preferentialType : 0
         * price : 5.0
         * preferentialPrice : 5.0
         * middleTicketType : 1
         * preferentialMiddleType : 0
         * middlePrice : 1.5
         * preferentialMiddlePrice : 1.5
         * votes : 90
         * startDate : 1569513600000
         * endDate : 1570636800000
         * remark : ���Ե�����
         * status : 1
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
         * schedulStationDTOList : [{"id":371,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10209,"time":"03:04","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":1,"name":"�Ĺ���","lon":"89","lat":"145","flag":0}},{"id":372,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10210,"time":"02:04","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":2,"name":"����","lon":"123","lat":"75","flag":1}},{"id":373,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10211,"time":"17:54","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":3,"name":"����ɽ","lon":"86","lat":"41","flag":1}},{"id":374,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10212,"time":"14:03","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":4,"name":"��ͷ��","lon":"75","lat":"91","flag":1}},{"id":375,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10213,"time":"08:09","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":5,"name":"Ȫ���´�","lon":"32","lat":"117","flag":1}},{"id":376,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10214,"time":"12:11","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":6,"name":"������ˮ�⾰��","lon":"86","lat":"11","flag":1}},{"id":377,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10215,"time":"13:15","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":7,"name":"����Ͽˮ��","lon":"863","lat":"15","flag":1}},{"id":378,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10216,"time":"15:09","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":8,"name":"�ƺӹ�԰","lon":"333","lat":"88","flag":1}},{"id":379,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10217,"time":"22:43","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":9,"name":"�ļ�ׯ","lon":"45","lat":"0312","flag":1}},{"id":380,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10218,"time":"22:31","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":10,"name":"����","lon":"68","lat":"12","flag":1}}]
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
        private long startDate;//�ӿ����ݱ䶯Ϊstring
        private long endDate;//�ӿ����ݱ䶯Ϊstring
        private String remark;
        private int status;
        private Object sartdis;
        private Object enddis;
        private Object distance;
        private Object startLon;
        private Object startLat;
        private Object endLon;
        private Object endLat;
        private String startDateStr;
        private String endDateStr;
        private Object stationListJson;
        private Object schedulDTOListJson;
        private List<SchedulStationDTOListBean> schedulStationDTOList;

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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getSartdis() {
            return sartdis;
        }

        public void setSartdis(Object sartdis) {
            this.sartdis = sartdis;
        }

        public Object getEnddis() {
            return enddis;
        }

        public void setEnddis(Object enddis) {
            this.enddis = enddis;
        }

        public Object getDistance() {
            return distance;
        }

        public void setDistance(Object distance) {
            this.distance = distance;
        }

        public Object getStartLon() {
            return startLon;
        }

        public void setStartLon(Object startLon) {
            this.startLon = startLon;
        }

        public Object getStartLat() {
            return startLat;
        }

        public void setStartLat(Object startLat) {
            this.startLat = startLat;
        }

        public Object getEndLon() {
            return endLon;
        }

        public void setEndLon(Object endLon) {
            this.endLon = endLon;
        }

        public Object getEndLat() {
            return endLat;
        }

        public void setEndLat(Object endLat) {
            this.endLat = endLat;
        }

        public Object getStartDateStr() {
            return startDateStr;
        }

        public void setStartDateStr(String startDateStr) {
            this.startDateStr = startDateStr;
        }

        public Object getEndDateStr() {
            return endDateStr;
        }

        public void setEndDateStr(String endDateStr) {
            this.endDateStr = endDateStr;
        }

        public Object getStationListJson() {
            return stationListJson;
        }

        public void setStationListJson(Object stationListJson) {
            this.stationListJson = stationListJson;
        }

        public Object getSchedulDTOListJson() {
            return schedulDTOListJson;
        }

        public void setSchedulDTOListJson(Object schedulDTOListJson) {
            this.schedulDTOListJson = schedulDTOListJson;
        }


        public List<SchedulStationDTOListBean> getSchedulStationDTOList() {
            return schedulStationDTOList;
        }

        public void setSchedulStationDTOList(List<SchedulStationDTOListBean> schedulStationDTOList) {
            this.schedulStationDTOList = schedulStationDTOList;
        }

        public static class SchedulStationDTOListBean implements Serializable {
            /**
             * id : 371
             * version : null
             * gmtCreate : null
             * gmtModify : null
             * deleted : null
             * schedulId : 10099
             * stationId : 10209
             * time : 03:04
             * station : {"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":1,"name":"�Ĺ���","lon":"89","lat":"145","flag":0}
             */

            private int id;
            private Object version;
            private Object gmtCreate;
            private Object gmtModify;
            private Object deleted;
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

            public Object getVersion() {
                return version;
            }

            public void setVersion(Object version) {
                this.version = version;
            }

            public Object getGmtCreate() {
                return gmtCreate;
            }

            public void setGmtCreate(Object gmtCreate) {
                this.gmtCreate = gmtCreate;
            }

            public Object getGmtModify() {
                return gmtModify;
            }

            public void setGmtModify(Object gmtModify) {
                this.gmtModify = gmtModify;
            }

            public Object getDeleted() {
                return deleted;
            }

            public void setDeleted(Object deleted) {
                this.deleted = deleted;
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

            public static class StationBean {
                /**
                 * id : null
                 * version : null
                 * gmtCreate : null
                 * gmtModify : null
                 * deleted : null
                 * gmtDeleted : null
                 * line : 56
                 * orderNum : 1
                 * name : �Ĺ���
                 * lon : 89
                 * lat : 145
                 * flag : 0
                 */

                private Object id;
                private Object version;
                private Object gmtCreate;
                private Object gmtModify;
                private Object deleted;
                private Object gmtDeleted;
                private int line;
                private int orderNum;
                private String name;
                private String lon;
                private String lat;
                private int flag;

                public Object getId() {
                    return id;
                }

                public void setId(Object id) {
                    this.id = id;
                }

                public Object getVersion() {
                    return version;
                }

                public void setVersion(Object version) {
                    this.version = version;
                }

                public Object getGmtCreate() {
                    return gmtCreate;
                }

                public void setGmtCreate(Object gmtCreate) {
                    this.gmtCreate = gmtCreate;
                }

                public Object getGmtModify() {
                    return gmtModify;
                }

                public void setGmtModify(Object gmtModify) {
                    this.gmtModify = gmtModify;
                }

                public Object getDeleted() {
                    return deleted;
                }

                public void setDeleted(Object deleted) {
                    this.deleted = deleted;
                }

                public Object getGmtDeleted() {
                    return gmtDeleted;
                }

                public void setGmtDeleted(Object gmtDeleted) {
                    this.gmtDeleted = gmtDeleted;
                }

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
    }

//
//    /**
//     * msg :
//     * code : 0
//     * success : false
//     * extraData : null
//     * noticeData : null
//     * data : [{"id":56,"version":4,"gmtCreate":1569575014867,"gmtModify":1569578179633,"deleted":0,"name":"131·","schedul":"09:00,17:54","startStation":"�Ĺ���","endStation":"����","preferentialType":0,"price":5,"preferentialPrice":5,"middleTicketType":1,"preferentialMiddleType":0,"middlePrice":1.5,"preferentialMiddlePrice":1.5,"votes":90,"startDate":1569513600000,"endDate":1570636800000,"remark":"���Ե�����","status":1,"sartdis":null,"enddis":null,"distance":null,"startLon":null,"startLat":null,"endLon":null,"endLat":null,"startDateStr":null,"endDateStr":null,"stationListJson":null,"stationList":[],"schedulDTOListJson":null,"schedulDTOList":[],"schedulStationDTOList":[{"id":371,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10209,"time":"03:04","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":1,"name":"�Ĺ���","lon":"89","lat":"145","flag":0}},{"id":372,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10210,"time":"02:04","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":2,"name":"����","lon":"123","lat":"75","flag":1}},{"id":373,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10211,"time":"17:54","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":3,"name":"����ɽ","lon":"86","lat":"41","flag":1}},{"id":374,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10212,"time":"14:03","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":4,"name":"��ͷ��","lon":"75","lat":"91","flag":1}},{"id":375,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10213,"time":"08:09","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":5,"name":"Ȫ���´�","lon":"32","lat":"117","flag":1}},{"id":376,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10214,"time":"12:11","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":6,"name":"������ˮ�⾰��","lon":"86","lat":"11","flag":1}},{"id":377,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10215,"time":"13:15","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":7,"name":"����Ͽˮ��","lon":"863","lat":"15","flag":1}},{"id":378,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10216,"time":"15:09","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":8,"name":"�ƺӹ�԰","lon":"333","lat":"88","flag":1}},{"id":379,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10217,"time":"22:43","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":9,"name":"�ļ�ׯ","lon":"45","lat":"0312","flag":1}},{"id":380,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10218,"time":"22:31","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":10,"name":"����","lon":"68","lat":"12","flag":1}}]},{"id":52,"version":3,"gmtCreate":1569550655867,"gmtModify":1569550743273,"deleted":0,"name":"1·","schedul":"07:00,18:00","startStation":"�鱦������","endStation":"�鱦�й�˰��","preferentialType":0,"price":0.01,"preferentialPrice":0.01,"middleTicketType":0,"preferentialMiddleType":0,"middlePrice":0.01,"preferentialMiddlePrice":0.01,"votes":100,"startDate":1569600000000,"endDate":1569772800000,"remark":"��������","status":1,"sartdis":null,"enddis":null,"distance":null,"startLon":null,"startLat":null,"endLon":null,"endLat":null,"startDateStr":null,"endDateStr":null,"stationListJson":null,"stationList":[],"schedulDTOListJson":null,"schedulDTOList":[],"schedulStationDTOList":[{"id":362,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10095,"stationId":10186,"time":"07:00","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":52,"orderNum":1,"name":"�鱦������","lon":"110.893327","lat":"34.521627","flag":0}},{"id":363,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10095,"stationId":10187,"time":"08:00","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":52,"orderNum":2,"name":"�鱦�й�˰��","lon":"110.906191","lat":"34.522995","flag":1}}]},{"id":45,"version":7,"gmtCreate":1569410085033,"gmtModify":1569464607097,"deleted":0,"name":"2·","schedul":"07:00,18:00","startStation":"�鱦����վ","endStation":"�鱦����۾�","preferentialType":0,"price":10,"preferentialPrice":10,"middleTicketType":0,"preferentialMiddleType":0,"middlePrice":10,"preferentialMiddlePrice":10,"votes":50,"startDate":1569427200000,"endDate":1569772800000,"remark":"��������","status":1,"sartdis":null,"enddis":null,"distance":null,"startLon":null,"startLat":null,"endLon":null,"endLat":null,"startDateStr":null,"endDateStr":null,"stationListJson":null,"stationList":[],"schedulDTOListJson":null,"schedulDTOList":[],"schedulStationDTOList":[{"id":355,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10092,"stationId":10170,"time":"07:00","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":45,"orderNum":1,"name":"�鱦����վ","lon":"110.882943","lat":"34.523798","flag":0}},{"id":356,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10092,"stationId":10171,"time":"08:00","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":45,"orderNum":2,"name":"�鱦����۾�","lon":"110.908706","lat":"34.524244","flag":1}}]}]
//     * count : 3
//     * nextUrl : null
//     */
//
//    private String msg;
//    private int code;
//    private boolean success;
//    private Object extraData;
//    private Object noticeData;
//    private int count;
//    private Object nextUrl;
//    private List<DataBean> data;
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public boolean isSuccess() {
//        return success;
//    }
//
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }
//
//    public Object getExtraData() {
//        return extraData;
//    }
//
//    public void setExtraData(Object extraData) {
//        this.extraData = extraData;
//    }
//
//    public Object getNoticeData() {
//        return noticeData;
//    }
//
//    public void setNoticeData(Object noticeData) {
//        this.noticeData = noticeData;
//    }
//
//    public int getCount() {
//        return count;
//    }
//
//    public void setCount(int count) {
//        this.count = count;
//    }
//
//    public Object getNextUrl() {
//        return nextUrl;
//    }
//
//    public void setNextUrl(Object nextUrl) {
//        this.nextUrl = nextUrl;
//    }
//
//    public List<DataBean> getData() {
//        return data;
//    }
//
//    public void setData(List<DataBean> data) {
//        this.data = data;
//    }
//
//    public static class DataBean {
//        /**
//         * id : 56
//         * version : 4
//         * gmtCreate : 1569575014867
//         * gmtModify : 1569578179633
//         * deleted : 0
//         * name : 131·
//         * schedul : 09:00,17:54
//         * startStation : �Ĺ���
//         * endStation : ����
//         * preferentialType : 0
//         * price : 5.0
//         * preferentialPrice : 5.0
//         * middleTicketType : 1
//         * preferentialMiddleType : 0
//         * middlePrice : 1.5
//         * preferentialMiddlePrice : 1.5
//         * votes : 90
//         * startDate : 1569513600000
//         * endDate : 1570636800000
//         * remark : ���Ե�����
//         * status : 1
//         * sartdis : null
//         * enddis : null
//         * distance : null
//         * startLon : null
//         * startLat : null
//         * endLon : null
//         * endLat : null
//         * startDateStr : null
//         * endDateStr : null
//         * stationListJson : null
//         * stationList : []
//         * schedulDTOListJson : null
//         * schedulDTOList : []
//         * schedulStationDTOList : [{"id":371,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10209,"time":"03:04","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":1,"name":"�Ĺ���","lon":"89","lat":"145","flag":0}},{"id":372,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10210,"time":"02:04","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":2,"name":"����","lon":"123","lat":"75","flag":1}},{"id":373,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10211,"time":"17:54","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":3,"name":"����ɽ","lon":"86","lat":"41","flag":1}},{"id":374,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10212,"time":"14:03","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":4,"name":"��ͷ��","lon":"75","lat":"91","flag":1}},{"id":375,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10213,"time":"08:09","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":5,"name":"Ȫ���´�","lon":"32","lat":"117","flag":1}},{"id":376,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10214,"time":"12:11","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":6,"name":"������ˮ�⾰��","lon":"86","lat":"11","flag":1}},{"id":377,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10215,"time":"13:15","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":7,"name":"����Ͽˮ��","lon":"863","lat":"15","flag":1}},{"id":378,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10216,"time":"15:09","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":8,"name":"�ƺӹ�԰","lon":"333","lat":"88","flag":1}},{"id":379,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10217,"time":"22:43","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":9,"name":"�ļ�ׯ","lon":"45","lat":"0312","flag":1}},{"id":380,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"schedulId":10099,"stationId":10218,"time":"22:31","station":{"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":10,"name":"����","lon":"68","lat":"12","flag":1}}]
//         */
//
//        private int id;
//        private int version;
//        private long gmtCreate;
//        private long gmtModify;
//        private int deleted;
//        private String name;
//        private String schedul;
//        private String startStation;
//        private String endStation;
//        private int preferentialType;
//        private double price;
//        private double preferentialPrice;
//        private int middleTicketType;
//        private int preferentialMiddleType;
//        private double middlePrice;
//        private double preferentialMiddlePrice;
//        private int votes;
//        private long startDate;
//        private long endDate;
//        private String remark;
//        private int status;
//        private Object sartdis;
//        private Object enddis;
//        private Object distance;
//        private Object startLon;
//        private Object startLat;
//        private Object endLon;
//        private Object endLat;
//        private Object startDateStr;
//        private Object endDateStr;
//        private Object stationListJson;
//        private Object schedulDTOListJson;
//        private List<?> stationList;
//        private List<?> schedulDTOList;
//        private List<SchedulStationDTOListBean> schedulStationDTOList;
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public int getVersion() {
//            return version;
//        }
//
//        public void setVersion(int version) {
//            this.version = version;
//        }
//        public String getShowBuyBtnInfo(){
//
//            return  String.valueOf(price) ;
//        }
//        public long getGmtCreate() {
//            return gmtCreate;
//        }
//
//        public void setGmtCreate(long gmtCreate) {
//            this.gmtCreate = gmtCreate;
//        }
//
//        public long getGmtModify() {
//            return gmtModify;
//        }
//
//        public void setGmtModify(long gmtModify) {
//            this.gmtModify = gmtModify;
//        }
//
//        public int getDeleted() {
//            return deleted;
//        }
//
//        public void setDeleted(int deleted) {
//            this.deleted = deleted;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public String getSchedul() {
//            return schedul;
//        }
//
//        public void setSchedul(String schedul) {
//            this.schedul = schedul;
//        }
//
//        public String getStartStation() {
//            return startStation;
//        }
//
//        public void setStartStation(String startStation) {
//            this.startStation = startStation;
//        }
//
//        public String getEndStation() {
//            return endStation;
//        }
//
//        public void setEndStation(String endStation) {
//            this.endStation = endStation;
//        }
//
//        public int getPreferentialType() {
//            return preferentialType;
//        }
//
//        public void setPreferentialType(int preferentialType) {
//            this.preferentialType = preferentialType;
//        }
//
//        public double getPrice() {
//            return price;
//        }
//
//        public void setPrice(double price) {
//            this.price = price;
//        }
//
//        public double getPreferentialPrice() {
//            return preferentialPrice;
//        }
//
//        public void setPreferentialPrice(double preferentialPrice) {
//            this.preferentialPrice = preferentialPrice;
//        }
//
//        public int getMiddleTicketType() {
//            return middleTicketType;
//        }
//
//        public void setMiddleTicketType(int middleTicketType) {
//            this.middleTicketType = middleTicketType;
//        }
//
//        public int getPreferentialMiddleType() {
//            return preferentialMiddleType;
//        }
//
//        public void setPreferentialMiddleType(int preferentialMiddleType) {
//            this.preferentialMiddleType = preferentialMiddleType;
//        }
//
//        public double getMiddlePrice() {
//            return middlePrice;
//        }
//
//        public void setMiddlePrice(double middlePrice) {
//            this.middlePrice = middlePrice;
//        }
//
//        public double getPreferentialMiddlePrice() {
//            return preferentialMiddlePrice;
//        }
//
//        public void setPreferentialMiddlePrice(double preferentialMiddlePrice) {
//            this.preferentialMiddlePrice = preferentialMiddlePrice;
//        }
//
//        public int getVotes() {
//            return votes;
//        }
//
//        public void setVotes(int votes) {
//            this.votes = votes;
//        }
//
//        public long getStartDate() {
//            return startDate;
//        }
//
//        public void setStartDate(long startDate) {
//            this.startDate = startDate;
//        }
//
//        public long getEndDate() {
//            return endDate;
//        }
//
//        public void setEndDate(long endDate) {
//            this.endDate = endDate;
//        }
//
//        public String getRemark() {
//            return remark;
//        }
//
//        public void setRemark(String remark) {
//            this.remark = remark;
//        }
//
//        public int getStatus() {
//            return status;
//        }
//
//        public void setStatus(int status) {
//            this.status = status;
//        }
//
//        public Object getSartdis() {
//            return sartdis;
//        }
//
//        public void setSartdis(Object sartdis) {
//            this.sartdis = sartdis;
//        }
//
//        public Object getEnddis() {
//            return enddis;
//        }
//
//        public void setEnddis(Object enddis) {
//            this.enddis = enddis;
//        }
//
//        public Object getDistance() {
//            return distance;
//        }
//
//        public void setDistance(Object distance) {
//            this.distance = distance;
//        }
//
//        public Object getStartLon() {
//            return startLon;
//        }
//
//        public void setStartLon(Object startLon) {
//            this.startLon = startLon;
//        }
//
//        public Object getStartLat() {
//            return startLat;
//        }
//
//        public void setStartLat(Object startLat) {
//            this.startLat = startLat;
//        }
//
//        public Object getEndLon() {
//            return endLon;
//        }
//
//        public void setEndLon(Object endLon) {
//            this.endLon = endLon;
//        }
//
//        public Object getEndLat() {
//            return endLat;
//        }
//
//        public void setEndLat(Object endLat) {
//            this.endLat = endLat;
//        }
//
//        public Object getStartDateStr() {
//            return startDateStr;
//        }
//
//        public void setStartDateStr(Object startDateStr) {
//            this.startDateStr = startDateStr;
//        }
//
//        public Object getEndDateStr() {
//            return endDateStr;
//        }
//
//        public void setEndDateStr(Object endDateStr) {
//            this.endDateStr = endDateStr;
//        }
//
//        public Object getStationListJson() {
//            return stationListJson;
//        }
//
//        public void setStationListJson(Object stationListJson) {
//            this.stationListJson = stationListJson;
//        }
//
//        public Object getSchedulDTOListJson() {
//            return schedulDTOListJson;
//        }
//
//        public void setSchedulDTOListJson(Object schedulDTOListJson) {
//            this.schedulDTOListJson = schedulDTOListJson;
//        }
//
//        public List<?> getStationList() {
//            return stationList;
//        }
//
//        public void setStationList(List<?> stationList) {
//            this.stationList = stationList;
//        }
//
//        public List<?> getSchedulDTOList() {
//            return schedulDTOList;
//        }
//
//        public void setSchedulDTOList(List<?> schedulDTOList) {
//            this.schedulDTOList = schedulDTOList;
//        }
//
//        public List<SchedulStationDTOListBean> getSchedulStationDTOList() {
//            return schedulStationDTOList;
//        }
//
//        public void setSchedulStationDTOList(List<SchedulStationDTOListBean> schedulStationDTOList) {
//            this.schedulStationDTOList = schedulStationDTOList;
//        }
//
//        public static class SchedulStationDTOListBean {
//            /**
//             * id : 371
//             * version : null
//             * gmtCreate : null
//             * gmtModify : null
//             * deleted : null
//             * schedulId : 10099
//             * stationId : 10209
//             * time : 03:04
//             * station : {"id":null,"version":null,"gmtCreate":null,"gmtModify":null,"deleted":null,"gmtDeleted":null,"line":56,"orderNum":1,"name":"�Ĺ���","lon":"89","lat":"145","flag":0}
//             */
//
//            private int id;
//            private Object version;
//            private Object gmtCreate;
//            private Object gmtModify;
//            private Object deleted;
//            private int schedulId;
//            private int stationId;
//            private String time;
//            private StationBean station;
//
//            public int getId() {
//                return id;
//            }
//
//            public void setId(int id) {
//                this.id = id;
//            }
//
//            public Object getVersion() {
//                return version;
//            }
//
//            public void setVersion(Object version) {
//                this.version = version;
//            }
//
//            public Object getGmtCreate() {
//                return gmtCreate;
//            }
//
//            public void setGmtCreate(Object gmtCreate) {
//                this.gmtCreate = gmtCreate;
//            }
//
//            public Object getGmtModify() {
//                return gmtModify;
//            }
//
//            public void setGmtModify(Object gmtModify) {
//                this.gmtModify = gmtModify;
//            }
//
//            public Object getDeleted() {
//                return deleted;
//            }
//
//            public void setDeleted(Object deleted) {
//                this.deleted = deleted;
//            }
//
//            public int getSchedulId() {
//                return schedulId;
//            }
//
//            public void setSchedulId(int schedulId) {
//                this.schedulId = schedulId;
//            }
//
//            public int getStationId() {
//                return stationId;
//            }
//
//            public void setStationId(int stationId) {
//                this.stationId = stationId;
//            }
//
//            public String getTime() {
//                return time;
//            }
//
//            public void setTime(String time) {
//                this.time = time;
//            }
//
//            public StationBean getStation() {
//                return station;
//            }
//
//            public void setStation(StationBean station) {
//                this.station = station;
//            }
//
//            public static class StationBean {
//                /**
//                 * id : null
//                 * version : null
//                 * gmtCreate : null
//                 * gmtModify : null
//                 * deleted : null
//                 * gmtDeleted : null
//                 * line : 56
//                 * orderNum : 1
//                 * name : �Ĺ���
//                 * lon : 89
//                 * lat : 145
//                 * flag : 0
//                 */
//
//                private Object id;
//                private Object version;
//                private Object gmtCreate;
//                private Object gmtModify;
//                private Object deleted;
//                private Object gmtDeleted;
//                private int line;
//                private int orderNum;
//                private String name;
//                private String lon;
//                private String lat;
//                private int flag;
//
//                public Object getId() {
//                    return id;
//                }
//
//                public void setId(Object id) {
//                    this.id = id;
//                }
//
//                public Object getVersion() {
//                    return version;
//                }
//
//                public void setVersion(Object version) {
//                    this.version = version;
//                }
//
//                public Object getGmtCreate() {
//                    return gmtCreate;
//                }
//
//                public void setGmtCreate(Object gmtCreate) {
//                    this.gmtCreate = gmtCreate;
//                }
//
//                public Object getGmtModify() {
//                    return gmtModify;
//                }
//
//                public void setGmtModify(Object gmtModify) {
//                    this.gmtModify = gmtModify;
//                }
//
//                public Object getDeleted() {
//                    return deleted;
//                }
//
//                public void setDeleted(Object deleted) {
//                    this.deleted = deleted;
//                }
//
//                public Object getGmtDeleted() {
//                    return gmtDeleted;
//                }
//
//                public void setGmtDeleted(Object gmtDeleted) {
//                    this.gmtDeleted = gmtDeleted;
//                }
//
//                public int getLine() {
//                    return line;
//                }
//
//                public void setLine(int line) {
//                    this.line = line;
//                }
//
//                public int getOrderNum() {
//                    return orderNum;
//                }
//
//                public void setOrderNum(int orderNum) {
//                    this.orderNum = orderNum;
//                }
//
//                public String getName() {
//                    return name;
//                }
//
//                public void setName(String name) {
//                    this.name = name;
//                }
//
//                public String getLon() {
//                    return lon;
//                }
//
//                public void setLon(String lon) {
//                    this.lon = lon;
//                }
//
//                public String getLat() {
//                    return lat;
//                }
//
//                public void setLat(String lat) {
//                    this.lat = lat;
//                }
//
//                public int getFlag() {
//                    return flag;
//                }
//
//                public void setFlag(int flag) {
//                    this.flag = flag;
//                }
//            }
//        }
//    }


}
