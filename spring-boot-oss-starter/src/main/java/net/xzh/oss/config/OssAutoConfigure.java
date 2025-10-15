package net.xzh.oss.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import net.xzh.oss.properties.FileServerProperties;
import net.xzh.oss.template.FdfsTemplate;
import net.xzh.oss.template.S3Template;

/**
 * 启动类
 * @author xzh
 * @date 2021/2/13
 */
@EnableConfigurationProperties(FileServerProperties.class)
@Import({FdfsTemplate.class, S3Template.class})
public class OssAutoConfigure {

}
