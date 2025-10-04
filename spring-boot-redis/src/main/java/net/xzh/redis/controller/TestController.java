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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.xzh.redis.common.model.CommonResult;
import net.xzh.redis.model.PmsBrand;
import net.xzh.redis.service.PmsBrandService;
import net.xzh.redis.service.RedisService;

/**
 * 测试
 */

@Tag(name = "基本数据类型测试", description = "基本数据类型测试")
@RestController
@RequestMapping("/redis")
public class TestController {
	
    @Autowired
    private RedisService redisService;
    @Autowired
    private PmsBrandService brandService;

    @Operation(summary = "对象类型", description = "对象类型")
    @RequestMapping(value = "/simpleTest", method = RequestMethod.GET)
    public CommonResult<PmsBrand> simpleTest() {
        List<PmsBrand> brandList = brandService.listAllBrand();
        PmsBrand brand = brandList.get(0);
        String key = "redis:simple:" + brand.getId();
        redisService.set(key, brand);
        PmsBrand cacheBrand = (PmsBrand) redisService.get(key);
        return CommonResult.success(cacheBrand);
    }

    @Operation(summary = "Hash结构", description = "Hash结构")
    @RequestMapping(value = "/hashTest", method = RequestMethod.GET)
    public CommonResult<PmsBrand> hashTest() {
        List<PmsBrand> brandList = brandService.listAllBrand();
        PmsBrand brand = brandList.get(0);
        String key = "redis:hash:" + brand.getId();
        Map<String, Object> value = BeanUtil.beanToMap(brand);
        redisService.hSetAll(key, value);
        Map<Object, Object> cacheValue = redisService.hGetAll(key);
        PmsBrand cacheBrand=new PmsBrand();
        BeanUtil.fillBeanWithMap(cacheValue, cacheBrand, false);
        return CommonResult.success(cacheBrand);
    }

    @Operation(summary = "Set结构", description = "Set结构")
    @RequestMapping(value = "/setTest", method = RequestMethod.GET)
    public CommonResult<Set<Object>> setTest() {
        List<PmsBrand> brandList = brandService.listAllBrand();
        String key = "redis:set:all";
        redisService.sAdd(key, (Object[]) ArrayUtil.toArray(brandList, PmsBrand.class));
        redisService.sRemove(key, brandList.get(0));
        Set<Object> cachedBrandList = redisService.sMembers(key);
        return CommonResult.success(cachedBrandList);
    }

    @Operation(summary = "List结构", description = "List结构")
    @RequestMapping(value = "/listTest", method = RequestMethod.GET)
    public CommonResult<List<Object>> listTest() {
        List<PmsBrand> brandList = brandService.listAllBrand();
        String key = "redis:list:all";
        redisService.lPushAll(key, (Object[]) ArrayUtil.toArray(brandList, PmsBrand.class));
        redisService.lRemove(key, 1, brandList.get(0));
        List<Object> cachedBrandList = redisService.lRange(key, 0, 3);
        return CommonResult.success(cachedBrandList);
    }
    
}
