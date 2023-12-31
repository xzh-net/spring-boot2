package net.xzh.redis.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.redis.common.model.CommonResult;
import net.xzh.redis.model.PmsBrand;
import net.xzh.redis.service.PmsBrandService;
import net.xzh.redis.service.RedisService;

/**
 * redis测试Controller
 * Created by macro on 2020/3/3.
 */
@Api(tags = "基础数据读写")
@RestController
@RequestMapping("/redis")
public class RedisController {
	
    @Autowired
    private RedisService redisService;
    @Autowired
    private PmsBrandService brandService;

    @ApiOperation("对象读写")
    @RequestMapping(value = "/simpleTest", method = RequestMethod.GET)
    public CommonResult<PmsBrand> simpleTest() {
        List<PmsBrand> brandList = brandService.list(1, 5);
        PmsBrand brand = brandList.get(0);
        String key = "redis:simple:" + brand.getId();
        redisService.set(key, brand);
        PmsBrand cacheBrand = (PmsBrand) redisService.get(key);
        return CommonResult.success(cacheBrand);
    }

    @ApiOperation("Hash缓存")
    @RequestMapping(value = "/hashTest", method = RequestMethod.GET)
    public CommonResult<PmsBrand> hashTest() {
        List<PmsBrand> brandList = brandService.list(1, 5);
        PmsBrand brand = brandList.get(0);
        String key = "redis:hash:" + brand.getId();
        Map<String, Object> value = BeanUtil.beanToMap(brand);
        redisService.hSetAll(key, value);
        Map<Object, Object> cacheValue = redisService.hGetAll(key);
        PmsBrand cacheBrand = BeanUtil.mapToBean(cacheValue, PmsBrand.class, true);
        return CommonResult.success(cacheBrand);
    }

    @ApiOperation("Set缓存")
    @RequestMapping(value = "/setTest", method = RequestMethod.GET)
    public CommonResult<Set<Object>> setTest() {
        List<PmsBrand> brandList = brandService.list(1, 5);
        String key = "redis:set:all";
        redisService.sAdd(key, (Object[]) ArrayUtil.toArray(brandList, PmsBrand.class));
        redisService.sRemove(key, brandList.get(0));
        Set<Object> cachedBrandList = redisService.sMembers(key);
        return CommonResult.success(cachedBrandList);
    }

    @ApiOperation("List缓存")
    @RequestMapping(value = "/listTest", method = RequestMethod.GET)
    public CommonResult<List<Object>> listTest() {
        List<PmsBrand> brandList = brandService.list(1, 5);
        String key = "redis:list:all";
        redisService.lPushAll(key, (Object[]) ArrayUtil.toArray(brandList, PmsBrand.class));
        redisService.lRemove(key, 1, brandList.get(0));
        List<Object> cachedBrandList = redisService.lRange(key, 0, 3);
        return CommonResult.success(cachedBrandList);
    }
    
}
