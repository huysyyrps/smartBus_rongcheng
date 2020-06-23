package main.smart.advert;
public class AdvertUrls
{
  private static final String ADVERT_BASE_URL = "/advert/rest/ad/";
  private static final String ADVERT_PIC_URL = "pictures/";
  private static final String DEBUG_SERVER_IP = "localhost:8070";
  private static final String HTTP_PREFIX = "http://";
  public static String QUERY_ADVERT_LEVELS = "levels/%d";
  public static String QUERY_ALL_ADVERTS = "advertisements/%d/%d";
  public static String UPLOAD_ADVERT_BROWSE_STATS;
  public static String UPLOAD_ADVERT_CLICK_STATS;
  public static String UPLOAD_DEVICE_INFO = "deviceData";

  static
  {
    UPLOAD_ADVERT_BROWSE_STATS = "browsingDurations";
    UPLOAD_ADVERT_CLICK_STATS = "clickNumbers";
  }
/**
 * »ñÈ¡ÍøÂçÍ¼Æ¬URL
 * */
  public static String getAdvertPicUrl(String paramString)
  {
    return getAdvertRestUrl( "pictures/" + paramString);
  }

  public static String getAdvertRestUrl( String paramString)
  {
      return "http://localhost:8070/advert/rest/ad/" + paramString;
 
   
  } 
}
