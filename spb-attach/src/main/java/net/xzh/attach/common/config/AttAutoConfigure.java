package net.xzh.attach.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import net.xzh.attach.common.properties.FileServerProperties;
import net.xzh.attach.common.template.FdfsTemplate;
import net.xzh.attach.common.template.S3Template;

/**
 * @author xzh
 * @date 2021/2/13
 */
@EnableConfigurationProperties(FileServerProperties.class)
@Import({FdfsTemplate.class, S3Template.class})
public class AttAutoConfigure {

}
