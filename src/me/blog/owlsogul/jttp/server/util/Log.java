package me.blog.owlsogul.jttp.server.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

	private final static Logger logger = Logger.getLogger("JttpServer");
	public final static Level Info = Level.INFO;
	public final static Level Warning = Level.WARNING;
	public final static Level Severe = Level.SEVERE;
	
	public static void error(Level level, Exception e) {
		StringBuilder builder = new StringBuilder();
		builder.append(e).append("\n");
		for (StackTraceElement element : e.getStackTrace()) {
			builder.append(element).append("\n");
		}
		logger.log(level, builder.toString());
	}
	
	public static void info(String format) {
		logger.info(format);
	}
	
	public static void info(String format, Object... args) {
		logger.info(String.format(format, args));
	}
	
	public static void log(Level level, String format) {
		logger.log(level, format);
	}
	
	public static void log(Level level, String format, Object... args) {
		logger.log(level, String.format(format, args));
	}
	
}
