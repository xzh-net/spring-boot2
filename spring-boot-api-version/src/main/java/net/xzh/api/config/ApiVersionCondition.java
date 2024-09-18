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