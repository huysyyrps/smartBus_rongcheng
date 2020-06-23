package main;


public class ApiAddress {
//    public final static String qrcode = "https://rcbus.org.cn:50015/checkSigna";
//     public final static String qrcode = "https://www.weixin4bus.com:63139/checkSigna";
public final static String qrcode = "http://123.232.38.10:63139/checkSigna";

//    public final static String ICRecharge = "https://rcbus.org.cn:50013/";
//    public final static String ICRecharge = "https://www.weixin4bus.com:1123/";
//public final static String ICRecharge = "http://123.232.38.10:61123/";
public final static String ICRecharge = "http://s0.nsloop.com:28385/";
    public final static String mainApi = "https://rcbus.org.cn:50013/rcapp/";
    //public final static String mainApi = "http://192.168.2.168:8099";//测试地址
    public final static String mainApiread = "https://rcbus.org.cn:50013/ICRecharge/pay!";
//    public final static String mainApiread = "http://123.232.38.10:1123/ICRecharge/pay!";

    public final static String register = "mobile_phone/save";

    public final static String login = "app_login";
    //版本更新
    public final static String upversion = "system/appversions/list";

    public final static String checkpassword = "app_change_password";
    //获取广告
    public final static String adver = "push/advertisingContent/list";
    //获取通知公告
    public final static String notice = "push/notificationNotice/notificationGeneral";
   // public final static String notice = "push/notificationNotice/notificationGeneral";
    //修改密码
    public final static String changeword = "app_change_password";
    //提交投诉建议
    public final static String complaints = "push/complaintSuggestions/insert";
    //提交投诉建议反馈
    public final static String complaintsfk = "push/complaintSuggestions/editor";
    //提交投诉建议
    public final static String complaintslist = "push/complaintSuggestions/list";
    //获取农信网点
    public final static String nxwd = "push/agriculturalCreditNetwork/list";
    //获取市民卡
    public final static String smk = "push/citizenCardOutlet/list";
    //失物认领图片上传
    public final static String swrlimage = "upload/file_upload";
    //失物认领上传
    public final static String swrllist = "push/claim/list?page=1&limit=1000";
    //便民服务
    public final static String bianminfuwu = "push/serviceManagement/queryDTOByMap";
    //生活服务
    public final static String shenghuo = "push/life/app_list";

    //线路列表
    public final static String lineList = "sdhyschedule/PhoneQueryAction!getLineInfo.shtml";
    //站点列表
    public final static String stationList = "sdhyschedule/PhoneQueryAction!getZDXX.shtml";
    //查询线路站点
    public final static String linestation = "sdhyschedule/PhoneQueryAction!getLineStation.shtml";

    /***********************定制公交*******************************/

    public final static String customizedSave = "customized/customizedDemand/save";
    //普通分页查询线路列表
    public final static String customizedLines = "customized/line/mobilePageList";
    //根据始发站、终点站经纬度查询附近线路列表
    public final static String SelectList = "customized/line/selectList";
    //根据详情
    public final static String LineDetial = "customized/line/view/page/mobile";
    //乘客需求列表查询
    public final static String customizedDemandlist = "customized/customizedDemand/list";
    //购票接口
    public final static String customizedGenOrder = "payment/payRecord/genOrder";
    //取消购票接口
    public final static String customizedCancelOrder = "payment/payRecord/cancelOrder";
    //车票列表查询接口
    public final static String customizedTicketList = "customized/ticket//mobilePageList";
    //车票详情查询接口
    public final static String customizedTicketDetail = "customized/ticket/view/page/mobile";
    //购票支付宝回调
    public final static String alipayNoticeUrl = mainApi + "payment/alipay/notify";
    //购票微信回调
    public final static String wxpayNoticeUrl = mainApi + "payment/wxpay/payNotify";
    //验票
    public final static String customizedTicketCheckUrl = mainApi + "customized/ticket/check";
    //退款申请
    public final static String customizedRefundApplyUrl = mainApi + "customized/ticket/refundApply";
    //购票指南
    public final static String customizedQueryDTOByMaprl = mainApi + "push/notificationNotice/crux_data";


}
