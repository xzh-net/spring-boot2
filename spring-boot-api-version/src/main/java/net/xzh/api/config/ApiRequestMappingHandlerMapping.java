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
public class ApiRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
	private static final String VERSION_FLAG = "{version}";

	/**
	 * 扫描类上的 @ApiVersion
	 * @param clazz
	 * @return
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

	@Override
	protected RequestCondition<?> getCustomMethodCondition(Method method) {
		return createCondition(method.getClass());
	}

	@Override
	protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
		return createCondition(handlerType);
	}
}