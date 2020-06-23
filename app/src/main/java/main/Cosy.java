package main;

import java.util.List;

public class Cosy {

    /**
     * success : ³É¹¦
     * Data : [{"busCode":"18556","population":"0"},{"busCode":"5606","population":"3"},{"busCode":"5631","population":"3"}]
     */

    private String success;
    private List<DataBean> Data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * busCode : 18556
         * population : 0
         */

        private String busCode;
        private String population;

        public String getBusCode() {
            return busCode;
        }

        public void setBusCode(String busCode) {
            this.busCode = busCode;
        }

        public String getPopulation() {
            return population;
        }

        public void setPopulation(String population) {
            this.population = population;
        }
    }
}
