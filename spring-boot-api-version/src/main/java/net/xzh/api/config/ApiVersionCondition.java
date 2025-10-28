package net.xzh.api.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

/**
 * 版本匹配条件 - 支持URL路径版本和参数版本
 * @author CR7
 *
 */
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

    /**
     * 接口路径中的版本号前缀，如: api/v[1-n]/test
     */
    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile(".*v(\\d+).*");
    
    /**
     * 参数版本名称
     */
    private final static String PARAM_VERSION_NAME = "version";
    
    /**
     * 请求头版本名称
     */
    private final static String HEADER_VERSION_NAME = "X-API-Version";

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
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        // 1. 首先尝试从URL路径中提取版本号
        Integer urlVersion = extractVersionFromUrl(request.getRequestURI());
        if (urlVersion != null && urlVersion >= this.apiVersion) {
            return this;
        }
        
        // 2. 如果URL中没有版本，尝试从查询参数中提取
        Integer paramVersion = extractVersionFromParameter(request);
        if (paramVersion != null && paramVersion >= this.apiVersion) {
            return this;
        }
        
        // 3. 最后尝试从请求头中提取
        Integer headerVersion = extractVersionFromHeader(request);
        if (headerVersion != null && headerVersion >= this.apiVersion) {
            return this;
        }
        
        return null;
    }

    /**
     * 从URL路径中提取版本号
     */
    private Integer extractVersionFromUrl(String requestUri) {
        Matcher m = VERSION_PREFIX_PATTERN.matcher(requestUri);
        if (m.find()) {
            try {
                return Integer.valueOf(m.group(1));
            } catch (NumberFormatException e) {
                // 版本号格式错误
            }
        }
        return null;
    }

    /**
     * 从查询参数中提取版本号
     */
    private Integer extractVersionFromParameter(HttpServletRequest request) {
        String versionParam = request.getParameter(PARAM_VERSION_NAME);
        if (versionParam != null) {
            try {
                return Integer.valueOf(versionParam);
            } catch (NumberFormatException e) {
                // 版本号格式错误
            }
        }
        return null;
    }

    /**
     * 从请求头中提取版本号
     */
    private Integer extractVersionFromHeader(HttpServletRequest request) {
        String versionHeader = request.getHeader(HEADER_VERSION_NAME);
        if (versionHeader != null) {
            try {
                return Integer.valueOf(versionHeader);
            } catch (NumberFormatException e) {
                // 版本号格式错误
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