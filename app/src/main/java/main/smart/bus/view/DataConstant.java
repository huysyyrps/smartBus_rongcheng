package main.smart.bus.view;

import main.smart.common.util.ConstData;
import main.smart.rcgj.R;


public class DataConstant {

	public static int[] ImgaeItems;
    public static String[] TextItems = new String[]{"线路查询","设置","通告","便民服务","声明","帮助"};
    
    public static void setImage(String type){
    	if (ConstData.bUpdateNotice) {
    		ImgaeItems = new int[]{R.drawable.ic_search_line, R.drawable.ic_search_site, R.drawable.ic_bus_map,
    	         R.drawable.ic_search, R.drawable.ic_flace_init, R.drawable.ic_other};
		} else {
            ImgaeItems = new int[]{R.drawable.ic_search_line, R.drawable.ic_search_site, R.drawable.ic_bus_map,
                    R.drawable.ic_search, R.drawable.ic_flace_init, R.drawable.ic_other};
		}
    }

    public static int[] getImages(int count) {
        int[] images = new int[count];
        for (int i=0;i<count;i++) {
            images[i] = ImgaeItems[i];
        }
        return images;
    }

    public static String[] getTexts(int count) {
        String[] texts = new String[count];
        for (int i=0;i<count;i++) {
            texts[i] = TextItems[i];
        }
        return texts;
    }
    
    

}
