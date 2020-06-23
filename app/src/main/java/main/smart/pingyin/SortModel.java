package main.smart.pingyin;

import java.io.Serializable;

/**
 * @author likaiyin
 */
public class SortModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;   //显示的数据
    private String sortLetters;  //显示数据拼音的首字母

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSortLetters() {
        return sortLetters;
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }
}
