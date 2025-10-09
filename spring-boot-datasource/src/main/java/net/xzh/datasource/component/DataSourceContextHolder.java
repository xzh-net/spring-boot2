package net.xzh.datasource.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据源上下文持有者
 * 
 * @author CR7
 *
 */
public class DataSourceContextHolder {

	public static final Logger log = LoggerFactory.getLogger(DataSourceContextHolder.class);

	private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

	/**
	 * 设置当前数据源
	 */
	public static void setDataSource(String dataSource) {
		log.info("切换到{}数据源", dataSource);
		CONTEXT_HOLDER.set(dataSource);
	}

	/**
	 * 获取当前数据源
	 */
	public static String getDataSource() {
		return CONTEXT_HOLDER.get();
	}

	/**
	 * 清除数据源
	 */
	public static void clearDataSource() {
		CONTEXT_HOLDER.remove();
	}
}