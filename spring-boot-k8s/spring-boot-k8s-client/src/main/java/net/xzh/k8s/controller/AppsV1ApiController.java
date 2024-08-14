package net.xzh.k8s.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.k8s.common.model.CommonResult;

/**
 * 用于管理 Kubernetes 应用程序的 API 对象，如 Deployment、StatefulSet、DaemonSet 和 ReplicaSet
 * 等
 * 
 * @author CR7
 *
 */
@Api(tags = "AppsV1Api管理")
@RestController
public class AppsV1ApiController {

	@ApiOperation("创建应用")
	@RequestMapping(value = "/createDeployment", method = RequestMethod.GET)	
	public CommonResult<?> createDeployments() {
		return CommonResult.success("未完成");
    }
	
	
}