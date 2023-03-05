package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
	private LogUtil() {
	}
	static {
	}
	public static Logger getLogger() {
		Logger logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		return logger;
	}
}
