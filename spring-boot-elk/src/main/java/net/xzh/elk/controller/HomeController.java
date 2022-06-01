package net.xzh.elk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.elk.common.api.CommonResult;
import net.xzh.elk.model.UmsAdmin;

/**
 * 品牌管理Controller
 * Created by macro on 2019/4/19.
 */
@Api(tags = "日志测试")
@Controller
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    private static Map<Long, UmsAdmin> map = new HashMap<>();
    
    
    @ApiOperation("获取所有列表")
    @RequestMapping(value = "listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<UmsAdmin>> getBrandList() {
    	List<UmsAdmin> list=new ArrayList<UmsAdmin>();
    	map.forEach((key, value) -> {
    		list.add(value);
        });
        return CommonResult.success(list);
    }

    @ApiOperation("添加用户")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createUser(@RequestBody UmsAdmin umsAdmin) {
        return CommonResult.success(map.put(umsAdmin.getId(), umsAdmin));
    }

    @ApiOperation("删除指定id的用户")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult deleteUser(@PathVariable("id") Long id) {
        if (map.containsKey(id)){
        	 map.remove(id);
        	 LOGGER.debug("deleteUser success :id={}", id);
        	 return CommonResult.success(null);
        }else {
        	 LOGGER.debug("deleteUser failed :id={}", id);
        	 return CommonResult.failed("操作失败");
        }
    }
           
}
