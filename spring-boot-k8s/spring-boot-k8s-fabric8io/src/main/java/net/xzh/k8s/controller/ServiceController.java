package net.xzh.k8s.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.fabric8.kubernetes.api.model.ServicePortBuilder;
import io.fabric8.kubernetes.api.model.StatusDetails;
import io.fabric8.kubernetes.client.KubernetesClient;

/**
 * 管理Service
 * 
 * @author CR7
 *
 */
@RestController
@RequestMapping("/svc")
public class ServiceController {

	@Autowired
	private KubernetesClient kubernetesClient;

	/**
	 * 查询所有Service
	 * 
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ArrayList<String> list(@RequestParam String namespace) {
		ArrayList<String> list = new ArrayList<String>();
		List<Service> serviceList = kubernetesClient.services().inNamespace(namespace).list().getItems();
		serviceList.forEach(svc -> list.add(svc.getMetadata().getName()));
		return list;
	}

	/**
	 * 创建NodePort类型Service
	 * 
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/addNodePort", method = RequestMethod.GET)
	public String add(@RequestParam String namespace) {

		Map<String, String> selectLabels = new HashMap<>();
		String serviceName = "svc-" + System.currentTimeMillis();
		selectLabels.put("app.zidingyi.name", "test1-pod-nginx"); // 此处需要对应pod中selectLabels中内容

		Service service = new ServiceBuilder().withNewMetadata().withName(serviceName).withLabels(selectLabels) // 代理具有labels标签的pod
				.endMetadata().withNewSpec().withSelector(selectLabels)
				.withPorts(new ServicePortBuilder().withPort(8000) // 集群内部监听的端口,service本身端口
						.withNodePort(30880) // 对外网暴漏端口
						.withNewTargetPort(80) // 流量转发的目标容器端口
						.build())
				.withType("NodePort").endSpec().build();
		Service svc = kubernetesClient.services().inNamespace(namespace).resource(service).create();
		return "创建成功：" + svc.getMetadata().getName();
	}

	/**
	 * 删除Service
	 * 
	 * @param namespace
	 * @param svcname
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public List<StatusDetails> delete(@RequestParam String namespace, @RequestParam String svcname) {
		List<StatusDetails> rtn = kubernetesClient.services().inNamespace(namespace).withName(svcname).delete();
		return rtn;
	}

}