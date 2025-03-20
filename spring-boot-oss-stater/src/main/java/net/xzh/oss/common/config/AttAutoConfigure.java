package net.xzh.oss.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import net.xzh.oss.common.properties.FileServerProperties;
import net.xzh.oss.common.template.FdfsTemplate;
import net.xzh.oss.common.template.S3Template;

/**
 * @author xzh
 * @date 2021/2/13
 */
@EnableConfigurationProperties(FileServerProperties.class)
@Import({FdfsTemplate.class, S3Template.class})
public class AttAutoConfigure {

}
