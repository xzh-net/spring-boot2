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

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodBuilder;
import io.fabric8.kubernetes.api.model.StatusDetails;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.k8s.common.model.CommonResult;

/**
 * 管理Pod
 * 
 * @author CR7
 *
 */
@Api(tags = "管理Pod")
@RestController
@RequestMapping("/pod")
public class PodController {

	private static final Logger logger = LoggerFactory.getLogger(PodController.class);

	@Autowired
	private KubernetesClient kubernetesClient;
	
	@ApiOperation("查询所有Pod")
	@RequestMapping(value = "/listPod", method = RequestMethod.POST)
	public CommonResult<?> listPod(@RequestParam String namespace) {
		ArrayList<String> rtn = new ArrayList<String>();
		List<Pod> podsList = kubernetesClient.pods().inNamespace(namespace).list().getItems();
		podsList.forEach(pod -> rtn.add(pod.getMetadata().getName()));
		return CommonResult.success(rtn);
	}
	
	@ApiOperation("对象实体创建Pod")
	@RequestMapping(value = "/createPod", method = RequestMethod.POST)
	public CommonResult<?> createPod(@RequestParam String namespace){
		String podName = "test1-pod-nginx";
	    String imageName = "nginx:1.22.1";
	    Map<String, String> selectLabels = new HashMap<>();
        selectLabels.put("app.zidingyi.name", podName);
        
        Pod pod = new PodBuilder()
                .withNewMetadata().withName(podName).withLabels(selectLabels).endMetadata()
                .withNewSpec()
                .addNewContainer()
                .withName(podName)
                .withImage(imageName)
                .addNewPort().withContainerPort(80).endPort()
                .addNewPort().withContainerPort(8080).endPort()
                .endContainer()
                .endSpec()
                .build();

        // 在指定的命名空间中创建 Pod
        Pod pod1 = kubernetesClient.pods().inNamespace(namespace).resource(pod).create();
		return CommonResult.success(pod1.getMetadata().getName());
	}
	
	@ApiOperation("文件方式创建Pod")
	@RequestMapping(value = "/createPod2", method = RequestMethod.POST)
	public CommonResult<?> createPod2(@RequestParam String namespace) {
		Pod pod = kubernetesClient.pods().load(PodController.class.getResourceAsStream("/test-pod.yaml")).get();
		Pod pod1 = kubernetesClient.pods().inNamespace(namespace).resource(pod).create();
		return CommonResult.success(pod1.getMetadata().getName());
	}
	
	@ApiOperation("删除Pod")
	@RequestMapping(value = "/deletePod", method = RequestMethod.POST)
	public CommonResult<?> deletePod(@RequestParam String namespace,@RequestParam String podname){
		List<StatusDetails> rtn = kubernetesClient.pods().inNamespace(namespace).withName(podname).delete();
		return CommonResult.success(rtn);
	}
	
}