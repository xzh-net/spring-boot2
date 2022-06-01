package net.xzh.activiti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.xzh.activiti.common.model.PageResultBean;
import net.xzh.activiti.common.model.ResultBean;
import net.xzh.activiti.model.ModelEntityVo;
import net.xzh.activiti.model.WorkFlowVo;
import net.xzh.activiti.service.IWorkFlowService;

/**
 * 流程模型
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/workflow")
public class ProcessDefinitionController {
	@Autowired
	private IWorkFlowService workFlowService;

	/**
	 * 跳转到模型管理界面
	 */
	@RequestMapping("list")
	public String toWorkFlowModel() {
		return "workflow/process-list";
	}

	/**
	 * 加载模型信息
	 */
	@ResponseBody
	@RequestMapping("loadAllProcessDefinition")
	public PageResultBean<ModelEntityVo> loadAllProcessDefinition(WorkFlowVo vo) {
		return workFlowService.queryProcessModel(vo);
	}

	/**
	 * 在线设计模型
	 */
	@ResponseBody
	@RequestMapping("createOnlineModel")
	public ResultBean<String> onlineModel() {
		String id = this.workFlowService.onlineModel();
		if (id.equals("-1")) {
			ResultBean.failed();
		}
		return ResultBean.success(id);
	}

	/**
	 * 发布模型为流程定义
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deployModel")
	public ResultBean<?> deployModel(String id) {
		this.workFlowService.deployModel(id);
		return ResultBean.success(null);
	}

	/**
	 * 删除模型
	 */
	@ResponseBody
	@RequestMapping("deleteProcessModel")
	public ResultBean<?> deleteProcessModel(Integer id) {
		try {
			this.workFlowService.deleteProcessModelById(id);
			return ResultBean.success(null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultBean.failed();
		}
	}

	/**
	 * 批量删除模型
	 */
	@ResponseBody
	@RequestMapping("batchdeleteProcessModel")
	public ResultBean<?> batchdeleteProcessModel(WorkFlowVo vo) {
		try {
			for (String id : vo.getIds()) {
				this.deleteProcessModel(Integer.parseInt(id));
			}
			return ResultBean.success(null);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultBean.failed();
		}
	}
}