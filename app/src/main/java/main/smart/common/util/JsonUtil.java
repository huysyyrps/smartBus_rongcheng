package main.smart.common.util;


import java.io.Reader;
import java.lang.reflect.Type;

import com.google.gson.Gson;

public class JsonUtil
{
	//google 
  private static final Gson mGson = new Gson();

  public static <T> T fromJson(Reader paramReader, Type paramType)
  {
    return mGson.fromJson(paramReader, paramType);
  }

  public static <T> T fromJson(String paramString, Type paramType)
  {
    if (String.class == paramType){
    	 return (T) paramString;
    }else{
    	 return mGson.fromJson(paramString, paramType);
    }
     
   
  }

  public static String toJson(Object paramObject)
  {
    return mGson.toJson(paramObject);
  }
}