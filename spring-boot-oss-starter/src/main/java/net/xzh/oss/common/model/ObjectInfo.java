package net.xzh.oss.common.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xzh
 * @date 2021/2/11
 */
@Setter
@Getter
public class ObjectInfo {
    /**
     * 对象查看路径
     */
    private String objectUrl;
    /**
     * 对象保存路径
     */
    private String objectPath;
}
