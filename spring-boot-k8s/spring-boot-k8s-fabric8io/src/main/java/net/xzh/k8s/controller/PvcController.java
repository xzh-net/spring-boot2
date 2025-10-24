package net.xzh.k8s.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.fabric8.kubernetes.api.model.Node;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaim;
import io.fabric8.kubernetes.api.model.networking.v1beta1.Ingress;
import io.fabric8.kubernetes.api.model.networking.v1beta1.IngressClass;
import io.fabric8.kubernetes.api.model.storage.StorageClass;
import io.fabric8.kubernetes.client.KubernetesClient;

/**
 * 管理PVC
 * 
 * @author CR7
 *
 */
@RestController
@RequestMapping("/pvc")
public class PvcController {

	@Autowired
	private KubernetesClient kubernetesClient;

	/**
	 * 查询所有
	 * 
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ArrayList<String> list(@RequestParam String namespace) {
		ArrayList<String> list = new ArrayList<String>();
		List<PersistentVolumeClaim> pvcList = kubernetesClient.persistentVolumeClaims().inNamespace(namespace).list()
				.getItems();
		pvcList.forEach(pvc -> list.add(pvc.getMetadata().getName()));
		return list;
	}

	/**
	 * 查询所有StorageClass
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listSC", method = RequestMethod.GET)
	public List<StorageClass> listStorageClass() {
		List<StorageClass> storageClasses = kubernetesClient.storage().storageClasses().list().getItems();
		return storageClasses;
	}

	/**
	 * 查询所有IngressClass
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listIC", method = RequestMethod.GET)
	public List<IngressClass> listIngressClass() {
		List<IngressClass> ingressClasses = kubernetesClient.network().v1beta1().ingressClasses().list().getItems();
		return ingressClasses;
	}

	/**
	 * 查询所有Ingress
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listIngress", method = RequestMethod.GET)
	public List<Ingress> listIngress() {
		List<Ingress> ingresses = kubernetesClient.network().ingresses().list().getItems();
		return ingresses;
	}

	/**
	 * 查询所有集群节点
	 * 
	 * @return
	 */
	@RequestMapping(value = "/listNodes", method = RequestMethod.GET)
	public List<Node> listNodes() {
		List<Node> nodes = kubernetesClient.nodes().list().getItems();
		return nodes;
	}
}
