package net.xzh.elasticsearch.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.elasticsearch.common.model.CommonPage;
import net.xzh.elasticsearch.common.model.CommonResult;
import net.xzh.elasticsearch.domain.IndexDto;
import net.xzh.elasticsearch.service.IndexService;

/**
 * 索引管理
 *
 * @author zlt
 */
@RestController
@Api(tags = "索引管理api")
@RequestMapping("/admin")
public class IndexController {
    @Autowired
    private IndexService indexService;


    @ApiOperation(value = "添加索引")
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createIndex(@RequestBody IndexDto indexDto) throws IOException {
        if (indexDto.getNumberOfShards() == null) {
            indexDto.setNumberOfShards(1);
        }
        if (indexDto.getNumberOfReplicas() == null) {
            indexDto.setNumberOfReplicas(0);
        }
        indexService.create(indexDto);
        
        return CommonResult.success("操作成功");
    }
    
    /**
     * 删除索引
     */
    @ApiOperation(value = "删除索引")
    @RequestMapping(value = "/index", method = RequestMethod.DELETE)
    @ResponseBody
    public CommonResult deleteIndex(String indexName) throws IOException {
        indexService.delete(indexName);
        return CommonResult.success("操作成功");
    }

    /**
     * 索引列表
     */
    @ApiOperation(value = "索引列表")
    @RequestMapping(value = "/indices", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage> list(@RequestParam(required = false) String queryStr) throws IOException {
    	CommonPage<Map<String, String>> restPage = CommonPage.restPage(indexService.list(queryStr, ""));
        return CommonResult.success(restPage);
    }

    /**
     * 索引明细
     */
    @ApiOperation(value = "索引明细")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Map<String, Object>> showIndex(String indexName) throws IOException {
        return CommonResult.success(indexService.show(indexName));
    }
   
}
