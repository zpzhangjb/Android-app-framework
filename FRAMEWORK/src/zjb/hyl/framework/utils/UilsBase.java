package zjb.hyl.framework.utils;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.Toast;

public class UilsBase {

	/**
	 * 显示消息
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            消息
	 */
	public static void showMsg(Context context, String msg) {
		if(context != null){
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 显示消息
	 * 
	 * @param context
	 *            上下文
	 * @param res
	 *            消息资源号
	 */
	public static void showMsg(Context context, int res) {
		if(context != null){
			Toast.makeText(context, context.getString(res), Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 显示消息
	 * 
	 * @param context
	 *            上下文
	 * @param res
	 *            消息资源号
	 * @param msg
	 *            附加消息
	 */
	public static void showMsg(Context context, int res, String msg) {
		if(context != null){
			Toast.makeText(context, context.getString(res) + msg, Toast.LENGTH_LONG).show();
		}
	}
	public static void showMsgL(Context context, String msg) {
		if(context != null){
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}
	}

	private static long lastPauseTime;

	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		if (time - lastPauseTime < ConstantsBase.CLICK_ENABLE_TIME) {
			return true;
		}
		lastPauseTime = time;
		return false;
	}

	/**
	 * 通用布局文件
	 * 
	 * @param context
	 * @param width
	 *            ：宽
	 * @param heigth
	 *            ：高
	 * @param direction
	 *            ：方向
	 * @return
	 */
	public static LinearLayout createLinearLayout(Context context, int width, int heigth, int direction) {
		LinearLayout llayout = new LinearLayout(context);
		llayout.setLayoutParams(new LinearLayout.LayoutParams(width, heigth));
		llayout.setOrientation(direction);
		return llayout;
	}

}
