package net.xzh.xss.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

/**
 * XSS过滤处理包装类
 * 对HTTP请求参数进行XSS安全过滤，防止跨站脚本攻击
 */

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }
    @Override
    public String[] getParameterValues(String name) {
        // 自定义排除字段
        if ("content".equals(name)) {
            return super.getParameterValues(name);
        }
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                escapseValues[i] = Jsoup.clean(values[i], Safelist.relaxed()).trim();
            }
            return escapseValues;
        }
        return super.getParameterValues(name);
    }
}