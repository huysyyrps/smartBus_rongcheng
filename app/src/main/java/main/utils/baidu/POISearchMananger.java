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
    private String searchCity;//POI的城市
    private OnGetPoiSearchResultListener onGetPoiSearchResultListener;
    private int limitInPage = 20;//页面显示条数
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
        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        // 初始化建议搜索模块，注册建议搜索事件监听
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
    }
    //城市内搜索
    public  void  searchInCity(String keyword,int pageNum){
       searchInCity(searchCity,keyword,pageNum);
    }
    //城市内搜索
    public  void  searchInCity(String city,String keyword,int pageNum){
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(city)
                .keyword(keyword)
                .pageNum(pageNum)
                .pageCapacity(limitInPage)
                .scope(1));
    }

    /**
     * 获取POI搜索结果，包括searchInCity，searchNearby，searchInBound返回的搜索结果
     *
     * @param poiResult    Poi检索结果，包括城市检索，周边检索，区域检索
     */
    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (onGetPoiSearchResultListener != null){
            onGetPoiSearchResultListener.onGetPoiResult(poiResult);
        }
        if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(this.context, "未找到结果", Toast.LENGTH_LONG).show();
            return;
        }

        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {

            Log.i("ssssss", "onGetPoiResult: ");
        }

        if (poiResult.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";

            for (CityInfo cityInfo : poiResult.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }

            strInfo += "找到结果";
            Toast.makeText(this.context, strInfo, Toast.LENGTH_LONG).show();
        }
    }
    /**
     * 获取POI详情搜索结果，得到searchPoiDetail返回的搜索结果
     * V5.2.0版本之后，还方法废弃，使用{@link #onGetPoiDetailResult(PoiDetailSearchResult)}代替
     * @param poiDetailResult   POI详情检索结果
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
     * 获取在线建议搜索结果，得到requestSuggestion返回的搜索结果
     *
     * @param suggestionResult    Sug检索结果
     */
    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {

    }
}
