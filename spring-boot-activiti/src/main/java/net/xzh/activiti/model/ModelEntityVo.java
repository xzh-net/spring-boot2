package net.xzh.activiti.model;

import java.io.Serializable;
import java.util.Date;

import org.activiti.engine.repository.Model;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author CR7
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ModelEntityVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String key;
    private String category;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastUpdateTime;
    private Integer version = 1;
    private String metaInfo;
    private String deploymentId;
    private String tenantId = "";

    public ModelEntityVo(Model modelEntity) {
        setId(modelEntity.getId());
        setVersion(modelEntity.getVersion());
        setName(modelEntity.getName());
        setKey(modelEntity.getKey());
        setCategory(modelEntity.getCategory());
        setCreateTime(modelEntity.getCreateTime());
        setLastUpdateTime(modelEntity.getLastUpdateTime());
        setMetaInfo(modelEntity.getMetaInfo());
        setDeploymentId(modelEntity.getDeploymentId());
        setTenantId(modelEntity.getTenantId());
    }
}
