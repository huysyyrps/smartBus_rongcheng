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
	private String name;   //��ʾ������
    private String sortLetters;  //��ʾ����ƴ��������ĸ

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
