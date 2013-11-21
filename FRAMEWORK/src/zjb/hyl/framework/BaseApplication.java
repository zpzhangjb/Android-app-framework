package zjb.hyl.framework;

import java.util.ArrayList;

import zjb.hyl.framework.log.Logger;
import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.SharedPreferences;

import com.nostra13.universalimageloader.core.ImageLoader;

public class BaseApplication extends Application {
	boolean CHATING;
	private Logger logger = Logger.getLogger(BaseApplication.class);
	public ArrayList<Activity> activities = new ArrayList<Activity>();
	public String deviceId;
	private static final int MSG = 0x001;
	String property;
	private ImageLoader mImageLoader;
	public static BaseApplication mApplication;
	private SharedPreferences sp;
	private NotificationManager mNotificationManager;
	private Notification mNotification;
	protected int t = 0;
	private static final String TAG = "BaseApplication";

	@Override
	public void onCreate() {
		super.onCreate();
		mApplication = this;
	}

	public void addActivity(Activity activity) {
		activities.add(activity);
	}

	public void removeActivity(Activity activity) {
		activities.remove(activity);
	}

	public ArrayList<Activity> getActivities() {
		return activities;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		System.out
				.println("BaseApplication is onTerminate()-------------------------------------------------");
	}

}