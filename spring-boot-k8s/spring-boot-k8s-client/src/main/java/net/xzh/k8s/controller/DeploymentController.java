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

/**
 * 用于管理 Kubernetes 应用程序的 API 对象，如 Deployment、StatefulSet、DaemonSet 和 ReplicaSet
 * 等
 * 
 * @author CR7
 *
 */
@RestController
@RequestMapping("/deploy")
public class DeploymentController {

	private static final Logger logger = LoggerFactory.getLogger(DeploymentController.class);

	/**
	 * 创建Deployment
	 * 
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(@RequestParam String namespace) {
		AppsV1Api apiInstance = new AppsV1Api();
		String deploymentName = "test-deploy-nginx";
		String imageName = "nginx:1.22.1";

		// Create an example deployment
		V1DeploymentBuilder deploymentBuilder = new V1DeploymentBuilder().withApiVersion("apps/v1")
				.withKind("Deployment").withMetadata(new V1ObjectMeta().name(deploymentName).namespace(namespace))
				.withSpec(new V1DeploymentSpec().replicas(1)
						.selector(new V1LabelSelector().putMatchLabelsItem("name", deploymentName))
						.template(new V1PodTemplateSpec()
								.metadata(new V1ObjectMeta().putLabelsItem("name", deploymentName))
								.spec(new V1PodSpec().containers(Collections
										.singletonList(new V1Container().name(deploymentName).image(imageName))))));
		V1Deployment v1Deployment = null;
		try {
			v1Deployment = apiInstance.createNamespacedDeployment(namespace, deploymentBuilder.build(), null, null,
					null);
		} catch (ApiException e) {
			logger.error("Exception when calling AppsV1Api#createNamespacedDeployment");
			e.printStackTrace();
		}
		return "创建成功：" + v1Deployment.getMetadata().getName();
	}

	/**
	 * 删除Deployment
	 * 
	 * @param namespace
	 * @param deployName
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestParam String namespace, @RequestParam String deployName) {
		AppsV1Api apiInstance = new AppsV1Api();
		V1Status v1Status = null;
		try {
			v1Status = apiInstance.deleteNamespacedDeployment(deployName, namespace, null, null, null, null, null,
					null);
		} catch (ApiException e) {
			logger.error("Exception when calling AppsV1Api#deleteNamespacedDeployment");
			e.printStackTrace();
		}
		return "刪除成功：" + v1Status;
	}

	/**
	 * 查询所有Deployment
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ArrayList<String> list(@RequestParam String namespace) {
		AppsV1Api apiInstance = new AppsV1Api();
		ArrayList<String> list = new ArrayList<String>();
		V1DeploymentList deploymentList = null;
		try {
			deploymentList = apiInstance.listNamespacedDeployment(namespace, null, null, null, null, null, null, null,
					null, null, null);
			deploymentList.getItems().forEach(deployment -> list.add(deployment.getMetadata().getName()));
		} catch (ApiException e) {
			logger.error("Exception when calling AppsV1Api#listNamespacedDeployment");
			e.printStackTrace();
		}
		return list;
	}

}