package main;


import java.util.Map;

import io.reactivex.Observable;
import main.customizedBus.home.bean.CustomizedBusIntroduceBean;
import main.customizedBus.home.bean.PublicResponseBean;
import main.customizedBus.initiateCustomization.bean.CustomizedDemandListBean;
import main.customizedBus.line.bean.CustomizedLineBean;
import main.customizedBus.line.bean.CustomizedLineDetailBean;
import main.customizedBus.ticket.bean.BuyTicketOrderBean;
import main.customizedBus.ticket.bean.TicketBean;
import main.customizedBus.ticket.bean.TicketDetailBean;
import main.login.bean.CheckPassWord;
import main.login.bean.Login;
import main.login.bean.Register;
import main.other.bean.BianMin;
import main.other.bean.ShengHuo;
import main.sheet.bean.AdvertDown;
import main.sheet.bean.AdvertTop;
import main.sheet.bean.Change;
import main.sheet.bean.Complaints;
import main.sheet.bean.ComplaintsList;
import main.sheet.bean.FanKui;
import main.sheet.bean.Notice;
import main.sheet.bean.Nxwd;
import main.sheet.bean.Swrllist;
import main.sheet.bean.UpVersion;
import main.smart.bus.bean.BusBean;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * @description:
 */

public interface AllApi {

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST(ApiAddress.register)
    Observable<Register> getLineStation(@Field("account") String account, @Field("password") String password);

    /**
     * 更新
     */
    @GET(ApiAddress.upversion)
    Observable<UpVersion> getVersion();

    /**
     * 登录
     */
    @GET(ApiAddress.login)
    Observable<Login> getLogin(@Query("appLoginName") String appLoginName, @Query("appPassword") String appPassword
            , @Query("type") String type);

    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST(ApiAddress.checkpassword)
    Observable<CheckPassWord> getCheckPassWord(@Field("account") String account, @Field("newPwd") String newPwd);

    /**
     * 获取广告顶部
     */
    @FormUrlEncoded
    @POST(ApiAddress.adver)
    Observable<AdvertTop> getAdvertTop(@Field("page") String page
            , @Field("limit") String limit, @Field("avdId") String avdId);

    /**
     * 获取广告底部
     */
    @FormUrlEncoded
    @POST(ApiAddress.adver)
    Observable<AdvertDown> getAdvertDown(@Field("page") String page
            , @Field("limit") String limit, @Field("avdId") String avdId);

    /**
     * 获取通知公告
     */
    @FormUrlEncoded
    @POST(ApiAddress.notice)
    Observable<Notice> getNotice(@Field("page") String page
            , @Field("limit") String limit, @Field("appLatestTimeStampStr") String messageType );


    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST(ApiAddress.changeword)
    Observable<Change> getChange(@Field("account") String account
            , @Field("password") String password, @Field("newPwd") String newPwd );

    /**
     * 提交投诉建议
     */
    @FormUrlEncoded
    @POST(ApiAddress.complaints)
    Observable<Complaints> getComplaints(@Field("telephone") String telephone, @Field("content") String content
                                        ,@Field("account") String account );

    /**
     * 提交投诉建议反馈
     */
    @FormUrlEncoded
    @POST(ApiAddress.complaintsfk)
    Observable<FanKui> getComplaintsFK(@Field("id") String id, @Field("feedback") String content );

    /**
     * 投诉建议列表
     */
    @FormUrlEncoded
    @POST(ApiAddress.complaintslist)
    Observable<ComplaintsList> getComplaintsList(@Field("processingStatus") String processingStatus
            ,@Field("account") String account, @Field("startComplaintDate") String startComplaintDate
            , @Field("endComplaintDate") String endComplaintDate, @Field("page") String page, @Field("limit") String limit);

    /**
     * 获取农信网点
     */
    @FormUrlEncoded
    @POST(ApiAddress.nxwd)
    Observable<Nxwd> getNxwd(@Field("page") String page, @Field("limit") String limit);

    /**
     * 获取农信网点
     */
    @FormUrlEncoded
    @POST(ApiAddress.smk)
    Observable<Nxwd> getSmk(@Field("page") String page, @Field("limit") String limit);

    /**
     * 获取失物认领列表
     */
    @FormUrlEncoded
    @POST(ApiAddress.swrllist)
    Observable<Swrllist> getSwrlList(@Field("state") String state );

    /**
     * 获取便民服务列表
     */
    @FormUrlEncoded
    @POST(ApiAddress.bianminfuwu)
    Observable<BianMin> getBianMin(@Field("serviceType") String page, @Field("Availability") String limit);

    /**
     * 获取生活服务列表
     */
    @FormUrlEncoded
    @POST(ApiAddress.shenghuo)
    Observable<ShengHuo> getShengHuo(@Field("serviceType") String page, @Field("Availability") String limit);

    /**
     * 发起公交定制
     */
    @FormUrlEncoded
    @POST(ApiAddress.customizedSave)
    Observable<PublicResponseBean> sendRequestBusCustomizedSave(@FieldMap Map<String, Object> param);
    /**
     *所有线路
     */
    @POST(ApiAddress.customizedLines)
    Observable<CustomizedLineBean> sendRequestGetLines(@QueryMap Map<String, Object> param);
    /**
     *出发地目的地线路
     */
    @POST(ApiAddress.SelectList)
    Observable<CustomizedLineBean> sendRequestGetLinesWithStartEndAddress(@QueryMap Map<String, Object> param);
    /**
     *定制客运需求
     */
    @POST(ApiAddress.customizedDemandlist)
    Observable<CustomizedDemandListBean> sendRequestGetcustomizedDemandList(@QueryMap Map<String, Object> param);
    /**
     * 线路详情查询接口
     */
    @GET(ApiAddress.LineDetial)
    Observable<CustomizedLineDetailBean> sendRequestGetcustomizedLineDetail(@Query("id") int id);
    //购票接口
    @FormUrlEncoded
    @POST(ApiAddress.customizedGenOrder)
    Observable<BuyTicketOrderBean> sendRequestGetBuyTicketOrderInfo(@FieldMap Map<String, Object> param);
    //取消购票
    @POST(ApiAddress.customizedCancelOrder)
    Observable<PublicResponseBean> sendRequestCancelOrder(@QueryMap Map<String, Object> param);
    //车票列表
    @POST(ApiAddress.customizedTicketList)
    Observable<TicketBean>  sendRequestGetTicketList(@QueryMap Map<String, Object> param);
    //车票详情
    @POST(ApiAddress.customizedTicketDetail)
    Observable<TicketDetailBean>  sendRequestGetTicketDetail(@QueryMap Map<String, Object> param);
    //验票
    @POST(ApiAddress.customizedTicketCheckUrl)
    Observable<PublicResponseBean> sendRequestCheckTicket(@QueryMap Map<String, Object> param);
    //退款申请
    @POST(ApiAddress.customizedRefundApplyUrl)
    Observable<PublicResponseBean> sendRequestTicketRefundApply(@QueryMap Map<String, Object> param);
    //乘车指南、购票须知等
    @POST(ApiAddress.customizedQueryDTOByMaprl)
    Observable<CustomizedBusIntroduceBean> sendRequestGetCustomizedBusIntroduce(@QueryMap Map<String, Object> param);
}
