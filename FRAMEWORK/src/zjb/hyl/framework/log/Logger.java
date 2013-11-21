package zjb.hyl.framework.log;

import zjb.hyl.framework.utils.ConstantsBase;
import android.util.Log;

/**
 * log实现
 * 
 * @author heiyl
 * 
 */
public class Logger extends BaseLogger {
	private Class<?> mclass;

	public Logger(Class<?> class1) {
		mclass = class1;
	}

	public static Logger getLogger(Class<?> class1) {
		return new Logger(class1);
	}

	public void error(String string) {
		if (ConstantsBase.LOG_LEVEL >= ConstantsBase.LOG_ERROR_FLG) {
			Log.e(mclass.getName(), string);
		}
	}

	public void error(String string, Exception e) {
		if (ConstantsBase.LOG_LEVEL >= ConstantsBase.LOG_ERROR_FLG) {
			Log.e(mclass.getName(), string, e);
		}
	}

	public void warn(String string) {
		if (ConstantsBase.LOG_LEVEL >= ConstantsBase.LOG_WARN_FLG) {
			Log.w(mclass.getName(), string);
		}
	}

	public void info(String string) {
		if (ConstantsBase.LOG_LEVEL >= ConstantsBase.LOG_INFO_FLG) {
			Log.i(mclass.getName(), string);
		}
	}

	public void debug(String string) {
		if (ConstantsBase.LOG_LEVEL >= ConstantsBase.LOG_DEBUG_FLG) {
			Log.d(mclass.getName(), string);
		}
	}

	public void verbos(String string) {
		if (ConstantsBase.LOG_LEVEL >= ConstantsBase.LOG_VERBOS_FLG) {
			Log.v(mclass.getName(), string);
		}
	}
}
