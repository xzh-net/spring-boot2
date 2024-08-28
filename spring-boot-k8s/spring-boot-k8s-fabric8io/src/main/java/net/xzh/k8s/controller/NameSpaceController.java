package net.xzh.k8s.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.StatusDetails;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.k8s.common.model.CommonResult;

/**
 * 命名空间管理
 * 
 * @author CR7
 *
 */
@Api(tags = "管理命名空间")
@RestController
@RequestMapping("/ns")
public class NameSpaceController {

	private static final Logger logger = LoggerFactory.getLogger(NameSpaceController.class);

	@Autowired
	private KubernetesClient kubernetesClient;

	@ApiOperation("查询所有命名空间")
	@RequestMapping(value = "/listNamespace", method = RequestMethod.GET)
	public CommonResult<?> listNamespace() {
		NamespaceList namespaceList = kubernetesClient.namespaces().list();
		ArrayList<String> list = new ArrayList<String>();
		namespaceList.getItems().forEach(namespace -> list.add(namespace.getMetadata().getName()));
		return CommonResult.success(list);
	}

	@ApiOperation("创建命名空间")
	@RequestMapping(value = "/createNamespace", method = RequestMethod.POST)
	public CommonResult<?> createNamespace(@RequestParam String namespace) {
		Map<String, String> labelMap = new HashMap<>();
		labelMap.put("app_name", "test");
		Namespace ns = new NamespaceBuilder().withNewMetadata().withName(namespace).addToLabels(labelMap).endMetadata()
				.build();
		Namespace k8sNamespace = kubernetesClient.namespaces().create(ns);
		return CommonResult.success(k8sNamespace);
	}

	@ApiOperation("删除命名空间")
	@RequestMapping(value = "/deleteNamespace", method = RequestMethod.POST)
	public CommonResult<?> deleteNamespace(@RequestParam String namespace) {
		Namespace ns = new NamespaceBuilder().withNewMetadata().withName(namespace).endMetadata().build();
		List<StatusDetails> delete = kubernetesClient.namespaces().delete(ns);
		return CommonResult.success(delete);
	}

	@ApiOperation("查询某个命名空间下的所有service")
	@RequestMapping(value = "/listServiceByNamespace", method = RequestMethod.GET)
	public CommonResult<?> listServiceByNamespaced(@RequestParam String namespace) {
		ArrayList<String> rtn = new ArrayList<String>();
		List<Service> serviceList = kubernetesClient.services().inNamespace(namespace).list().getItems();
		serviceList.forEach(svc -> rtn.add(svc.getMetadata().getName()));
		return CommonResult.success(rtn);
	}

	@ApiOperation("查询某个命名空间下的所有Pod")
	@RequestMapping(value = "/listPodByNamespace", method = RequestMethod.GET)
	public CommonResult<?> listPodByNamespace(@RequestParam String namespace) {
		ArrayList<String> rtn = new ArrayList<String>();
		List<Pod> podsList = kubernetesClient.pods().inNamespace(namespace).list().getItems();
		podsList.forEach(pod -> rtn.add(pod.getMetadata().getName()));
		return CommonResult.success(rtn);
	}

	@ApiOperation("查询所有Service")
	@RequestMapping(value = "/listService", method = RequestMethod.GET)
	public CommonResult<?> listService() {
		ArrayList<String> rtn = new ArrayList<String>();
		List<Service> serviceList = kubernetesClient.services().list().getItems();
		serviceList.forEach(svc -> rtn.add(svc.getMetadata().getName()));
		return CommonResult.success(rtn);
	}

	@ApiOperation("查询所有Pod")
	@RequestMapping(value = "/listPod", method = RequestMethod.GET)
	public CommonResult<?> listPod() {
		ArrayList<String> rtn = new ArrayList<String>();
		List<Pod> podsList = kubernetesClient.pods().list().getItems();
		podsList.forEach(pod -> rtn.add(pod.getMetadata().getName()));
		return CommonResult.success(rtn);
	}

}