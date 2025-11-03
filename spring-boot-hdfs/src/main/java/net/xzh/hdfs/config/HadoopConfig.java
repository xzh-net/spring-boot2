package net.xzh.hdfs.config;

import java.io.IOException;

import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HDFS相关配置
 */
@Configuration
public class HadoopConfig {

    @Value("${hadoop.name-node}")
    private String nameNode;

    @Value("${hadoop.home-dir}")
    private String hadoopHomeDir;

    @Value("${hadoop.user-name}")
    private String hdfsUser;

    @Value("${hadoop.dfs-replication}")
    private String replication;

    @Bean
    public org.apache.hadoop.conf.Configuration hadoopConfiguration() {
        // 设置Hadoop环境变量
        System.setProperty("hadoop.home.dir", hadoopHomeDir);
        System.setProperty("HADOOP_USER_NAME", hdfsUser);
        
        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
        conf.set("fs.defaultFS", nameNode);
        conf.set("dfs.replication", replication);
        
        // 性能优化配置
        conf.setBoolean("fs.hdfs.impl.disable.cache", false);
        conf.setInt("io.file.buffer.size", 4096);
        
        // 解决Windows开发环境常见问题
        conf.setBoolean("dfs.client.use.datanode.hostname", true);
        conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
        
        return conf;
    }

    @Bean(destroyMethod = "close")
    public FileSystem fileSystem() throws IOException {
        return FileSystem.get(hadoopConfiguration());
    }
}