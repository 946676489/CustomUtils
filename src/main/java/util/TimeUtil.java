package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
	private static final DateTimeFormatter TIMESTAMP_FORMATER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private TimeUtil() {
	}

	public static String toTimeStamp(LocalDateTime localDateTime) {
		return localDateTime.format(TIMESTAMP_FORMATER);
	}
}
