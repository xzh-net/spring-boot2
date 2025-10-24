package net.xzh.k8s.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

/**
 * 命名空间管理
 * 
 * @author CR7
 *
 */
@RestController
@RequestMapping("/ns")
public class NameSpaceController {

	@Autowired
	private KubernetesClient kubernetesClient;

	/**
	 * 查询所有命名空间
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ArrayList<String> list() {
		NamespaceList namespaceList = kubernetesClient.namespaces().list();
		ArrayList<String> list = new ArrayList<String>();
		namespaceList.getItems().forEach(namespace -> list.add(namespace.getMetadata().getName()));
		return list;
	}

	/**
	 * 创建命名空间
	 * 
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@RequestParam String namespace) {
		Map<String, String> labelMap = new HashMap<>();
		labelMap.put("app_name", "test");
		Namespace ns = new NamespaceBuilder().withNewMetadata().withName(namespace).addToLabels(labelMap).endMetadata()
				.build();
		Namespace k8sNamespace = kubernetesClient.namespaces().create(ns);
		return "创建成功：" + k8sNamespace;
	}

	/**
	 * 删除命名空间
	 * 
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public List<StatusDetails> delete(@RequestParam String namespace) {
		Namespace ns = new NamespaceBuilder().withNewMetadata().withName(namespace).endMetadata().build();
		List<StatusDetails> status = kubernetesClient.namespaces().delete(ns);
		return status;
	}

	/**
	 * 查询某个命名空间下的所有service
	 * 
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/listService/{namespace}", method = RequestMethod.GET)
	public ArrayList<String> listService(@PathVariable String namespace) {
		ArrayList<String> rtn = new ArrayList<String>();
		List<Service> serviceList = kubernetesClient.services().inNamespace(namespace).list().getItems();
		serviceList.forEach(svc -> rtn.add(svc.getMetadata().getName()));
		return rtn;
	}

	/**
	 * 查询某个命名空间下的所有Pod
	 * 
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/listPod/{namespace}", method = RequestMethod.GET)
	public ArrayList<String> listPode(@PathVariable String namespace) {
		ArrayList<String> rtn = new ArrayList<String>();
		List<Pod> podsList = kubernetesClient.pods().inNamespace(namespace).list().getItems();
		podsList.forEach(pod -> rtn.add(pod.getMetadata().getName()));
		return rtn;
	}

	/**
	 * 查询所有Service
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listService", method = RequestMethod.GET)
	public ArrayList<String> listService() {
		ArrayList<String> rtn = new ArrayList<String>();
		List<Service> serviceList = kubernetesClient.services().list().getItems();
		serviceList.forEach(svc -> rtn.add(svc.getMetadata().getName()));
		return rtn;
	}

	/**
	 * 查询所有Pod
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listPod", method = RequestMethod.GET)
	public ArrayList<String> listPod() {
		ArrayList<String> rtn = new ArrayList<String>();
		List<Pod> podsList = kubernetesClient.pods().list().getItems();
		podsList.forEach(pod -> rtn.add(pod.getMetadata().getName()));
		return rtn;
	}

}