package net.xzh.k8s.controller;

import java.util.ArrayList;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.models.V1Container;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1DeploymentBuilder;
import io.kubernetes.client.openapi.models.V1DeploymentList;
import io.kubernetes.client.openapi.models.V1DeploymentSpec;
import io.kubernetes.client.openapi.models.V1LabelSelector;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1PodSpec;
import io.kubernetes.client.openapi.models.V1PodTemplateSpec;
import io.kubernetes.client.openapi.models.V1Status;
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
@Api(tags = "AppsV1Api管理Deployment")
@RestController
public class DeploymentController {
	
	private static final Logger logger = LoggerFactory.getLogger(DeploymentController.class);

	@ApiOperation("创建Deployment")
	@RequestMapping(value = "/createDeployment", method = RequestMethod.GET)
	public CommonResult<?> createDeployments(@RequestParam String namespace) {
		AppsV1Api apiInstance = new AppsV1Api();
		String deploymentName = "test-deploy-nginx";
	    String imageName = "nginx:1.22.1";

	    // Create an example deployment
	    V1DeploymentBuilder deploymentBuilder =
	        new V1DeploymentBuilder()
	            .withApiVersion("apps/v1")
	            .withKind("Deployment")
	            .withMetadata(new V1ObjectMeta().name(deploymentName).namespace(namespace))
	            .withSpec(
	                new V1DeploymentSpec()
	                    .replicas(1)
	                    .selector(new V1LabelSelector().putMatchLabelsItem("name", deploymentName))
	                    .template(
	                        new V1PodTemplateSpec()
	                            .metadata(new V1ObjectMeta().putLabelsItem("name", deploymentName))
	                            .spec(
	                                new V1PodSpec()
	                                    .containers(
	                                        Collections.singletonList(
	                                            new V1Container()
	                                                .name(deploymentName)
	                                                .image(imageName))))));
	    V1Deployment v1Deployment =  null;
	    try {
	    	v1Deployment = apiInstance.createNamespacedDeployment(
			    namespace, deploymentBuilder.build(), null, null, null);
		} catch (ApiException e) {
			logger.error("Exception when calling AppsV1Api#createNamespacedDeployment");
			e.printStackTrace();
		}
		return CommonResult.success(v1Deployment.getMetadata().getName());
	}
	
	
	@ApiOperation("删除Deployment")
	@RequestMapping(value = "/deleteDeployment", method = RequestMethod.GET)
	public CommonResult<?> deleteDeployment(@RequestParam String namespace,@RequestParam String deployName) {
		AppsV1Api apiInstance = new AppsV1Api();
		V1Status v1Status =  null;
	    try {
	    	v1Status = apiInstance.deleteNamespacedDeployment(deployName,namespace, null, null, null, null, null, null);
		} catch (ApiException e) {
			logger.error("Exception when calling AppsV1Api#deleteNamespacedDeployment");
			e.printStackTrace();
		}
		return CommonResult.success(v1Status);
	}
	
	@ApiOperation("查询Deployment")
	@RequestMapping(value = "/listDeployment", method = RequestMethod.GET)
	public CommonResult<?> listDeployment(@RequestParam String namespace) {
		AppsV1Api apiInstance = new AppsV1Api();
		ArrayList<String> list = new ArrayList<String>();
		V1DeploymentList deploymentList =  null;
	    try {
	    	 deploymentList  = apiInstance.listNamespacedDeployment(namespace, null, null, null, null, null, null, null, null, null, null);
	    	 deploymentList.getItems().forEach(deployment -> list.add(deployment.getMetadata().getName()));
		} catch (ApiException e) {
			logger.error("Exception when calling AppsV1Api#listNamespacedDeployment");
			e.printStackTrace();
		}
		return CommonResult.success(list);
	}


}