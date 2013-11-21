package zjb.hyl.framework.log;

import java.lang.reflect.Constructor;

/**
 * log定义
 * 
 * @author heiyl
 * 
 */
public abstract class BaseLogger {
	@SuppressWarnings("rawtypes")
	public static BaseLogger getLogger(Class class1) {
		Class<?> className = null;
		try {
			className = Class.forName("com.chanjet.sns.log.Logger");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		}

		if (className == null) {
			throw new RuntimeException("log class not found!");
		}

		try {
			Constructor<?> constructor = className.getConstructor(Class.class);
			BaseLogger logger = (BaseLogger) constructor.newInstance(class1);
			return logger;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public abstract void error(String string);

	public abstract void error(String string, Exception e);

	public abstract void warn(String string);

	public abstract void info(String string);

	public abstract void debug(String string);

}
