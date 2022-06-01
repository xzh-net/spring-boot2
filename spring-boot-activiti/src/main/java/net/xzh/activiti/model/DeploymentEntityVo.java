package net.xzh.activiti.model;

import java.io.Serializable;
import java.util.Date;

import org.activiti.engine.repository.Deployment;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeploymentEntityVo implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String id;
    protected String name;
    protected String category;
    protected String tenantId = "";
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    protected Date deploymentTime;

    public DeploymentEntityVo(Deployment deployment) {
        setId(deployment.getId());
        setName(deployment.getName());
        setDeploymentTime(deployment.getDeploymentTime());
        setCategory(deployment.getCategory());
        setTenantId(deployment.getTenantId());
    }
}
