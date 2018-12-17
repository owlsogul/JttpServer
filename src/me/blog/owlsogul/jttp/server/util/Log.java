package me.blog.owlsogul.jttp.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	private final static Logger logger = new Logger();
	
	public static void error(Level level, Exception e) {
		StringBuilder builder = new StringBuilder();
		builder.append(e).append("\n");
		for (StackTraceElement element : e.getStackTrace()) {
			builder.append(element).append("\n");
		}
		logger.warning(builder.toString());
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
class Logger {
	private String logFormat = "&(date) &(class)->&(method) [&(level)] &(log)";
	public Logger() {
		
	}
	public Logger(String logFormat) {
		this.logFormat = logFormat;
	}
	
	public void warning(String log) {
		log(Level.Warning, log);
	}
	
	public void severe(String log) {
		log(Level.Severe, log);
	}
	
	public void info(String log) {
		log(Level.Info, log);
	}
	
	public void log(Level level, String log) {
		StackTraceElement[] stack = new Throwable().getStackTrace();
		int idx = stack.length - 1;
		while (stack[idx].getClassName().equals("MyLogger") || stack[idx].getClassName().equals("Log")) {
			idx--;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("YY.MM.dd hh:mm:ss");
		String currentDate = sdf.format(new Date());
		
		String totalLog = new String(logFormat)
								.replace("&(date)", currentDate)
								.replace("&(class)", stack[idx].getClassName())
								.replace("&(method)", stack[idx].getMethodName())
								.replace("&(level)", level.toString())
								.replace("&(log)", log);
		System.out.println(totalLog);
	}
}
