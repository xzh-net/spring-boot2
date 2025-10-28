package net.xzh.api.config;

import java.lang.reflect.Method;

import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import net.xzh.api.annotation.ApiVersion;

/**
 * 自定义匹配处理器 - 支持参数版本
 * @author CR7
 *
 */
public class ApiVersionHandlerMapping extends RequestMappingHandlerMapping {

    /**
     * 扫描类上的 @ApiVersion
     */
    private static RequestCondition<ApiVersionCondition> createCondition(Class<?> clazz) {
        ApiVersion apiVersion = clazz.getAnnotation(ApiVersion.class);
        // 不再检查URL中是否包含{version}，直接根据注解创建条件
        return apiVersion == null ? null : new ApiVersionCondition(apiVersion.value());
    }

    /**
     * 扫描方法上的 @ApiVersion
     */
    private static RequestCondition<ApiVersionCondition> createCondition(Method method) {
        // 检查方法上是否有 @ApiVersion 注解
        ApiVersion methodApiVersion = method.getAnnotation(ApiVersion.class);
        if (methodApiVersion != null) {
            // 方法级别版本注解优先
            return new ApiVersionCondition(methodApiVersion.value());
        }
        return null;
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        return createCondition(method);
    }

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        return createCondition(handlerType);
    }
}