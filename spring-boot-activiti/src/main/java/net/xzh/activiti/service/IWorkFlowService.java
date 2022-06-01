package net.xzh.activiti.service;

import java.io.InputStream;

import net.xzh.activiti.common.model.PageResultBean;
import net.xzh.activiti.model.ModelEntityVo;
import net.xzh.activiti.model.WorkFlowVo;

/**
 * 工作流的服务接口
 */
public interface IWorkFlowService {

	/**
	 * 查询所有的流程定义信息
	 * 
	 * @param vo
	 * @return
	 */
	PageResultBean<ModelEntityVo> queryProcessModel(WorkFlowVo vo);

	/**
	 * 在线设计模型
	 * 
	 * @return
	 */
	String onlineModel();

	/**
	 * 删除流程模型
	 * 
	 * @param id
	 */
	void deleteProcessModelById(Integer id);

	/**
	 * 发布模型为流程定义
	 * 
	 * @param id
	 */
	void deployModel(String id);
	
	/**
     * 查询所有的流程部署信息
     * @param vo
     * @return
     */
	PageResultBean<ModelEntityVo> queryProcessDeploy(WorkFlowVo vo);
    
    /**
     * 删除流程部署
     * @param id
     */
    void deleteProcessDeployById(Integer id);
    
    /**
     * 查看流程图片
     * @param deploymentId
     * @return
     */
    InputStream viewProcessImage(String deploymentId);

}
