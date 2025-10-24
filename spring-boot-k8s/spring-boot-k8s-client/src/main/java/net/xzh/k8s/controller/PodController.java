package net.xzh.k8s.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1ContainerPort;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodBuilder;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.openapi.models.V1Status;
import io.kubernetes.client.util.Yaml;
import io.swagger.annotations.Api;

/**
 * 用于管理 Kubernetes 核心 API 对象，如 Pod、Service、Namespace、Node 和 PersistentVolume
 * 
 * @author CR7
 *
 */
@Api(tags = "CoreV1Api管理Pod")
@RestController
@RequestMapping("/pod")
public class PodController {

	private static final Logger logger = LoggerFactory.getLogger(PodController.class);

	/**
	 * 查询所有Pod
	 * 
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ArrayList<String> list(@RequestParam String namespace) {
		CoreV1Api apiInstance = new CoreV1Api();
		ArrayList<String> rtn = new ArrayList<String>();
		try {
			V1PodList podList = apiInstance.listNamespacedPod(namespace, null, null, null, null, null, null, null, null,
					null, null);
			podList.getItems().forEach(list -> rtn.add(list.getMetadata().getName()));
		} catch (ApiException e) {
			logger.error("Exception when calling CoreV1Api#listNamespacedPod");
			e.printStackTrace();
		}
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
		CoreV1Api apiInstance = new CoreV1Api();
		String podName = "test1-pod-nginx";
		String imageName = "nginx:1.22.1";
		Map<String, String> selectLabels = new HashMap<>();
		selectLabels.put("app.zidingyi.name", podName);

		// 容器暴漏端口
		List<V1ContainerPort> portList = Arrays.asList(new V1ContainerPort().containerPort(80).protocol("TCP"),
				new V1ContainerPort().containerPort(8080).protocol("TCP"));

		V1Pod v1Pod = new V1PodBuilder().withNewMetadata().withName(podName).withLabels(selectLabels).endMetadata()
				.withNewSpec().addNewContainer().withName(podName).withImage(imageName).withPorts(portList)
				.endContainer().endSpec().build();
		V1Pod v1Pod1 = null;
		try {
			v1Pod1 = apiInstance.createNamespacedPod(namespace, v1Pod, null, null, null);
		} catch (ApiException e) {
			logger.error("Exception when calling CoreV1Api#createNamespacedPod");
			e.printStackTrace();
		}
		return "创建成功" + v1Pod1.getMetadata().getName();
	}

	/**
	 * yml创建pod
	 * 
	 * @param namespace
	 * @return
	 */
	@RequestMapping(value = "/ymlAdd", method = RequestMethod.POST)
	public String ymlAdd(@RequestParam String namespace) {
		CoreV1Api apiInstance = new CoreV1Api();
		// 加载配置文件
		V1Pod v1Pod = null;
		try {
			v1Pod = (V1Pod) Yaml.load(new ClassPathResource("test-pod.yaml").getFile());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 预览
		System.out.println(Yaml.dump(v1Pod));
		V1Pod v1Pod1 = null;
		try {
			v1Pod1 = apiInstance.createNamespacedPod(namespace, v1Pod, "false", null, null);
		} catch (ApiException e) {
			logger.error("Exception when calling CoreV1Api#createNamespacedPod");
			e.printStackTrace();
		}
		return "创建成功" + v1Pod1.getMetadata().getName();
	}

	/**
	 * 删除Pod
	 * 
	 * @param namespace
	 * @param podname
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(@RequestParam String namespace, @RequestParam String podname) {
		CoreV1Api apiInstance = new CoreV1Api();
		V1Status rtn = null;
		try {
			rtn = apiInstance.deleteCollectionNamespacedPod(namespace, podname, null, null, null, null, null, null,
					null, null, null, null, null, null);
		} catch (ApiException e) {
			logger.error("Exception when calling CoreV1Api#deleteCollectionNamespacedPod");
			e.printStackTrace();
		}
		return "刪除成功" + rtn;
	}

}