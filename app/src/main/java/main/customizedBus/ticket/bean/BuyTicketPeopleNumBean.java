package main.customizedBus.ticket.bean;


import android.content.Context;

import com.contrarywind.interfaces.IPickerViewData;

/**
 * Created by Sai on 15/11/22.
 */
public class BuyTicketPeopleNumBean implements IPickerViewData {

    private int num;
    private String showStr;


    public BuyTicketPeopleNumBean (int num,String showStr){
      this.num = num;
      this.showStr = showStr;
    }

    public int getNum() {
        return num;
    }

    public String getShowStr() {
        return showStr;
    }

    //这个用来显示在PickerView上面的字符串,PickerView会通过getPickerViewText方法获取字符串显示出来。
    @Override
    public String getPickerViewText() {
        return  showStr;
    }
}
