package net.xzh.k8s.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
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

/**
 * 用于管理 Kubernetes 核心 API 对象，如 Pod、Service、Namespace、Node 和 PersistentVolume
 * 
 * @author CR7
 *
 */
@Api(tags = "CoreV1Api管理命名空间")
@RestController
@RequestMapping("/ns")
public class NameSpaceController {

	private static final Logger logger = LoggerFactory.getLogger(NameSpaceController.class);

	@ApiOperation("查询所有命名空间")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ArrayList<String> list() {
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
		return list;
	}

	/**
	 * 创建命名空间
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@RequestParam String namespace) {
		CoreV1Api apiInstance = new CoreV1Api();
		V1Namespace rtn = null;

		V1Namespace ns = new V1Namespace();
		ns.setApiVersion("v1");
		ns.setKind("Namespace");
		ns.setMetadata(new V1ObjectMeta().name(namespace));
		try {
			rtn = apiInstance.createNamespace(ns, null, null, null);
		} catch (ApiException e) {
			logger.error("Exception when calling CoreV1Api#createNamespace");
			e.printStackTrace();
		}
		return "创建成功：" + rtn;
	}

	/**
	 * 删除命名空间
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestParam String namespace) {
		V1Status rtn = null;
		CoreV1Api apiInstance = new CoreV1Api();
		try {
			rtn = apiInstance.deleteNamespace(namespace, null, null, null, null, null, null);
		} catch (ApiException e) {
			logger.error("Exception when calling CoreV1Api#deleteNamespace");
			e.printStackTrace();
		}
		return "删除成功：" + rtn;
	}

	/**
	 * 查询某个命名空间下的所有service
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/listService", method = RequestMethod.GET)
	public ArrayList<String> listService(@PathVariable String namespace) {
		CoreV1Api apiInstance = new CoreV1Api();
		ArrayList<String> rtn = new ArrayList<String>();
		try {
			V1ServiceList serviceList = apiInstance.listNamespacedService(namespace, null, null, null, null, null, null, null,
					null, null, null);
			serviceList.getItems()
					.forEach(list -> rtn.add(list.getMetadata().getName()));
		} catch (ApiException e) {
			logger.error("Exception when calling CoreV1Api#listNamespacedService");
			e.printStackTrace();
		}
		return rtn;
	}

	/**
	 * 查询某个命名空间下的所有Pod
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/listPod/{namespace}", method = RequestMethod.GET)
	public ArrayList<String> listPodByNamespace(@RequestParam String namespace) {
		CoreV1Api apiInstance = new CoreV1Api();
		ArrayList<String> rtn = new ArrayList<String>();
		try {
			V1PodList podList = apiInstance.listNamespacedPod(namespace, null, null, null, null, null, null, null, null, null,
					null);
			podList.getItems()
					.forEach(list -> rtn.add(list.getMetadata().getName()));
		} catch (ApiException e) {
			logger.error("Exception when calling CoreV1Api#listNamespacedPod");
			e.printStackTrace();
		}
		return rtn;
	}
	
	/**
	 * 查询所有Service
	 * @return
	 */
	@RequestMapping(value = "/listService", method = RequestMethod.GET)
	public ArrayList<String> listService() {
		CoreV1Api apiInstance = new CoreV1Api();
		ArrayList<String> rtn = new ArrayList<String>();
		try {
			V1ServiceList serviceList = apiInstance.listServiceForAllNamespaces(null, null, null, null, null, null, null, null, null, null);
			serviceList.getItems()
					.forEach(list -> rtn.add(list.getMetadata().getName()));
		} catch (ApiException e) {
			logger.error("Exception when calling CoreV1Api#listNamespacedService");
			e.printStackTrace();
		}
		return rtn;
	}

	/**
	 * 查询所有Pod
	 * @return
	 */
	@RequestMapping(value = "/listPod", method = RequestMethod.GET)
	public ArrayList<String> listPod() {
		CoreV1Api apiInstance = new CoreV1Api();
		ArrayList<String> rtn = new ArrayList<String>();
		try {
			V1PodList podList = apiInstance.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null,
					null);
			podList.getItems()
					.forEach(list -> rtn.add(list.getMetadata().getName()));
		} catch (ApiException e) {
			logger.error("Exception when calling CoreV1Api#listNamespacedPod");
			e.printStackTrace();
		}
		return rtn;
	}

}