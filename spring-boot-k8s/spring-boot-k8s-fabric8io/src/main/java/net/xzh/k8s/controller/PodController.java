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

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.api.model.StatusDetails;
import io.fabric8.kubernetes.client.KubernetesClient;

/**
 * 管理Pod
 * 
 * @author CR7
 *
 */
@RestController
@RequestMapping("/pod")
public class PodController {

	@Autowired
	private KubernetesClient kubernetesClient;

	/**
	 * 查询所有Pod
	 * 
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ArrayList<String> list(@RequestParam String namespace) {
		ArrayList<String> rtn = new ArrayList<String>();
		List<Pod> podsList = kubernetesClient.pods().inNamespace(namespace).list().getItems();
		podsList.forEach(pod -> rtn.add(pod.getMetadata().getName()));
		return rtn;
	}

	/**
	 * 创建Pod
	 * 
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@RequestParam String namespace) {
		String podName = "test1-pod-nginx";
		String imageName = "nginx:1.22.1";
		Map<String, String> selectLabels = new HashMap<>();
		selectLabels.put("app.zidingyi.name", podName);

		Pod pod = new PodBuilder().withNewMetadata().withName(podName).withLabels(selectLabels).endMetadata()
				.withNewSpec().addNewContainer().withName(podName).withImage(imageName).addNewPort()
				.withContainerPort(80).endPort().addNewPort().withContainerPort(8080).endPort().endContainer().endSpec()
				.build();

		// 在指定的命名空间中创建 Pod
		Pod pod1 = kubernetesClient.pods().inNamespace(namespace).resource(pod).create();
		return "创建成功" + pod1.getMetadata().getName();
	}

	/**
	 * yml创建pod
	 * 
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/ymlAdd", method = RequestMethod.POST)
	public String ymlAdd(@RequestParam String namespace) {
		Pod pod = kubernetesClient.pods().load(PodController.class.getResourceAsStream("/test-pod.yaml")).get();
		Pod pod1 = kubernetesClient.pods().inNamespace(namespace).resource(pod).create();
		return "创建成功" + pod1.getMetadata().getName();
	}

	/**
	 * 删除Pod
	 * 
	 * @param namespace
	 * @param podname
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public List<StatusDetails> delete(@RequestParam String namespace, @RequestParam String podname) {
		List<StatusDetails> status = kubernetesClient.pods().inNamespace(namespace).withName(podname).delete();
		return status;
	}

}