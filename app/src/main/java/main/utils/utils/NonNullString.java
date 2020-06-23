package main.utils.utils;

public  class NonNullString {

    public static String getString(Object obj){
        if (obj != null){
            return  obj.toString();
        }
        return "";
    }
}
