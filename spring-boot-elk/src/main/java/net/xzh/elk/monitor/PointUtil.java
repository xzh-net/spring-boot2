package net.xzh.elk.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Component;

/**
 * 埋点日志工具类
 * @author xzh
 *
 */
@Component
public class PointUtil {
    
    /**
     * 获取调用类的Logger
     */
    private static Logger getLogger() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = stackTrace[3].getClassName();
        return LoggerFactory.getLogger(className);
    }
    
    public static void info(String format, Object... args) {
        getLogger().info(format, args);
    }
    
    public static void debug(String format, Object... args) {
        getLogger().debug(format, args);
    }
    
    public static void warn(String format, Object... args) {
        getLogger().warn(format, args);
    }
    
    public static void error(String format, Object... args) {
        getLogger().error(format, args);
    }
    
    public static void error(String message, Throwable throwable) {
        getLogger().error(message, throwable);
    }
}