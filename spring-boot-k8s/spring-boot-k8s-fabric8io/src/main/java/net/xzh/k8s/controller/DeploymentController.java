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

import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.StatusDetails;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;

/**
 * 管理Deployment
 * 
 * @author CR7
 *
 */
@RestController
@RequestMapping("/deploy")
public class DeploymentController {

	@Autowired
	private KubernetesClient kubernetesClient;

	/**
	 * 创建Deployment
	 * 
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(@RequestParam String namespace) {

		String deploymentName = "test-deploy-nginx";
		String imageName = "nginx:1.22.1";

		Map<String, String> labels = new HashMap<String, String>();
		labels.put("app", "my-dep-fabric8");

		Deployment deployment = new DeploymentBuilder().withNewMetadata().withName(deploymentName).withLabels(labels)
				.endMetadata().withNewSpec().withReplicas(2).withNewSelector().withMatchLabels(labels).endSelector()
				.withNewTemplate().withNewMetadata().withLabels(labels).endMetadata().withNewSpec()
				.withContainers(new ContainerBuilder().withName(deploymentName).withImage(imageName).build()).endSpec()
				.endTemplate().endSpec().build();
		Deployment deployment2 = kubernetesClient.apps().deployments().inNamespace(namespace).resource(deployment)
				.create();
		return "创建成功：" + deployment2.getMetadata().getName();
	}

	/**
	 * 删除Deployment
	 * 
	 * @param namespace
	 * @param deployName
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public List<StatusDetails> delete(@RequestParam String namespace, @RequestParam String deployName) {
		List<StatusDetails> status = kubernetesClient.apps().deployments().inNamespace(namespace).withName(deployName)
				.delete();
		return status;
	}

	/**
	 * 查询所有Deployment
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ArrayList<String> list(@RequestParam String namespace) {
		ArrayList<String> list = new ArrayList<String>();
		List<Deployment> deployments = kubernetesClient.apps().deployments().inNamespace(namespace).list().getItems();
		deployments.forEach(deployment -> list.add(deployment.getMetadata().getName()));
		return list;
	}

}