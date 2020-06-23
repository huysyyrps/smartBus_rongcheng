package main.smart.common.util;

import main.smart.common.SmartBusApp;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferencesHelper {
	/**
	 * 共享数据工具
	 * **/
	private static PreferencesHelper mManager;
	private SharedPreferences mPreferences;

	private PreferencesHelper(){
		mPreferences = PreferenceManager.getDefaultSharedPreferences(SmartBusApp.getInstance());
	}


	public static PreferencesHelper getInstance()
	{
		if (mManager == null)
			mManager = new PreferencesHelper();
		return mManager;
	}
	/**
	 * 返回手动选择的城市编码
	 * */
	public int getCity()
	{
		return   mPreferences.getInt("city", 0);
	}
	public void updateCity(int paramInteger)
	{
		SharedPreferences.Editor localEditor = this.mPreferences.edit();
		localEditor.putInt("city", paramInteger );
		localEditor.commit();
	}
	public Boolean isNotified()
	{
		return  this.mPreferences.getBoolean("notify", false);
	}
	public void updateNotified()
	{
		SharedPreferences.Editor localEditor = this.mPreferences.edit();
		localEditor.putBoolean("notify", true);
		localEditor.commit();
	}

	/**
	 * 查询验证key
	 */
	public int getCodeKey(){
		return mPreferences.getInt("passkey", 0);
	}
	/**
	 * 修改验证key
	 */
	public void updateCodeKey(){
		SharedPreferences.Editor localEditor = this.mPreferences.edit();
		localEditor.putInt("passkey", 1);
		localEditor.commit();
	}

	public String  getCityHelp(){
		return mPreferences.getString("cityHelp", ConstData.help);
	}

	/**
	 * 更新城市帮助信息
	 */
	public void updateCityHelp(String help){
		SharedPreferences.Editor localEditor = this.mPreferences.edit();
		localEditor.putString("cityHelp", help);
		localEditor.commit();
	}



	/**
	 * 查询站点版本
	 */
	public int getStationVersion(){
		return mPreferences.getInt("stationVer", 0);
	}

	/**
	 * 修改站点版本
	 */
	public void updateStationVer(int version){
		SharedPreferences.Editor localEditor = this.mPreferences.edit();
		localEditor.putInt("stationVer", version);
		localEditor.commit();
	}

	/**
	 * 弹出提示框
	 */

	public int getTitleVersion(){
		return mPreferences.getInt("TitleVersion", 0);
	}
	/**
	 * 修改提示框参数
	 */
	public void updateTitleVersion(){
		SharedPreferences.Editor localEditor = this.mPreferences.edit();
		localEditor.putInt("TitleVersion", 1);
		localEditor.commit();
	}
	/**
	 * 写入选择城市
	 * @param city
	 */
	public void setSelectCity(String city){
		SharedPreferences.Editor localEditor = this.mPreferences.edit();
		localEditor.putString("selectCity", city);
		localEditor.commit();
	}

	/**
	 * 获取选择城市
	 * @return
	 */
	public String getSelectCity(){
		return mPreferences.getString("selectCity", null);
	}

	/**
	 * 修改震动类型
	 * @param type
	 */
	public void updateShock(int type){
		SharedPreferences.Editor localEditor = this.mPreferences.edit();
		localEditor.putInt("shockType", type);
		localEditor.commit();
	}


	/**
	 * 查询震动类型
	 */
	public int getShock(){
		return mPreferences.getInt("shockType", 0);
	}

	/**
	 * 修改提醒类型
	 */
	public void updateReminder(int type){
		SharedPreferences.Editor localEditor = this.mPreferences.edit();
		localEditor.putInt("reminderType", type);
		localEditor.commit();
	}



	/**
	 * 查询提醒类型
	 * @return
	 */
	public int getReminder(){
		return mPreferences.getInt("reminderType",0);
	}

	/**
	 * 修改提醒类型
	 */
	public void updatebusrefreshset(int type){
		SharedPreferences.Editor localEditor = this.mPreferences.edit();
		localEditor.putInt("busrefreshset", type);
		localEditor.commit();
	}

	/**
	 * 查询数据刷新频率
	 * @return
	 */
	public int getBusrefresh(){
		return mPreferences.getInt("busrefreshset",0);
	}

	public void updateVideoType(int type) {
		// TODO Auto-generated method stub
		SharedPreferences.Editor localEditor = this.mPreferences.edit();
		localEditor.putInt("videoType", type);
		localEditor.commit();
	}

	public int getVideoType() {
		// TODO Auto-generated method stub
		return mPreferences.getInt("videoType",0);
	}

	/**
	 * 查询广告版本
	 */
	public int getADVersion(){
		return mPreferences.getInt("adVer", 0);
	}

	/**
	 * 修改广告版本
	 */
	public void updateADVersion(int version){
		SharedPreferences.Editor localEditor = this.mPreferences.edit();
		localEditor.putInt("adVer", version);
		localEditor.commit();
	}

	/**
	 * 查询通告最近日期
	 */
	public String getNoticeLastDate(){
		return mPreferences.getString("noticeDate", "");
	}

	/**
	 * 修改通告的最新日期
	 */
	public void updateNoticeLastDate(String date){
		SharedPreferences.Editor localEditor = this.mPreferences.edit();
		localEditor.putString("noticeDate", date);
		localEditor.commit();
	}
}
