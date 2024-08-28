package net.xzh.k8s.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1ObjectMetaBuilder;
import io.kubernetes.client.openapi.models.V1Service;
import io.kubernetes.client.openapi.models.V1ServiceBuilder;
import io.kubernetes.client.openapi.models.V1ServiceList;
import io.kubernetes.client.openapi.models.V1ServicePort;
import io.kubernetes.client.openapi.models.V1ServicePortBuilder;
import io.kubernetes.client.openapi.models.V1ServiceSpecBuilder;
import io.kubernetes.client.openapi.models.V1Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.k8s.common.model.CommonResult;

/**
 * 用于管理 Kubernetes 核心 API 对象，如 Pod、Service、Namespace、Node 和 PersistentVolume
 * 
 * @author CR7
 *
 */
@Api(tags = "CoreV1Api管理Service")
@RestController
@RequestMapping("/svc")
public class ServiceController {

	private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

	@ApiOperation("查询所有Service")
	@RequestMapping(value = "/listService", method = RequestMethod.GET)
	public CommonResult<?> listService(@RequestParam String namespace) {
		CoreV1Api apiInstance = new CoreV1Api();
		ArrayList<String> list = new ArrayList<String>();
		try {
			V1ServiceList serviceList = apiInstance.listNamespacedService(namespace, null, null, null, null, null, null,
					null, null, null, null);
			serviceList.getItems().forEach(svc -> list.add(svc.getMetadata().getName()));
		} catch (ApiException e) {
			logger.error("Exception when calling CoreV1Api#listPodForAllNamespaces");
			e.printStackTrace();
		}
		return CommonResult.success(list);
	}

	@ApiOperation("创建NodePort类型Service")
	@RequestMapping(value = "/createService", method = RequestMethod.GET)
	public CommonResult<?> createService(@RequestParam String namespace) {
		CoreV1Api apiInstance = new CoreV1Api();

		Map<String, String> selectLabels = new HashMap<>();
		String serviceName = "svc-" + System.currentTimeMillis();
		selectLabels.put("app.zidingyi.name", "test1-pod-nginx"); // 此处需要对应pod中selectLabels中内容

		// 创建 V1ServicePortBuilder 对象列表
		List<V1ServicePortBuilder> portBuilders = new ArrayList<>();

		// 添加端口构建器到列表中
		portBuilders.add(new V1ServicePortBuilder().withProtocol("TCP").withPort(8000)// 集群内部监听的端口,service本身端口
				.withTargetPort(new IntOrString(80)) // 目标容器
				.withNodePort(30880)); // 对外网暴漏端口

		// 使用 Stream API 将 portBuilders 列表转换为 servicePorts 列表
		List<V1ServicePort> servicePorts = portBuilders.stream().map(V1ServicePortBuilder::build)
				.collect(Collectors.toList());

		V1Service body = new V1ServiceBuilder().withMetadata(new V1ObjectMetaBuilder().withName(serviceName) // DNS-1035
				.withNamespace(namespace) // 命名空间
				.build()).withSpec(new V1ServiceSpecBuilder().withType("NodePort") // 设置服务类型为NodePort
						.withSelector(selectLabels) // 设置选择器
						.withPorts(servicePorts).build())
				.build();
		V1Service v1Service = null;
		try {
			v1Service = apiInstance.createNamespacedService(namespace, body, null, null, null);
		} catch (ApiException e) {
			logger.error("Exception when calling CoreV1Api#createNamespacedService");
			e.printStackTrace();
		}
		return CommonResult.success(v1Service.getMetadata().getName());
	}

	@ApiOperation("删除Service")
	@RequestMapping(value = "/deleteService", method = RequestMethod.GET)
	public CommonResult<?> deleteService(@RequestParam String namespace, @RequestParam String svcname) {
		CoreV1Api apiInstance = new CoreV1Api();
		V1Status v1Status = null;
		try {
			v1Status = apiInstance.deleteNamespacedService(svcname, namespace, null, null, null, null, null, null);
		} catch (ApiException e) {
			logger.error("Exception when calling CoreV1Api#deleteNamespacedService");
			e.printStackTrace();
		}
		return CommonResult.success(v1Status);
	}

}