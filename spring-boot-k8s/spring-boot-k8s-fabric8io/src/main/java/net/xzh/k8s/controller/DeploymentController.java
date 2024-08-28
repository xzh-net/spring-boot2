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

import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.StatusDetails;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.k8s.common.model.CommonResult;

/**
 * 管理Deployment
 * 
 * @author CR7
 *
 */
@Api(tags = "管理Deployment")
@RestController
@RequestMapping("/deploy")
public class DeploymentController {
	
	private static final Logger logger = LoggerFactory.getLogger(DeploymentController.class);

	@Autowired
	private KubernetesClient kubernetesClient;
	
	@ApiOperation("创建Deployment")
	@RequestMapping(value = "/createDeployment", method = RequestMethod.GET)
	public CommonResult<?> createDeployments(@RequestParam String namespace) {
		
		String deploymentName = "test-deploy-nginx";
	    String imageName = "nginx:1.22.1";
	    
		Map<String, String> labels = new HashMap();
        labels.put("app", "my-dep-fabric8");

        Deployment deployment = new DeploymentBuilder()
                .withNewMetadata()
                    .withName(deploymentName)
                    .withLabels(labels)
                .endMetadata()
                .withNewSpec()
                    .withReplicas(2)
                    .withNewSelector()
                        .withMatchLabels(labels)
                    .endSelector()
                    .withNewTemplate()
                        .withNewMetadata()
                            .withLabels(labels)
                        .endMetadata()
                        .withNewSpec()
                            .withContainers(
                                    new ContainerBuilder()
                                        .withName(deploymentName)
                                        .withImage(imageName)
                                        .build()
                            )
                        .endSpec()
                    .endTemplate()
                .endSpec()
                .build();
        Deployment deployment2 = kubernetesClient.apps().deployments().inNamespace(namespace).resource(deployment).create();
		return CommonResult.success(deployment2.getMetadata().getName());
	}
	
	
	@ApiOperation("删除Deployment")
	@RequestMapping(value = "/deleteDeployment", method = RequestMethod.GET)
	public CommonResult<?> deleteDeployment(@RequestParam String namespace,@RequestParam String deployName) {
		List<StatusDetails> rtn = kubernetesClient.apps().deployments().inNamespace(namespace).withName(deployName).delete();
		return CommonResult.success(rtn);
	}
	
	@ApiOperation("查询所有Deployment")
	@RequestMapping(value = "/listDeployment", method = RequestMethod.GET)
	public CommonResult<?> listDeployment(@RequestParam String namespace) {
		ArrayList<String> list = new ArrayList<String>();
		List<Deployment> deployments = kubernetesClient.apps().deployments().inNamespace(namespace).list().getItems();
		deployments.forEach(deployment -> list.add(deployment.getMetadata().getName()));
		return CommonResult.success(list);
	}


}