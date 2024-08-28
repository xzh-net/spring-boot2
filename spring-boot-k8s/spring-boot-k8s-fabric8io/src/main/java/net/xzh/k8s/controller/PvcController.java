package net.xzh.k8s.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.k8s.common.model.CommonResult;

/**
 * 管理PVC
 * 
 * @author CR7
 *
 */
@Api(tags = "管理pvc")
@RestController
@RequestMapping("/pvc")
public class PvcController {

	private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

	@Autowired
	private KubernetesClient kubernetesClient;
	
	@ApiOperation("查询所有PVC")
	@RequestMapping(value = "/listPvc", method = RequestMethod.GET)
	public CommonResult<?> listPvc(@RequestParam String namespace) {
		ArrayList<String> list = new ArrayList<String>();
		List<PersistentVolumeClaim> pvcList = kubernetesClient.persistentVolumeClaims().inNamespace(namespace).list().getItems();
		pvcList.forEach(pvc -> list.add(pvc.getMetadata().getName()));
		return CommonResult.success(list);
	}
	
	@ApiOperation("查询所有StorageClass")
	@RequestMapping(value = "/listStorageClass", method = RequestMethod.GET)
	public CommonResult<?> listStorageClass(@RequestParam String namespace) {
		ArrayList<String> list = new ArrayList<String>();
		List<StorageClass> storageClasses = kubernetesClient.storage().storageClasses().list().getItems();
		return CommonResult.success(storageClasses);
	}
	
	
	@ApiOperation("查询所有IngressClass")
	@RequestMapping(value = "/listIngressClass", method = RequestMethod.GET)
	public CommonResult<?> listIngressClass(@RequestParam String namespace) {
		List<IngressClass> ingressClasses = kubernetesClient.network().v1beta1().ingressClasses().list().getItems();
		return CommonResult.success(ingressClasses);
	}
	
	@ApiOperation("查询所有Ingress")
	@RequestMapping(value = "/listIngress", method = RequestMethod.GET)
	public CommonResult<?> listIngress() {
		List<Ingress> ingresses = kubernetesClient.network().ingresses().list().getItems();
		return CommonResult.success(ingresses);
	}
	
	@ApiOperation("查询所有集群节点")
	@RequestMapping(value = "/listNodes", method = RequestMethod.GET)
	public CommonResult<?> listNodes() {
		List<Node> nodes = kubernetesClient.nodes().list().getItems();
		return CommonResult.success(nodes);
	}
}
