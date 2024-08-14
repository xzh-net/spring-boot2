package net.xzh.k8s.controller;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1NamespaceList;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.openapi.models.V1ServiceList;
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
@Api(tags = "CoreV1Api管理")
@RestController
public class CoreV1ApiController {

	private static final Logger logger = LoggerFactory.getLogger(CoreV1ApiController.class);

	@ApiOperation("获取应用空间列表")
	@RequestMapping(value = "/listNamespace", method = RequestMethod.GET)
	public CommonResult<?> listNamespace() {
		CoreV1Api apiInstance = new CoreV1Api();
		ArrayList<String> list = new ArrayList<String>();
		try {
			V1NamespaceList namespaceList = apiInstance.listNamespace(null, null, null, null, null, null, null, null,
					null, null);
			namespaceList.getItems().forEach(namespace -> list.add(namespace.getMetadata().getName()));
		} catch (ApiException e) {
			logger.error("Exception when calling CoreV1Api#listNamespace");
			e.printStackTrace();
		}
		return CommonResult.success(list);
	}

	@ApiOperation("创建命名空间")
	@RequestMapping(value = "/createNamespace", method = RequestMethod.GET)
	public CommonResult<?> createNamespace(@RequestParam String ns) {
		CoreV1Api apiInstance = new CoreV1Api();
		V1Namespace rtn = null;

		V1Namespace namespace = new V1Namespace();
		namespace.setApiVersion("v1");
		namespace.setKind("Namespace");
		namespace.setMetadata(new V1ObjectMeta().name(ns));
		try {
			rtn = apiInstance.createNamespace(namespace, null, null, null);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CommonResult.success(rtn);
	}

	@ApiOperation("删除命名空间")
	@RequestMapping(value = "/deleteNamespace", method = RequestMethod.GET)
	public CommonResult<?> deleteNamespace(@RequestParam String ns) {
		CoreV1Api apiInstance = new CoreV1Api();
		V1Status rtn = null;
		try {
			rtn = apiInstance.deleteNamespace(ns, null, null, null, null, null, null);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CommonResult.success(rtn);
	}

	@ApiOperation("查询某个应用空间下的所有service")
	@RequestMapping(value = "/listNamespacedService", method = RequestMethod.GET)
	public CommonResult<?> listNamespacedService(@RequestParam String ns) {
		CoreV1Api apiInstance = new CoreV1Api();
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try {
			V1ServiceList serviceList = apiInstance.listNamespacedService(ns, null, null, null, null, null, null, null,
					null, null, null);
			serviceList.getItems()
					.forEach(list -> rtn.put(list.getMetadata().getName(), list.getMetadata().getManagedFields()));
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CommonResult.success(rtn);
	}

	@ApiOperation("查询某个应用空间下的所有Pod")
	@RequestMapping(value = "/listNamespacedPod", method = RequestMethod.GET)
	public CommonResult<?> listNamespacedPod(@RequestParam String ns) {
		CoreV1Api apiInstance = new CoreV1Api();
		HashMap<String, Object> rtn = new HashMap<String, Object>();
		try {
			V1PodList podList = apiInstance.listNamespacedPod(ns, null, null, null, null, null, null, null, null, null,
					null);
			podList.getItems()
					.forEach(list -> rtn.put(list.getMetadata().getName(), list.getMetadata().getManagedFields()));
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CommonResult.success(rtn);
	}

}