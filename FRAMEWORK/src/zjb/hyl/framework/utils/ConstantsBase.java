package zjb.hyl.framework.utils;

import java.io.File;

import android.os.Environment;

/**
 * 属性定义
 * 
 * @author heiyl
 * 
 */
public class ConstantsBase {

	/** LOG打印 **/
	public static final int LOG_LEVEL = 5;
	public static final int LOG_VERBOS_FLG = 5;
	public static final int LOG_DEBUG_FLG = 4;
	public static final int LOG_INFO_FLG = 3;
	public static final int LOG_WARN_FLG = 2;
	public static final int LOG_ERROR_FLG = 1;

	public static String PROGRASS_TITLE = "请稍等";
	public static String PROGRASS_CONTANT = "获取数据中...";

	// 请求获取动态条目的数量
	public static int count = 20;

	/** 单击间隔时间 **/
	public static final int CLICK_ENABLE_TIME = 2000;

	public static String photoTakePath = Environment.getExternalStorageDirectory() + File.separator + "dcim"
			+ File.separator + "Camera";

	public static String DATAPATH = File.separator + "data" + File.separator + "data" + File.separator
			+ "com.chanjet.ma.xty.esns.Activities" + File.separator + "files";

	public static String getDynamicallyPicPath() {
		return DATAPATH + File.separator + "SPACE" + File.separator + File.separator + "DEVSNS_PIC";
	}

	public static int CROP_WB_IMG_X = 320;
	public static int CROP_WB_IMG_Y = 320;
	
	public static int freshtime = 60000;

}
