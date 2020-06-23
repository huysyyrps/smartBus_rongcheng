package main.utils.baidu;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;

import main.smart.rcgj.R;

public class POISearchMananger implements
        OnGetPoiSearchResultListener, OnGetSuggestionResultListener {

    private final Context context;
    private PoiSearch mPoiSearch = null;
    private SuggestionSearch mSuggestionSearch = null;
    private String searchCity;//POI�ĳ���
    private OnGetPoiSearchResultListener onGetPoiSearchResultListener;
    private int limitInPage = 20;//ҳ����ʾ����
    public interface OnGetPoiSearchResultListener{
        public void onGetPoiResult(PoiResult poiResult);
    }

    public int getLimitInPage() {
        return limitInPage;
    }

    public POISearchMananger(Context context) {
        this.context = context;
        searchCity = context.getResources().getString(R.string.app_city);
       initObject();
    }

    public void setOnGetPoiSearchResultListener(OnGetPoiSearchResultListener onGetPoiSearchResultListener) {
        this.onGetPoiSearchResultListener = onGetPoiSearchResultListener;
    }

    void initObject(){
        // ��ʼ������ģ�飬ע�������¼�����
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        // ��ʼ����������ģ�飬ע�Ὠ�������¼�����
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
    }
    //����������
    public  void  searchInCity(String keyword,int pageNum){
       searchInCity(searchCity,keyword,pageNum);
    }
    //����������
    public  void  searchInCity(String city,String keyword,int pageNum){
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(city)
                .keyword(keyword)
                .pageNum(pageNum)
                .pageCapacity(limitInPage)
                .scope(1));
    }

    /**
     * ��ȡPOI�������������searchInCity��searchNearby��searchInBound���ص��������
     *
     * @param poiResult    Poi����������������м������ܱ߼������������
     */
    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (onGetPoiSearchResultListener != null){
            onGetPoiSearchResultListener.onGetPoiResult(poiResult);
        }
        if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(this.context, "δ�ҵ����", Toast.LENGTH_LONG).show();
            return;
        }

        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {

            Log.i("ssssss", "onGetPoiResult: ");
        }

        if (poiResult.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
            // ������ؼ����ڱ���û���ҵ����������������ҵ�ʱ�����ذ����ùؼ�����Ϣ�ĳ����б�
            String strInfo = "��";

            for (CityInfo cityInfo : poiResult.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }

            strInfo += "�ҵ����";
            Toast.makeText(this.context, strInfo, Toast.LENGTH_LONG).show();
        }
    }
    /**
     * ��ȡPOI��������������õ�searchPoiDetail���ص��������
     * V5.2.0�汾֮�󣬻�����������ʹ��{@link #onGetPoiDetailResult(PoiDetailSearchResult)}����
     * @param poiDetailResult   POI����������
     */
    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }
    /**
     * ��ȡ���߽�������������õ�requestSuggestion���ص��������
     *
     * @param suggestionResult    Sug�������
     */
    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {

    }
}
