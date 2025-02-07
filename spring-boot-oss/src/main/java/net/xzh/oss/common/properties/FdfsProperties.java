package net.xzh.oss.common.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * fastdfs配置
 *
 * @author xzh
 * @date 2021/2/11
 */
@Setter
@Getter
public class FdfsProperties {
    /**
     * fastdfs的http访问地址
     */
    private String webUrl;
}
