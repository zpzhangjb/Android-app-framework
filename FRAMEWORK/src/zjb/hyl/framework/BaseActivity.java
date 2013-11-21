package zjb.hyl.framework;

import java.util.List;

import zjb.hyl.framework.log.Logger;
import zjb.hyl.framework.net.MArrayList;
import zjb.hyl.framework.net.Response;
import zjb.hyl.framework.utils.UilsBase;
import zjb.hyl.framework.widget.BaseLayout;
import zjb.hyl.framework.widget.TitleBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 基类
 * 
 * @author heiyl
 * 
 */
@SuppressWarnings("deprecation")
public abstract class BaseActivity extends Activity implements View.OnClickListener {

	protected BaseLayout ly;
	protected RelativeLayout titleBar;
	protected ImageView mMenu;
	protected TextView midText;
	protected BaseApplication application;
	Logger logger = Logger.getLogger(BaseActivity.class);
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	
	public interface TvInfoClickListener{
		void onTvInfoClickListener(TextView tv);
	}

	protected void setView(int layoutResID) {
		ly = new BaseLayout(this, layoutResID);
		setContentView(ly);
		mMenu = ly.leftButton;
		ly.leftButton.setOnClickListener(this);
		ly.rightButton.setOnClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (BaseApplication) getApplication();
		application.addActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		logger.info("onResume........... " + this.getClass().getSimpleName());
	}

	protected void onPause() {
		super.onPause();
	}

	/**
	 * OnCreate时 构建Activity的布局
	 */
	protected void setupView() {
	}

	protected void put(int id, MArrayList params,boolean show) {
		if(show) {
			try {
				showDialog(DIALOG_PROGRESS_LOADING);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		application.putTask(new Task(id, this, params));
	}

	public static final int DIALOG_PROGRESS_LOADING = 1000;

	@Override
	protected Dialog onCreateDialog(int id) {
		if (id == DIALOG_PROGRESS_LOADING) {
//			return Utils.createProgressDialog(this, R.string.loading, true);
		}
		return super.onCreateDialog(id);
	}

	/**
	 * 业务回调
	 * 
	 * @param objects
	 */
	public abstract void refresh(Response<?> response);

	/**
	 * Toast显示
	 * 
	 * @param text
	 */
	protected void showToast(CharSequence text) {
		// Toast.makeText(this, text, 0).show();
		UilsBase.showMsg(this, text.toString());
	}

	/**
	 * Toast显示
	 * 
	 * @param text
	 */
	protected void showToast(int r) {
		// Toast.makeText(this, r, 0).show();
		UilsBase.showMsg(this, r);
	}

	/**
	 * 设置标题行内容
	 * 
	 * @param flag
	 * @param leftbar
	 * @param titlebar
	 * @param rightbar
	 */
	protected void setTitleBar(int flag, String leftbar, String titlebar, String rightbar) {
		if (this.ly != null)
			this.ly.setButtonTypeAndInfo(flag, leftbar, titlebar, rightbar, false);
	}
	
	protected void setTitleBelow(String content){
		if (this.ly != null)
			this.ly.setTitleBelowInfo(content);
	}
	protected void setTitleBelow(){
		if (this.ly != null)
			this.ly.setTitleBelowInfo();
	}

	/**
	 * 设置leftButton
	 */
	protected void setLeftButton(boolean needText, String text, int backgroundId) {
		if (this.ly != null)
			this.ly.setLeftButton(needText, text, backgroundId);
	}
	
	protected ImageView getLeftButton() {
		if (this.ly != null){
			return this.ly.getLeftButton();
		}
		return null;
	}
	protected TextView getTitleBelowView() {
		if (this.ly != null){
			return this.ly.getTitleBelow();
		}
		return null;
	}

	/**
	 * 设置右边的button
	 */
	protected void setRightButton(boolean needText, String text, int backgroundId) {
		if (this.ly != null)
			this.ly.setRightButton(needText, text, backgroundId);
	}
	protected TextView getRightButton() {
		if (this.ly != null){
			return this.ly.getRightButton();
		}
		return null;
	}

	/*
	 * 设置中间文字
	 */
	protected void setTvInfo(String text) {
		if (this.ly != null)
			this.ly.setTitle(text);
	}

	/*protected void setTvInfo2(String text) {
		if (this.ly != null)
			this.ly.setTitle2(text);
	}*/

	/*
	 * 设置中间文字
	 */
	protected void setTvInfo(String title, float textSize, boolean pic, int picResourse) {
		if (this.ly != null)
			this.ly.setTitle(title, textSize, pic, picResourse);
	}
	
	protected TextView getTitleTextView(){
		if (this.ly != null){
			return this.ly.getTitle();
		}
		return null;
	}

	/**
	 * 设置标题行内容
	 * 
	 * @param flag
	 * @param leftbar
	 * @param titlebar
	 * @param rightbar
	 * @param isClickTitle
	 */
	protected void setTitleBar(int flag, String leftbar, String titlebar, String rightbar, boolean isClickTitle) {
		if (this.ly != null)
			this.ly.setButtonTypeAndInfo(flag, leftbar, titlebar, rightbar, isClickTitle);
		if (isClickTitle)
			ly.tvInfo.setOnClickListener(this);
//		ly.tvInfo2.setOnClickListener(this);
	}

	protected void setTitleBars(int flag, String leftbar, String titlebar, String rightbar, boolean isClickTitle) {
		if (this.ly != null)
			this.ly.setButtonTypeAndInfo(flag, leftbar, titlebar, rightbar, isClickTitle);
		if (isClickTitle)
			ly.tvInfo.setOnClickListener(this);
	}

	protected void setTvInfoClickListener(final TvInfoClickListener click){
		ly.tvInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View tv) {
				click.onTvInfoClickListener((TextView)tv);
			}
		});
	}

	public View getTitleView(){
		return ly.rlTitle;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		application.removeActivity(this);
	}

	/**
	 * 响应头部Bar事件
	 * 
	 * @param bar
	 */
	protected abstract void handleTitleBarEvent(TitleBar bar);

	@Override
	public void onClick(View v) {
		if (v == ly.leftButton)
			handleTitleBarEvent(TitleBar.LEFT);
		if (v == ly.tvInfo)
			handleTitleBarEvent(TitleBar.TEXT);
		if (v == ly.rightButton)
			handleTitleBarEvent(TitleBar.RIGHT);
//		if (v == ly.tvInfo2)
//			handleTitleBarEvent(TitleBar.TEXT2);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		if (!isAppOnForeground()) {
			// app 进入后台

			// 全局变量isActive = false 记录当前已经进入后台
		}
	}

	/**
	 * 程序是否在前台运行
	 * 
	 * @return
	 */
	public boolean isAppOnForeground() {
		// Returns a list of application processes that are running on the
		// device

		ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(
				Context.ACTIVITY_SERVICE);
		String packageName = getApplicationContext().getPackageName();

		List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		if (appProcesses == null)
			return false;

		for (RunningAppProcessInfo appProcess : appProcesses) {
			// The name of the process that this object is associated with.
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}

		return false;
	}

}
