package net.xzh.hdfs.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.xzh.hdfs.repository.HdfsService;

/**
 * HDFS相关配置
 */
@Configuration
public class HdfsConfig {

	@Value("${hadoop.name-node}")
	private String nameNode;

	@Bean
	public HdfsService getHbaseService() {
		System.setProperty("hadoop.home.dir", "D:\\tools\\hadoop-3.1.4");
		System.setProperty("HADOOP_USER_NAME","root");
		org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
		conf.set("fs.defaultFS", nameNode);
		conf.set("dfs.replication", "3");
		return new HdfsService(conf, nameNode);
	}
}