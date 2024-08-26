package net.xzh.k8s.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.io.resource.ClassPathResource;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1ContainerPort;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodBuilder;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.openapi.models.V1Status;
import io.kubernetes.client.util.Yaml;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.k8s.common.model.CommonResult;

/**
 * 用于管理 Kubernetes 核心 API 对象，如 Pod、Service、Namespace、Node 和 PersistentVolume
 * 
 * @author CR7
 *
 */
@Api(tags = "CoreV1Api管理Pod")
@RestController
public class PodController {

	private static final Logger logger = LoggerFactory.getLogger(PodController.class);

	@ApiOperation("查询所有Pod")
	@RequestMapping(value = "/listPods", method = RequestMethod.GET)
	public CommonResult<?> listPods() {
		CoreV1Api apiInstance = new CoreV1Api();
		ArrayList<String> list = new ArrayList<String>();
		try {
			V1PodList podList = apiInstance.listPodForAllNamespaces(null, null, null, null, null, null, null, null,
					null, null);
			podList.getItems().forEach(pod -> list.add(pod.getMetadata().getName()));
		} catch (ApiException e) {
			logger.error("Exception when calling CoreV1Api#listPodForAllNamespaces");
			e.printStackTrace();
		}
		return CommonResult.success(list);
	}
	
	@ApiOperation("对象实体创建Pod")
	@RequestMapping(value = "/createPod", method = RequestMethod.POST)
	public CommonResult<?> createPod(@RequestParam String namespace) throws IOException {
		CoreV1Api apiInstance = new CoreV1Api();
		String podName = "test1-pod-nginx";
	    String imageName = "nginx:1.22.1";
		
		// 容器暴漏端口
		List<V1ContainerPort> portList = Arrays.asList(new V1ContainerPort().containerPort(80).protocol("TCP"),
				new V1ContainerPort().containerPort(8080).protocol("TCP"));
        
		V1Pod v1Pod =
		        new V1PodBuilder()
		            .withNewMetadata()
		            .withName(podName)
		            .endMetadata()
		            .withNewSpec()
		            .addNewContainer()
		            .withName(podName)
		            .withImage(imageName)
		            .withPorts(portList)
		            .endContainer()
		            .endSpec()
		            .build();
		V1Pod v1Pod1 = null;
		try {
			v1Pod1 = apiInstance.createNamespacedPod(namespace, v1Pod, null, null, null);
		} catch (ApiException e) {
			logger.error("Exception when calling CoreV1Api#createNamespacedPod");
			e.printStackTrace();
		}
		return CommonResult.success(v1Pod1.getMetadata().getName());
	}
	
	@ApiOperation("文件方式创建Pod")
	@RequestMapping(value = "/createPod2", method = RequestMethod.POST)
	public CommonResult<?> createPod2(@RequestParam String namespace) throws IOException {
		CoreV1Api apiInstance = new CoreV1Api();
		//加载配置文件
		V1Pod v1Pod = (V1Pod) Yaml.load(new ClassPathResource("test-pod.yaml").getFile());
	    //预览
	    System.out.println(Yaml.dump(v1Pod));   
	    V1Pod v1Pod1 = null;
		try {
			v1Pod1 = apiInstance.createNamespacedPod(namespace, v1Pod, "false", null, null);
		} catch (ApiException e) {
			logger.error("Exception when calling CoreV1Api#createNamespacedPod");
			e.printStackTrace();
		}
		return CommonResult.success(v1Pod1.getMetadata().getName());
	}
	
	@ApiOperation("删除Pod")
	@RequestMapping(value = "/deletePod", method = RequestMethod.POST)
	public CommonResult<?> deletePod(@RequestParam String namespace,@RequestParam String podname) throws IOException {
		CoreV1Api apiInstance = new CoreV1Api();
		V1Status rtn = null;
		try {
			rtn=apiInstance.deleteCollectionNamespacedPod(namespace, podname, null, null, null, null, null, null, null, null, null, null, null, null);
		} catch (ApiException e) {
			logger.error("Exception when calling CoreV1Api#deleteCollectionNamespacedPod");
			e.printStackTrace();
		}
		return CommonResult.success(rtn);
	}
	
}