package net.xzh.oss.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import net.xzh.oss.common.properties.FileServerProperties;
import net.xzh.oss.common.template.FdfsTemplate;
import net.xzh.oss.common.template.S3Template;

/**
 * @author zlt
 * @date 2021/2/13
 * <p>
 * Blog: https://zlt2000.gitee.io
 * Github: https://github.com/zlt2000
 */
@EnableConfigurationProperties(FileServerProperties.class)
@Import({FdfsTemplate.class, S3Template.class})
public class OssAutoConfigure {

}
