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

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.fabric8.kubernetes.api.model.ServicePortBuilder;
import io.fabric8.kubernetes.api.model.StatusDetails;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.k8s.common.model.CommonResult;

/**
 * 管理Service
 * 
 * @author CR7
 *
 */
@Api(tags = "管理Service")
@RestController
@RequestMapping("/svc")
public class ServiceController {

	private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

	@Autowired
	private KubernetesClient kubernetesClient;
	
	@ApiOperation("查询所有Service")
	@RequestMapping(value = "/listService", method = RequestMethod.GET)
	public CommonResult<?> listService(@RequestParam String namespace) {
		ArrayList<String> list = new ArrayList<String>();
		List<Service> serviceList = kubernetesClient.services().inNamespace(namespace).list().getItems();
		serviceList.forEach(svc -> list.add(svc.getMetadata().getName()));
		return CommonResult.success(list);
	}

	@ApiOperation("创建NodePort类型Service")
	@RequestMapping(value = "/createService", method = RequestMethod.GET)
	public CommonResult<?> createService(@RequestParam String namespace) {
		
		Map<String, String> selectLabels = new HashMap<>();
		String serviceName = "svc-" + System.currentTimeMillis();
		selectLabels.put("app.zidingyi.name", "test1-pod-nginx"); // 此处需要对应pod中selectLabels中内容
		
        Service service = new ServiceBuilder()
                .withNewMetadata()
                    .withName(serviceName)
                .withLabels(selectLabels) // 代理具有labels标签的pod
                .endMetadata()
                .withNewSpec()
                    .withSelector(selectLabels)
                    .withPorts(new ServicePortBuilder()
                                    .withPort(8000) // 集群内部监听的端口,service本身端口
                                    .withNodePort(30880) //对外网暴漏端口
                                    .withNewTargetPort(80)  // 流量转发的目标容器端口
                                    .build())
                    .withType("NodePort")
                .endSpec()
                .build();
        Service svc = kubernetesClient.services().inNamespace(namespace).resource(service).create();
		return CommonResult.success(svc.getMetadata().getName());
	}

	@ApiOperation("删除Service")
	@RequestMapping(value = "/deleteService", method = RequestMethod.GET)
	public CommonResult<?> deleteService(@RequestParam String namespace, @RequestParam String svcname) {
		List<StatusDetails> rtn = kubernetesClient.services().inNamespace(namespace).withName(svcname).delete();
		return CommonResult.success(rtn);
	}

}