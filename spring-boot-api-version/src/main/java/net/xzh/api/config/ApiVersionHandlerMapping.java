package net.xzh.api.config;

import java.lang.reflect.Method;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import net.xzh.api.annotation.ApiVersion;

/**
 * 自定义匹配处理器
 * @author CR7
 *
 */
public class ApiVersionHandlerMapping extends RequestMappingHandlerMapping {
    private static final String VERSION_FLAG = "{version}";

    /**
     * 扫描类上的 @ApiVersion
     */
    private static RequestCondition<ApiVersionCondition> createCondition(Class<?> clazz) {
        RequestMapping classRequestMapping = clazz.getAnnotation(RequestMapping.class);
        if (classRequestMapping == null) {
            return null;
        }
        StringBuilder mappingUrlBuilder = new StringBuilder();
        if (classRequestMapping.value().length > 0) {
            mappingUrlBuilder.append(classRequestMapping.value()[0]);
        }
        String mappingUrl = mappingUrlBuilder.toString();
        if (!mappingUrl.contains(VERSION_FLAG)) {
            return null;
        }
        ApiVersion apiVersion = clazz.getAnnotation(ApiVersion.class);
        return apiVersion == null ? new ApiVersionCondition(1) : new ApiVersionCondition(apiVersion.value());
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
        // 修复：检查方法上的 @ApiVersion 注解
        return createCondition(method);
    }

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        return createCondition(handlerType);
    }
}