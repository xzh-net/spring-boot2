# Rest API版本控制

## Api 版本控制方式

1. 域名区分管理，即不同的版本使用不同的域名，v1.api.test.com，v2.api.test.com
2. 请求url 路径区分，在同一个域名下使用不同的url路径，test.com/api/v1/，test.com/api/v2
3. 请求参数区分，在同一url路径下，增加version=v1或v2 等，然后根据不同的版本，选择执行不同的方法。

> 因为url 路径包含了通配符，所以未整合Knife4j，本次采用第2种方式使用Spring Boot来实现。
>



## 代码场景

1. 【V1】包含保存和查询功能
2. 【V2】增强了保存功能， 同时添加了删除功能
3. 调用【V2】的查询实则继承了【V1】的查询功能。高版本可以访问低版本接口，低版本无法访问高版本接口




## 实现方案

### 1. 新增自定义注解

```java
/**
 * 自定义版本注解
 * @author xzh
 * @date 2024/9/18
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion {

    /**
     * 标识版本号，从1开始
     */
    int value() default 1;

}

```



### 2. 定义匹配逻辑

```java
package net.xzh.api.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.condition.RequestCondition;
/**
 * 自定义匹配逻辑
 * @author CR7
 *
 */
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

	/**
	 * 接口路径中的版本号前缀，如: api/v[1-n]/test
	 */
	private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile(".*v(\\d+).*");

	private int apiVersion;

	ApiVersionCondition(int apiVersion) {
		this.apiVersion = apiVersion;
	}

	private int getApiVersion() {
		return apiVersion;
	}

	@Override
	public ApiVersionCondition combine(ApiVersionCondition apiVersionCondition) {
		// 最近优先原则，方法定义的 @ApiVersion > 类定义的 @ApiVersion
		return new ApiVersionCondition(apiVersionCondition.getApiVersion());
	}

	@Override
	public ApiVersionCondition getMatchingCondition(HttpServletRequest httpServletRequest) {
		Matcher m = VERSION_PREFIX_PATTERN.matcher(httpServletRequest.getRequestURI());
		if (m.find()) {
			// 获得符合匹配条件的ApiVersionCondition
			Integer version = Integer.valueOf(m.group(1));
			if (version >= this.apiVersion) {
				return this;
			}
		}
		return null;
	}

	@Override
	public int compareTo(ApiVersionCondition apiVersionCondition, HttpServletRequest httpServletRequest) {
		// 当出现多个符合匹配条件的ApiVersionCondition，优先匹配版本号较大的
		return apiVersionCondition.getApiVersion() - this.apiVersion;
	}
}
```



### 3. 自定义匹配处理

```java
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
```



### 4. 增加配置类WebMvcRegistrationsConfig

```java
package net.xzh.api.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class WebMvcRegistrationsConfig implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new ApiRequestMappingHandlerMapping();
    }
}

```



## 访问地址

http://127.0.0.1:8080/api/v1/order/save/123
http://127.0.0.1:8080/api/v2/order/save/123
http://127.0.0.1:8080/api/v2/order/search/123
http://127.0.0.1:8080/api/v2/order/delete/123